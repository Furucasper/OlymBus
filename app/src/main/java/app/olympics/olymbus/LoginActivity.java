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

public class LoginActivity extends AppCompatActivity
{
    private ArrayList<AccountItem> accountData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn = findViewById(R.id.login_btn);
        final EditText uid = findViewById(R.id.in_uid);
        final EditText pwd = findViewById(R.id.in_pwd);

        InputStream input = getResources().openRawResource(R.raw.input);
        InputProcess in = new InputProcess(new Scanner(input));

        String[] accountDetail ;
        for (int k = 0; k < in.getAccount().size(); k++)
        {
            accountDetail=in.getAccount().get(k).split(",");
            accountData.add(new AccountItem(accountDetail[0], accountDetail[1], accountDetail[2], accountDetail[3]));
        }

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = uid.getText().toString().trim();
                String password = pwd.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please input username/password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean validUsername = false;
                Boolean validPassword = false;

                for (int i = 0; i < accountData.size();i++)
                {
                    if(username.equals(accountData.get(i).getUsername()))
                    {
                        validUsername = true;

                        if(password.equals(accountData.get(i).getPassword()))
                        {
                            validPassword = true;
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else if (!validUsername && i == accountData.size()-1)
                    {
                        Toast.makeText(getApplicationContext(), "Incorrect Username", Toast.LENGTH_SHORT).show();
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
