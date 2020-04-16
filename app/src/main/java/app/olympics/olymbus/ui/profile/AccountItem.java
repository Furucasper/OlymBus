package app.olympics.olymbus.ui.profile;

public class AccountItem
{
    String username,password,citizen_id,CCV;

    public AccountItem(){

    }

    public AccountItem(String username, String password, String id, String ccv)
    {
        this.username = username;
        this.password = password;
        this.citizen_id = id;
        this.CCV = ccv;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getCitizen_id() {
        return citizen_id;
    }

    public String getCCV() {
        return CCV;
    }
}
