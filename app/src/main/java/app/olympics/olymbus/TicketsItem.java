package app.olympics.olymbus;

import java.util.ArrayList;

import app.olympics.olymbus.ui.booking.Tickets;

public class TicketsItem
{
    private ArrayList<Tickets> allTickets = new ArrayList<>();

    public TicketsItem (){

    }

    public void registTicket (Tickets t) { allTickets.add(t); }

    public ArrayList<Tickets> getAllTickets() { return allTickets; }
}
