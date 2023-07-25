import java.util.*;

public class Main {
    // Splits training data for testing purposes
    public static double[][] splitData(double[][] data, int index) {
        if (index > 0) {
            return Arrays.copyOfRange(data, 0, index);
        } else {
            return Arrays.copyOfRange(data, data.length + index, data.length);
        }
    }
    // Splits the output data
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

        /* Controls what mode the model is in
         * If trainingMode = true, the program splits training data up to measure model performance
         * If trainingMode = false, the program predicts the testing data for Kaggle submission
         */
        boolean trainingMode = false;

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
            for (int i = 0; i < 70000; i++) {
                classifier.learn(0.001, 0.9);
                if (i % 1000 == 0) {
                    System.out.println("Train Accuracy: " + classifier.testPredictions(train, trainResult) / (double)trainingSize);
                }
            }
            System.out.println("Test Accuracy: " + classifier.testPredictions(test, testResult) / (double)testingSize);
        } else {
            LogisticRegression classifier = new LogisticRegression(trainingData, results);
            for (int i = 0; i < 70000; i++) {
                if (i % 1000 == 0) {
                    System.out.println("Accuracy: " + classifier.testPredictions(trainingData, results) / (double)trainingData.length);
                }
                classifier.learn(0.001, 0.9);
            }
            System.out.println("Accuracy: " + classifier.testPredictions(trainingData, results) / (double)trainingData.length);
            // Read testing data
            double[][] testData = CSVReader.readFeatures("ScaledTestingSet.csv");
            int[] testDataPredictions = new int[testData.length];
            // Use the model to predict survival for testing data
            for (int i = 0; i < testData.length; i++) {
                testDataPredictions[i] = (int) Math.round(classifier.prediction(testData[i]));
            }
            // Write predictions to a .csv file
            CSVWriter.writeFile("Predictions.csv", testDataPredictions);
        }
    }
}
