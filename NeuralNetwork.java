import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
    ArrayList<Layer> layers;

    NeuralNetwork(Layer inputLayer) {
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
            node.biases = new double[previous.numNodes()];
            Arrays.fill(node.biases, 0.0);
        }
    }



}
