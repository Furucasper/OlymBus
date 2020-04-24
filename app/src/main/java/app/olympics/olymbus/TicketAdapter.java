package app.olympics.olymbus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.ui.booking.Tickets;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private Context mContext;
    private List<Tickets> mData;
    private OnTicketListener onTicketListener;

    public TicketAdapter(Context mContext, List<Tickets> mData, OnTicketListener onTicketListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.onTicketListener = onTicketListener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.event_item,parent,false);

        return new TicketViewHolder(layout, onTicketListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, final int position) {

        holder.type.setText(mData.get(position).getTicketBusType());
        holder.depart.setText(mData.get(position).getTicketDepart());
        holder.arrive.setText(mData.get(position).getTicketArrive());
        holder.date.setText(mData.get(position).getTicketDate());
        holder.seatNo.setText(mData.get(position).getSeatNo());
        holder.destination.setText(mData.get(position).getTicketDestination());
        holder.eventName.setText(mData.get(position).getTicketEventName());
        holder.eventCategory.setText(mData.get(position).getTicketEventCategory());
        holder.eventDiscipline.setText(mData.get(position).getTicketEventDiscipline());

        /*
        holder.event.setText(mData.get(position).getEvent());
        holder.category.setText(mData.get(position).getCategory());
        holder.discipline.setText(mData.get(position).getDiscipline());
        holder.venue.setText(mData.get(position).getVenue());
        holder.date.setText(mData.get(position).getDateMonth());
        holder.time.setText(mData.get(position).getTime());
        holder.pic.setImageResource(mData.get(position).getPic());
         */
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

         private TextView ticketStatus, type, destination, depart, arrive, date, seatNo, eventName, eventCategory, eventDiscipline;
         private OnTicketListener onTicketListener;

        public TicketViewHolder(@NonNull View itemView, OnTicketListener onTicketListener){
            super(itemView);

            type = itemView.findViewById(R.id.busType_ticket);
            destination = itemView.findViewById(R.id.destination_ticket);
            depart = itemView.findViewById(R.id.depart_ticket);
            arrive = itemView.findViewById(R.id.arrive_ticket);
            date = itemView.findViewById(R.id.date_ticket);
            seatNo = itemView.findViewById(R.id.seatNo_ticket);
            eventName = itemView.findViewById(R.id.event_ticket);
            eventCategory = itemView.findViewById(R.id.category_ticket);
            eventDiscipline = itemView.findViewById(R.id.discipline_ticket);
            ticketStatus = itemView.findViewById(R.id.ticket_status);

            this.onTicketListener = onTicketListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onTicketListener.onTicketClick(getAdapterPosition());
        }
    }

    public interface OnTicketListener{
        void onTicketClick(int position);
    }

}

