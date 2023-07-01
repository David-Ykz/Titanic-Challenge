import java.util.ArrayList;

public class Passenger {
    double passengerClass;
    double survived;
    double sex;
    double age;

    public Passenger(ArrayList<String> info) {
        this.survived = Double.parseDouble(info.get(1));
        this.passengerClass = Double.parseDouble(info.get(2));
        int start = -1;
        for (int i = 3; i < info.size(); i++) {
            if (info.get(i).equals("female") || info.get(i).equals("male")) {
                start = i;
            }
        }
        // Female is represented as 0, male is represented as 1
        if (info.toString().contains("female")) {
            this.sex = 0.0;
        } else {
            this.sex = 1.0;
        }
        this.age = Double.parseDouble(info.get(start + 1));
    }


    public void printInfo() {
        System.out.println("Class: " + Double.toString(passengerClass) + " -- Sex: " + Double.toString(sex) + " -- Age: " + Double.toString(age));
    }

}
