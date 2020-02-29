package deeplearning;

import com.clearspring.analytics.util.Lists;
import org.assertj.core.api.Assertions;
import org.deeplearning4j.nn.conf.ComputationGraphConfiguration;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasLayer;
import org.deeplearning4j.nn.modelimport.keras.KerasModel;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.KerasSequentialModel;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.utils.KerasModelBuilder;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.IOException;
import java.util.Arrays;

public class FinalH5Test {

    @Test
    void name() throws Exception {
        String fileName = "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model.h5";
//        String fileNameSimple = "/Users/stefanostermayr/Documents/MeetupJUG2020DL4J/model.h5";

//        final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights(fileName, false);
//        final ComputationGraph network = KerasModelImport.importKerasModelAndWeights(fileName,
//        new int[] {20 } ,
//                false);

        KerasModelBuilder builder = new KerasModel().modelBuilder()
//                .weightsHdf5Filename()
//                .modelJsonFilename("")
                .inputShape(new int[] {-1, 9920 })
                .modelHdf5Filename(fileName)

//                .dimOrder(KerasLayer.DimOrder.TENSORFLOW)
                .enforceTrainingConfig(false);
        System.out.println("shape " + Arrays.toString(builder.getInputShape()));

        // reshape input (none, 20, 200), output (None, 1, 20, 200)
        //

        final ComputationGraph network = builder.buildModel().getComputationGraph();


        //        final MultiLayerNetwork network = KerasModelImport.importKerasSequentialModelAndWeights(fileName);

        final float[] blabla = {1, 64, 520, 739, 1692, 1508, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        final float[][] blabla2 = new float[][] {blabla};

        System.out.println("summary " + network.summary());
        final INDArray input = Nd4j.create(blabla2);

        // Invalid input: EmbeddingSequenceLayer expects either rank 2 input of shape [minibatch,seqLength] or rank 3
        // input of shape [minibatch,1,seqLength]. Got rank 3 input of shape [0, 0, 0]
        final INDArray[] output = network.output(input);

        System.out.println("output " +output[0]);


    }
}
