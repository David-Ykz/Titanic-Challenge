import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    double[] weights;
    double[] biases;
    double value;


    public String nodeInfo() {
        return "Weights: " + Arrays.toString(weights) + " -- Biases: " + Arrays.toString(biases);
    }

//    ArrayList<Double> weights;
//    ArrayList<Double> biases;



}
