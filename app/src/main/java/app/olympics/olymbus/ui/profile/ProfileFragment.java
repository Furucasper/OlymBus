package app.olympics.olymbus.ui.profile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import app.olympics.olymbus.LoginActivity;
import app.olympics.olymbus.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private String username ,password,cardNo,cvc = "";                                              // String instance variables

    public ProfileFragment () {                                                                     // Empty constructor

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView user = view.findViewById(R.id.userViewID);                                         // Declare username text box
        TextView pass = view.findViewById(R.id.userViewPass);                                       // Declare password text box
        TextView civ_id = view.findViewById(R.id.userViewCardID);                                   // Declare Card Number text box

        user.setText(username);                                                                     // set user's ID text for each logged in user
        pass.setText(password);                                                                     // set user's password text for each logged in user
        civ_id.setText(cardNo);                                                                     // set user's card number text for each logged in user

        Button logout_btn = view.findViewById(R.id.logout_btn);                                     // Declare log out button
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                       // Activate when clicked log out button
                startActivity(new Intent(getActivity(), LoginActivity.class));
                finishActivity();

            }
        });

        //About us Dialog
        Button aboutUsBtn = view.findViewById(R.id.about_btn);                               // Declare event details button
        aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                           // Activate when clicked event details button
                final Dialog dialog = new Dialog(getActivity());                                    // Create new Dialog
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_aboutus);                                // Set Dialog's Display
                dialog.setCancelable(true);

                Button closeBtn = dialog.findViewById(R.id.close_dialog_abs);                       // Declare close dialog button
                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) { dialog.cancel(); }
                });                            // Close Dialog

                dialog.show();                                                                      // Display Dialog on screen
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