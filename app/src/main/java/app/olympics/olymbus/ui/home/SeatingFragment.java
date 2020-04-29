package app.olympics.olymbus.ui.home;


import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

import androidx.fragment.app.Fragment;
import app.olympics.olymbus.BusItem;
import app.olympics.olymbus.MainActivity;
import app.olympics.olymbus.R;
import app.olympics.olymbus.ui.booking.Tickets;
import app.olympics.olymbus.ui.profile.AccountItem;


public class SeatingFragment extends Fragment {

    private int cols, rows;
    private int seatingID[];
    private int seatsStatus[];
    private CheckBox[][] seats;
    private Bundle bundle;
    private EventItem EVENT;
    private BusItem BUS;
    private String event,category,discipline,venue,date, price, time, duration,byBus,busType, destination,depart,arrive;
    private String selectedSeat[];
    private double amountCost, addOnPriority;
    private AccountItem account;
    private Tickets ticket1,ticket2;
    private int priorityRow;

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
        account = ((MainActivity)getActivity()).getAccount();

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
        destination = BUS.getDestination();
        depart = BUS.getDepart();
        arrive = BUS.getArrive();
        busType = BUS.getType();
        price = BUS.getCost();

        priorityRow = 0; //Set Priority seats in First Row
        addOnPriority = 200; //add on priority seats +200฿ per seats

        TextView busDate = view.findViewById(R.id.date_seating);
        busDate.setText(EVENT.getInitialDate());

        TextView busTime = view.findViewById(R.id.time_seating);
        busTime.setText(BUS.getBusDuration());

        TextView busDes = view.findViewById(R.id.venue_seating);
        busDes.setText(destination);

        TextView busTypeAndSeats = view.findViewById(R.id.busTypeAndSeats);
        busTypeAndSeats.setText("BUS "+busType+" / "+BUS.getBusSeats());

        TextView busPrice = view.findViewById(R.id.busPrice);
        busPrice.setText("Normal Seat "+price+" ฿ / Priority Seat +"+addOnPriority+" ฿");

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
                if(i == priorityRow)
                    seats[i][j].setButtonDrawable(R.drawable.checkbox_priority_selector);
                else
                    seats[i][j].setButtonDrawable(R.drawable.checkbox_selector);

                seats[i][j].setId(SeatNum);

                //CHECK the seat is available with Seat Number. ( Condition Tester )
                for(int k = 0; k < BUS.getBookedSeats().size(); k++){
                    if(BUS.getBookedSeats().get(k)[0].equals(SeatNum+"")) seats[i][j].setEnabled(false);
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
                int cnt = 0, cntS = 0;
                seatingID = new int[2];
                selectedSeat = new String[2];
                char[]colNames = {'A','B','C','D','E','F','G'};
                String uSeat = "";
                for(int i = 0; i<rows; i++){
                    for (int j = 0; j<cols; j++){
                        if(seats[i][j].isChecked()){
                            cnt++;
                            if(i == priorityRow)cntS++;
                            int sid = seats[i][j].getId();
                            if(cnt==1){
                                uSeat += ("" + (i + 1) + colNames[j]);
                                seatingID[0] = sid;
                                selectedSeat[0] = ("" + (i + 1) + colNames[j]);
                            }else if(cnt==2){
                                if (account.getBookedBus().contains(BUS))
                                {
                                    Toast.makeText(getActivity(), "You can only book 1 more seat" +
                                            "", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                uSeat += (" " + (i + 1) + colNames[j]); // Condition tester
                                seatingID[1] = sid;
                                selectedSeat[1] = ("" + (i + 1) + colNames[j]);
                            }
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
                //Toast.makeText(getActivity(), "Your seat :"+uSeat, Toast.LENGTH_SHORT).show();
                //KEEP Booking Seat with Seat Number [ex. Seat No.1 ,2 ] and Seat rowColumn format [ 1A ,2A ]

                if(cnt==1){

                    ticket1 = new Tickets(EVENT, BUS, seatingID[0], selectedSeat[0], account.getAccountID());                          // Create new ticket for this selected seating

                }else if(cnt==2){

                    ticket1 = new Tickets(EVENT, BUS, seatingID[0], selectedSeat[0], account.getAccountID());
                    ticket2 = new Tickets(EVENT, BUS, seatingID[1], selectedSeat[1], account.getAccountID());

                }

                amountCost = (Double.parseDouble(price)*cnt)+(addOnPriority*cntS);
                final int ticketCnt = cnt;
                //Confirm Booking Dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_confirm_booking);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.setCancelable(false);

                TextView type = dialog.findViewById(R.id.busType_cb);
                TextView des = dialog.findViewById(R.id.destination_cb);
                TextView departTxt = dialog.findViewById(R.id.depart_cb);
                TextView arriveTxt = dialog.findViewById(R.id.arrive_cb);
                TextView dateTxt = dialog.findViewById(R.id.date_cb);
                TextView priceTxt = dialog.findViewById(R.id.price_cb);
                TextView seatNo = dialog.findViewById(R.id.seatNo_cb);
                TextView eventName = dialog.findViewById(R.id.event_cb);
                TextView eventCategory = dialog.findViewById(R.id.category_cb);
                TextView eventDiscipline = dialog.findViewById(R.id.discipline_cb);

                final ProgressBar progressBar = dialog.findViewById(R.id.progressBar_cb);

                type.setText(busType);
                des.setText(destination);
                dateTxt.setText(EVENT.getInitialDate());
                departTxt.setText(depart);
                arriveTxt.setText(arrive);
                priceTxt.setText(amountCost+" ฿");
                seatNo.setText(uSeat);
                eventName.setText(event);
                eventCategory.setText(category);
                eventDiscipline.setText(discipline);

                Button cancelBtn = dialog.findViewById(R.id.cancel_cb);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

                final Button confirmBtn = dialog.findViewById(R.id.confirm_cb);
                confirmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmBtn.setEnabled(false); // Block click many times.
                        progressBar.setVisibility(View.VISIBLE); // Show progress bar
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() { //Delay process 1.5 sec.
                            @Override
                            public void run() {
                                dialog.cancel();
                                final Dialog payDialog = new Dialog(getActivity());
                                payDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                payDialog.setContentView(R.layout.dialog_confirm_payment);
                                payDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                payDialog.setCancelable(false); //Block dismiss dialog

                                TextView amountPay = payDialog.findViewById(R.id.amount_cp);
                                TextView cardID = payDialog.findViewById(R.id.card_ID_cp);
                                final EditText csv = payDialog.findViewById(R.id.csv_check_cp);
                                final ProgressBar progressBar2 = payDialog.findViewById(R.id.progressBar_cp);

                                amountPay.setText(amountCost+" ฿");
                                cardID.setText("**** **** **** "+account.getCardNumber().substring(12,16));

                                final Button confirmBtn = payDialog.findViewById(R.id.confirm_cp);
                                confirmBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        confirmBtn.setEnabled(false);
                                        progressBar2.setVisibility(View.VISIBLE);
                                        Handler handler2 = new Handler();
                                        handler2.postDelayed(new Runnable() { //Delay process 1 sec.
                                            @Override
                                            public void run() {
                                                progressBar2.setVisibility(View.INVISIBLE);

                                                String csvCode = csv.getText().toString().trim();
                                                if(csvCode.isEmpty()){
                                                    Toast.makeText(getActivity(), "Please enter CSV code", Toast.LENGTH_SHORT).show();
                                                    confirmBtn.setEnabled(true);
                                                    return;
                                                }
                                                if(csvCode.equals(account.getCSV())){
                                                    payDialog.cancel();
                                                    Toast.makeText(getActivity(), "Payment Successful !", Toast.LENGTH_SHORT).show();

                                                    if (ticketCnt == 1) {
                                                        account.addTicket(ticket1);                                      // Add this ticket to the account
                                                        GregorianCalendar bookingDate = new GregorianCalendar();
                                                        ticket1.setGregoTicketTime(bookingDate);
                                                        BUS.bookSeat(seatingID[0] + "", account.getAccountID(), bookingDate.getTime().toString());
                                                    }
                                                    else if (ticketCnt == 2) {
                                                        account.addTicket(ticket1);
                                                        account.addTicket(ticket2);
                                                        GregorianCalendar bookingDate = new GregorianCalendar();
                                                        ticket1.setGregoTicketTime(bookingDate);
                                                        ticket2.setGregoTicketTime(bookingDate);
                                                        BUS.bookSeat(seatingID[0]+"", account.getAccountID(), bookingDate.getTime().toString());
                                                        BUS.bookSeat(seatingID[1]+"", account.getAccountID(), bookingDate.getTime().toString());
                                                    }
                                                    final Dialog completeDialog = new Dialog(getActivity());
                                                    completeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                    completeDialog.setContentView(R.layout.dialog_booking_complete);
                                                    completeDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                    completeDialog.setCancelable(false); //Block dismiss dialog

                                                    Button continueBtn = completeDialog.findViewById(R.id.confirm_cb);
                                                    continueBtn.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            completeDialog.cancel();
                                                            ((MainActivity) getActivity()).selectedBooking();
                                                        }
                                                    });

                                                    completeDialog.show();
                                                }else{
                                                    Toast.makeText(getActivity(), "Incorrect CSV code", Toast.LENGTH_SHORT).show();
                                                    confirmBtn.setEnabled(true);
                                                }
                                            }
                                        },1000);

                                    }
                                });

                                Button cancelBtn = payDialog.findViewById(R.id.cancel_cp);
                                cancelBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        payDialog.cancel();
                                    }
                                });

                                payDialog.show();
                            }
                        },1500);
                    }
                });

                dialog.show();
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
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
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
