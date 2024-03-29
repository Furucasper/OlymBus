package app.olympics.olymbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import androidx.appcompat.app.AppCompatActivity;

import app.olympics.olymbus.ui.profile.AccountItem;
import app.olympics.olymbus.ui.profile.Register;

public class LoginActivity extends AppCompatActivity
{
    private ArrayList<AccountItem> accountData = new ArrayList<>();                                 //Declare instance variables
    private GregorianCalendar wait_init = new GregorianCalendar();
    private int duration_hour = 0;
    private int duration_min = 0;
    private int attempts = 0;
    private int wait_multiply = 1;
    private AccountItem account = null;
    private String accD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = findViewById(R.id.login_btn);                                                  // Declare login button
        final EditText uid = findViewById(R.id.in_uid);                                             // Declare userID text
        final EditText pwd = findViewById(R.id.in_pwd);                                             // Declare password text

        InputStream input = getResources().openRawResource(R.raw.input);                            // Import data from input.txt
        InputProcess in = new InputProcess(new Scanner(input));                                     // Process input to categorize them

        String[] accountDetail ;                                                                    // Add each account form input to ArrayList
        for (int k = 0; k < in.getAccount().size(); k++) {
            accountDetail = in.getAccount().get(k).split(",");
            accountData.add(new AccountItem(accountDetail[0], accountDetail[1], accountDetail[2], accountDetail[3], accountDetail[4]));
        }

        FileInputStream fisAccounts = null;                                                         //Update newly registered accounts from update file
        try {
            fisAccounts = openFileInput("accountsDat.txt");
            int sizeA = fisAccounts.available();
            byte[] bufferA = new byte[sizeA];
            fisAccounts.read(bufferA);
            fisAccounts.close();
            accD = new String(bufferA);
            Log.i("Accounts Data", accD);
            int loops3 = 0;
            Updates accountUpdates = new Updates(new Scanner(accD));
            String[] acc_change;                                                                    // Add each account form input to ArrayList
            for (int k = 0; k < accountUpdates.getAccountUpdates().size(); k++) {
                acc_change = accountUpdates.getAccountUpdates().get(k).split(",");
                for(int i = 0; i < accountData.size(); i++){
                    if(Integer.parseInt(acc_change[0]) > accountData.size()){
                        accountData.add(new AccountItem(acc_change[0],acc_change[1],acc_change[2],acc_change[3],acc_change[4]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), Register.class);
        intent.putExtra("Account", account);

        btn.setOnClickListener(new View.OnClickListener()                                           // Activate method when click log in button
        {
            @Override
            public void onClick(View v)
            {
                String username = uid.getText().toString().trim();                                  // Get string from username text
                String password = pwd.getText().toString();                                         // Get string from password text

                Boolean validUsername = false;
                Boolean validPassword = false;


                if (duration_hour <= 0 && duration_min <= 0) {                                      // First, check if cooldown period ends
                    for (int i = 0; i < accountData.size(); i++) {                                  // Second, check if Username valid
                        if (username.equals(accountData.get(i).getUsername())) {
                            validUsername = true;                                                   // set username to valid
                            if (password.equals(accountData.get(i).getPassword())) {                // Then, check if password valid
                                validPassword = true;                                               // Set password to valid
                                account = accountData.get(i);                                       // Get an account prepare to login
                            }
                            else {
                                if (password.isEmpty()) {                                           // If user didn't fill-in the password, show toast
                                    Toast.makeText(getApplicationContext(), "Please enter password.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (attempts < 3) {                                                 // If user fill a wrong password but less than 3 tries. Show toast
                                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    attempts++;                                                     // attempts count plus;
                                }
                                if (attempts == 3) {                                                // If user fill a wrong password and reach 3 tries
                                    wait_init.add(Calendar.MINUTE, 15 * wait_multiply);     // Tell when timer ends
                                    wait_multiply++;                                                // More waiting time if still insert wrong password
                                    GregorianCalendar curr_time = new GregorianCalendar();          // Get current local time
                                    duration_min = wait_init.get(Calendar.MINUTE) - curr_time.get(Calendar.MINUTE);// Calculate the duration and show toast
                                    Toast.makeText(getApplicationContext(), "Please try again in " + duration_min + " minutes.", Toast.LENGTH_LONG).show();
                                    attempts = 0;                                                   // Reset attempts count
                                }
                                return;
                            }
                        }
                        else
                            {
                                if (username.isEmpty()) {                                           //If user didn't fill-in the username, show toast
                                    Toast.makeText(getApplicationContext(), "Please enter username.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (!validUsername && i == accountData.size() - 1) {                 // if username not valid. Show toast
                                    Toast.makeText(getApplicationContext(), "UserId does not exist. Please try again.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                    }

                    if (validUsername && validPassword)                                             // if username and password are valid. Proceed to Profile page.
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Account", account);                                  // Send account object and accountID to MainActivity
                        intent.putExtra("AID",account.getAccountID());
                        startActivity(intent);
                        finish();
                    }
                else                                                                                // if tries to login while still in cooldown
                {
                    GregorianCalendar curr_time = new GregorianCalendar();                          // get current local time
                    duration_hour = wait_init.get(Calendar.HOUR) - curr_time.get(Calendar.HOUR);    //calculate the duration and show toast
                    duration_min = wait_init.get(Calendar.MINUTE) - curr_time.get(Calendar.MINUTE);

                    if ( duration_hour <= 0 ) {                                                     // Show cooldown toast depends on how long will it last.
                        Toast.makeText(getApplicationContext(), "Please try again in " + duration_min + " minutes.", Toast.LENGTH_LONG).show();
                    }
                    else Toast.makeText(getApplicationContext(), "Please try again in " + duration_hour + " hours." + duration_min + " minutes.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button signUp = findViewById(R.id.reg_btn);                                                 // Declare signup button
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                           // Activate when click
                Intent intent = new Intent(getApplicationContext(), Register.class);                // Jump to Register page
                intent.putExtra("Account", accountData);
                startActivity(intent);
                finish();
            }
        });
    }

}
