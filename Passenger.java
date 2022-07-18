import java.util.ArrayList;

public class Passenger {
    private int id;
    private int survived;
    private int passengerClass;
    private String sex;
    private double age;
    private int numSiblings;
    private int numParents;
    private String ticket;
    private double fare;
    private String cabin;

    public Passenger(ArrayList<String> info) {
        this.id = Integer.parseInt(info.get(0));
        this.survived = Integer.parseInt(info.get(1));
        this.passengerClass = Integer.parseInt(info.get(2));
        int start = -1;
        for (int i = 3; i < info.size(); i++) {
            if (info.get(i).equals("female") || info.get(i).equals("male")) {
                start = i;
            }
        }
        this.sex = info.get(start);
        this.age = Double.parseDouble(info.get(start + 1));
        this.numSiblings = Integer.parseInt(info.get(start + 2));
        this.numParents = Integer.parseInt(info.get(start + 3));
        this.ticket = info.get(start + 4);
        this.fare = Double.parseDouble(info.get(start + 5));
        this.cabin = info.get(start + 6);
    }

    public double calculateSurvival(double[] predictionWeights) {
        double survivalChance = 0.0;
        survivalChance += (passengerClass - 2) * predictionWeights[0];
        if (sex.equals("female")) {
            survivalChance += 1 * predictionWeights[1];
        } else {
            survivalChance += 1 * predictionWeights[2];
        }
        if (age < 18 || age > 40) {
            survivalChance += age * predictionWeights[3];
        } else {
            survivalChance -= age * predictionWeights[3];
        }
        survivalChance += numSiblings * predictionWeights[4];
        survivalChance += numParents * predictionWeights[5];
        return survivalChance;
    }
























    public int getId() {
        return id;
    }
    public int getSurvived() {
        return survived;
    }
    public int getPassengerClass() {
        return passengerClass;
    }
    public String getSex() {
        return sex;
    }
    public int getNumSiblings() {
        return numSiblings;
    }
    public int getNumParents() {
        return numParents;
    }
}
