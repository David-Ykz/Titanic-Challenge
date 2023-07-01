import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    static ArrayList<Passenger> passengers = new ArrayList<>();

    public static void importPassengers() {
        CSVReader reader = new CSVReader();
        try {
            String fileName = "practice.csv";
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            String line;
            input.nextLine();
            while (input.hasNext()) {
                line = input.nextLine();
                passengers.add(new Passenger(reader.parseString(line)));
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }

    }

    public static void main(String[]args) {
        importPassengers();
        for (Passenger passenger : passengers) {
            passenger.printInfo();
        }

        Layer inputLayer = new Layer(3);
        NeuralNetwork network = new NeuralNetwork(inputLayer);

        network.addLayer(new Layer(4));
        network.addLayer(new Layer(2));

        network.printNetwork();
        double[] output = network.computeResult(new double[]{3, 1, 34});
        System.out.println(Arrays.toString(output));
    }


}
