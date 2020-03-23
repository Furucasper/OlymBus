package app.olympics.olymbus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private Context mContext;
    private List<EventItem> mData;

    public EventAdapter(Context mContext, List<EventItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.event_item,parent,false);

        return new EventViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {

        EventViewHolder.event.setText(mData.get(position).getEvent());
        EventViewHolder.category.setText(mData.get(position).getCategory());
        EventViewHolder.content.setText(mData.get(position).getContent());
        EventViewHolder.venue.setText(mData.get(position).getVenue());
        EventViewHolder.date.setText(mData.get(position).getDate());
        EventViewHolder.time.setText(mData.get(position).getTime());
        EventViewHolder.pic.setImageResource(mData.get(position).getPic());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

     static class EventViewHolder extends RecyclerView.ViewHolder{

        static TextView event,category,content,venue,date,time;
        static ImageView pic;


        public EventViewHolder(@NonNull View itemView){
            super(itemView);
            event = itemView.findViewById(R.id.eventHead);
            category = itemView.findViewById(R.id.eventSubhead);
            content = itemView.findViewById(R.id.eventDiscipline);
            venue = itemView.findViewById(R.id.eventVenue);
            date = itemView.findViewById(R.id.eventDate);
            time = itemView.findViewById(R.id.eventTime);
            pic = itemView.findViewById(R.id.eventIcon);

        }
    }

}

