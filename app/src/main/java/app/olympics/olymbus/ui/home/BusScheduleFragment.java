package app.olympics.olymbus.ui.home;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.BusAdapter;
import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.R;


public class BusScheduleFragment extends Fragment implements BusAdapter.OnBusListener {

    private RecyclerView busRecyclerview;
    private BusAdapter busAdapter;
    private List<BusItem> busData;
    private Bundle bundle = getArguments();

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
        final String date = bundle.getString("date");
        final String name = bundle.getString("eventName");


        TextView busDate = view.findViewById(R.id.textDate);
        busDate.setText(date);

        ImageButton backBtn= view.findViewById(R.id.back_btn_busS);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

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
                eventName.setText(name);

                dialog.show();
            }
        });

        //fill bus list with data
        //BusItem(String duration, String departFrom, String busType, String busSeats, int availableSeats, double price)
        //example >> This is RecyclerView test. >>

        busData.add(new BusItem("Type A", "Olympic Stadium", "16.30", 30, 5, 4, 1000));
        busData.add(new BusItem("Type A", "Olympic Stadium", "17.30", 30, 5, 4, 1000));
        //busData.add(new BusItem("Type A", "Tokyo Aquatics Centre", "09.00", 45, 5, 4, 1000));
        //busData.add(new BusItem("Type A", "Tokyo Aquatics Centre", "09.30", 45, 5, 4, 1000));
        //busData.add(new BusItem("Type A", "Tokyo International Forum", "12.30", 23, 5, 4, 1000));
        busData.add(new BusItem("Type B", "Olympic Stadium", "19.00", 30, 5, 2, 1500));
        /*
        busData.add(new EventItem("Event","category","/// This is discipline. ////// This is discipline. ////// This is discipline. ////// This is discipline. ////// This is discipline. ///","Event Venus","30 FEB","00.00"));
        for (int i=0;i<10;i++){
            busData.add(new EventItem("Running","Men","Final round","National Stadium","23 MAR","07.00"));
        }
        busData.add(new EventItem("Swimming","Women","Preliminary round","Aquatics Centre","29 MAR","13.00"));
        busData.add(new EventItem("Football","Men","U-23/ Final round/ Brazil - Germany","Tokyo Stadium","01 APR","18.00"));

         */

        //setup adapter

        busAdapter = new BusAdapter(getActivity(),busData,this);
        busRecyclerview.setAdapter(busAdapter);
        busRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onEventClick(int position) {
        NavHostFragment.findNavController(this).navigate(R.id.action_busScheduleFragment_to_seatingFragment, bundle);
    }

}
