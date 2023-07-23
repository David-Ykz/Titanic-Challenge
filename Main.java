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

    public static double[] splitData(double[] data, int index) {
        if (index > 0) {
            return Arrays.copyOfRange(data, 0, index);
        } else {
            return Arrays.copyOfRange(data, data.length + index, data.length);
        }
    }

    public static void main(String[]args) {
        double[] example = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        double[] train = splitData(example, 10);
        double[] test = splitData(example, -5);
        System.out.println(Arrays.toString(train));
        System.out.println(Arrays.toString(test));
        System.out.println(1/0);



        CSVReader reader = new CSVReader();
        double[][] trainingData = reader.readFeatures("ScaledTrainingSet.csv");
        int[] results = reader.readResults("ResultsSet.csv", trainingData.length);

        LogisticRegression classifier = new LogisticRegression();
        double[] parameters = classifier.initializeParameters(trainingData[0].length, 0);
        double bias = 0.0;
        for (int i = 0; i < 30000; i++) {
            bias = classifier.learn(parameters, bias, trainingData, results, 0.001, 0.01);
        }


        System.out.println(classifier.testPredictions(parameters, bias, trainingData, results));
        System.out.println(Arrays.toString(parameters));
        System.out.println(bias);

        double[] predictions = classifier.allPredictions(parameters, bias, trainingData);
        System.out.println(numberCorrect(predictions, results));



        double[][] testData = reader.readFeatures("ScaledTestingSet.csv");
        int[] testDataPredictions = new int[testData.length];
        for (int i = 0; i < testData.length; i++) {
            predictions[i] = (int) Math.round(classifier.prediction(parameters, bias, testData[i]));
        }

        CSVWriter writer = new CSVWriter();
        writer.writeFile("Predictions.csv", testDataPredictions);

        for (int i = 0; i < trainingData.length; i++) {
            double[] data = trainingData[i];
            if (Math.abs(results[i] - classifier.prediction(parameters, bias, data)) < 0.5) {
                System.out.print(Integer.toString(i) + ", ");
            }
        }
    }

}
