import java.util.ArrayList;

/**
 * Created by thiago on 22/10/16.
 */
public class User {
    private int id;
    private String login;
    private String password;
    private ArrayList<Product> products;

    public User(String login,String password,int id){
        this.login = login;
        this.password = password;
        this.id = id;

    }

    //Setters and getters
    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
