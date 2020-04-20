package app.olympics.olymbus.ui.home;


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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.BusAdapter;
import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.InputProcess;
import app.olympics.olymbus.R;


public class BusScheduleFragment extends Fragment implements BusAdapter.OnBusListener {

    private RecyclerView busRecyclerview;
    private BusAdapter busAdapter;
    private ArrayList<BusItem> busData;
    private ArrayList<BusItem> busFilter = new ArrayList<>();
    private String event,category,discipline,venue,date,time, duration,byBus;
    private EventItem EVENT;
    private BusItem BUS;
    private Bundle bundle;

    public BusScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_bus_schedule, container, false);

        busRecyclerview = view.findViewById(R.id.busList);
        busData = new ArrayList<>();
        bundle = getArguments();

        EVENT =  (EventItem)bundle.getSerializable("EVENT");

        event = EVENT.getEvent();
        category = EVENT.getCategory();
        discipline = EVENT.getDiscipline();
        venue = EVENT.getVenue();
        date = EVENT.getDate();
        time = EVENT.getTime();
        duration = EVENT.getDuration();
        byBus = EVENT.getByBus();
        final int pic = EVENT.getPic();


        //Show Destination
        TextView busDes = view.findViewById(R.id.venue_bs);
        busDes.setText(venue);
        //Show date
        TextView busDate = view.findViewById(R.id.textDate);
        busDate.setText(date);

        //Back Button
        ImageButton backBtn= view.findViewById(R.id.back_btn_busS);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Event Detail Dialog
        Button evdBtn = view.findViewById(R.id.eventDetail_btn_busS);
        evdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_event_detail);
                dialog.setCancelable(true);

                Button closeBtn = dialog.findViewById(R.id.close_dialog_evd);
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                TextView eventName = dialog.findViewById(R.id.eventHead_dialog_evd);
                TextView eventCategory = dialog.findViewById(R.id.eventSubhead_dialog_evd);
                TextView eventDiscipline = dialog.findViewById(R.id.eventDiscipline_dialog_evd);
                TextView eventVenue = dialog.findViewById(R.id.venue_dialog_evd);
                TextView eventDate = dialog.findViewById(R.id.date_dialog_evd);
                TextView eventTime = dialog.findViewById(R.id.starttime_dialog_evd);
                TextView eventDuration = dialog.findViewById(R.id.duration_dialog_evd);
                TextView eventByBus = dialog.findViewById(R.id.bybus_dialog_evd);
                ImageView eventPic = dialog.findViewById(R.id.eventIcon_dialog_evd);
                eventName.setText(event);
                eventCategory.setText(category);
                eventDiscipline.setText(discipline);
                eventVenue.setText("Venue : "+venue);
                eventDate.setText("Date : "+date);
                eventTime.setText("Start time : "+time);
                eventDuration.setText("Duration : "+duration+" hrs");
                eventByBus.setText("Travel by bus : "+byBus+" min");
                eventPic.setImageResource(pic);

                dialog.show();
            }
        });

        InputStream input = getResources().openRawResource(R.raw.input);
        InputProcess in = new InputProcess(new Scanner(input));
        String[] busDetail ;
        for (int j = 0; j < in.getBus().size(); j++)
        {
            busDetail=in.getBus().get(j).split(",");
            busData.add(new BusItem(busDetail[0], busDetail[1], busDetail[2], busDetail[3], busDetail[4], busDetail[5], busDetail[6], EVENT.getInitialDate()));
        }
        GregorianCalendar beforeEvent2Hr = EVENT.getGregolendar();
        beforeEvent2Hr.add(Calendar.HOUR,-2);
        GregorianCalendar afterEvent1Hr = EVENT.getGregolendar();
        afterEvent1Hr.add(Calendar.HOUR,1);

        for(BusItem b : busData){
            String destinationRequest = venue.toLowerCase().trim();
            if(b.getDestination().toLowerCase().trim().contains(destinationRequest)) {
                if(b.getGregoarrive().before(beforeEvent2Hr) || b.getGregoarrive().after(afterEvent1Hr))
                    busFilter.add(b);
            }
        }

        busData.clear();

        for(int n = 0; n<busFilter.size(); n++){
            for(int i = 0; i<busFilter.size()-1; i++){
                if(busFilter.get(i+1).getGregodepart().before(busFilter.get(i).getGregodepart())){
                    busFilter.add(i,busFilter.get(i+1));
                    busFilter.remove(i+2);
                }
            }
        }


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
        bundle.putSerializable("BUS",busFilter.get(position));
        NavHostFragment.findNavController(this).navigate(R.id.action_busScheduleFragment_to_seatingFragment, bundle);
    }

}
