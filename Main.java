import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Main {


    public static ArrayList<double[]> readPassengers() {
        ArrayList<double[]> data = new ArrayList<>();
        try {
            String fileName = "practice.csv";
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
                    data.add(new double[]{pClass, gender, age, survival});
                    System.out.println("Class: " + Double.toString(pClass) + " -- Gender: " + Double.toString(gender) + " -- Age: " + Double.toString(age) + " -- Survival: " + Double.toString(survival));

                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println(lineNum);
                }
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }
        return data;
    }


    public static void main(String[]args) {
        ArrayList<double[]> passengers = readPassengers();

        Layer inputLayer = new Layer(3);
        NeuralNetwork network = new NeuralNetwork(inputLayer);

        network.addLayer(new Layer(4));
        network.addLayer(new Layer(1));

        network.printNetwork();

        System.out.println("");
        System.out.println("");
        System.out.println("");

        System.out.println(network.computeError(passengers));

    }


}
