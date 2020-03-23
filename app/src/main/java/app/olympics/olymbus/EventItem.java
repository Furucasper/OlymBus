package app.olympics.olymbus;

public class EventItem {

    String event,category,content,venue,date,time;

    public EventItem(){

    }

    public EventItem(String event, String category, String content, String venue, String date, String time) {
        this.event = event;
        this.category = category;
        this.content = content;
        this.venue = venue;
        this.date = date;
        this.time = time;
    }


    public String getEvent() {
        return event;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
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

    public void setEvent(String event) {
        this.event = event;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
