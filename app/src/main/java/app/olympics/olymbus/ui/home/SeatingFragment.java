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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import app.olympics.olymbus.R;


public class SeatingFragment extends Fragment {

    private int cols, rows;
    private int theSeat[];
    private CheckBox seat;

    public SeatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seating, container, false);

        Button bookBtn = view.findViewById(R.id.book_btn);
        /*
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seat.getId();
            }
        });

         */

        Button backBtn = view.findViewById(R.id.back_btn_seating);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

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

                dialog.show();
            }
        });

        TableLayout seatingZone = view.findViewById(R.id.seatingZone);

        /*
        ViewGroup.LayoutParams params = seatingZone.getLayoutParams();
        params.height = 800;
        params.width = 500;
        seatingZone.setLayoutParams(params);
         */
        cols = 4;
        rows = 5;
        theSeat = new int[20];
        theSeat[10]=1;
        theSeat[5]=1;
        int n = 0;

        int margin5dp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources()
                        .getDisplayMetrics());

        for (int i = 0; i < rows; i++) {

            TableRow seatRow= new TableRow(getActivity());
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            TableRow.LayoutParams params4 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            seatRow.setLayoutParams(params);

            //show Row Number position
            /*
            TextView rowNum = new TextView(getActivity());
            rowNum.setText(""+(i+1));
            rowNum.setTextSize(10);
            rowNum.setTextColor(getResources().getColor(colorPrimary));
            seatRow.addView(rowNum);

            char colsPosition = 'A';

             */


            for(int j = 0; j<cols; j++){

                seat = new CheckBox(getActivity());
                seat.setButtonDrawable(R.drawable.checkbox_selector);
                seat.setId(n);
                //seat.setId((10*(i+1)+j)); //SEAT ID = row col [ ex. row 1 col 4 = 14 ]
                if(theSeat[n]==1) seat.setEnabled(false); //check the seat is available.
                /*
                seat.setText(""+colsPosition);
                seat.setTextSize(9);
                seat.setTextColor(Color.GRAY);
                seat.setGravity(Gravity.BOTTOM);
                seat.setPadding(5,0,0,0);
                */
              if(cols==2){
                    params4.setMargins(margin5dp,50,0,0);
                    seat.setLayoutParams(params4);
                }
                else if(j%2==0&&j!=0){
                    params2.setMargins(120,100,0,0);
                    seat.setLayoutParams(params2);
                }
                else{
                    params.setMargins(margin5dp,100,0,0);
                    seat.setLayoutParams(params);
                }
                seatRow.addView(seat);

                TextView colNum = new TextView(getActivity());
                /*
                colNum.setText(""+n);
                colNum.setGravity(Gravity.RIGHT);
                //if(i==0)colNum.setText(""+colsPosition);
                //else colNum.setText(" ");
                colNum.setTextSize(8);
                colNum.setTextColor(getResources().getColor(R.color.colorPrimary));
                seatRow.addView(colNum);
                 */

                n++;

            }


            seatingZone.addView(seatRow, i);

        }
        /*
        seat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

         */

        return view;
    }

}
