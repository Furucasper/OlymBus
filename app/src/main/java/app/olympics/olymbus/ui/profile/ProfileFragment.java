package app.olympics.olymbus.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import app.olympics.olymbus.LoginActivity;
import app.olympics.olymbus.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button logout_btn = view.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                finishActivity();

            }
        });

        return view;
    }

    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
}