import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static int numberCorrect(double[] predictions, int[] results) {
        int numCorrect = 0;
        for (int i = 0; i < predictions.length; i++) {
            if (Math.abs(predictions[i] - results[i]) < 0.5) {
                numCorrect++;
            }
        }
        return numCorrect;
    }

    public static double[][] splitData(double[][] data, int index) {
        if (index > 0) {
            return Arrays.copyOfRange(data, 0, index);
        } else {
            return Arrays.copyOfRange(data, data.length + index, data.length);
        }
    }

    public static int[] splitResults(int[] results, int index) {
        if (index > 0) {
            return Arrays.copyOfRange(results, 0, index);
        } else {
            return Arrays.copyOfRange(results, results.length + index, results.length);
        }
    }

    public static void main(String[]args) {
        // Read training data
        double[][] trainingData = CSVReader.readFeatures("ScaledTrainingSet.csv");
        int[] results = CSVReader.readResults("ResultsSet.csv", trainingData.length);

        // Initiate model
        LogisticRegression classifier = new LogisticRegression();
        double[] parameters = classifier.initializeParameters(trainingData[0].length, 0);
        double bias = 0.0;


        boolean trainingMode = true;

        if (trainingMode) {
            // Split data for training and testing
            int trainingSize = 624;
            int testingSize = trainingData.length - trainingSize;
            double[][] train = splitData(trainingData, trainingSize);
            double[][] test = splitData(trainingData, -testingSize);
            int[] trainResult = splitResults(results, trainingSize);
            int[] testResult = splitResults(results, -testingSize);
            // Train model
            for (int i = 0; i < 30000; i++) {
                bias = classifier.learn(parameters, bias, train, trainResult, 0.001, 0.001);
            }
            // Print output
            System.out.println("Accuracy: " + classifier.testPredictions(parameters, bias, test, testResult) / (double)testingSize);
            System.out.println(Arrays.toString(parameters));
            System.out.println(bias);
        } else {
            for (int i = 0; i < 30000; i++) {
                bias = classifier.learn(parameters, bias, trainingData, results, 0.001, 0.001);
            }
            double[][] testData = CSVReader.readFeatures("ScaledTestingSet.csv");
            int[] testDataPredictions = new int[testData.length];
            for (int i = 0; i < testData.length; i++) {
                testDataPredictions[i] = (int) Math.round(classifier.prediction(parameters, bias, testData[i]));
            }
            CSVWriter.writeFile("Predictions.csv", testDataPredictions);
        }
    }
}
