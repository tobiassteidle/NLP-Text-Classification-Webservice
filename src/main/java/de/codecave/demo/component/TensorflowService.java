package de.codecave.demo.component;

public interface TensorflowService {

    /**
     * run tensorflow prediction for a single input vector (batch size 1)
     *
     * - input shape INT32 tensor with shape [1, 20]
     * - input vector [225, 1113, 186, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
     * - output shape FLOAT tensor with shape [1, 10]
     * - output vector [0.02423917, 0.07196671, 8.999937E-4, 0.011337514, 0.0049416884,
     *                  0.84558976, 0.026367448, 6.4031663E-4, 0.0064433725, 0.0075740833]
     */
    float[] predictSingleTensorflow(int[] inputTokens, final int nCategories);

}
