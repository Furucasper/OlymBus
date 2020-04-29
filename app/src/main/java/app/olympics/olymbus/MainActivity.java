package app.olympics.olymbus;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import app.olympics.olymbus.ui.booking.Tickets;
import app.olympics.olymbus.ui.home.EventItem;
import app.olympics.olymbus.ui.profile.AccountItem;

public class MainActivity extends AppCompatActivity {

    private AccountItem account;
    private BottomNavigationView navView;
    private ArrayList<EventItem> eventData;
    private ArrayList<BusItem> busData;
    private ArrayList<AccountItem> accountData;
    private ArrayList<Tickets> ticketData;
    private String ticDir,accDir,busDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_booking, R.id.navigation_profile)
                .build();
         */

        InputStream input = getResources().openRawResource(R.raw.input);                            // Import data from input.txt
        InputProcess in = new InputProcess(new Scanner(input));                                     // Use InputProcess

        eventData = new ArrayList<>();                                        // Create new ArrayList named eventData
        String[] eventDetail;                                                                      // Declare Array String

        for (int i = 0; i < in.getEvent().size(); i++)                                              // Loop until no event left
        {
            eventDetail = in.getEvent().get(i).split(",");                                    // Separate each category from each event and sent those to EventItem constructor
            eventData.add(new EventItem(eventDetail[0], eventDetail[1], eventDetail[2], eventDetail[3], eventDetail[4], eventDetail[5], eventDetail[6], eventDetail[7]));
        }                                                                                           // Then add each event to ArrayLists

        ArrayList<String> eventDate = new ArrayList<>();
        for (EventItem e : eventData) {
            if (!eventDate.contains(e.getInitialDate())) {
                eventDate.add(e.getInitialDate());
            }
        }

        busData = new ArrayList<>();                                            // Create new BusItem ArrayList named busData
        String[] busDetail;                                                                        // Declare String Array
        for (int j = 0; j < in.getBus().size(); j++)                                                // Loop until last bus
        {
            busDetail = in.getBus().get(j).split(",");                                          // Separate each bus details and send to BusItem
            for (String date : eventDate) {
                busData.add(new BusItem(busDetail[0], busDetail[1], busDetail[2], busDetail[3], busDetail[4], busDetail[5], busDetail[6], date));
            }
        }

        accountData = new ArrayList<>();
        String[] accountDetail;                                                                    // Add each account form input to ArrayList
        for (int k = 0; k < in.getAccount().size(); k++) {
            accountDetail = in.getAccount().get(k).split(",");
            accountData.add(new AccountItem(accountDetail[0], accountDetail[1], accountDetail[2], accountDetail[3]));
        }

        this.account = (AccountItem) getIntent().getSerializableExtra("Account");

        File tickets_updates = new File ("tickets.txt");
        File buses_updates = new File ("buses.txt");
        File accounts_updates = new File ("accounts.txt");

        try {
            Updates ticketUpdates = new Updates(new Scanner(tickets_updates));
            Updates busUpdates = new Updates(new Scanner(buses_updates));
            Updates accountUpdates = new Updates(new Scanner(accounts_updates));

            String[] bus_change;                                                                    // Add each account form input to ArrayList
            for (int k = 0; k < busUpdates.getAccountUpdates().size(); k++) {
                bus_change = busUpdates.getAllBookedBusUpdates().get(k).split(",");
                busData.get(Integer.parseInt(bus_change[0])).bookSeat(bus_change[1],bus_change[2],bus_change[3]);
            }
            Toast.makeText(MainActivity.this, "Bus updated!", Toast.LENGTH_SHORT).show();

            String[] act_ticket;                                                                    // Add each account form input to ArrayList
            for (int i = 0; i < ticketUpdates.getAllTickets().size(); i++) {
                act_ticket = ticketUpdates.getAllTickets().get(i).split(",");
                Tickets t = new Tickets(eventData.get(Integer.parseInt(act_ticket[0])),busData.get(Integer.parseInt(act_ticket[1])),Integer.parseInt(act_ticket[2]),act_ticket[3],act_ticket[4]);
                ticketData.add(t);
                for (int j = 0; j < accountData.size(); j++){
                    if (t.getOwnerID()==accountData.get(j).getAccountID()){
                        accountData.get(j).addTicket(t);
                        if (act_ticket[5] == "Cancelled"){
                            accountData.get(j).cancelTicket(t);
                        }
                    }
                }
                t.setBookedTime(act_ticket[6]);
            }
            Toast.makeText(MainActivity.this, "Tickets updated!", Toast.LENGTH_SHORT).show();

            String[] acc_change;                                                                    // Add each account form input to ArrayList
            for (int k = 0; k < accountUpdates.getAccountUpdates().size(); k++) {
                acc_change = in.getAccount().get(k).split(",");
            }
            Toast.makeText(MainActivity.this, "Account updated!", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "Data can not update!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

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

    public AccountItem getAccount() {
        return account;
    }

    public ArrayList<EventItem> getAllEvent() {
        return eventData;
    }

    public ArrayList<BusItem> getAllBus() {
        return busData;
    }

    public ArrayList<AccountItem> getAllAccount() {
        return accountData;
    }

    public void selectedBooking() {
        navView.setSelectedItemId(R.id.navigation_booking);
    }

//    public void updateTicketsData() {
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput("tickets.txt", Context.MODE_PRIVATE));
//
//            for (int i = 0; i < account.getTicketsHistory().size(); i++) {
//                Tickets t = account.getTicketsHistory().get(i);
//                outputStreamWriter.write("Ticket : " + t.getTicketEvent().getEventID() + ", " + t.getTicketBus().getBusID() + ", " + t.getSid() + ", "
//                        + t.getSeatNo() + ", " + t.getOwnerID() + ", " + t.getTicketStatus() + ", " + t.getBookingTime() + "\n");
//            }
//            outputStreamWriter.close();
//            Toast.makeText(MainActivity.this, "Tickets Saved!", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//            Toast.makeText(MainActivity.this, "Tickets Save Failed!", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void updateTicketsData() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput("tickets.txt", Context.MODE_PRIVATE));
            for (int i = 0; i < account.getTicketsHistory().size(); i++) {
                Tickets t = account.getTicketsHistory().get(i);
                outputStreamWriter.write("Ticket : " + t.getTicketEvent().getEventID() + ", " + t.getTicketBus().getBusID() + ", " + t.getSid() + ", "
                        + t.getSeatNo() + ", " + t.getOwnerID() + ", " + t.getTicketStatus() + ", " + t.getBookingTime() + "\n");
            }
            outputStreamWriter.close();
            Toast.makeText(MainActivity.this, "Tickets Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Toast.makeText(MainActivity.this, "Tickets Save Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBusData() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput("buses.txt", Context.MODE_PRIVATE));
            for (int i = 0; i < account.getBusHistory().size(); i++) {
                BusItem b = account.getBusHistory().get(i);
                for (int j = 0; j < b.getBookedSeats().size(); j++)
                outputStreamWriter.write("Bus : " + b.getBusID() + ", " + b.getBookedSeats().get(j)[0]
                        +", "+ b.getBookedSeats().get(j)[1] + ", " + b.getBookedSeats().get(j)[2] +"\n");
            }
            outputStreamWriter.close();
            Toast.makeText(MainActivity.this, "Buses Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Toast.makeText(MainActivity.this, "Buses Save Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateAccountData() {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(MainActivity.this.openFileOutput("accounts.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("Account : " + account.getAccountID() + ", " + account.getPassword());
            outputStreamWriter.close();
            Toast.makeText(MainActivity.this, "Accounts Saved!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            Toast.makeText(MainActivity.this, "Accounts Save Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop () {
        super.onStop();
        updateTicketsData();
        updateBusData();
        updateAccountData();
    }
}
