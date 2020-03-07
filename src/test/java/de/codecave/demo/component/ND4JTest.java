package de.codecave.demo.component;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.CpuNDArrayFactory;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.nativeblas.NativeOpsHolder;

public class ND4JTest {

    @Test
    void vector_shape() {
        INDArray col = Nd4j.create(new float[]{5,6},new int[]{2,1}); //vector as column
        INDArray row = Nd4j.create(new float[]{5,6},new int[]{2}); //vector as row
        System.out.println(col);
        System.out.println(row);
    }

    @Test
    void sum5x5() {
        final INDArray a = Nd4j.ones(5, 5);
        final INDArray row = a.sum(1);
        System.out.println(row);
    }

    @Test
    void initShape() {
        INDArray nd = Nd4j.create(new float[]{1,2,3,4,5,6},new int[]{3,2});
        System.out.println(nd);
        System.out.println(nd.rows());
    }
}
