package app.olympics.olymbus;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    private AccountItem account, newAccount;
    private BottomNavigationView navView;
    private ArrayList<EventItem> eventData;
    private ArrayList<BusItem> busData;
    private ArrayList<AccountItem> accountData;
    private ArrayList<Tickets> ticketData;
    private String ticD,accD,busD;
    private String aid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        InputStream input = getResources().openRawResource(R.raw.input);                            // Import data from input.txt
        InputProcess in = new InputProcess(new Scanner(input));                                     // Use InputProcess

        eventData = new ArrayList<>();                                        // Create new ArrayList named eventData
        String[] eventDetail;                                                                      // Declare Array String

        for (int i = 0; i < in.getEvent().size(); i++)                                              // Loop until no event left
        {
            eventDetail = in.getEvent().get(i).split(",");                                    // Separate each category from each event and sent those to EventItem constructor
            eventData.add(new EventItem(eventDetail[0], eventDetail[1], eventDetail[2], eventDetail[3], eventDetail[4], eventDetail[5], eventDetail[6], eventDetail[7], eventDetail[8]));
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
                busData.add(new BusItem(busDetail[0], busDetail[1], busDetail[2], busDetail[3], busDetail[4], busDetail[5], busDetail[6], busDetail[7], date));
            }
        }

        accountData = new ArrayList<>();
        String[] accountDetail;                                                                    // Add each account form input to ArrayList
        for (int k = 0; k < in.getAccount().size(); k++) {
            accountDetail = in.getAccount().get(k).split(",");
            accountData.add(new AccountItem(accountDetail[0], accountDetail[1], accountDetail[2], accountDetail[3], accountDetail[4]));
        }
        newAccount = (AccountItem) getIntent().getSerializableExtra("NEW ACCOUNT");
        if(newAccount!=null) accountData.add(newAccount);
        aid = getIntent().getStringExtra("AID");
        for(AccountItem a : accountData){
            if (a.getAccountID().equals(aid)) {
                account = a;
            }
        }

        ticketData = new ArrayList<>();
        try {
            FileInputStream fisTickets = openFileInput("ticketsDat.txt");
            FileInputStream fisBuses = openFileInput("busesDat.txt");
            FileInputStream fisAccounts = openFileInput("accountsDat.txt");
            int sizeT = fisTickets.available();
            int sizeB = fisBuses.available();
            int sizeA = fisAccounts.available();
            byte[] bufferT = new byte[sizeT];
            byte[] bufferB = new byte[sizeB];
            byte[] bufferA = new byte[sizeA];
            fisTickets.read(bufferT);
            fisBuses.read(bufferB);
            fisAccounts.read(bufferA);
            fisTickets.close();
            fisBuses.close();
            fisAccounts.close();
            ticD = new String(bufferT);
            busD = new String(bufferB);
            accD = new String(bufferA);
            Log.i("Tickets Data", ticD);
            Log.i("Buses Data", busD);
            Log.i("Accounts Data", accD);
            Updates ticketUpdates = new Updates(new Scanner(ticD));
            int loops1 = 0;
            String[] act_ticket;                                                                    // Add each account form input to ArrayList
            for(String ticketUp : ticketUpdates.getAllTickets()){
                EventItem event = null;
                BusItem bus = null;
                act_ticket = ticketUp.split(",");
                for (EventItem e : eventData){
                    if (e.getEventID().equals(act_ticket[0])) event = e;
                }
                for (BusItem b : busData){
                    if (b.getBusID().equals(act_ticket[1])) bus = b;
                }
                if(event!=null && bus!=null){ // Ticket : EventID, BusID, SID, SeatNo., AccountID, Status, BookingTime
                    Tickets t = new Tickets(event, bus,Integer.parseInt(act_ticket[2]),act_ticket[3],act_ticket[4]);
                    t.setOldTicket();
                    ticketData.add(t);
                    for (AccountItem a : accountData){
                        if (a.getAccountID().equals(t.getOwnerID())){
                            a.addTicket(t);
                            loops1++;
                            if (act_ticket[5].equals("Cancelled")){
                                a.cancelTicket(t);
                            }
                        }
                    }
                    t.setBookedTime(act_ticket[6]);
                    int yyyy,MM,dd,HH,mm,ss;
                    String[] bookedTime = act_ticket[6].split(" ");
                    String[] date = bookedTime[0].split("\\.");
                    String[] time = bookedTime[1].split(":");
                    yyyy = Integer.parseInt(date[0]);
                    MM = Integer.parseInt(date[1]);
                    dd = Integer.parseInt(date[2]);
                    HH = Integer.parseInt(date[0]);
                    mm = Integer.parseInt(date[1]);
                    ss = Integer.parseInt(date[2]);
                    t.setGregoTicketTime(yyyy,MM,dd,HH,mm,ss);
                    //2020.04.30 19:49:36 Date(int year, int month, int date, int hrs, int min, int sec)
                }
            }
            //Toast.makeText(MainActivity.this, loops1 + " Tickets updated!", Toast.LENGTH_SHORT).show();

            int loops2 = 0;
            Updates busUpdates = new Updates(new Scanner(busD));
            String[] bus_change;                                                                    // Add each account form input to ArrayList
            for (String busUpdate : busUpdates.getAllBookedBusUpdates()){
                bus_change = busUpdate.split(",");
                for(BusItem b : busData){
                    if(b.getBusID().equals(bus_change[0])){
                        b.bookSeat(bus_change[1],bus_change[2],bus_change[3]);
                        loops2++;
                    }
                }
            }
            //Toast.makeText(MainActivity.this, loops2 + " Bus updated!", Toast.LENGTH_SHORT).show();

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
            //Toast.makeText(MainActivity.this, "Account updated!", Toast.LENGTH_SHORT).show();
            loops3 = 0;

        } catch (IOException e) {
            //Toast.makeText(MainActivity.this, "Data can not update!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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

    public void updateTicketsData() {
        try {
            for (int i = 0; i < account.getTickets().size(); i++){
                if(!ticketData.contains(account.getTickets().get(i))){
                    ticketData.add(account.getTickets().get(i));
                }
            }
            String data = "";
            FileOutputStream fos = openFileOutput("ticketsDat.txt",Context.MODE_PRIVATE);
            data += "// Ticket : EventID, BusID, SID, SeatNo., AccountID, Status, BookingTime\n"; // Ticket : EventID, BusID, SID, SeatNo., AccountID, Status, BookingTime
            for (int i = 0; i < ticketData.size(); i++) {
                Tickets t = ticketData.get(i);
                data+=("Ticket : " + t.getTicketEvent().getEventID() + ", " + t.getTicketBus().getBusID() + ", " + t.getSid() + ", "
                        + t.getSeatNo() + ", " + t.getOwnerID() + ", " + t.getTicketStatus() + ", " + t.getBookingTime() + "\n");
            }
            fos.write(data.getBytes());
            Log.i("Tickets Data WRITE", data);
            fos.close();
            //Toast.makeText(MainActivity.this, "Tickets Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            //Toast.makeText(MainActivity.this, "Tickets Save Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBusData() {
        try {
            String data = "";
            FileOutputStream fos = openFileOutput("busesDat.txt",Context.MODE_PRIVATE);
            data += "// Bus : BusID, SID, AccountID, Date\n";
            for (int i = 0; i < account.getBusHistory().size(); i++) {
                    BusItem b = account.getBusHistory().get(i);
                    for (int j = 0; j < b.getBookedSeats().size(); j++)                                 // Bus : BusID, SID, AccountID, Date
                        data+=("Bus : " + b.getBusID() + ", " + b.getBookedSeats().get(j)[0]
                                +", "+ b.getBookedSeats().get(j)[1] + ", " + b.getBookedSeats().get(j)[2] +"\n");
            }
            fos.write(data.getBytes());
            Log.i("Buses Data WRITE", data);
            fos.close();
            //Toast.makeText(MainActivity.this, "Buses Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            //Toast.makeText(MainActivity.this, "Buses Save Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateAccountData() {
        try {
            String data = "";
            FileOutputStream fos = openFileOutput("accountsDat.txt",Context.MODE_PRIVATE);
            data += "// Account : AccountID, Username, Password, Card, CSV\n";
            for (int i = 0; i <accountData.size(); i++ ){
                data+=("Account : " + accountData.get(i).getAccountID() + ", " +
                        accountData.get(i).getUsername() + ", " + accountData.get(i).getPassword()
                        + ", " + accountData.get(i).getCardNumber() + ", " + accountData.get(i).getCSV()+"\n");
            }
            fos.write(data.getBytes());
            Log.i("Accounts Data WRITE", data);
            fos.close();
            //Toast.makeText(MainActivity.this, "Accounts Saved!", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            //Toast.makeText(MainActivity.this, "Accounts Save Failed!", Toast.LENGTH_SHORT).show();
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
