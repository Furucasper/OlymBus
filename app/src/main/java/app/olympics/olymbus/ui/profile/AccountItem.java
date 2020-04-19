package app.olympics.olymbus.ui.profile;

public class AccountItem
{
    String username,password,cardNo,CVC;

    public AccountItem(){

    }

    public AccountItem(String username, String password, String card, String cvc)
    {
        this.username = username;
        this.password = password;
        this.cardNo = card;
        this.CVC = cvc;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getCardNumber() { return cardNo;}

    public String getCVC() {
        return CVC;
    }
}
