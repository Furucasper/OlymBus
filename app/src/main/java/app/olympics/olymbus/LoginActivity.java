package app.olympics.olymbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import app.olympics.olymbus.ui.profile.AccountItem;

public class LoginActivity extends AppCompatActivity
{
    private ArrayList<AccountItem> accountData = new ArrayList<>();
    private EditText uid;
    private EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = findViewById(R.id.login_btn);

        File input = new File("input.txt");
        InputProcess in = new InputProcess(new Scanner("input.txt"));

        String[] accountDetail ;
        for (int k = 0; k < in.getBus().size(); k++)
        {
            accountDetail=in.getAccount().get(k).split(",");
            accountData.add(new AccountItem(accountDetail[0], accountDetail[1], accountDetail[2], accountDetail[3]));
        }
        uid = (EditText) findViewById(R.id.in_uid);
        pwd = (EditText) findViewById(R.id.in_pwd);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = uid.getText().toString();
                String password = pwd.getText().toString();

                Boolean validUsername = false;
                Boolean validPassword = false;

                for (int i = 0; i < accountData.size();i++)
                {
                    if(username.equalsIgnoreCase(accountData.get(i).getUsername()))
                    {
                        validUsername = true;

                        if(password.equalsIgnoreCase(accountData.get(i).getPassword()))
                        {
                            validPassword = true;
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    else if (!validUsername && i == accountData.size()-1)
                    {
                        Toast.makeText(getApplicationContext(), "Incorrect Username", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                if (validUsername && validPassword)
                {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }
}
