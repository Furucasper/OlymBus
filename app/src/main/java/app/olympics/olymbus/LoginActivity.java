package app.olympics.olymbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import androidx.appcompat.app.AppCompatActivity;
import app.olympics.olymbus.ui.profile.AccountItem;

public class LoginActivity extends AppCompatActivity
{
    private ArrayList<AccountItem> accountData = new ArrayList<>();
    private GregorianCalendar wait_init = new GregorianCalendar();
    private int duration = 0;
    private int attempts = 0;
    private int wait_multiply = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = findViewById(R.id.login_btn);                                                  //declare login button
        final EditText uid = findViewById(R.id.in_uid);                                             //declare userID text
        final EditText pwd = findViewById(R.id.in_pwd);                                             //declare password text

        InputStream input = getResources().openRawResource(R.raw.input);                            //import data from input.txt
        InputProcess in = new InputProcess(new Scanner(input));                                     //process input to categorize them

        String[] accountDetail ;                                                                    //add each account form input to ArrayList
        for (int k = 0; k < in.getAccount().size(); k++) {
            accountDetail = in.getAccount().get(k).split(",");
            accountData.add(new AccountItem(accountDetail[0], accountDetail[1], accountDetail[2], accountDetail[3]));
        }

        btn.setOnClickListener(new View.OnClickListener()                                           // Activate method when click log in button
        {
            @Override
            public void onClick(View v)
            {
                String username = uid.getText().toString();                                         //  Get string from username text
                String password = pwd.getText().toString();                                         //  Get string from password text

                Boolean validUsername = false;
                Boolean validPassword = false;


                if (duration <= 0) {                                                                // First, check if cooldown period ends
                    for (int i = 0; i < accountData.size(); i++) {                                  // Second, check if Username valid
                        if (username.equalsIgnoreCase(accountData.get(i).getUsername())) {
                            validUsername = true;                                                   // set username to valid

                            if (password.equalsIgnoreCase(accountData.get(i).getPassword())) {      // Then, check if password valid
                                validPassword = true;                                               // set password to valid
                            } else {
                                if (attempts < 3) {                                                 // if not and less than 3 tries. Show toast
                                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    attempts++;                                                     // attempts plus;
                                }
                                if (attempts == 3) {                                                // if not and reach 3 tries
                                    wait_init.add(Calendar.MINUTE, 15 * wait_multiply);     // tell when timer ends
                                    wait_multiply++;                                                // more waiting time if still insert wrong password
                                    GregorianCalendar curr_time = new GregorianCalendar();          // get current local time
                                    duration = wait_init.get(Calendar.MINUTE) - curr_time.get(Calendar.MINUTE);//calculate the duration and show toast
                                    Toast.makeText(getApplicationContext(), "Please try again in " + duration + " minutes.", Toast.LENGTH_LONG).show();
                                    attempts = 0;                                                   // reset attempt
                                }
                                return;
                            }
                        } else if (!validUsername && i == accountData.size() - 1) {                 // if username not valid. Show toast
                            Toast.makeText(getApplicationContext(), "UserId does not exist. Please try again.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if (validUsername && validPassword) {                                           // if username and password are valid. Proceed to Main page.
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }
                else                                                                                // if tries to login while still in cooldown
                {
                    GregorianCalendar curr_time = new GregorianCalendar();                          // get current local time
                    duration = wait_init.get(Calendar.MINUTE) - curr_time.get(Calendar.MINUTE);     //calculate the duration and show toast
                    Toast.makeText(getApplicationContext(), "Please try again in " + duration + " minutes.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
