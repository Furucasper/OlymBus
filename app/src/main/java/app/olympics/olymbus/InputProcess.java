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
        String trimmedDetails = "";                                                                 // Keep an easy to read details for each category

        while (input.hasNextLine())                                                                 // Loop until input reach it's end
        {
            line = input.nextLine();                                                                // change String in line each time read
            if (line.startsWith("Event"))                                                           // Check if it an event category
            {
                trimmedDetails += line.substring(5).split(":")[0].trim()+",";
                Details = line.substring(10).split(",");                                      // get rid of headers and split for sub category sorting
                for (int i = 0; i < Details.length; i++)                                            // Loop trimming each details
                {
                    trimmedDetails += Details[i].trim()+",";
                }
                allEvents.add(trimmedDetails);                                                      // Add good-looking event data to an ArrayList
                trimmedDetails = "";

            }
            else if (line.startsWith("Bus"))                                                        // Same steps with events
            {
                trimmedDetails += line.substring(3).split(":")[0].trim()+",";
                Details = line.substring(7).split(",");
                for (int j = 0; j < Details.length; j++)
                {
                    trimmedDetails += Details[j].trim()+",";
                }
                allBus.add(trimmedDetails);
                trimmedDetails = "";
            }
            else if (line.startsWith("Account"))                                                    // Same steps with events and buses
            {
                trimmedDetails += line.substring(7).split(":")[0].trim()+",";
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

    public ArrayList<String> getEvent() { return allEvents; }                                       //Declare methods for easy-to-access

    public ArrayList<String> getBus()
    {
        return allBus;
    }

    public ArrayList<String> getAccount()
    {
        return allAccount;
    }
}