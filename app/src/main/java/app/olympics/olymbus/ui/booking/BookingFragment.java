package app.olympics.olymbus.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import app.olympics.olymbus.R;

public class BookingFragment extends Fragment {

    private BookingViewModel bookingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookingViewModel =
                ViewModelProviders.of(this).get(BookingViewModel.class);
        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        return view;
    }
}