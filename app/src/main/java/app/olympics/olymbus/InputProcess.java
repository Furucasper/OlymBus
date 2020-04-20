package app.olympics.olymbus;

import java.util.ArrayList;
import java.util.Scanner;

public class InputProcess
{
    private  ArrayList <String> allEvents = new ArrayList <>();                                     // Create ArrayList for Events
    private  ArrayList <String> allBus = new ArrayList <>();                                        // Create ArrayList for Buses
    private  ArrayList <String> allAccount = new ArrayList <>();                                    // Create ArrayList for Accounts

    public InputProcess (Scanner input)                                                             // Constructor with input in parameter
    {                                                                                               // Categorized with sequential access file. (might change to RandomAccessFile in next update)
        String line ;                                                                               // Declare line for each line read from input
        String[] Details ;                                                                          // Declare Array to split each category from each line
        String trimmedDetails = "";                                                                 //

        while (input.hasNextLine())
        {
            line = input.nextLine();
            if (line.startsWith("Event"))
            {
                Details = line.substring(10).split(",");
                for (int i = 0; i < Details.length; i++)
                {
                    trimmedDetails += Details[i].trim()+",";
                }
                allEvents.add(trimmedDetails);
                trimmedDetails = "";

            }
            else if (line.startsWith("Bus"))
            {
                Details = line.substring(7).split(",");
                for (int j = 0; j < Details.length; j++)
                {
                    trimmedDetails += Details[j].trim()+",";
                }
                allBus.add(trimmedDetails);
                trimmedDetails = "";
            }
            else if (line.startsWith("Account"))
            {
                Details = line.substring(11).split(",");
                for (int k = 0; k < Details.length; k++)
                {
                    trimmedDetails += Details[k].trim()+",";
                }
                allAccount.add(trimmedDetails);
                trimmedDetails = "";
            }
        }
    }

    public ArrayList<String> getEvent()
    {
        return allEvents;
    }

    public ArrayList<String> getBus()
    {
        return allBus;
    }

    public ArrayList<String> getAccount()
    {
        return allAccount;
    }
}