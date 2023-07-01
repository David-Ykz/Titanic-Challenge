import java.util.ArrayList;
public class Layer {
    ArrayList<Node> nodes;

    Layer(int numNodes) {
        for (int i = 0; i < numNodes; i++) {
            nodes.add(new Node());
        }
    }

    public int numNodes() {
        return nodes.size();
    }

}
