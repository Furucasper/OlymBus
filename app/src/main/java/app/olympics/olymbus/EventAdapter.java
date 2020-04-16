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
    private OnEventListener onEventListener;

    public EventAdapter(Context mContext, List<EventItem> mData, OnEventListener onEventListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.onEventListener = onEventListener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.event_item,parent,false);

        return new EventViewHolder(layout, onEventListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, final int position) {

        holder.event.setText(mData.get(position).getEvent());
        holder.category.setText(mData.get(position).getCategory());
        holder.discipline.setText(mData.get(position).getDiscipline());
        holder.venue.setText(mData.get(position).getVenue());
        holder.date.setText(mData.get(position).getDateMonth());
        holder.time.setText(mData.get(position).getTime());
        holder.pic.setImageResource(mData.get(position).getPic());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         TextView event,category,discipline,venue,date,time;
         ImageView pic;
         OnEventListener onEventListener;

        public EventViewHolder(@NonNull View itemView, OnEventListener onEventListener){
            super(itemView);
            event = itemView.findViewById(R.id.eventHead);
            category = itemView.findViewById(R.id.eventSubhead);
            discipline = itemView.findViewById(R.id.eventDiscipline);
            venue = itemView.findViewById(R.id.eventVenue);
            date = itemView.findViewById(R.id.eventDate);
            time = itemView.findViewById(R.id.eventTime);
            pic = itemView.findViewById(R.id.eventIcon);
            this.onEventListener = onEventListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onEventListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnEventListener{
        void onEventClick(int position);
    }

}

