package app.olympics.olymbus.ui.profile;

public class AccountItem
{
    String username,password,cardNo,CVC;                                                            // Declare String instance variables

    public AccountItem(){ }                                                                         // Empty constructor

    public AccountItem(String username, String password, String card, String cvc)                   // Constructor with each every account's details
    {
        this.username = username;                                                                   //set each instance variable depends on each account
        this.password = password;
        this.cardNo = card;
        this.CVC = cvc;
    }

    public String getUsername() { return username; }                                                // Declare methods for-easy-to-access

    public String getPassword() { return password; }

    public String getCardNumber() { return cardNo;}

    public String getCVC() { return CVC; }
}
