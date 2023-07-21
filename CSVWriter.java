import java.io.File;
import java.io.PrintWriter;

public class CSVWriter {

    public static void writeFile(String fileName, int[] predictions) {
        try {
            File outputFile = new File(fileName);
            PrintWriter output = new PrintWriter(outputFile);
            output.println("PassengerId,Survived");
            for (int i = 0; i < predictions.length; i++) {
                output.println(Integer.toString(i + 892) + "," + Integer.toString(predictions[i]));
            }
            output.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("dofjkghdfkjgdfhg");
        }

    }
}
