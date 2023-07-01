import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
    ArrayList<Layer> layers;

    NeuralNetwork(Layer inputLayer) {
        layers = new ArrayList<Layer>();
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

    public double[] computeResult(double[] inputData) {
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

        return output;
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
