package app.olympics.olymbus.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import app.olympics.olymbus.MainActivity;
import app.olympics.olymbus.R;
import app.olympics.olymbus.TicketAdapter;
import app.olympics.olymbus.ui.profile.AccountItem;

public class BookingFragment extends Fragment implements TicketAdapter.OnTicketListener {

    private BookingViewModel bookingViewModel;
    private RecyclerView ticketsRecyclerview;
    private TicketAdapter ticketAdapter;
    private AccountItem account;
    private ArrayList<Tickets> ticketsData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        account = ((MainActivity)getActivity()).getAccount();

        ticketsRecyclerview = view.findViewById(R.id.ticketList);
        ticketsData = account.getTickets();

            //set RecyclerView Adapter
            ticketAdapter = new TicketAdapter(getActivity(), ticketsData,this);
            ticketsRecyclerview.setAdapter(ticketAdapter);
            ticketsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));



        return view;
    }

    @Override
    public void onTicketClick(int position) {

    }
}