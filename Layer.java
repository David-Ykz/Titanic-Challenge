import java.util.ArrayList;
public class Layer {
    ArrayList<Node> nodes;

    Layer(int numNodes) {
        nodes = new ArrayList<Node>();
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node());
        }
    }

    public int numNodes() {
        return nodes.size();
    }

    public void printLayer() {
        int counter = 1;
        for (Node node : nodes) {
            System.out.println("Node " + Integer.toString(counter) + ": " + node.nodeInfo());
            counter++;
        }
    }

    public void calculateNodeValues(Layer prevLayer) {
        for (Node node : nodes) {
            node.calculateValue(prevLayer);
        }
    }
}
