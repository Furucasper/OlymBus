package app.olympics.olymbus;

import java.sql.Time;

public class BusItem {

    private String type, destination,depart,arrive;
    private double cost;
    private int  rows, cols, availableSeats, duration;
    private Time busDepart,busArrive;

    //String type, String destination, String depart, int duration, int rows, int cols, double cost
    public BusItem(String type, String destination, String depart, int duration, int rows, int cols, double cost) {
        this.type = type;
        this.destination = destination;
        this.depart = depart;
        this.duration = duration;
        this.cost = cost;
        this.rows = rows;
        this.cols = cols;
        this.availableSeats = cols*rows; //default

        String aTime[] = depart.split("\\.");

        int hour = Integer.parseInt(aTime[0]);
        int min = Integer.parseInt(aTime[1]);
        //demo
        int minA = min+duration;
        arrive = (min+duration<60)? hour+"."+(min+duration): (hour+1)+"."+((min+duration)-60);
        //busDepart = new Time(hourD,minD,0);


        //busArrive = busDepart.plusMinutes(duration);

    }

    /*
    public String getBusDuration(){
        return busDepart.getHour() + "." + busDepart.getMinute() + " - " + busArrive.getHour() + "." + busArrive.getMinute();
    }
     */

    @Override
    public String toString(){
        return  "Bus : "+type+
                "\nDestination : "+destination+
                "\nDepart : "+depart+
                "\nDuration : "+duration+" min"+
                "\nCost : "+cost;
    }

    public String getType() {
        return type;
    }

    public String getBusSeats(){
        return (rows*cols)+" Seats";
    }

    public String getAvailableSeats(){
        return ""+availableSeats;
    }

    public void setAvailableSeats(int availableSeats){
        this.availableSeats = availableSeats;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepart() {
        return depart;
    }

    public String getBusDuration() {
        return depart+" - "+arrive;
    }

    public double getCost() {
        return cost;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
