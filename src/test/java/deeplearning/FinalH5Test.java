package deeplearning;

import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModel;
import org.deeplearning4j.nn.modelimport.keras.utils.KerasModelBuilder;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

public class FinalH5Test {

    @Test
    void name() throws Exception {
        //String fileName = "D:/Deep Learning/NLP-Text-Classification/pretrained/final_news.h5";
        String fileName = "D:/Deep Learning/NLP-Text-Classification/model.h5";

        //String fileName = "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model.h5";
//        String fileNameSimple = "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model.h5";

        //final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights(fileName, false);
        final ComputationGraph network = KerasModelImport.importKerasModelAndWeights(fileName,false);

        //final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights("D:/model.json", "D:/model_weights.h5", false);
/*
        KerasModelBuilder builder = new KerasModel().modelBuilder()
                .modelJsonFilename("D:/model.json")
                .weightsHdf5Filename("D:/model.h5")


//                .weightsHdf5Filename()
//                .modelJsonFilename("")
                .inputShape(new int[] {20 })
                //.modelHdf5Filename(fileName)

               // .dimOrder(KerasLayer.DimOrder.THEANO)
                .enforceTrainingConfig(false);


        System.out.println("shape " + Arrays.toString(builder.getInputShape()));
*/
        // reshape input (none, 20, 200), output (None, 1, 20, 200)
        //

        //final ComputationGraph network = builder.buildModel().getComputationGraph();


        //        final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights(fileName);

        final float[] blabla = {1, 64, 520, 739, 1692, 1508, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        final float[][] blabla2 = new float[][] {blabla};

        System.out.println("summary " + network.summary());
        final INDArray input = Nd4j.create(blabla2);

        // Invalid input: EmbeddingSequenceLayer expects either rank 2 input of shape [minibatch,seqLength] or rank 3
        // input of shape [minibatch,1,seqLength]. Got rank 3 input of shape [0, 0, 0]
        final INDArray [] output = network.output(input);

        System.out.println("output " +output[0]);

//['POLITICS', 'WELLNESS', 'ENTERTAINMENT', 'TRAVEL', 'STYLE & BEAUTY', 'PARENTING', 'HEALTHY LIVING', 'QUEER VOICES', 'FOOD & DRINK', 'BUSINESS']
    }
}
