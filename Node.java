import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

public class Node {
    double[] weights;
    double[] weightGradient;
    double bias;
    double biasGradient;
    double value;


    public void calculateValue(Layer prevLayer) {
        value = 0;
        int i = 0;
        for (Node prevNode : prevLayer.nodes) {
            value += prevNode.value * weights[i];
            i++;
        }
        value = Math.max(0.0, value + bias); // Relu
        value = 1/(1 + Math.exp(-value)); // Sigmoid activation function
    }



    public String nodeInfo() {
        return "Weights: " + printArray(weights) + " -- Weight Gradient: " + printArray(weightGradient) + " -- Bias: " + Double.toString(round(bias, 3)) + " -- Bias Gradient: " + Double.toString(round(biasGradient, 3));
    }

    public String printArray(double[] arr) {
        String str = "[";
        for (double num : arr) {
            str += Double.toString(round(num, 3)) + ",";
        }
        str = str.substring(0, str.length() - 1);
        str += "]";

        return str;
    }

    public double round(double value, int places) {
        BigDecimal decimal = BigDecimal.valueOf(value);
        decimal = decimal.setScale(places, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }


//    ArrayList<Double> weights;
//    ArrayList<Double> biases;



}
