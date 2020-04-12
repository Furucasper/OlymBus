package app.olympics.olymbus;

public class EventItem {

    String event,category,discipline,venue,date,time, duration,byBus;

    public EventItem(){

    }

    public EventItem(String event, String category, String content, String venue, String date, String time) {
        this.event = event;
        this.category = category;
        this.discipline = content;
        this.venue = venue;
        this.date = date;
        this.time = time;
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
    }

    public int getPic(){

        switch (event.toLowerCase()){
            case "swimming" : return R.drawable.ic_swimming_pictogram;
            case "football" : return R.drawable.ic_football_pictogram;
            case "volleyball" : return R.drawable.ic_volleyball_pictogram;
            case  "running" : return R.drawable.ic_athletics_pictogram;
            case "taekwondo" : return R.drawable.ic_taekwondo_pictogram;
            default : return R.drawable.ic_black_olympic_rings;
        }
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
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public String getByBus() {
        return byBus;
    }
}
