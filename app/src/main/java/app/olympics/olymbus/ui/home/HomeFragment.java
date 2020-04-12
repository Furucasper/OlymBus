package app.olympics.olymbus.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.EventItem;
import app.olympics.olymbus.EventAdapter;
import app.olympics.olymbus.InputProcess;
import app.olympics.olymbus.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView eventsRecyclerview;
    private EventAdapter eventsAdapter;
    private ArrayList<EventItem> eventData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        eventsRecyclerview = view.findViewById(R.id.eventList);
        eventData = new ArrayList<>();
        InputStream input = getResources().openRawResource(R.raw.input);
        InputProcess in = new InputProcess(new Scanner(input));

        String[] eventDetail ;

        for (int i = 0; i < 11; i++)
        {
            eventDetail = in.getEvent().get(i).split(",");
            eventData.add(new EventItem(eventDetail[0], eventDetail[1], eventDetail[2], eventDetail[3], eventDetail[4], eventDetail[5], eventDetail[6], eventDetail[7]));
        }

        eventsAdapter = new EventAdapter(getActivity(), eventData);
        eventsRecyclerview.setAdapter(eventsAdapter);
        eventsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

}