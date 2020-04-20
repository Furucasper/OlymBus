package app.olympics.olymbus.ui.home;

import java.io.Serializable;
import java.util.GregorianCalendar;

import app.olympics.olymbus.R;

public class EventItem implements Serializable {                                                    // Make these objects serializable

    private String event,category,discipline,venue,date,time, duration,byBus;                       // Declare String instance variables
    private GregorianCalendar gregolendar;                                                          // Declare Gregorain instance variables
    private int year,month,day,hour,min;                                                            // Declare integer instance variables

    public EventItem(){ }                                                                           // Empty constructor

    //Sport, Discipline, Category, Venue, Date, Start time, Duration, Bus travel time
    public EventItem(String event, String discipline, String category, String venue, String date, String time,String duration, String byBus) {
        this.event = event;                                                                         // Set each variables depends on each event
        this.category = category;
        this.discipline = discipline;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.byBus = byBus;

        String[] aDay = date.trim().split("\\.");                                             // Get event date
        String[] aTime = time.split("\\.");                                                   // Get event time

        year = Integer.parseInt(aDay[2])+2000;                                                      // Get event year (int)
        month = Integer.parseInt(aDay[1]);                                                          // Get event month (int)
        day = Integer.parseInt(aDay[0]);                                                            // Get event day (int)
        hour = Integer.parseInt(aTime[0]);                                                          // Get event hour (int)
        min = Integer.parseInt(aTime[1]);                                                           // Get event minutes (int)

        gregolendar = new GregorianCalendar(year, month, day, hour, min);                           // Create a new event calendar
    }

    public String getEvent() {                                                                      // Declare methods for-easy-to-access
        return event;
    }

    public String getCategory() {
        return category;
    }

    public String getDiscipline() {
        return discipline;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        String[] MONTH = {"January","February","March","April","May","June","July","August","September","October","November","December"};

        return ""+day+" "+MONTH[month-1]+" "+year; }

    public String getDateMonth() {

        String[] MONTH = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        return day+" "+MONTH[month-1];
    }


    public String getTime() {
        return time;
    }

    public String getInitialDate(){
        return date;
    }

    public String getDuration() { return duration; }

    public String getByBus() { return byBus; }

    public int getPic(){                                                                            // Method for displaying icons for each sports category

        switch (event.toLowerCase()){
            case "athletics" : return R.drawable.ic_athletics_pictogram;
            case "ceremony" : return R.drawable.ic_laurel;
            case "weightlifting" : return R.drawable.ic_weightlifting_pictogram;
            case "swimming" : return R.drawable.ic_swimming_pictogram;
            case "diving" : return R.drawable.ic_diving_pictogram;
            case "football" : return R.drawable.ic_football_pictogram;
            case "volleyball" : return R.drawable.ic_volleyball_pictogram;
            default : return R.drawable.ic_black_olympic_rings;
        }
    }

    public GregorianCalendar getGregolendar() {
        return gregolendar;
    }

}
