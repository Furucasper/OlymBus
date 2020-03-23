package app.olympics.olymbus.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.EventItem;
import app.olympics.olymbus.EventAdapter;
import app.olympics.olymbus.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView eventsRecyclerview;
    private EventAdapter eventsAdapter;
    private List<EventItem> mData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        eventsRecyclerview = view.findViewById(R.id.eventList);
        mData = new ArrayList<>();

        //fill list event with data
        //EventItem(String event, String category, String content, String venue, String date, String time)
        //example >> This is RecyclerView test. >>
        mData.add(new EventItem("Event","category","/// This is discipline. ////// This is discipline. ////// This is discipline. ////// This is discipline. ////// This is discipline. ///","Event Venus","30 FEB","00.00"));
        for (int i=0;i<3;i++){
            mData.add(new EventItem("Running","Men","Final round","National Stadium","23 MAR","07.00"));
        }
        mData.add(new EventItem("Swimming","Women","Preliminary round","Aquatics Centre","29 MAR","13.00"));
        mData.add(new EventItem("Football","Men","U-23/ Final round/ Brazil - Germany","Tokyo Stadium","01 APR","18.00"));


        //setup adapter
        eventsAdapter = new EventAdapter(getActivity(),mData);
        eventsRecyclerview.setAdapter(eventsAdapter);
        eventsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}