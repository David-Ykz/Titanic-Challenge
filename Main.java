import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {


    public static void readPassengers(ArrayList<double[]> trainingData, ArrayList<Double> results) {
        try {
            String fileName = "train.csv";
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            input.nextLine();
            int lineNum = 1;
            while (input.hasNext()) {
                lineNum++;
                try {
                    String[] line = input.nextLine().split(",");
                    double survival = Double.parseDouble(line[1]);
                    double pClass = Double.parseDouble(line[2]);
                    double gender = 0.0; // Male
                    int start = -1;
                    for (int i = 3; i < line.length; i++) {
                        if (line[i].contains("female")) {
                            start = i;
                            gender = 1.0; // Female
                        } else if (line[i].contains("male")){
                            start = i;
                        }
                    }
                    double age = Double.parseDouble(line[start + 1]);
                    trainingData.add(new double[]{pClass, gender, age});
                    results.add(survival);
//                    System.out.println("Class: " + Double.toString(pClass) + " -- Gender: " + Double.toString(gender) + " -- Age: " + Double.toString(age) + " -- Survival: " + Double.toString(survival));

                } catch (Exception e) {
//                    System.out.println(e);
 //                   System.out.println(lineNum);
                }
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }
    }


    public static void main(String[]args) {
        ArrayList<double[]> passengers = new ArrayList<>();
        ArrayList<Double> survival = new ArrayList<>();
        readPassengers(passengers, survival);
        System.out.println(passengers.size());

        double[][] trainingData = new double[passengers.size()][];
        double[] results = new double[survival.size()];
        for (int i = 0; i < passengers.size(); i++) {
            trainingData[i] = passengers.get(i);
            results[i] = survival.get(i);
        }

        LogisticRegression classifier = new LogisticRegression();
        double[] parameters = classifier.initializeParameters(trainingData[0].length, -1);
        double bias = 3.0;
        System.out.println(classifier.testPredictions(parameters, bias, trainingData, results));
        System.out.println(Arrays.toString(parameters));
        System.out.println(classifier.logarithmicLoss(parameters, bias, trainingData, results));
        System.out.println(bias);
        for (int i = 0; i < 100000; i++) {
            bias = classifier.learn(parameters, bias, trainingData, results, 0.001, 0.01);
            if (i % 5000 == 0) {
                System.out.println("");
                System.out.println(classifier.testPredictions(parameters, bias, trainingData, results));
                System.out.println(Arrays.toString(parameters));
                System.out.println(classifier.logarithmicLoss(parameters, bias, trainingData, results));
                System.out.println(bias);

            }
        }









//        Layer inputLayer = new Layer(3);
//        NeuralNetwork network = new NeuralNetwork(inputLayer);
//
//        network.addLayer(new Layer(1));
//
//        network.printNetwork();
//
//        System.out.println("");
//        System.out.println("");
//        System.out.println("");
//
//        network.trainNetwork(1000, 0.01, passengers);
//
//        System.out.println(network.sum(network.computeIndividualOutput(passengers.get(1))));

    }


}
