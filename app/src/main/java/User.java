import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by thiago on 22/10/16.
 */
public class User {
    String id;
    String email;
    String password;
    String name;
    ArrayList<String> products;

    public User(){
        this.email = " ";
        this.password = " ";
        this.id = " ";
        this.name = " ";
        this.products = null;
    }

    public User restoreUser(SharedPreferences prefs){
        this.setEmail(prefs.getString("email"," "));
        this.setName(prefs.getString("name"," "));
        this.setPassword(prefs.getString("password"," "));
        return this;
    }

    public void updateUser(SharedPreferences prefs){
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("name",this.name);
        ed.putString("email",this.email);
        ed.putString("password",this.password);
        ed.putString("id",this.id);
        ed.commit();
        //Toast.makeText(context,"Local User Info Updated", Toast.LENGTH_LONG).show();
    }

    public Boolean exits(){
        if (email != " ") return true;
        return false;
    }

    public Boolean authenticate(String email, String password){
        if(this.email.equals(email) && this.password.equals(password)) return true;
        else return false;
    }

    //Setters and getters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
