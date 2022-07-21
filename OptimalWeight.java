import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class OptimalWeight {
    static final double WEIGHT_MODIFIER = 0.05;
    static double[] predictionWeights = {0, 0.5, 0, 0, 0, 0, 0};
    static double[] bestWeights = {0, 0, 0, 0, 0, 0, 0};
    static HashSet<Passenger> passengers = new HashSet<>();

    public static void importPassengers() {
        CSVReader reader = new CSVReader();
        try {
            String fileName = "train.csv";
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            String line;
            input.nextLine();
            while (input.hasNext()) {
                line = input.nextLine();
//                System.out.println(line);
                //               System.out.println(reader.parseString(line));
                passengers.add(new Passenger(reader.parseString(line)));
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }

    }

    public static int bestSuccessRate() {
        int successes = 0;
        for (Passenger p : passengers) {
            if (p.calculateSurvival(bestWeights) >= 0.5) {
                if (p.getSurvived() == 1) {
                    successes++;
                }
            } else if (p.calculateSurvival(bestWeights) < 0.5) {
                if (p.getSurvived() == 0) {
                    successes++;
                }
            }
        }
        return successes;
    }


    public static int successRate() {
        int successes = 0;
        for (Passenger p : passengers) {
            if (p.calculateSurvival(predictionWeights) >= 0.5) {
                if (p.getSurvived() == 1) {
                    successes++;
                }
            } else if (p.calculateSurvival(predictionWeights) < 0.5) {
                if (p.getSurvived() == 0) {
                    successes++;
                }
            }
        }
        return successes;
    }

    public static void checkSuccess(int a, int b, int c, int d, int e) {
        predictionWeights[0] = a * WEIGHT_MODIFIER;
        predictionWeights[1] = b * WEIGHT_MODIFIER;
        predictionWeights[6] = c * WEIGHT_MODIFIER;
        predictionWeights[4] = d * WEIGHT_MODIFIER;
        predictionWeights[5] = e * WEIGHT_MODIFIER;
        if (successRate() > bestSuccessRate()) {
            System.arraycopy(predictionWeights, 0, bestWeights, 0, bestWeights.length);
        }
//        System.out.println("Successes: " + successRate() + " - Weights: " + Arrays.toString(predictionWeights));
    }

    public static void main(String[]args) {
        importPassengers();
        int max = 10;
        for (int a = 0; a < max; a++) {
            for (int b = 0; b < max; b++) {
                for (int c = 0; c < max; c++) {
                    for (int d = 0; d < max; d++) {
                        for (int e = 0; e < max; e++) {
                            checkSuccess(a, b, c, d, e);
                        }
                    }
                }
            }
        }

        System.out.println("");
        System.out.println("Successes: " + bestSuccessRate() + " - Weights: " + Arrays.toString(bestWeights));
    }



}
