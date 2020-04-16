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
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
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
        final String venue = bundle.getString("venue");
        final String name = bundle.getString("eventName");

        TextView busDes = view.findViewById(R.id.venue_bs);
        busDes.setText(venue);

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

        InputStream input = getResources().openRawResource(R.raw.input);
        InputProcess in = new InputProcess(new Scanner(input));
        String[] busDetail ;
        for (int j = 0; j < in.getBus().size(); j++)
        {
            busDetail=in.getBus().get(j).split(",");
            busData.add(new BusItem(busDetail[0], busDetail[1], busDetail[2], busDetail[3], busDetail[4], busDetail[5], busDetail[6]));
        }

        for(BusItem b : busData){
            String destinationRequest = venue.toLowerCase().trim();
            if(b.getDestination().toLowerCase().trim().contains(destinationRequest)) {
                busFilter.add(b);
            }
        }

        busData.clear();


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
        NavHostFragment.findNavController(this).navigate(R.id.action_busScheduleFragment_to_seatingFragment, bundle);
    }

}
