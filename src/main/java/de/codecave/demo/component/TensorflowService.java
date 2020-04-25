package de.codecave.demo.component;

public interface TensorflowService {

    /**
     * run tensorflow prediction for a single input vector (batch size 1)
     */
    float[] predictSingleTensorflow(int[] inputTokens, final int nCategories);

}
