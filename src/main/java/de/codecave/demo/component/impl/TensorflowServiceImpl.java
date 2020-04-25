package de.codecave.demo.component.impl;

import com.google.common.base.Preconditions;
import de.codecave.demo.component.TensorflowService;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Tensor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
public class TensorflowServiceImpl implements TensorflowService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TensorflowServiceImpl.class);

    // note: do not change
    private static final int BATCH_SIZE_ONE = 1;

    @Value("${tensorflow.model_dir}")
    private String tensorflowModelDir;

    private SavedModelBundle modelBundle;

    @PostConstruct
    public void loadAndConfigure() {
        LOGGER.info("Loading Tensorflow model from directory {}", tensorflowModelDir);
        final Path modelDir = Paths.get(tensorflowModelDir);
        Preconditions.checkState(Files.isDirectory(modelDir), "Tensorflow model missing - see README");
        modelBundle = SavedModelBundle.load(modelDir.toAbsolutePath().toString(), "serve");
    }

    @PreDestroy
    public void shutdownTensorflow() {
        // also closes session
        modelBundle.close();
        modelBundle = null;
    }

    /**
     * - input shape INT32 tensor with shape [1, 20]
     * - input vector [225, 1113, 186, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
     * - output shape FLOAT tensor with shape [1, 10]
     * - output vector [0.02423917, 0.07196671, 8.999937E-4, 0.011337514, 0.0049416884,
     *                  0.84558976, 0.026367448, 6.4031663E-4, 0.0064433725, 0.0075740833]
     */
    @Override
    public float[] predictSingleTensorflow(final int[] inputTokens, final int nCategories) {
        final StopWatch stopWatch = StopWatch.createStarted();

        final int nFeatures = 20;

        Preconditions.checkState(inputTokens.length == nFeatures);

        final Tensor<Integer> inputTensor = Tensor.create(
                new long[]{BATCH_SIZE_ONE, nFeatures},
                vectorInt(nFeatures, inputTokens));

        final Tensor<?> resultTensor =
                modelBundle.session().runner()
                        .feed("serving_default_input_1", inputTensor)
                        .fetch("StatefulPartitionedCall") // output layer
                        .run()
                        .get(0); // first output

        final float[][] resultArray = resultTensor.copyTo(new float[BATCH_SIZE_ONE][nCategories]);

        // assuming batch size 1
        Preconditions.checkState(resultArray.length == 1);
        final float[] result = resultArray[0];

        LOGGER.info("Tensorflow prediction completed in {}us ...\n" +
                        "Tensorflow details:\n" +
                        "- input shape {}\n" +
                        "- input vector {}\n" +
                        "- output shape {}\n" +
                        "- output vector {}",
                stopWatch.getTime(TimeUnit.MICROSECONDS),
                inputTensor,
                Arrays.toString(inputTokens),
                resultTensor,
                Arrays.toString(resultArray[0]));

        return result;
    }


    private static IntBuffer vectorInt(final int nFeatures, final int[] data) {
        final IntBuffer matrixData = IntBuffer.allocate(nFeatures);
        for (int i = 0; i < nFeatures; i++) {
            matrixData.put(data[i]);
        }
        matrixData.rewind();
        return matrixData;
    }

}
