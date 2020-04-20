package app.olympics.olymbus.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.olympics.olymbus.LoginActivity;
import app.olympics.olymbus.R;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final TextView username = findViewById(R.id.userID_reg);
        final TextView password = findViewById(R.id.password_reg);
        TextView con_password = findViewById(R.id.confirm_password);
        TextView cardNo = findViewById(R.id.card_number_reg);
        TextView cvcNo = findViewById(R.id.cvc_number);
        Button sign_up = findViewById(R.id.signup_btn);                                             // Declare sign up button
        Button sign_in = findViewById(R.id.sign_in_btn);                                            // Declare sign in button

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid_account = false;

                if (valid_account)
                {
                    startActivity(new Intent(Register.this, ProfileFragment.class));
                    finish();
                }
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, LoginActivity.class));
                finish();
            }
        });
    }
}
