import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
    ArrayList<Layer> layers;

    NeuralNetwork(Layer inputLayer) {
        layers = new ArrayList<>();
        layers.add(inputLayer);
    }

    public void addLayer(Layer layer) {
        Layer prevLayer = layers.get(layers.size() - 1);
        layers.add(layer);
        initializeLayerConnection(prevLayer, layer);
    }

    public void initializeLayerConnection(Layer previous, Layer current) {
        for (Node node : current.nodes) {
            node.weights = new double[previous.numNodes()];
            Arrays.fill(node.weights, 1.0);
            node.bias = 0.0;
        }
    }

    public double[] computeIndividualOutput(double[] inputData) {
        // Initialize values for the first layer
        Layer firstLayer = layers.get(0);
        for (int i = 0; i < firstLayer.numNodes(); i++) {
            firstLayer.nodes.get(i).value = inputData[i];
        }

        for (int i = 1; i < layers.size(); i++) {
            layers.get(i).calculateNodeValues(layers.get(i - 1));
        }

        Layer outputLayer = layers.get(layers.size() - 1);
        double[] output = new double[outputLayer.numNodes()];
        for (int i = 0; i < outputLayer.numNodes(); i++) {
            output[i] = outputLayer.nodes.get(i).value;
        }
        System.out.println(Arrays.toString(output));
        return output;
    }

    public double computeError(ArrayList<double[]> data) {
        Layer outputLayer = layers.get(layers.size() - 1);
        double output = 0.0;
        for (double[] inputData : data) {
            output += square(inputData[inputData.length - 1] - sum(computeIndividualOutput(inputData)));
        }
        return output;
    }

    public double square(double num) {
        return num * num;
    }

    public double sum(double[] arr) {
        double value = 0;
        for (double i : arr) {
            value += i;
        }
        return value;
    }



    public void printNetwork() {
        int counter = 0;
        for (Layer layer : layers) {
            if (counter == 0) {
                System.out.println("---------- Input Layer ----------");
            } else {
                System.out.println("---------- Hidden Layer " + Integer.toString(counter) + " ----------");
            }
            layer.printLayer();
            counter++;
        }
    }


}
