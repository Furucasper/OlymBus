package app.olympics.olymbus.ui.booking;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;

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
    private ArrayList<Tickets> ticketsData, cancelledTicketsData;
    private String defaultFilter;
    private boolean cancelledSelected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        account = ((MainActivity)getActivity()).getAccount();

        ticketsRecyclerview = view.findViewById(R.id.ticketList);
        ticketsData = account.getTickets();
        cancelledTicketsData = account.getCancelledTickets();
        defaultFilter = "booked"; //set default filter is booked time.
        final Button ticketsFilter = view.findViewById(R.id.ticketsFilter);
        ticketsFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterMenu(v);
            }
        });

        showAvailableTicketsRecyclerView(defaultFilter);

        RadioGroup ticketsStatus = view.findViewById(R.id.tickets_status_radio);
        ticketsStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.availableRadio :
                        showAvailableTicketsRecyclerView(defaultFilter);
                        ticketsFilter.setVisibility(View.VISIBLE);
                        cancelledSelected = false;
                        break;
                    case R.id.cancelledRadio :
                        showCancelledTicketsRecyclerView();
                        ticketsFilter.setVisibility(View.INVISIBLE);
                        cancelledSelected = true;
                        break;
                }
            }
        });

        return view;
    }

    private void showFilterMenu(View v){
        PopupMenu filterMenu = new PopupMenu(getActivity(), v);
        filterMenu.inflate(R.menu.tickets_filter_popup_menu);
        filterMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.sort_by_booked){
                    defaultFilter = "booked";
                    Toast.makeText(getActivity(), "Sort by Booked Time ", Toast.LENGTH_SHORT).show();
                    showAvailableTicketsRecyclerView("booked");
                }else if (item.getItemId() == R.id.sort_by_depart){
                    defaultFilter = "depart";
                    Toast.makeText(getActivity(), "Sort by Depart Time ", Toast.LENGTH_SHORT).show();
                    showAvailableTicketsRecyclerView("depart");
                }else if (item.getItemId() == R.id.sort_by_arrive){
                    defaultFilter = "arrive";
                    Toast.makeText(getActivity(), "Sort by Arrive Time ", Toast.LENGTH_SHORT).show();
                    showAvailableTicketsRecyclerView("arrive");
                }
                return true;
            }
        });
        filterMenu.show();
    }

    private void showAvailableTicketsRecyclerView(String sortBy){
        switch (sortBy){
            case "depart" :
                for(int n = 0; n<ticketsData.size(); n++){                                                    // Loop for each event in ArrayList
                    for(int i = 0; i<ticketsData.size()-1; i++){                                              // Loop to sort events to correct time order
                        if(ticketsData.get(i+1).getGregoTicketDepart().before(ticketsData.get(i).getGregoTicketDepart())){
                            ticketsData.add(i,ticketsData.get(i+1));
                            ticketsData.remove(i+2);
                        }
                    }
                } break;
            case "arrive" :
                for(int n = 0; n<ticketsData.size(); n++){                                                    // Loop for each ticket in ArrayList
                    for(int i = 0; i<ticketsData.size()-1; i++){                                              // Loop to sort ticket to correct time order
                        if(ticketsData.get(i+1).getGregoTicketArrive().before(ticketsData.get(i).getGregoTicketArrive())){
                            ticketsData.add(i,ticketsData.get(i+1));
                            ticketsData.remove(i+2);
                        }
                    }
                }break;
            case "booked" :
                for(int n = 0; n<ticketsData.size(); n++){                                                    // Loop for each ticket in ArrayList
                    for(int i = 0; i<ticketsData.size()-1; i++){                                              // Loop to sort tickets to correct time order
                        if(ticketsData.get(i+1).getGregoTicketTime().after(ticketsData.get(i).getGregoTicketTime())){
                            ticketsData.add(i,ticketsData.get(i+1));
                            ticketsData.remove(i+2);
                        }
                    }
                }break;
        }
        ticketAdapter = new TicketAdapter(getActivity(), ticketsData,this);
        ticketsRecyclerview.setAdapter(ticketAdapter);
        ticketsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void showCancelledTicketsRecyclerView(){
        for(int n = 0; n<cancelledTicketsData.size(); n++){                                                    // Loop for each ticket in ArrayList
            for(int i = 0; i<cancelledTicketsData.size()-1; i++){                                              // Loop to sort tickets to correct time order
                if(cancelledTicketsData.get(i+1).getGregoTicketTime().after(cancelledTicketsData.get(i).getGregoTicketTime())){
                    cancelledTicketsData.add(i,cancelledTicketsData.get(i+1));
                    cancelledTicketsData.remove(i+2);
                }
            }
        }

        ticketAdapter = new TicketAdapter(getActivity(), cancelledTicketsData,this);
        ticketsRecyclerview.setAdapter(ticketAdapter);
        ticketsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onTicketClick(int position) {
        //Ticket Detail Dialog
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
        TextView bookedTime = dialog.findViewById(R.id.ticketTime_td);

        TextView eventName = dialog.findViewById(R.id.event_td);
        TextView eventCategory = dialog.findViewById(R.id.category_td);
        TextView eventDiscipline = dialog.findViewById(R.id.discipline_td);
        TextView eventVenue = dialog.findViewById(R.id.venue_td);
        TextView eventDate = dialog.findViewById(R.id.dateEvent_td);
        TextView eventTime = dialog.findViewById(R.id.eventStart_td);
        TextView eventDuration = dialog.findViewById(R.id.duration_td);
        TextView eventByBus = dialog.findViewById(R.id.byBus_td);
        ImageView eventPic = dialog.findViewById(R.id.eventPic_td);
        ImageView busIcon = dialog.findViewById(R.id.busIcon_td);
        ImageView olymIcon = dialog.findViewById(R.id.olymIcon_td);

        final Tickets ticket = (cancelledSelected)?
                cancelledTicketsData.get(position):ticketsData.get(position);
        EventItem event = (cancelledSelected)?
                cancelledTicketsData.get(position).getTicketEvent():ticketsData.get(position).getTicketEvent();

        status.setText(ticket.getTicketStatus());
        type.setText(ticket.getTicketBusType());
        destination.setText(ticket.getTicketDestination());
        depart.setText(ticket.getTicketDepart());
        arrive.setText(ticket.getTicketArrive());
        date.setText(ticket.getTicketDate());
        seatNo.setText(ticket.getSeatNo());
        price.setText(ticket.getTicketPrice()+" ฿");
        bookedTime.setText(ticket.getGregoTicketTime().getTime().toString());

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
        if(!ticket.isAvailable()){
            cancelTicket.setVisibility(View.GONE);
            status.setTextColor(0xFFFF4D4D);
            type.setTextColor(Color.GRAY);
            destination.setTextColor(Color.GRAY);
            depart.setTextColor(Color.GRAY);
            arrive.setTextColor(Color.GRAY);
            date.setTextColor(Color.GRAY);
            seatNo.setTextColor(Color.GRAY);
            price.setTextColor(Color.GRAY);

            eventName.setTextColor(Color.GRAY);
            eventCategory.setTextColor(Color.GRAY);
            eventDiscipline.setTextColor(Color.GRAY);
            eventVenue.setTextColor(Color.GRAY);
            eventDate.setTextColor(Color.GRAY);
            eventTime.setTextColor(Color.GRAY);
            eventDuration.setTextColor(Color.GRAY);
            eventByBus.setTextColor(Color.GRAY);
            eventPic.setVisibility(View.INVISIBLE);
            olymIcon.setVisibility(View.INVISIBLE);
            busIcon.setVisibility(View.INVISIBLE);
        }
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
                        GregorianCalendar cancelledDate = new GregorianCalendar();
                        ticket.setGregoTicketTime(cancelledDate);
                        account.cancelTicket(ticket);
                        showAvailableTicketsRecyclerView(defaultFilter);
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