package app.olympics.olymbus.ui.profile;

import java.io.Serializable;
import java.util.ArrayList;

import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.ui.booking.Tickets;

public class AccountItem implements Serializable
{
    String username,password,cardNo,CSV;                                                            // Declare String instance variables
    ArrayList <Tickets> tickets = new ArrayList <> ();                                              // Make new Ticket ArrayList that user owns
    ArrayList <Tickets> cancelled_tickets = new ArrayList <> ();                                    // Make new Ticket ArrayList that user has been cancelled
    ArrayList <BusItem> bookedBus = new ArrayList<> ();                                             // Make new Bus ArrayList that user booked
    ArrayList <BusItem> Maxed_Quota_Bus = new ArrayList<> ();                                       // Make new Bus ArrayList that user cannot booked anymore

    public AccountItem(){ }                                                                         // Empty constructor

    public AccountItem(String username, String password, String card, String cvc)                   // Constructor with each every account's details
    {
        this.username = username;                                                                   //set each instance variable depends on each account
        this.password = password;
        this.cardNo = card;
        this.CSV = cvc;
    }

    public void addTicket (Tickets t)                                                               // Add Ticket to User account
    {
        tickets.add(t);
        if (bookedBus.contains(t.getTicketBus()))                                                   // Check if this bus has been book before by this user
        {
            Maxed_Quota_Bus.add(t.getTicketBus());                                                  // Transfer this bus to bus that this user maxed out quota
            bookedBus.remove(t.getTicketBus());
        }
        else bookedBus.add(t.getTicketBus());                                                       // Add the bus to bookedBus Array
    }

    public void cancelTicket (Tickets t)                                                            // Cancel one of user's ticket
    {
        tickets.remove(t);
        if (Maxed_Quota_Bus.contains(t.getTicketBus()))                                             // Check if this bus's quota has been maxed out by this user
        {
            bookedBus.add(t.getTicketBus());                                                        // Transfer this bus to booked bus
            Maxed_Quota_Bus.remove(t.getTicketBus());
        }
        else bookedBus.remove(t.getTicketBus());                                                    // Remove the ticket from owned ticket

    }

    public String getUsername() { return username; }                                                // Declare methods for-easy-to-access

    public String getPassword() { return password; }

    public String getCardNumber() { return cardNo;}

    public String getCSV() { return CSV; }

    public ArrayList<Tickets> getTickets () { return tickets; }

    public ArrayList<BusItem> getBookedBus () { return bookedBus; }

    public ArrayList<BusItem> getMaxedQuotaBus () { return Maxed_Quota_Bus; }

}
