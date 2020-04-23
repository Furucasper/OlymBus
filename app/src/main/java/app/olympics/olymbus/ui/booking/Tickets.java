package app.olympics.olymbus.ui.booking;

import java.util.GregorianCalendar;

import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.ui.home.EventItem;

public class Tickets
{
    private static int idnumber = 0;                                                                // Generate Ticket ID
    private EventItem event;                                                                        // Declare Event instance variables
    private BusItem bus;                                                                            // Declare Bus instance variables
    private int id;                                                                                 // Declare Integer instance variables
    private String type, destination, date, seatNo, price, sid, eventName, depart, arrive;          // Declare String instance variables

    public Tickets (EventItem ev, BusItem bus, int sid, String seatNo)
    {
        idnumber++;                                                                                 // Set each instance variable depends on each ticket
        this.id = idnumber;
        this.event = ev;
        this.bus = bus;
        this.type = bus.getType();
        this.destination = ev.getVenue();
        this.date = ev.getDate();
        this.depart = bus.getGregodepart().toString();
        this.arrive = bus.getGregoarrive().toString();
        this.price = bus.getCost();
        this.sid = sid+"";
        this.seatNo = seatNo;
        this.eventName = ev.getEvent()+"\n"+ev.getCategory()+"\n"+ev.getDiscipline();
    }

    public String getSeatNo() { return seatNo; }                                                    // Declare methods for-easy-to-access

    public String getSid() { return sid; }

    public String getTicketPrice() { return price; }

    public String getTicketBusType() { return type; }

    public String getTicketDestination() { return destination; }

    public String getTicketDate() { return date; }

    public String getTicketEventName() { return eventName; }

    public String getTicketDepart() { return depart; }

    public String getTicketArrive() { return arrive; }

    public EventItem getTicketEvent() { return event; }

    public BusItem getTicketBus() { return bus; }
}
