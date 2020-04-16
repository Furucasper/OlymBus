package app.olympics.olymbus;

import java.sql.Time;

public class BusItem {

    private String type, destination,depart,arrive;
    private String cost;
    private int  rows, cols, availableSeats, duration;
    private Time busDepart,busArrive;

    //String type, String destination, String depart, int duration, int rows, int cols, double cost
    public BusItem(String type, String destination, String depart, String duration, String rows, String cols, String cost) {
        this.type = type;
        this.destination = destination;
        this.depart = depart;
        this.duration = Integer.parseInt(duration);
        this.cost = cost.substring(1);
        this.rows = Integer.parseInt(rows);
        this.cols = Integer.parseInt(cols);
        this.availableSeats = this.cols*this.rows; //default

        String aTime[] = depart.split("\\.");

        int hour = Integer.parseInt(aTime[0]);
        int min = Integer.parseInt(aTime[1]);
        //demo
        int minA = min+this.duration;
        arrive = (min+this.duration<60)? hour+"."+(min+this.duration): (hour+1)+"."+((min+this.duration)-60);
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

    public int getAvailableSeats(){
        return availableSeats;
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

    public String getCost() {
        return cost;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
