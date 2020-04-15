package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Floats;
import de.codecave.demo.component.PredictionService;
import de.codecave.demo.component.TextPreprocessor;
import one.util.streamex.EntryStream;
import org.apache.commons.lang3.time.StopWatch;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TensorflowPredictionServiceImpl implements PredictionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TensorflowPredictionServiceImpl.class);

    final private List<String> categories = Lists.newArrayList(
        "BLACK VOICES", "COMEDY", "ENTERTAINMENT", "MEDIA", "POLITICS",
            "QUEER VOICES", "SPORTS", "WEIRD NEWS", "WOMEN", "WORLD NEWS");

    @Autowired
    private TextPreprocessor textPreprocessor;

    private SavedModelBundle modelBundle;

    @PostConstruct
    public void loadAndConfigure() {
        modelBundle = SavedModelBundle.load(
                "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model/tensorflow2/tensorflow", "serve");
    }

    @PreDestroy
    public void shutdownTensorflow() {
        // also closes session
        modelBundle.close();
        modelBundle = null;
    }

    @Override
    public Map<Float, String> predictCategories(final String newsLine) {

        final StopWatch stopWatch = StopWatch.createStarted();

        int batchSize = 1;
        int maxFeaturesAmount = 20;
        int nCategories = 10;

        final int[] inputTokens = textPreprocessor.pipeline(newsLine);

        LOGGER.debug("Tokens after preprocessing: {}", Arrays.toString(inputTokens));

        final Tensor<Integer> inputTensor = Tensor.create(new long[]{batchSize, maxFeaturesAmount}, matrixInt(batchSize, maxFeaturesAmount, inputTokens));

        final Tensor<?> resultTensor =
            modelBundle.session().runner()
                .feed("serving_default_tobias", inputTensor)
                .fetch("StatefulPartitionedCall")
                .run()
            .get(0);

        final float[][] resultArray = resultTensor.copyTo(new float[batchSize][nCategories]);

        Preconditions.checkState(resultArray.length == batchSize);

        // assuming batch size 1
        final float[] result = resultArray[0];

        LOGGER.info("Prediction completed in {}ms for news line <{}> got vector {}",
                stopWatch.getTime(TimeUnit.MILLISECONDS), newsLine, Arrays.toString(resultArray[0]));

        return
            EntryStream.zip(Floats.asList(result), categories)
            .toImmutableMap();
    }

    @Override
    public String findMaxCategory(final Map<Float, String> predictions) {
        return
            EntryStream.of(predictions)
                .maxByDouble(entry -> entry.getKey())
                .orElseThrow(() -> new IllegalStateException("stream must not be empty"))
                .getValue();
    }

    @Override
    public boolean confidence(final Map<Float, String> predictions) {

        final long strongCount = EntryStream.of(predictions)
                .keys()
                .filter(pred -> pred > 0.7)
                .count();

        final long weakCount = EntryStream.of(predictions)
                .keys()
                .filter(pred -> pred < 0.3)
                .count();

        return strongCount == 1 && weakCount == predictions.size() - 1;
    }


    private IntBuffer matrixInt(int batchSize, int maxFeaturesAmount, int[] data) {
        final IntBuffer matrixData = IntBuffer.allocate(batchSize * maxFeaturesAmount);
        for (int i=0;i <batchSize * maxFeaturesAmount; i++) {
            matrixData.put(data[i]);
        }
        matrixData.rewind();
        return matrixData;
    }
}
