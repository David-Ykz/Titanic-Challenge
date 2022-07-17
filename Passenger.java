import java.util.ArrayList;

public class Passenger {
    private int id;
    private int survived;
    private int passengerClass;
    private String name;
    private String sex;
    private int numSiblings;
    private int numParents;
    private String ticket;
    private double fare;
    private String cabin;
    private String embarkLocation;

    public Passenger(ArrayList<String> info) {
        this.id = Integer.parseInt(info.get(0));
        this.survived = Integer.parseInt(info.get(1));
        this.passengerClass = Integer.parseInt(info.get(2));
        this.name = info.get(3) + info.get(4);
        this.sex = info.get(5);
        if (info.get(6).equals("")) {
            this.numSiblings = 0;
        } else {
            this.numSiblings = Integer.parseInt(info.get(6));
        }
        this.numParents = Integer.parseInt(info.get(7));
        this.ticket = info.get(8);
        this.fare = Double.parseDouble(info.get(9));
        this.cabin = info.get(10);
        this.embarkLocation = info.get(11);
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
    public String getName() {
        return name;
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
    public String getEmbarkLocation() {
        return embarkLocation;
    }
}
