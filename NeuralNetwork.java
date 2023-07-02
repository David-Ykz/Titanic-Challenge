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
            node.weightGradient = new double[previous.numNodes()];
            Arrays.fill(node.weights, 1.0);
            Arrays.fill(node.weights, 0.0);
            node.bias = 0.0;
            node.biasGradient = 0.0;
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
//        System.out.println(Arrays.toString(output));
        return output;
    }

    public double computeError(ArrayList<double[]> data) {
        Layer outputLayer = layers.get(layers.size() - 1);
        double output = 0.0;
        for (double[] inputData : data) {
            output += Math.abs(inputData[inputData.length - 1] - sum(computeIndividualOutput(inputData)));
        }
        return output;
    }

    public void trainNetwork(int numIterations, double learningRate, ArrayList<double[]> trainingData) {
        for (int trainingCycle = 1; trainingCycle < numIterations + 1; trainingCycle++) {
            learn(learningRate, trainingData);


            // Loop through all weights
            // Nudge each one a bit
            // Check how much the cost changes
            //


            if (trainingCycle % (numIterations/10) == 0) {
                System.out.println("Iteration " + Integer.toString(trainingCycle) + " -- Cost: " + Double.toString(computeError(trainingData)) + " -- Number of Correct: " + testNetwork(trainingData));
                printNetwork();
                System.out.println("");
            }
        }
    }

    public void learn(double learningRate, ArrayList<double[]> trainingData) {
        double delta = 0.00001;
        double originalError = computeError(trainingData);
        for (int layerNum = 1; layerNum < layers.size(); layerNum++) {
            Layer layer = layers.get(layerNum);
            for (Node node : layer.nodes) {
                for (int i = 0; i < node.weights.length; i++) {
                    node.weights[i] = node.weights[i] + delta;
                    double deltaCost = originalError - computeError(trainingData);
                    node.weights[i] = node.weights[i] - delta;
                    node.weightGradient[i] = deltaCost/delta;
                }

                node.bias += delta;
                double deltaCost = originalError - computeError(trainingData);
                node.bias -= delta;
                node.biasGradient = deltaCost/delta;
            }
        }

        applyGradients(learningRate);
    }

    public void applyGradients(double learningRate) {
        for (int layerNum = 1; layerNum < layers.size(); layerNum++) {
            Layer layer = layers.get(layerNum);
            for (Node node : layer.nodes) {
                for (int i = 0; i < node.weights.length; i++) {
                    node.weights[i] = node.weights[i] + learningRate * node.weightGradient[i]; // SHOULD BE A NEGATIVE
                }
                node.bias += learningRate * node.biasGradient; // SHOULD BE A NEGATIVE
            }
        }
    }

    public double testNetwork(ArrayList<double[]> trainingData) {
        int numCorrect = 0;
        for (double[] inputData : trainingData) {
            double prediction = sum(computeIndividualOutput(inputData));
            if (prediction > 0.5 && inputData[inputData.length - 1] == 1) {
                numCorrect++;
            } else if (prediction < 0.5 && inputData[inputData.length - 1] == 0) {
                numCorrect++;
            }
        }
        return numCorrect;
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
