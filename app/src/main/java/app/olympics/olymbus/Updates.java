package app.olympics.olymbus;

import java.util.ArrayList;
import java.util.Scanner;

public class Updates {
    private ArrayList<String> allTicketsHistory = new ArrayList <>();                                     // Create ArrayList for Events
    private  ArrayList <String> allBookedBusesUpdates = new ArrayList <>();                                        // Create ArrayList for Buses
    private  ArrayList <String> allMaxedQuotaBusesUpdates = new ArrayList <>();
    private  ArrayList <String> allAccountsUpdates = new ArrayList <>();                                    // Create ArrayList for Accounts

    public Updates (Scanner update){
        String line ;                                                                               // Declare line for each line read from input
        String[] Details ;                                                                          // Declare Array to split each category from each line
        String trimmedDetails = "";                                                                 // Keep an easy to read details for each category

        while (update.hasNextLine())                                                                 // Loop until input reach it's end
        {
            line = update.nextLine();                                                                // change String in line each time read
            if (line.startsWith("Ticket"))                                                           // Check if it an event category
            {
                Details = line.substring(8).split(",");                                      // get rid of headers and split for sub category sorting
                for (int i = 0; i < Details.length; i++)                                            // Loop trimming each details
                {
                    trimmedDetails += Details[i].trim()+",";
                }
                allTicketsHistory.add(trimmedDetails);                                                      // Add good-looking event data to an ArrayList
                trimmedDetails = "";

            }
            else if (line.startsWith("Bus"))                                                        // Same steps with events
            {
                Details = line.substring(5).split(",");
                for (int j = 0; j < Details.length; j++)
                {
                    trimmedDetails += Details[j].trim()+",";
                }
                allBookedBusesUpdates.add(trimmedDetails);
                trimmedDetails = "";
            }
            else if (line.startsWith("Account"))                                                    // Same steps with events and buses
            {
                Details = line.substring(9).split(",");
                for (int k = 0; k < Details.length; k++)
                {
                    trimmedDetails += Details[k].trim()+",";
                }
                allAccountsUpdates.add(trimmedDetails);
                trimmedDetails = "";
            }
        }
    }

    public ArrayList<String> getAllTickets() { return allTicketsHistory; }                                       //Declare methods for easy-to-access

    public ArrayList<String> getAllBookedBusUpdates()
    {
        return allBookedBusesUpdates;
    }

    public ArrayList<String> getAllMaxedQuotaBusesUpdates() { return allBookedBusesUpdates; }

    public ArrayList<String> getAccountUpdates()
    {
        return allAccountsUpdates;
    }

}
