package app.olympics.olymbus;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import app.olympics.olymbus.ui.home.EventItem;
import app.olympics.olymbus.ui.profile.AccountItem;

public class MainActivity extends AppCompatActivity {

    private AccountItem account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_booking, R.id.navigation_profile)
                .build();
         */
        InputStream input = getResources().openRawResource(R.raw.input);                            // Import data from input.txt
        InputProcess in = new InputProcess(new Scanner(input));                                     // Use InputProcess

        ArrayList <EventItem> eventData = new ArrayList<>();                                        // Create new ArrayList named eventData
        String[] eventDetail ;                                                                      // Declare Array String

        for (int i = 0; i < in.getEvent().size(); i++)                                              // Loop until no event left
        {
            eventDetail = in.getEvent().get(i).split(",");                                    // Separate each category from each event and sent those to EventItem constructor
            eventData.add(new EventItem(eventDetail[0], eventDetail[1], eventDetail[2], eventDetail[3], eventDetail[4], eventDetail[5], eventDetail[6], eventDetail[7]));
        }                                                                                           // Then add each event to ArrayLists

        ArrayList <BusItem> busData = new ArrayList<>();                                            // Create new BusItem ArrayList named busData
        String[] busDetail ;                                                                        // Declare String Array
        for (int j = 0; j < in.getBus().size(); j++)                                                // Loop until last bus
        {
            busDetail=in.getBus().get(j).split(",");                                          // Separate each bus details and send to BusItem
            busData.add(new BusItem(busDetail[0], busDetail[1], busDetail[2], busDetail[3], busDetail[4], busDetail[5], busDetail[6], EVENT.getInitialDate()));
        }

        this.account = (AccountItem) getIntent().getSerializableExtra("Account");

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        navView.setSelectedItemId(R.id.navigation_profile);

        /*
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        transaction.replace(R.id.content,new HomeFragment()).commit();

                        return true;
                    case R.id.navigation_booking:
                        transaction.replace(R.id.content,new BookingFragment()).commit();

                        return true;
                    case R.id.navigation_profile:
                        transaction.replace(R.id.content,new ProfileFragment()).commit();
                        return true;
                }
                return false;
            }
        };
         */
    }

    public AccountItem getAccount(){
        return account;
    }
}
