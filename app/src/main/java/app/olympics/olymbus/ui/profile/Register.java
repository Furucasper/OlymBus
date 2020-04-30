package app.olympics.olymbus.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import app.olympics.olymbus.LoginActivity;
import app.olympics.olymbus.MainActivity;
import app.olympics.olymbus.R;

public class Register extends AppCompatActivity {

    private AccountItem newAccount;
    private static Character[] UPPER_CASE = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    private static Character[] LOWER_CASE = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private static Character[] NUMBER = {'1','2','3','4','5','6','7','8','9','0'};
    private static Character[] SPECIAL = {'!', '"', '#', '$', '%', '&', '(', ')', '*', "'".charAt(0), '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\' , ']', '^', '_', '`', '{', '|', '}', '~'};
    private boolean validID, validPass, validConfirmPass, validCard, validCSV;
    private ArrayList<AccountItem> allAccount ;
    private TextView username, password, con_password, cardNo, csvNo;
    private TextView idPrompt, passPrompt, conPrompt, cardPrompt, csvPrompt;
    private String id, pass, confirmPass, card, csv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        this.allAccount = (ArrayList<AccountItem>) getIntent().getSerializableExtra("Account");

        username = findViewById(R.id.userID_reg);
        password = findViewById(R.id.password_reg);
        con_password = findViewById(R.id.confirm_password);
        cardNo = findViewById(R.id.card_number_reg);
        csvNo = findViewById(R.id.csv_number);
        idPrompt = findViewById(R.id.id_prompt);
        passPrompt = findViewById(R.id.password_prompt);
        conPrompt = findViewById(R.id.confirm_password_prompt);
        cardPrompt = findViewById(R.id.card_prompt);
        csvPrompt = findViewById(R.id.csv_prompt);

        Button sign_up = findViewById(R.id.signup_btn);                                             // Declare sign up button
        Button sign_in = findViewById(R.id.sign_in_btn);                                            // Declare sign in button

        username.addTextChangedListener(UsernameCheck);
        password.addTextChangedListener(PasswordCheck);
        con_password.addTextChangedListener(ConfrimCheck);
        cardNo.addTextChangedListener(CardConfirm);
        csvNo.addTextChangedListener(CSVConfrim);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid_account = validID && validPass && validConfirmPass && validCard && validCSV;
                if (valid_account)
                {
                    allAccount.add(new AccountItem(allAccount.size()+"",id, pass,card,csv));
                    newAccount = new AccountItem(allAccount.size()+"",id, pass,card,csv);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("Account", newAccount);
                    startActivity(intent);
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

    private TextWatcher UsernameCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            validID = false;
            id = username.getText().toString().trim();
            if (id.isEmpty())
            {
                idPrompt.setText("Please Enter Your ID.");
            }

            else {
                boolean startWithUpper = false;
                for (int i = 0; i < UPPER_CASE.length; i++) {
                    if (id.charAt(0) == UPPER_CASE[i]) {
                        startWithUpper = true;
                        validID = true;
                    }
                }
                if (!startWithUpper) {
                    idPrompt.setText("UserID must start with uppercase letter");
                }
                else{
                    idPrompt.setText("");
                }

            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            id = username.getText().toString().trim();
        }
    };

    private TextWatcher PasswordCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            validPass = false;
            pass = password.getText().toString().trim();
            int upperCnt = 0;
            int lowerCnt = 0;
            int numCnt = 0;
            int specialCnt = 0;
            String message = "Password required : ";

            for (int i = 0; i < pass.length(); i++){
                for (int j = 0; j < UPPER_CASE.length; j++){
                    if(pass.charAt(i) == UPPER_CASE[j]){
                        upperCnt++;
                    }
                }
                for (int j = 0; j < LOWER_CASE.length; j++){
                    if(pass.charAt(i) == LOWER_CASE[j]){
                        lowerCnt++;
                    }
                }
                for (int j = 0; j < NUMBER.length; j++){
                    if(pass.charAt(i) == NUMBER[j]){
                        numCnt++;
                    }
                }
                for (int j = 0; j < SPECIAL.length; j++){
                    if(pass.charAt(i) == SPECIAL[j]){
                        specialCnt++;
                    }
                }
            }
            if(upperCnt<1) { message += "1 Uppercase ";}
            if(lowerCnt<5) { message += "5 Lowercase ";}
            if(numCnt<3) { message += "3 Number ";}
            if(specialCnt<2) { message += "2 Special character";}
            if(upperCnt<1 ||lowerCnt<5 ||numCnt<3 ||specialCnt<2) { passPrompt.setText(message);}
            else {
                passPrompt.setText("");
                validPass = true;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            pass = password.getText().toString().trim();
        }
    };

    private TextWatcher ConfrimCheck = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            validConfirmPass = false;
            confirmPass = con_password.getText().toString().trim();
            if(password.getText().toString().length() == confirmPass.length()){
                for(int i = 0; i < password.getText().toString().trim().length(); i++){
                    if (password.getText().toString().charAt(i) != confirmPass.charAt(i)){
                        conPrompt.setText("Password and confirm password are mismatch");
                        validConfirmPass = false;
                        break;
                    }
                    else {
                        validConfirmPass = true;
                        conPrompt.setText("");
                    }
                }
            }
            else conPrompt.setText("Password and confirm password are mismatch");
            if (confirmPass.isEmpty())
            {
                conPrompt.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher CardConfirm = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validCard = false;
            card = cardNo.getText().toString().trim();
            if (card.length() == 16) {
                cardPrompt.setText("");
                validCard = true;
            }
            else cardPrompt.setText("Card must be 16 numbers long");
        }

        @Override
        public void afterTextChanged(Editable s) {
            card = cardNo.getText().toString().trim();
        }
    };

    private TextWatcher CSVConfrim = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            validCSV = false;
            csv = csvNo.getText().toString().trim();
            if (csv.length() == 3) {
                csvPrompt.setText("");
                validCSV = true;
            }
            else csvPrompt.setText("CSV must be 3 numbers long");
        }

        @Override
        public void afterTextChanged(Editable s) {
            csv = csvNo.getText().toString().trim();
        }
    };
}
