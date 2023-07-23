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

        boolean trainingMode = true;

        if (trainingMode) {
            // Split data for training and testing
            int trainingSize = 624;
            int testingSize = trainingData.length - trainingSize;
            double[][] train = splitData(trainingData, trainingSize);
            double[][] test = splitData(trainingData, -testingSize);
            int[] trainResult = splitResults(results, trainingSize);
            int[] testResult = splitResults(results, -testingSize);
            LogisticRegression classifier = new LogisticRegression(train, trainResult);
            // Train model
            for (int i = 0; i < 30000; i++) {
                classifier.learn(0.001, 0.01, 0.7);
                if (i % 1000 == 0) {
                    System.out.println("Train Accuracy: " + classifier.testPredictions(train, trainResult) / (double)trainingSize);
                //    System.out.println("Test Accuracy: " + classifier.testPredictions(parameters, bias, test, testResult) / (double)testingSize);
                }
            }
            // Print output
            System.out.println("Accuracy: " + classifier.testPredictions(test, testResult) / (double)testingSize);
        } else {
            LogisticRegression classifier = new LogisticRegression(trainingData, results);
            for (int i = 0; i < 100000; i++) {
                if (i % 5000 == 0) {
                    System.out.println("Accuracy: " + classifier.testPredictions(trainingData, results) / (double)trainingData.length);
                }
//                bias = classifier.learn(parameters, bias, trainingData, results, 0.001, 0.001);
            }
            System.out.println("Accuracy: " + classifier.testPredictions(trainingData, results) / (double)trainingData.length);
            double[][] testData = CSVReader.readFeatures("ScaledTestingSet.csv");
            int[] testDataPredictions = new int[testData.length];
            for (int i = 0; i < testData.length; i++) {
                testDataPredictions[i] = (int) Math.round(classifier.prediction(testData[i]));
            }
            CSVWriter.writeFile("Predictions.csv", testDataPredictions);
        }
    }
}
