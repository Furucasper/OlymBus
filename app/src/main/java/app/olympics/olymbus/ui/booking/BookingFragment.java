package app.olympics.olymbus.ui.booking;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.MainActivity;
import app.olympics.olymbus.R;
import app.olympics.olymbus.TicketAdapter;
import app.olympics.olymbus.ui.home.EventItem;
import app.olympics.olymbus.ui.profile.AccountItem;

public class BookingFragment extends Fragment implements TicketAdapter.OnTicketListener {

    private BookingViewModel bookingViewModel;
    private RecyclerView ticketsRecyclerview;
    private TicketAdapter ticketAdapter;
    private AccountItem account;
    private ArrayList<Tickets> ticketsData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        account = ((MainActivity)getActivity()).getAccount();

        ticketsRecyclerview = view.findViewById(R.id.ticketList);
        ticketsData = account.getTickets();

        //set RecyclerView Adapter
        showRecyclerView();

        return view;
    }

    public void showRecyclerView(){
        ticketAdapter = new TicketAdapter(getActivity(), ticketsData,this);
        ticketsRecyclerview.setAdapter(ticketAdapter);
        ticketsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onTicketClick(int position) {
        final Dialog dialog = new Dialog(getActivity());                                    // Create new Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_ticket_detail);                                // Set Dialog's Display
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);

        TextView status = dialog.findViewById(R.id.ticket_status2);
        TextView type = dialog.findViewById(R.id.busType_td);
        TextView destination = dialog.findViewById(R.id.destination_td);
        TextView depart = dialog.findViewById(R.id.depart_td);
        TextView arrive = dialog.findViewById(R.id.arrive_td);
        TextView date = dialog.findViewById(R.id.date_td);
        TextView seatNo = dialog.findViewById(R.id.seatNo_td);
        TextView price = dialog.findViewById(R.id.price_td);

        TextView eventName = dialog.findViewById(R.id.event_td);
        TextView eventCategory = dialog.findViewById(R.id.category_td);
        TextView eventDiscipline = dialog.findViewById(R.id.discipline_td);
        TextView eventVenue = dialog.findViewById(R.id.venue_td);
        TextView eventDate = dialog.findViewById(R.id.dateEvent_td);
        TextView eventTime = dialog.findViewById(R.id.eventStart_td);
        TextView eventDuration = dialog.findViewById(R.id.duration_td);
        TextView eventByBus = dialog.findViewById(R.id.byBus_td);
        ImageView eventPic = dialog.findViewById(R.id.eventPic_td);

        final Tickets tickets = ticketsData.get(position);
        EventItem event = ticketsData.get(position).getTicketEvent();

        status.setText(tickets.getTicketStatus());
        type.setText(tickets.getTicketBusType());
        destination.setText(tickets.getTicketDestination());
        depart.setText(tickets.getTicketDepart());
        arrive.setText(tickets.getTicketArrive());
        date.setText(tickets.getTicketDate());
        seatNo.setText(tickets.getSeatNo());
        price.setText(tickets.getTicketPrice()+" ฿");

        eventName.setText(event.getEvent());
        eventCategory.setText(event.getCategory());
        eventDiscipline.setText(event.getDiscipline());
        eventVenue.setText("Venue : " + event.getVenue());
        eventDate.setText("Date : " + event.getDate());
        eventTime.setText("Start time : " + event.getTime());
        eventDuration.setText("Duration : " + event.getDuration() +" hrs");
        eventByBus.setText("Travel by bus : " + event.getByBus() + " min");
        eventPic.setImageResource(event.getPic());

        final Button cancelTicket = dialog.findViewById(R.id.cancel_ticket);
        cancelTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog cancelDialog = new Dialog(getActivity());                                    // Create new Dialog
                cancelDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cancelDialog.setContentView(R.layout.dialog_cancel_ticket);                                // Set Dialog's Display
                cancelDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                cancelDialog.setCancelable(true);

                Button confirmBtn = cancelDialog.findViewById(R.id.confirm_ct);
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelDialog.cancel();
                        dialog.cancel();
                        account.cancelTicket(tickets);
                        showRecyclerView();
                    }
                });

                Button cancelBtn = cancelDialog.findViewById(R.id.cancel_ct);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelDialog.cancel();
                    }
                });

                cancelDialog.show();
            }
        });

        Button closeBtn = dialog.findViewById(R.id.close_td);                       // Declare close dialog button
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dialog.cancel(); }
        });                            // Close Dialog

        dialog.show();
    }
}