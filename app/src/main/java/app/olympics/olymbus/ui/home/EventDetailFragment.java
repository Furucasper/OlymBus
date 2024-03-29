package app.olympics.olymbus.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import app.olympics.olymbus.R;

public class EventDetailFragment extends Fragment {

    private String event,category,discipline,venue,date,time, duration,byBus;                       // Declare String instance variables
    private TextView eventName,eventCategory,eventDiscipline,eventVenue,eventDate,eventTime, eventDuration,eventByBus;//Declare each text box from the layout
    private EventItem EVENT;                                                                        // Declare EventItem instance variable

    public EventDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_event_detail, container, false);
        final Bundle bundle = getArguments();
        EVENT = (EventItem) bundle.getSerializable("EVENT");                                   // Get serializable object

        eventName = view.findViewById(R.id.eventHead_evd);                                          // Link each instance variables from the layout
        eventCategory = view.findViewById(R.id.eventSubhead_evd);
        eventDiscipline = view.findViewById(R.id.eventDiscipline_evd);
        eventVenue = view.findViewById(R.id.venue_evd2);
        eventDate = view.findViewById(R.id.date_evd2);
        eventTime = view.findViewById(R.id.starttime_evd2);
        eventDuration = view.findViewById(R.id.duration_evd2);
        eventByBus = view.findViewById(R.id.bybus_evd2);
        ImageView eventPic = view.findViewById(R.id.event_image_evd);

        eventName.setText(EVENT.getEvent());                                                        // Display each Texts/Images on the layout
        eventCategory.setText(EVENT.getCategory());
        eventDiscipline.setText(EVENT.getDiscipline());
        eventVenue.setText(EVENT.getVenue());
        eventDate.setText(EVENT.getDate());
        eventTime.setText(EVENT.getTime());
        eventDuration.setText(EVENT.getDuration()+" hrs");
        eventByBus.setText(EVENT.getByBus()+" min");
        eventPic.setImageResource(EVENT.getPic());


        Button bookNow = view.findViewById(R.id.book_now_btn);                                      // Declare each buttons from the layout
        Button backBtn = view.findViewById(R.id.back_btn_evd);

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                          // Activate when clicked book now button
                Navigation.findNavController(v).navigate(R.id.action_busDetailFragment_to_busScheduleFragment,bundle);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                          // Activate when clicked back button
                getActivity().onBackPressed();
            }
        });




        return view;
    }

}
