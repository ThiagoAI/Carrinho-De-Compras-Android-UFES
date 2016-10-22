package com.example.thiago.carrinhodecompras;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by thiago on 22/10/16.
 */
public class Lifecycle extends AppCompatActivity {
    protected static final String CATEGORIA = "Estado";

    public Lifecycle context;

    public SharedPreferences userPrefs;
    public SharedPreferences sysPrefs;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
    }
}
