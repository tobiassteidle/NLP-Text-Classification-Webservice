package de.codecave.demo;

import org.junit.jupiter.api.Test;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class TensorflowTest {

    @Test
    void name() {

        final SavedModelBundle modelBundle = SavedModelBundle.load(
                "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model/tensorflow", "serve");

        modelBundle.graph().operations().forEachRemaining(op -> {
            System.out.println("- " + op.name());
        });

        try (Session session = modelBundle.session()) {

            final Session.Runner runner = session.runner();

            int batchSize = 1;
            int maxFeaturesAmount = 10;
            int nCategories = 10;

            final Tensor<Float> padTensor = Tensor.create(new long[]{batchSize, maxFeaturesAmount}, matrix(batchSize, maxFeaturesAmount));
            final Tensor<Float> auxTensor = Tensor.create(new long[]{batchSize, maxFeaturesAmount}, matrix(batchSize, maxFeaturesAmount));

            final Tensor<?> resultTensor =
                    runner.feed("input", padTensor)
//                    .feed("x_aux", auxTensor)
                    .fetch("total_1")
                    .run()
                    .get(0);


            final float[][] resultArray = resultTensor.copyTo(new float[batchSize][(int) nCategories]);
            Arrays.toString(resultArray);

        }

        modelBundle.close();
    }

    @Test
    void pfm() {

        final SavedModelBundle modelBundle = SavedModelBundle.load(
                "/Users/stefanostermayr/Documents/treefin/git/saved_models/versioned_models/category/6", "serve");

        modelBundle.graph().operations().forEachRemaining(op -> {
            System.out.println("- " + op.name());
        });

        try (Session session = modelBundle.session()) {

            final Session.Runner runner = session.runner();

            int batchSize = 1;
            int maxFeaturesAmount = 20;// TODO check
            int nCategories = 10;

            final Tensor<Float> padTensor = Tensor.create(new long[]{batchSize, maxFeaturesAmount}, matrix(batchSize, maxFeaturesAmount));
            final Tensor<Float> auxTensor = Tensor.create(new long[]{batchSize, maxFeaturesAmount}, matrix(batchSize, maxFeaturesAmount));

            final Tensor<?> resultTensor = runner.feed("x_pad", padTensor)
                    .feed("x_aux", auxTensor)
                    .fetch("out/Softmax")
                    .run()
                    .get(0);


            final float[][] resultArray = resultTensor.copyTo(new float[batchSize][(int) nCategories]);
            Arrays.toString(resultArray);

        }

        modelBundle.close();
    }

    private FloatBuffer matrix(int batchSize, int maxFeaturesAmount) {
        final FloatBuffer matrixData = FloatBuffer.allocate(batchSize * maxFeaturesAmount);
        for (int i=0;i <batchSize * maxFeaturesAmount; i++) {
            matrixData.put(0.01f);
        }
        matrixData.rewind();
        return matrixData;
    }
}
