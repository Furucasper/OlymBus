package app.olympics.olymbus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class BusItem implements Serializable {

    private String type, destination,depart,arrive,cost;                                            //Declare String instance variables
    private int  rows, cols, maximumSeats, availableSeats, duration, id, idGen;                     //Declare Integer instance variables
    private GregorianCalendar gregoarrive, gregodepart;                                             //Declare Time instance variables
    private String[] booking = new String[3];                                                       //Declare String Array to store booking details
    private ArrayList <String[]> bookedSeat = new ArrayList <> ();                                  //Make String ArrayList to store all booked seats for the bus

    //String type, String destination, String depart, int duration, int rows, int cols, double cost
    public BusItem(String type, String destination, String depart, String duration, String rows, String cols, String cost, String date) {
        idGen++;
        this. id = idGen;
        this.type = type;
        this.destination = destination;
        this.duration = Integer.parseInt(duration);
        this.cost = cost.substring(1);
        this.rows = Integer.parseInt(rows);
        this.cols = Integer.parseInt(cols);
        this.availableSeats = this.cols*this.rows; //default
        this.maximumSeats = this.cols*this.rows;

        String[] aDay = date.trim().split("\\.");
        String[] aTime = depart.split("\\.");

        int year = Integer.parseInt(aDay[2]);
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

    public void bookSeat (String sid, String aid, String time)
    {
        booking[0] = sid;
        booking[1] = aid;
        booking[2] = time;
        bookedSeat.add(booking);
        availableSeats--;
    }

    public void cancelSeat (String sid, String aid)
    {
        booking[0] = sid;
        booking[1] = aid;
        bookedSeat.remove(booking);
        availableSeats++;
    }

    @Override
    public String toString(){
        String detail =  "[ Bus : "+id+", "+type+", "+destination+", "+depart+", "+duration+" ]\n";
        detail += "\t[ Seat booked : " + (maximumSeats - availableSeats) + " ]\n";
        for (int i = 0; i < bookedSeat.size(); i++){
            detail += "[ Seat : "+ bookedSeat.get(i)[0] + "Owner : " + bookedSeat.get(i)[1] + "Booked in : " + bookedSeat.get(i)[2] + " ]\n" ;
        }
        return detail;
    }

    public String getBusID() { return id+""; }

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

    public String getArrive() {
        return arrive;
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

    public ArrayList<String[]> getBookedSeats (){
        return bookedSeat;
    }

}
