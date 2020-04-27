package app.olympics.olymbus.ui.booking;

import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.ui.home.EventItem;

public class Tickets
{
    private static int idnumber = 0;                                                                // Generate Ticket ID
    private EventItem event;                                                                        // Declare Event instance variables
    private BusItem bus;                                                                            // Declare Bus instance variables
    private int id;                                                                                 // Declare Integer instance variables
    private String type, destination, date, seatNo, price, sid, eventName, depart, arrive, eventCategory, eventDiscipline;// Declare String instance variables
    private boolean available;                                                                      // Declare Boolean instance variables

    public Tickets (EventItem ev, BusItem bus, int sid, String seatNo)
    {
        idnumber++;                                                                                 // Set each instance variable depends on each ticket
        this.id = idnumber;
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
    }

    public String getSeatNo() { return seatNo; }                                                    // Declare methods for-easy-to-access

    public String getSid() { return sid; }

    public String getTicketPrice() { return price; }

    public String getTicketBusType() { return type; }

    public String getTicketDestination() { return destination; }

    public String getTicketDate() { return date; }

    public String getTicketEventName() { return eventName; }

    public String getTicketEventCategory() { return eventCategory; }

    public String getTicketEventDiscipline() { return eventDiscipline; }

    public String getTicketDepart() { return depart; }

    public String getTicketArrive() { return arrive; }

    public EventItem getTicketEvent() { return event; }

    public BusItem getTicketBus() { return bus; }

    public Boolean isAvailable() {return available; }

    public String getTicketStatus() {
        if (available) {
            return "available.";
        } else {
            return "unavailable.";
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
