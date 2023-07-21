import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {


    public static void main(String[]args) {
        CSVReader reader = new CSVReader();
        double[][] trainingData = reader.readFeatures("ScaledTrainingSet.csv");
        double[] results = reader.readResults("ResultsSet.csv", trainingData.length);

        LogisticRegression classifier = new LogisticRegression();
        double[] parameters = classifier.initializeParameters(trainingData[0].length, 0);
        double bias = 0.0;
        System.out.println("--- Starting Values --- ");
        System.out.println(classifier.testPredictions(parameters, bias, trainingData, results));
        System.out.println(Arrays.toString(parameters));
        System.out.println(classifier.logarithmicLoss(parameters, bias, trainingData, results));
        System.out.println(bias);
        for (int i = 0; i < 30000; i++) {
            bias = classifier.learn(parameters, bias, trainingData, results, 0.001, 0.01);
            if (i % 5000 == 0) {
                System.out.println("");
                System.out.println(classifier.testPredictions(parameters, bias, trainingData, results));
                System.out.println(Arrays.toString(parameters));
                System.out.println(bias);
            }
        }


        System.out.println("--------------------");
        System.out.println(classifier.testPredictions(parameters, bias, trainingData, results));
        System.out.println("--------------------");
        System.out.println(Arrays.toString(parameters));
        System.out.println(bias);




        double[][] testData = reader.readFeatures("ScaledTestingSet.csv");
        int[] predictions = new int[testData.length];
        for (int i = 0; i < testData.length; i++) {
            predictions[i] = (int) Math.round(classifier.prediction(parameters, bias, testData[i]));
        }

        CSVWriter writer = new CSVWriter();
        writer.writeFile("Predictions.csv", predictions);

        for (int i = 0; i < trainingData.length; i++) {
            double[] data = trainingData[i];
            if (Math.abs(results[i] - classifier.prediction(parameters, bias, data)) < 0.5) {
                System.out.print(Integer.toString(i) + ", ");
            }
        }





    }

}
