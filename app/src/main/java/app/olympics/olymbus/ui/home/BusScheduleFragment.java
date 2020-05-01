package app.olympics.olymbus.ui.home;


import android.accounts.Account;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.BusAdapter;
import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.MainActivity;
import app.olympics.olymbus.R;
import app.olympics.olymbus.ui.profile.AccountItem;


public class BusScheduleFragment extends Fragment implements BusAdapter.OnBusListener {

    private RecyclerView busRecyclerview;
    private BusAdapter busAdapter;
    private ArrayList<BusItem> busData;                                                             // Declare BusItem ArrayList named busData
    private ArrayList<BusItem> busFilter;                                                           // Create new BusItem ArrayList named busFilter
    private String event,category,discipline,venue,date,time, duration,byBus;                       // Declare String instance variables
    private EventItem EVENT;                                                                        // Declare EventItem instance variable
    private AccountItem account;
    private Bundle bundle;                                                                          // Declare Bundle instance variable

    public BusScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bus_schedule, container, false);

        busRecyclerview = view.findViewById(R.id.busList);
        busFilter = new ArrayList<>();
        bundle = getArguments();

        EVENT =  (EventItem)bundle.getSerializable("EVENT");                                   // Get Serializable object
        busData = ((MainActivity)getActivity()).getAllBus();
        event = EVENT.getEvent();                                                                   // Set each variables with data from EventItem
        category = EVENT.getCategory();
        discipline = EVENT.getDiscipline();
        venue = EVENT.getVenue();
        date = EVENT.getDate();
        time = EVENT.getTime();
        duration = EVENT.getDuration();
        byBus = EVENT.getByBus();
        final int pic = EVENT.getPic();
        account = ((MainActivity)getActivity()).getAccount();

        //Show Destination
        TextView busDes = view.findViewById(R.id.venue_bs);
        busDes.setText(venue);
        //Show date
        TextView busDate = view.findViewById(R.id.textDate);
        busDate.setText(date);

        TextView eventStart = view.findViewById(R.id.textEventStart);
        eventStart.setText("event start : "+EVENT.getTime());

        //Back Button
        ImageButton backBtn= view.findViewById(R.id.back_btn_busS);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Event Detail Dialog
        Button evdBtn = view.findViewById(R.id.eventDetail_btn_busS);                               // Declare event details button
        evdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                           // Activate when clicked event details button
                final Dialog dialog = new Dialog(getActivity());                                    // Create new Dialog
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_event_detail);                                // Set Dialog's Display
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(true);

                Button closeBtn = dialog.findViewById(R.id.close_dialog_evd);                       // Declare close dialog button
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialog.cancel(); }
                });                            // Close Dialog

                TextView eventName = dialog.findViewById(R.id.eventHead_dialog_evd);                // Link each instance variables from the layout
                TextView eventCategory = dialog.findViewById(R.id.eventSubhead_dialog_evd);
                TextView eventDiscipline = dialog.findViewById(R.id.eventDiscipline_dialog_evd);
                TextView eventVenue = dialog.findViewById(R.id.venue_dialog_evd);
                TextView eventDate = dialog.findViewById(R.id.date_dialog_evd);
                TextView eventTime = dialog.findViewById(R.id.starttime_dialog_evd);
                TextView eventDuration = dialog.findViewById(R.id.duration_dialog_evd);
                TextView eventByBus = dialog.findViewById(R.id.bybus_dialog_evd);
                ImageView eventPic = dialog.findViewById(R.id.eventIcon_dialog_evd);
                eventName.setText(event);                                                           // Display each Texts/Images on the layout
                eventCategory.setText(category);
                eventDiscipline.setText(discipline);
                eventVenue.setText("Venue : "+venue);
                eventDate.setText("Date : "+date);
                eventTime.setText("Start time : "+time);
                eventDuration.setText("Duration : "+duration+" hrs");
                eventByBus.setText("Travel by bus : "+byBus+" min");
                eventPic.setImageResource(pic);

                dialog.show();                                                                      // Display Dialog on screen
            }
        });

        boolean valid = true;
        for(BusItem b : busData) {
            valid = true;
            String destinationRequest = venue.toLowerCase().trim();                                 // First, get venue name// Check if each bus is qualified for an event
            if (b.getDestination().toLowerCase().trim().contains(destinationRequest)) {              // Then, check if the bus goes to the venue
                if (b.getGregoarrive().after(EVENT.getBeforeEvent2HR()) && b.getGregoarrive().before(EVENT.getAfterEvent1HR())) {// Then, check if the bus is depart to a venue 2 hour before and 1 hour after from a venue
                    for (BusItem maxed : account.getMaxedQuotaBus()) {
                        if (maxed.getBusID().equals(b.getBusID())) {
                            valid = false;
                        }
                    }
                    if (valid) {
                        busFilter.add(b);                                                 // Bus qualified add to ArrayList
                    }
                }
            }
        }


        for(int n = 0; n<busFilter.size(); n++){                                                    // Loop to sort an qualified buses to a time order
            for(int i = 0; i<busFilter.size()-1; i++){
                if(busFilter.get(i+1).getGregodepart().before(busFilter.get(i).getGregodepart())){
                    busFilter.add(i,busFilter.get(i+1));
                    busFilter.remove(i+2);
                }
            }
        }

        //set RecyclerView Adapter
        busAdapter = new BusAdapter(getActivity(),busFilter,this);
        busRecyclerview.setAdapter(busAdapter);
        busRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onEventClick(int position) {
        if(busFilter.get(position).getAvailableSeats()==0){
            Toast.makeText(getActivity(), "This bus is sold out.", Toast.LENGTH_SHORT).show();
            return;
        }
        bundle.putSerializable("BUSID",busFilter.get(position).getBusID());
        NavHostFragment.findNavController(this).navigate(R.id.action_busScheduleFragment_to_seatingFragment, bundle);
    }

}
