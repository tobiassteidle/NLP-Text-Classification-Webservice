package de.codecave.demo;

import javafx.scene.paint.Stop;
import org.apache.commons.lang3.time.StopWatch;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.factory.Nd4j;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.framework.SavedModel;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TensorflowTest {

    final private List<String> categoriesZZZ = Lists.newArrayList("POLITICS", "WELLNESS", "ENTERTAINMENT", "TRAVEL", "STYLE & BEAUTY", "PARENTING", "HEALTHY LIVING", "QUEER VOICES", "FOOD & DRINK", "BUSINESS");
    final private List<String> categories = Lists.newArrayList(            "BLACK VOICES", "COMEDY", "ENTERTAINMENT", "MEDIA", "POLITICS", "QUEER VOICES", "SPORTS", "WEIRD NEWS", "WOMEN", "WORLD NEWS");

    @Test
    void singleRun() {

        final SavedModelBundle modelBundle = SavedModelBundle.load(
                "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model/tensorflow2/tensorflow", "serve");

        modelBundle.graph().operations().forEachRemaining(op -> {
//            System.out.println("- " + op.name() + " " + op.type());
        });

        try (Session session = modelBundle.session()) {

            final Session.Runner runner = session.runner();

            int batchSize = 1;
            int maxFeaturesAmount = 20;
            int nCategories = 10;

            // entertainment
            final int[] inputWedding = {266, 754, 584, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            // style&beauty
            final int[] inputFashionMistakes = {1, 64, 520, 739, 1692, 1508, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            final int[] inputTravelApps = {133, 937, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

            final Tensor<Integer> padTensor = Tensor.create(new long[]{1,maxFeaturesAmount}, matrixInt(1, maxFeaturesAmount, inputTravelApps));
//            final Tensor<Float> auxTensor = Tensor.create(new long[]{batchSize, maxFeaturesAmount}, matrix(batchSize, maxFeaturesAmount));

            final Tensor<?> resultTensor =
                    runner.feed("serving_default_tobias", padTensor)
//                    .feed("x_aux", auxTensor)
                    .fetch("StatefulPartitionedCall")
                    .run()
                    .get(0);


//            saved_model_cli show --dir .
//            saved_model_cli show --dir . --tag_set serve
//            saved_model_cli show --dir . --tag_set serve --signature_def serving_default

//            inputs['input_1'] tensor_info:
//            dtype: DT_INT32
//            shape: (-1, 20)
//            name: serving_default_input_1:0
//            The given SavedModel SignatureDef contains the following output(s):
//            outputs['dense'] tensor_info:
//            dtype: DT_FLOAT
//            shape: (-1, 10)
//            name: StatefulPartitionedCall:0
//            Method name is: tensorflow/serving/predict
            final float[][] resultArray = resultTensor.copyTo(new float[batchSize][(int) nCategories]);

            System.out.println(Arrays.toString(resultArray[0]));


            final int max = Nd4j.argMax(Nd4j.create(resultArray[0])).getInt(0);
            System.out.println("max# " + max);
            System.out.println("max " + categories.get(max));

        }

        modelBundle.close();
    }

    @Test
    void massSessions() {

        final SavedModelBundle modelBundle = SavedModelBundle.load(
                "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model/tensorflow2/tensorflow", "serve");

        final StopWatch stopWatch = StopWatch.createStarted();


        // note: this is NOT a session factory method - session is part of model
        try (Session session = modelBundle.session()) {

            for (int i=0; i<1_000; i++) {

                                int batchSize = 1;
                int maxFeaturesAmount = 20;
                int nCategories = 10;

                final int[] inputTravelApps = {133, 937, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

                // input must be 4-dimensional[20,1,200]
                final Tensor<Integer> padTensor = Tensor.create(new long[]{1,maxFeaturesAmount}, matrixInt(1, maxFeaturesAmount, inputTravelApps));

                // runner is a factory method
                session.runner()
                    .feed("serving_default_tobias", padTensor)
//                    .feed("x_aux", auxTensor)
                    .fetch("StatefulPartitionedCall")
                    .run()
                    .get(0);

            }

        }


        System.out.println("elapsed " + stopWatch.getTime(TimeUnit.MILLISECONDS));

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

    private IntBuffer matrixInt(int batchSize, int maxFeaturesAmount, int[] data) {
        final IntBuffer matrixData = IntBuffer.allocate(batchSize * maxFeaturesAmount);
        for (int i=0;i <batchSize * maxFeaturesAmount; i++) {
            matrixData.put(data[i]);
        }
        matrixData.rewind();
        return matrixData;
    }
}
