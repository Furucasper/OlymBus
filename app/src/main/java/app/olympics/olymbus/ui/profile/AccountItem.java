package app.olympics.olymbus.ui.profile;

import java.io.Serializable;
import java.util.ArrayList;

import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.MainActivity;
import app.olympics.olymbus.ui.booking.Tickets;

public class AccountItem implements Serializable
{

    private String username,password,cardNo,CSV;                                                    // Declare String instance variables
    private int id, idGen;                                                                          // Declare Integer instance variables
    private ArrayList <Tickets> ticketsHistory = new ArrayList <> ();
    private ArrayList <Tickets> active_tickets = new ArrayList <> ();                                      // Make new Ticket ArrayList that user owns
    private ArrayList <Tickets> cancelled_tickets = new ArrayList <> ();                            // Make new Ticket ArrayList that user has been cancelled
    private ArrayList <BusItem> busHistory = new ArrayList <> ();
    private ArrayList <BusItem> bookedBus = new ArrayList<> ();                                     // Make new Bus ArrayList that user booked
    private ArrayList <BusItem> Maxed_Quota_Bus = new ArrayList<> ();                               // Make new Bus ArrayList that user cannot booked anymore

    public AccountItem(){ }                                                                         // Empty constructor

    public AccountItem(String username, String password, String card, String cvc)                   // Constructor with each every account's details
    {
        idGen++;                                                                                    //set each instance variable depends on each account
        this. id = idGen;
        this.username = username;
        this.password = password;
        this.cardNo = card;
        this.CSV = cvc;
    }

    public void addTicket (Tickets t)                                                               // Add Ticket to User account
    {
        active_tickets.add(t);
        ticketsHistory.add(t);
        busHistory.add(t.getTicketBus());
        if (bookedBus.contains(t.getTicketBus()))                                                   // Check if this bus has been book before by this user
        {
            Maxed_Quota_Bus.add(t.getTicketBus());                                                  // Transfer this bus to bus that this user maxed out quota
            bookedBus.remove(t.getTicketBus());
        }
        else bookedBus.add(t.getTicketBus());                                                       // Add the bus to bookedBus Array
    }

    public void cancelTicket (Tickets t)                                                            // Cancel one of user's ticket
    {
        active_tickets.remove(t);
        if (Maxed_Quota_Bus.contains(t.getTicketBus()))                                             // Check if this bus's quota has been maxed out by this user
        {
            bookedBus.add(t.getTicketBus());                                                        // Transfer this bus to booked bus
            Maxed_Quota_Bus.remove(t.getTicketBus());
        }
        else bookedBus.remove(t.getTicketBus());                                                    // Remove the ticket from owned ticket
        cancelled_tickets.add(t);
        t.getTicketBus().cancelSeat(t.getSid(),id+"");
        t.setUnavailable();
    }

    public String getAccountID() { return id+""; }                                                  // Declare methods for-easy-to-access

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getCardNumber() { return cardNo;}

    public String getCSV() { return CSV; }

    public ArrayList<Tickets> getTickets () { return active_tickets; }

    public ArrayList<Tickets> getTicketsHistory () { return ticketsHistory; }

    public ArrayList<Tickets> getCancelledTickets () { return cancelled_tickets; }

    public ArrayList<BusItem> getBusHistory () { return busHistory; }

    public ArrayList<BusItem> getBookedBus () { return bookedBus; }

    public ArrayList<BusItem> getMaxedQuotaBus () { return Maxed_Quota_Bus; }

    @Override
    public String toString(){
        String detail = "[ID : " +id +" ]\n";
        for (int i = 0; i < active_tickets.size(); i++){
            detail += "\t[Ticket : " + i +" ]\n";
            detail += "\t\t"+active_tickets.get(i).toString() + "\n";
        }
        return detail;
    }

}
