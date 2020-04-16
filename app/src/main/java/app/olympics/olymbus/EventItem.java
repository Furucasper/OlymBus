package app.olympics.olymbus;

import java.util.GregorianCalendar;

public class EventItem {

    String event,category,discipline,venue,date,time, duration,byBus;
    private GregorianCalendar gregolendar;
    private int year,month,day,hour,min;

    public EventItem(){

    }
    //Sport, Discipline, Category, Venue, Date, Start time, Duration, Bus travel time
    public EventItem(String event, String discipline, String category, String venue, String date, String time,String duration, String byBus) {
        this.event = event;
        this.category = category;
        this.discipline = discipline;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.byBus = byBus;

        String[] aDay = date.trim().split("\\.");
        String[] aTime = time.split("\\.");

        year = Integer.parseInt(aDay[2])+2000;
        month = Integer.parseInt(aDay[1]);
        day = Integer.parseInt(aDay[0]);
        hour = Integer.parseInt(aTime[0]);
        min = Integer.parseInt(aTime[1]);

        gregolendar = new GregorianCalendar(year, month, day, hour, min);
    }

    public String getEvent() {
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

    public String getShortTime(){
        return null;
    }

    public String getDuration() { return duration; }

    public String getByBus() { return byBus; }

    public int getPic(){

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
