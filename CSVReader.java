import java.util.ArrayList;

public class CSVReader {
    public static int findLastQuotation(String message) {
        int firstComma = message.indexOf(",");
        int nextComma = message.substring(firstComma + 1).indexOf(",");
        return nextComma - 1;
    }
    public static ArrayList<String> parseString(String inputMessage) {
        ArrayList<String> outputMessage = new ArrayList<>();
        while (inputMessage.contains(",")) {
            if (inputMessage.charAt(0) == '"') {
                inputMessage = inputMessage.substring(1); // Removes the first "
                outputMessage.add(inputMessage.substring(0, inputMessage.indexOf('"')));
                inputMessage = inputMessage.substring(inputMessage.indexOf('"') + 2); // Removes the last " and the comma
            } else {
                String str = inputMessage.substring(0, inputMessage.indexOf(','));
                if (str.equals("")) {
                    str = "0";
                }
                outputMessage.add(str);
                inputMessage = inputMessage.substring(inputMessage.indexOf(',') + 1);
            }
        }
        outputMessage.add(inputMessage);
        return outputMessage;
    }


    public static void main(String[]args) {
        System.out.println(findLastQuotation("\"Ford, Miss. Robina Maggie \"\"Ruby\"\"\","));
        parseString("148,0,3,\"Ford, Miss. Robina Maggie \"\"Ruby\"\"\",female,9,2,2,W./C. 6608,34.375,,S");
    }
}