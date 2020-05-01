package app.olympics.olymbus.ui.booking;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.ui.home.EventItem;

public class Tickets implements Serializable
{
    private static int idnumber = 0;                                                                // Generate Ticket ID
    private EventItem event;                                                                        // Declare Event instance variables
    private BusItem bus;                                                                            // Declare Bus instance variables
    private int id;                                                                                 // Declare Integer instance variables
    private String type, destination, date, seatNo, price, sid, aid, eventName, depart, arrive, eventCategory, eventDiscipline, bookingTime;// Declare String instance variables
    private boolean available, newTicket, oldTicket;                                                                      // Declare Boolean instance variables
    private GregorianCalendar gregoTicketDepart ,gregoTicketArrive ,gregoTicketTime;

    public Tickets (EventItem ev, BusItem bus, int sid, String seatNo, String aid)
    {
        idnumber++;                                                                                 // Set each instance variable depends on each ticket
        this.id = idnumber;
        this.aid = aid;
        this.event = ev;
        this.bus = bus;
        this.type = bus.getType();
        this.destination = ev.getVenue();
        this.date = ev.getInitialDate();
        this.depart = bus.getDepart();
        this.arrive = bus.getArrive();
        this.price = bus.getCost();
        this.sid = sid+"";
        this.seatNo = seatNo;
        this.eventName = ev.getEvent();
        this.eventCategory = ev.getCategory();
        this.eventDiscipline = ev.getDiscipline();
        this.available = true;
        this.gregoTicketArrive = bus.getGregoarrive();
        this.gregoTicketDepart = bus.getGregodepart();
        this.newTicket = true;
    }

    public void setBookingTime (String time) { this.bookingTime = time; }

    public boolean isOldTicket() { return oldTicket; }

    public void setOldTicket() { this.oldTicket = true; }

    public String getTicketID() { return id+""; }

    public String getSeatNo() { return seatNo; }                                                    // Declare methods for-easy-to-access

    public String getSid() { return sid; }

    public String getOwnerID() { return aid; }

    public String getTicketPrice() { return price; }

    public String getTicketBusType() { return type; }

    public String getTicketDestination() { return destination; }

    public String getTicketDate() { return date; }

    public String getBookingTime() { return bookingTime; }

    public String getTicketEventName() { return eventName; }

    public String getTicketEventCategory() { return eventCategory; }

    public String getTicketEventDiscipline() { return eventDiscipline; }

    public String getTicketDepart() { return depart; }

    public String getTicketArrive() { return arrive; }

    public EventItem getTicketEvent() { return event; }

    public BusItem getTicketBus() { return bus; }

    public void setGregoTicketTime(int yyyy, int MM, int dd, int HH, int mm, int ss){
        this.gregoTicketTime = new GregorianCalendar(yyyy,MM,dd,HH,mm,ss);
    }

    public void setGregoTicketTime(GregorianCalendar bookedTime){
        this.gregoTicketTime = bookedTime;

        String time = "";
        int MM,dd,HH,mm,ss;

        MM = gregoTicketTime.get(Calendar.MONTH);
        dd = gregoTicketTime.get(Calendar.DATE);
        HH = gregoTicketTime.get(Calendar.HOUR);
        mm = gregoTicketTime.get(Calendar.MINUTE);
        ss = gregoTicketTime.get(Calendar.SECOND);

        time += gregoTicketTime.get(Calendar.YEAR)+".";
        time += (MM<10)? "0"+MM+"." : MM+".";
        time += (dd<10)? "0"+dd+" " : dd+" ";
        time += (HH<10)? "0"+HH+":" : HH+":";
        time += (mm<10)? "0"+mm+":" : mm+":";
        time += (ss<10)? "0"+ss : ss;


        bookingTime = time;
    }

    public GregorianCalendar getGregoTicketTime(){ return gregoTicketTime; }

    public GregorianCalendar getGregoTicketDepart(){ return gregoTicketArrive; }

    public GregorianCalendar getGregoTicketArrive(){ return  gregoTicketArrive; }

    public Boolean isAvailable() {return available; }

    public Boolean isNewTicket() {return newTicket; }

    public void setNormalTicket() { this.newTicket = false; }

    public void setUnavailable() { this.available = false;}

    public String getTicketStatus() {
        if (available) {
            return "Available";
        } else {
            return "Cancelled";
        }
    }

    @Override
    public String toString(){
        String detail = event.toString()+"\n";
        detail += "\t\t[ Bus : "+bus.getBusID()+", "+bus.getType()+", "+bus.getDepart()+", "+
                bus.getBusDuration()+", "+bus.getRows()+", "+bus.getCols()+", "+bus.getCost()+" ]\n";
        String bookedDate = "";
        for (int i = 0; i <getTicketBus().getBookedSeats().size(); i++){
            if (getSid().equals(getTicketBus().getBookedSeats().get(i)[0])) {
                bookedDate = getTicketBus().getBookedSeats().get(i)[2];
            }
        }
        detail += "\t\t[ Seat : "+getSeatNo()+" Cost : "+getTicketPrice()+ " Booked in : "+bookedDate+" ]\n";
        return  detail;
    }
}
