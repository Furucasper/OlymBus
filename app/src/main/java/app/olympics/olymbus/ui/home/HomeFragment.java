package app.olympics.olymbus.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.EventAdapter;
import app.olympics.olymbus.EventItem;
import app.olympics.olymbus.InputProcess;
import app.olympics.olymbus.R;

public class HomeFragment extends Fragment implements EventAdapter.OnEventListener {

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

        for(int n = 0; n<eventData.size(); n++){
            for(int i = 0; i<eventData.size()-1; i++){
                if(eventData.get(i+1).getGregolendar().before(eventData.get(i).getGregolendar())){
                    eventData.add(i,eventData.get(i+1));
                    eventData.remove(i+2);
                }
            }
        }

        eventsAdapter = new EventAdapter(getActivity(), eventData,this);
        eventsRecyclerview.setAdapter(eventsAdapter);
        eventsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }



    @Override
    public void onEventClick(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("eventName",eventData.get(position).getEvent());
        bundle.putString("discipline",eventData.get(position).getDiscipline());
        bundle.putString("category",eventData.get(position).getCategory());
        bundle.putString("venue",eventData.get(position).getVenue());
        bundle.putString("date",eventData.get(position).getDate());
        bundle.putString("startTime",eventData.get(position).getTime());
        bundle.putString("eventDuration",eventData.get(position).getDuration());
        bundle.putString("byBus",eventData.get(position).getByBus());
        bundle.putInt("eventPic",eventData.get(position).getPic());
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_busDetailFragment,bundle);
        //Toast.makeText(getActivity(), "Event : "+eventData.get(position).getEvent(), Toast.LENGTH_SHORT).show();

    }
}