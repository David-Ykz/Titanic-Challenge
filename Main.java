import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static HashSet<Passenger> passengers = new HashSet<>();
    static ArrayList<Generation> generations = new ArrayList<>();
    static final int CUTOFF = 5;
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
                System.out.println(line);
                System.out.println(reader.parseString(line));
                passengers.add(new Passenger(reader.parseString(line)));
            }
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }

    }

    public static ArrayList<Generation> findTopGenerations() {
        Collections.sort(generations, new SortBySuccess());
        return new ArrayList<>(generations.subList(0, CUTOFF - 1));
    }

    public static void repopulate(ArrayList<Generation> newGeneration) {
        generations.clear();
        for (int i = 0; i < CAPACITY; i++) {
            int randIndex = (int)(Math.random() * CUTOFF); // Picks a random generation
            generations.add(new Generation(newGeneration.get(randIndex)));
        }
    }

    public static int bestSuccess() {
        Collections.sort(generations, new SortBySuccess());
        return generations.get(0).getNumSuccesses();
    }

    public static void main(String[]args) {
        importPassengers();
        System.out.println(passengers.size());
        boolean properlyFit = false;
        int generationNum = 0;
        while (!properlyFit || generationNum >= 1000) {
            generationNum++;
            System.out.println("Generation Number: " + generationNum + " --- Successes: " + bestSuccess());
            if (bestSuccess() > passengers.size() * SUCCESS_PERCENTAGE) {
                properlyFit = true;
            }
            repopulate(findTopGenerations());
        }


    }


}
