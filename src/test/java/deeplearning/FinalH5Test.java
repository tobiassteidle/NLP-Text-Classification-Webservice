package deeplearning;

import org.apache.commons.lang3.time.StopWatch;
import org.assertj.core.util.Lists;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModel;
import org.deeplearning4j.nn.modelimport.keras.utils.KerasModelBuilder;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FinalH5Test {

    final private List<String> categories = Lists.newArrayList("POLITICS", "WELLNESS", "ENTERTAINMENT", "TRAVEL", "STYLE & BEAUTY", "PARENTING", "HEALTHY LIVING", "QUEER VOICES", "FOOD & DRINK", "BUSINESS");

    @Test
    void name() throws Exception {
        //String fileName = "D:/Deep Learning/NLP-Text-Classification/pretrained/final_news.h5";
//        String fileName = "D:/Deep Learning/NLP-Text-Classification/model.h5";

        String fileName = "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/final_news.h5";
//        String fileNameSimple = "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model.h5";

        //final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights(fileName, false);
//        final ComputationGraph network = KerasModelImport.importKerasModelAndWeights(fileName,false);

        //final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights("D:/model.json", "D:/model_weights.h5", false);
        KerasModelBuilder builder = new KerasModel()
                .modelBuilder()
                .modelHdf5Filename(fileName)
                .enforceTrainingConfig(false);


        // reshape input (none, 20, 200), output (None, 1, 20, 200)
        //

        final ComputationGraph network = builder.buildModel().getComputationGraph();

        System.out.println("summary " + network.summary());

        StopWatch stopWatch = StopWatch.createStarted();

        //        final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights(fileName);

        // entertainment
        final float[] inputWedding = {266, 754, 584, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        // style&beauty
        final float[] inputFashionMistakes = {1, 64, 520, 739, 1692, 1508, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        final float[] inputTravelApps = {12, 56, 1310, 1312, 307, 328, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        final float[][] blabla2 = new float[][]{inputWedding, inputFashionMistakes, inputTravelApps};

        final INDArray input = Nd4j.create(blabla2);

        // Invalid input: EmbeddingSequenceLayer expects either rank 2 input of shape [minibatch,seqLength] or rank 3
        // input of shape [minibatch,1,seqLength]. Got rank 3 input of shape [0, 0, 0]
        final INDArray output = network.outputSingle(input);
        System.out.println("elapsed " + stopWatch.getTime(TimeUnit.MILLISECONDS));

        for (int i = 0; i < blabla2.length; i++) {
            final INDArray result = output.getRow(i);
            System.out.println("output " + result);

            final int max = Nd4j.argMax(result).getInt(0);
            System.out.println("max# " + max);
            System.out.println("max " + categories.get(max));
        }


//['POLITICS', 'WELLNESS', 'ENTERTAINMENT', 'TRAVEL', 'STYLE & BEAUTY', 'PARENTING', 'HEALTHY LIVING', 'QUEER VOICES', 'FOOD & DRINK', 'BUSINESS']
    }
}
