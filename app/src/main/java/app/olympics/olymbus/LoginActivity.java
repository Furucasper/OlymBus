package app.olympics.olymbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import androidx.appcompat.app.AppCompatActivity;
import app.olympics.olymbus.ui.profile.AccountItem;
import app.olympics.olymbus.ui.profile.ProfileFragment;
import app.olympics.olymbus.ui.profile.Register;
import app.olympics.olymbus.ui.profile.ResetPassword;

public class LoginActivity extends AppCompatActivity
{
    private ArrayList<AccountItem> accountData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Button btn = findViewById(R.id.login_btn);
        final EditText uid = findViewById(R.id.in_uid);
        final EditText pwd = findViewById(R.id.in_pwd);

        InputStream input = getResources().openRawResource(R.raw.input);
        InputProcess in = new InputProcess(new Scanner(input));

        String[] accountDetail ;
        for (int k = 0; k < in.getAccount().size(); k++)
        {
            accountDetail = in.getAccount().get(k).split(",");
            accountData.add(new AccountItem(accountDetail[0], accountDetail[1], accountDetail[2], accountDetail[3]));
        }

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = uid.getText().toString();
                String password = pwd.getText().toString();

                Boolean validUsername = false;
                Boolean validPassword = false;
                int attempts = 0;

                for (int i = 0; i < accountData.size();i++)
                {
                    if(username.equalsIgnoreCase(accountData.get(i).getUsername()))
                    {
                        validUsername = true;

                        if(password.equalsIgnoreCase(accountData.get(i).getPassword()))
                        {
                            validPassword = true;
                            ProfileFragment curr_acc = new ProfileFragment(accountData.get(i));
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                            attempts++;
                            if (attempts == 3)
                            {
                                Toast.makeText(getApplicationContext(), "Please try again in $time.", Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                    }
                    else if (!validUsername && i == accountData.size()-1)
                    {
                        Toast.makeText(getApplicationContext(), "UserId does not exist. Please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (validUsername && validPassword)
                {
                    startActivity(new Intent(LoginActivity.this, ProfileFragment.class));
                    finish();
                }

            }
        });

        final Button reset = findViewById(R.id.forgot_pwd_btn);
        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, ResetPassword.class));
                finish();
            }
        });

        final Button register = findViewById(R.id.reg_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Register.class));
                finish();
            }
        });
    }
}
