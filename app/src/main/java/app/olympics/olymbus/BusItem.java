package app.olympics.olymbus;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BusItem implements Serializable {

    private String id, type, destination,depart,arrive,cost;                                        //Declare String instance variables
    private int  rows, cols, maximumSeats, availableSeats, duration;                                //Declare Integer instance variables
    private GregorianCalendar gregoarrive, gregodepart;                                             //Declare Time instance variables
    private String[] booking;                                                                       //Declare String Array to store booking details
    private List<String[]> bookedSeat = new CopyOnWriteArrayList<>();                               //Make String ArrayList to store all booked seats for the bus
    private boolean isBooked = false;

    //String type, String destination, String depart, int duration, int rows, int cols, double cost
    public BusItem(String bid, String type, String destination, String depart, String duration, String rows, String cols, String cost, String date) {
        this. id = bid;
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
        booking = new String[3];
        booking[0] = sid;
        booking[1] = aid;
        booking[2] = time;
        isBooked = true;

        boolean seatNoBooked = true;
        for(String[] bs : bookedSeat){
            if(bs[0].equals(sid)){
                seatNoBooked = false;
                break;
            }
        }

        if(seatNoBooked){
            bookedSeat.add(booking);
            availableSeats--;
        }
    }

    public void cancelSeat (String sid, String aid)
    {
        for (int i = 0; i < bookedSeat.size(); i++){
            if(bookedSeat.get(i)[0].equals(sid) && bookedSeat.get(i)[1].equals(aid)){
                bookedSeat.remove(i);
            }
        }
        availableSeats++;
        if(availableSeats == maximumSeats) { isBooked = false; }
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

    public String getBusID() { return id; }

    public boolean isBooked() {
        return isBooked;
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

    public List<String[]> getBookedSeats (){
        return bookedSeat;
    }

}
