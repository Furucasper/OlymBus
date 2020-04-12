package app.olympics.olymbus;

import java.util.ArrayList;
import java.util.Scanner;

public class InputProcess {
    private  ArrayList <String> allEvents = new ArrayList <>();
    private  ArrayList <String> allBus = new ArrayList <>();
    private  ArrayList <String> allAccount = new ArrayList <>();

    public InputProcess (Scanner input)
    {
        String line ;
        String[] Details ;
        String trimmedDetails = "";

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
}
