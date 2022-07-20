import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static HashSet<Passenger> passengers = new HashSet<>();
    static ArrayList<Generation> generations = new ArrayList<>();
    static final int CUTOFF = 4;
    static final int CAPACITY = 5000;
    static final double SUCCESS_PERCENTAGE = 0.8;
    static final double MUTATION_CHANCE = 0.5;
    static final int GENERATION_CAP = 2000000;

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

    public static void trimPopulation() {
        Collections.sort(generations, new SortBySuccess());
        Collections.reverseOrder();
        generations.removeAll(generations.subList(0, generations.size() - CAPACITY));
    }

    public static void repopulate(ArrayList<Generation> newGeneration) {
        for (int i = 0; i < newGeneration.size(); i++) {
            Generation parentA = newGeneration.get((int)(Math.random() * CUTOFF));
            Generation parentB = newGeneration.get((int)(Math.random() * CUTOFF));
            int randIndex = (int)(Math.random() * parentA.getPredictionWeights().length);
            generations.add(new Generation(parentA, parentB, randIndex));
        }
    }

    public static double bestSuccess() {
        Collections.sort(generations, new SortBySuccess());
        Collections.reverseOrder();
        return generations.get(0).getNumSuccesses();
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

        int[] genNumber = new int[GENERATION_CAP];
        double[] success = new double[GENERATION_CAP];
        double[][] weights = new double[GENERATION_CAP][6];

        double start = System.nanoTime();
        while (generationNum < GENERATION_CAP) {
            repopulate(findTopGenerations());
            if (generations.size() > CAPACITY) {
                trimPopulation();
            }
            int randPrediction = (int)(generations.get(0).getPredictionWeights().length * Math.random());
            for (int i = 0; i < CAPACITY/5; i++) {
                if (Math.random() > MUTATION_CHANCE) {
                    int randIndex = (int) (Math.random() * generations.size());
                    generations.get(randIndex).randomModifyWeights();
                }
            }

            generationNum++;
            genNumber[generationNum - 1] = generationNum;
            success[generationNum - 1] = bestSuccess();
            for (int i = 0; i < bestGeneration().getPredictionWeights().length; i++) {
                weights[generationNum - 1][i] = bestGeneration().getPredictionWeights()[i];
            }
//            weights[generationNum - 1] = bestGeneration().getPredictionWeights();
            if (generationNum % 100000 == 0) {
                System.out.println("Generation Number: " + generationNum + " --- Successes: " + bestSuccess() + " --- Weights: " + Arrays.toString(bestGeneration().getPredictionWeights()));
            }
            if (bestSuccess() > passengers.size() * SUCCESS_PERCENTAGE) {
                properlyFit = true;
            }
        }
        double end = System.nanoTime();

        System.out.println((end - start) / 1000000000);

        try {
            File outputFile = new File("geneticAlg.csv");
            PrintWriter output = new PrintWriter(outputFile);
            String line;
            for (int i = 0; i < genNumber.length; i += 100000) {
                line = "";
                line += genNumber[i] + ", ";
                line += success[i];
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
