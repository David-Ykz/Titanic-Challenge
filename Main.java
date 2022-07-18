import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static HashSet<Passenger> passengers = new HashSet<>();
    static ArrayList<Generation> generations = new ArrayList<>();
    static final int CUTOFF = 1;
    static final int CAPACITY = 1000;
    static final double SUCCESS_PERCENTAGE = 0.8;

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

    public static ArrayList<Generation> findTopGenerations() {
        Collections.sort(generations, new SortBySuccess());
        Collections.reverseOrder();
        return new ArrayList<>(generations.subList(0, CUTOFF));
    }

    public static void populate() {
        for (int i = 0; i < CAPACITY; i++) {
            generations.add(new Generation());
        }
    }

    public static void repopulate(ArrayList<Generation> newGeneration) {
        generations.clear();
        generations.addAll(newGeneration);
        for (int i = 0; i < CAPACITY - newGeneration.size(); i++) {
//        for (int i = 0; i < CAPACITY; i++) {
            int randIndex = (int)(Math.random() * CUTOFF); // Picks a random generation
            generations.add(new Generation(newGeneration.get(randIndex)));
        }
    }

    public static double bestSuccess() {
        Collections.sort(generations, new SortBySuccess());
        Collections.reverseOrder();
        return generations.get(0).getDeviation();
    }

    public static Generation bestGeneration() {
        Collections.sort(generations, new SortBySuccess());
        Collections.reverseOrder();
        return generations.get(0);
    }

    public static void main(String[]args) {
        importPassengers();
        System.out.println(passengers.size());
        boolean properlyFit = false;
        int generationNum = 0;
        populate();

        int[] genNumber = new int[10000];
        double[] deviation = new double[10000];
        double[][] weights = new double[10000][6];

        while (generationNum < 3000) {
            repopulate(findTopGenerations());
            generationNum++;
            genNumber[generationNum - 1] = generationNum;
            deviation[generationNum - 1] = bestSuccess();
            for (int i = 0; i < bestGeneration().getPredictionWeights().length; i++) {
                weights[generationNum - 1][i] = bestGeneration().getPredictionWeights()[i];
            }
//            weights[generationNum - 1] = bestGeneration().getPredictionWeights();
//            if (generationNum % 100 == 0) {
//                System.out.println("Generation Number: " + generationNum + " --- Successes: " + bestSuccess() + " --- Weights: " + Arrays.toString(bestGeneration().getPredictionWeights()));
//            }
            if (bestSuccess() > passengers.size() * SUCCESS_PERCENTAGE) {
                properlyFit = true;
            }
        }


        try {
            File outputFile = new File("geneticAlg.csv");
            PrintWriter output = new PrintWriter(outputFile);
            String line;
            for (int i = 0; i < genNumber.length; i += 10) {
                line = "";
                line += genNumber[i] + ", ";
                line += deviation[i];
                for (int j = 0; j < 6; j++) {
                    line += ", " + weights[i][j];
                }
                output.println(line);
            }
            output.close();
        } catch (Exception e) {
            System.out.println("Error Writing File");
            System.out.println(e);
        }


    }


}
