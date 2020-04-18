package de.codecave.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.codecave.demo.component.ModelMetadata;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

public class TensorflowTest {

    @Test
    void singleRun() {

        final SavedModelBundle modelBundle = SavedModelBundle.load(
                "../model/tensorflow3/tensorflow", "serve");

        final List<String> categories = modelMetadata().getClasses();

        try (Session session = modelBundle.session()) {

            final Session.Runner runner = session.runner();

            int batchSize = 1;
            int maxFeaturesAmount = 20;
            int nCategories = 10;

            // ferrel molli shannon cover royal wed cord tish
            final int[] inputTravelApps = {248, 58, 47, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            final Tensor<Integer> padTensor = Tensor.create(new long[]{1, maxFeaturesAmount}, matrixInt(1, maxFeaturesAmount, inputTravelApps));

            final Tensor<?> resultTensor =
                    runner.feed("serving_default_input_1", padTensor)
//                    .feed("x_aux", auxTensor)
                            .fetch("StatefulPartitionedCall")
                            .run()
                            .get(0);


//            saved_model_cli show --dir .
//            saved_model_cli show --dir . --tag_set serve
//            saved_model_cli show --dir . --tag_set serve --signature_def serving_default

            final float[][] resultArray = resultTensor.copyTo(new float[batchSize][(int) nCategories]);

            System.out.println(Arrays.toString(resultArray[0]));

        }

        modelBundle.close();
    }

    private ModelMetadata modelMetadata() {
        final ObjectMapper objectMapper = new ObjectMapper();
        try (final InputStream is = TensorflowTest.class.getResourceAsStream("/model/model_metadata.json")) {
            return objectMapper.readValue(is, ModelMetadata.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private FloatBuffer matrix(int batchSize, int maxFeaturesAmount) {
        final FloatBuffer matrixData = FloatBuffer.allocate(batchSize * maxFeaturesAmount);
        for (int i = 0; i < batchSize * maxFeaturesAmount; i++) {
            matrixData.put(0.01f);
        }
        matrixData.rewind();
        return matrixData;
    }

    private IntBuffer matrixInt(int batchSize, int maxFeaturesAmount, int[] data) {
        final IntBuffer matrixData = IntBuffer.allocate(batchSize * maxFeaturesAmount);
        for (int i = 0; i < batchSize * maxFeaturesAmount; i++) {
            matrixData.put(data[i]);
        }
        matrixData.rewind();
        return matrixData;
    }
}
