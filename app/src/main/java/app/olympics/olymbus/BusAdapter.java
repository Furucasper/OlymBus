package app.olympics.olymbus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private Context mContext;
    private List<BusItem> mData;
    private OnBusListener onBusListener;

    public BusAdapter(Context mContext, List<BusItem> mData,OnBusListener onBusListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.onBusListener = onBusListener;
    }

    @NonNull
    @Override
    public BusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.bus_item,parent,false);

        return new BusViewHolder(layout, onBusListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BusViewHolder holder, int position) {

        holder.duration.setText(mData.get(position).getBusDuration());
        //holder.departFrom.setText("Depart : "+mData.get(position).getDepartFrom());
        holder.busType.setText(mData.get(position).getType());
        holder.busSeats.setText(mData.get(position).getBusSeats());
        holder.availableSeats.setText(mData.get(position).getAvailableSeats());
        holder.price.setText(""+mData.get(position).getCost()+" ฿");
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class BusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView duration,departFrom,busType,busSeats,busStatus,availableSeats,price;
        OnBusListener onBusListener;


        public BusViewHolder(@NonNull View itemView,OnBusListener onBusListener){
            super(itemView);

            duration = itemView.findViewById(R.id.busDuration);
            //departFrom = itemView.findViewById(R.id.busDepart);
            busType = itemView.findViewById(R.id.busType);
            busSeats = itemView.findViewById(R.id.busSeats);
            busStatus = itemView.findViewById(R.id.busStatus);
            availableSeats = itemView.findViewById(R.id.availableSeats);
            price = itemView.findViewById(R.id.ticketPrice);
            this.onBusListener = onBusListener;
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            onBusListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnBusListener{
        void onEventClick(int position);
    }

}

