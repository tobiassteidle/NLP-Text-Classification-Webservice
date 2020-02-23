package deeplearning;

import de.codecave.demo.rest.HelloController;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.api.NeuralNetwork;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * see https://github.com/eclipse/deeplearning4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/feedforward/mnist/MLPMnistSingleLayerExample.java
 *
 * MnistFetcher data will be kept in $HOME/.deeplearning4j/
 */
public class MnistTrainTestMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(MnistTrainTestMain.class);

    public static void main(String[] args) throws IOException {

        final MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(123)
                .updater(new Nesterovs.Builder().learningRate(0.006).momentum(0.9).build())
                .l2(1e-4)
                .list()
                .layer(new DenseLayer.Builder()
                        .nIn(28 * 28)
                        .nOut(1000)
                        .activation(Activation.RELU)
                        .weightInit(WeightInit.XAVIER)
                        .build()
                )
                .layer(new OutputLayer.Builder()
                        .nIn(1000)
                        .nOut(10)
                        .activation(Activation.SOFTMAX)
                        .weightInit(WeightInit.XAVIER)
                        .build()
                )
                .build();

        final MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        final MnistDataSetIterator mnistTrain = new MnistDataSetIterator(128, false, 99);

        model.fit(mnistTrain, 128);

        final String summary = model.summary();
        LOGGER.info("Summary " + summary);

    }

}
