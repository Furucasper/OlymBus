package app.olympics.olymbus;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BusItem implements Serializable {

    private String type, destination,depart,arrive;
    private String cost;
    private int  rows, cols, availableSeats, duration;
    private Time busDepart,busArrive;
    private GregorianCalendar gregoarrive, gregodepart;

    //String type, String destination, String depart, int duration, int rows, int cols, double cost
    public BusItem(String type, String destination, String depart, String duration, String rows, String cols, String cost, String date) {
        this.type = type;
        this.destination = destination;

        this.duration = Integer.parseInt(duration);
        this.cost = cost.substring(1);
        this.rows = Integer.parseInt(rows);
        this.cols = Integer.parseInt(cols);
        this.availableSeats = this.cols*this.rows; //default

        String[] aDay = date.trim().split("\\.");
        String[] aTime = depart.split("\\.");

        int year = Integer.parseInt(aDay[2])+2000;
        int month = Integer.parseInt(aDay[1]);
        int day = Integer.parseInt(aDay[0]);
        int hour = Integer.parseInt(aTime[0]);
        int min = Integer.parseInt(aTime[1]);
        this.depart = hour+"."+((min<10)? "0"+min : min);
        gregodepart = new GregorianCalendar(year, month, day, hour, min);
        gregoarrive = new GregorianCalendar(year, month, day, hour, min);
        gregoarrive.add(Calendar.MINUTE, this.duration);
        int minA = min+this.duration;
        arrive = (minA<60)? hour+"."+((minA<10)?"0"+minA : minA):
                (hour+1)+"."+(((minA-60)<10)? "0"+(minA-60): minA-60 );


    }

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

    public GregorianCalendar getGregoarrive() {
        return gregoarrive;
    }

    public GregorianCalendar getGregodepart() {
        return gregodepart;
    }
}
