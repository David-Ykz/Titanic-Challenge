import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVReader {
    public static double[][] readFeatures(String fileName) {
        ArrayList<double[]> data = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            input.nextLine();
            while (input.hasNext()) {
                String[] line = input.nextLine().split(",");
                double pClass = Double.parseDouble(line[1]);
                double gender = Double.parseDouble(line[2]);
                double age = Double.parseDouble(line[3]);
                double sibsp = Double.parseDouble(line[4]);
                double parch = Double.parseDouble(line[5]);
                double fare = Double.parseDouble(line[6]);
                double embarked = Double.parseDouble(line[7]);
                data.add(new double[] {pClass, gender, age, sibsp, parch, fare, embarked});
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }
        double[][] arr = new double[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = data.get(i);
        }
        return arr;
    }

    public static double[] readResults(String fileName, int numResults) {
        double[] arr = new double[numResults];
        try {
            File file = new File(fileName);
            Scanner input = new Scanner(file);
            input.nextLine();
            while (input.hasNext()) {
                String[] line = input.nextLine().split(",");
                int id = Integer.parseInt(line[0]);
                double survived = Double.parseDouble(line[1]);
                arr[id - 1] = survived;
            }
            input.close();
        } catch (Exception e) {
            System.out.println("Error Reading File");
            System.out.println(e);
        }
        return arr;
    }

}