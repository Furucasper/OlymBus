package app.olympics.olymbus.ui.home;


import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.R;


public class SeatingFragment extends Fragment {

    private int cols, rows;
    private int seatsStatus[];
    private CheckBox[][] seats;
    private Bundle bundle;
    private EventItem EVENT;
    private BusItem BUS;
    private String event,category,discipline,venue,date,time, duration,byBus;

    public SeatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seating, container, false);

        bundle = getArguments();
        EVENT = (EventItem) bundle.getSerializable("EVENT");
        BUS = (BusItem) bundle.getSerializable("BUS");

        event = EVENT.getEvent();
        category = EVENT.getCategory();
        discipline = EVENT.getDiscipline();
        venue = EVENT.getVenue();
        date = EVENT.getDate();
        time = EVENT.getTime();
        duration = EVENT.getDuration();
        byBus = EVENT.getByBus();
        final int pic = EVENT.getPic();

        rows = BUS.getRows();
        cols = BUS.getCols();

        TextView busTime = view.findViewById(R.id.time_seating);
        busTime.setText(BUS.getBusDuration());

        TextView busDes = view.findViewById(R.id.venue_seating);
        busDes.setText(BUS.getDestination());

        TextView busTypeAndSeats = view.findViewById(R.id.busTypeAndSeats);
        busTypeAndSeats.setText("BUS "+BUS.getType()+" / "+BUS.getBusSeats());

        TextView busPrice = view.findViewById(R.id.busPrice);
        busPrice.setText("Normal Seat "+BUS.getCost()+" ฿ / Priority Seat +500 ฿");

        TableLayout seatingZone = view.findViewById(R.id.seatingZone);
        int[] bookSeats = {1,2,7,9}; //TEST Seat No.1 ,2 ,7 ,9 are booked
        seatsStatus = new int[rows*cols];
        seatsStatus[4]=1; //Test
        seatsStatus[5]=1;
        int SeatNum = 1; //SEAT NO.1 - 10/20
        seats = new CheckBox[rows][cols];

        int margin5dp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources()
                        .getDisplayMetrics());
        //loop for generate seats(CheckBox)
        for (int i = 0; i < rows; i++) {

            TableRow seatRow= new TableRow(getActivity());
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams params4 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            seatRow.setLayoutParams(params);

            for(int j = 0; j<cols; j++){

                seats[i][j] = new CheckBox(getActivity());
                seats[i][j].setButtonDrawable(R.drawable.checkbox_selector);
                seats[i][j].setId(SeatNum);

                //CHECK the seat is available with Seat Number. ( Condition Tester )
                for(int bs : bookSeats){
                    if(bs == SeatNum) seats[i][j].setEnabled(false);
                }
                //if(seatsStatus[SeatNum-1] == 1) seats[i][j].setEnabled(false);


              if(cols==2){
                    params4.setMargins(margin5dp,50,0,0);
                    seats[i][j].setLayoutParams(params4);
                }
                else if(j%2==0&&j!=0){
                    params2.setMargins(120,100,0,0);
                    seats[i][j].setLayoutParams(params2);
                }
                else{
                    params.setMargins(margin5dp,100,0,0);
                    seats[i][j].setLayoutParams(params);
                }
                seatRow.addView(seats[i][j]);

                SeatNum++;

            }

            seatingZone.addView(seatRow, i);

        }

        //Book Button + loop check the seat was selected
        Button bookBtn = view.findViewById(R.id.book_btn);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cnt = 0;
                char[]colNames = {'A','B','C','D','E','F','G'};
                String uSeat = "";
                for(int i = 0; i<rows; i++){
                    for (int j = 0; j<cols; j++){
                        if(seats[i][j].isChecked()){
                            cnt++;
                            int sid = seats[i][j].getId();
                            uSeat += (" " + (i + 1) + colNames[j]); // Condition tester
                        }
                        if(cnt>2){
                            Toast.makeText(getActivity(), "Bus booking can book no more than 2 seats", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                }
                if(cnt == 0){
                    Toast.makeText(getActivity(), "Please select a seat", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getActivity(), "Your seat :"+uSeat, Toast.LENGTH_SHORT).show();
                //KEEP Booking Seat with Seat Number [ex. Seat No.1 ,2 ] and Seat rowColumn format [ 1A ,2A ]
            }
        });

        //Back Button
        Button backBtn = view.findViewById(R.id.back_btn_seating);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        //Event Detail Dialog
        Button evdBtn = view.findViewById(R.id.eventDetail_btn_seating);
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

        return view;
    }

}
