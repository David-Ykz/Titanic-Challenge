import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {
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
                System.out.println(line);
                System.out.println(reader.parseString(line));
                passengers.add(new Passenger(reader.parseString(line)));
            }
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }

    }


    public static void main(String[]args) {
        importPassengers();
        System.out.println(passengers.size());
    }


}
