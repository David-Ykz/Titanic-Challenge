import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    double[] weights;
    double bias;
    double value;

    public void calculateValue(Layer prevLayer) {
        value = 0;
        int i = 0;
        for (Node prevNode : prevLayer.nodes) {
            value += prevNode.value * weights[i];
            i++;
        }
        value = Math.max(0.0, value + bias); // Relu
    }



    public String nodeInfo() {
        return "Weights: " + Arrays.toString(weights) + " -- Bias: " + Double.toString(bias);
    }

//    ArrayList<Double> weights;
//    ArrayList<Double> biases;



}
