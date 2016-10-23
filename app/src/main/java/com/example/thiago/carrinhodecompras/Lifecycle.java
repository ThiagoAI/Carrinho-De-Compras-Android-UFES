package com.example.thiago.carrinhodecompras;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by thiago on 22/10/16.
 */
public class Lifecycle extends AppCompatActivity {
    protected static final String CATEGORIA = "Estado";

    public Lifecycle context;

    public SharedPreferences userPrefs;
    public SharedPreferences sysPrefs;
    public User user;

    protected String activityInfo(){
        String aux = getClass().getName();
        return aux;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this;
        Log.i(CATEGORIA, activityInfo()+".onCreate();");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStart(){
        super.onStart();
        Log.i(CATEGORIA, activityInfo()+".onStart();");
    }

    protected void onRestart(){
        super.onRestart();
        Log.i(CATEGORIA, activityInfo()+".onRestart();");
    }

    protected void onResume(){
        super.onResume();
        sysPrefs = getSharedPreferences("sys", Context.MODE_PRIVATE);
        userPrefs = getSharedPreferences("user", Context.MODE_PRIVATE);
        user = new User().restoreUser(userPrefs);
        Log.i(CATEGORIA, activityInfo()+".onResume();");
    }

    protected void onPause(){
        super.onPause();
        Log.i(CATEGORIA, activityInfo()+".onPause();");
    }

    protected void onStop(){
        super.onStop();
        Log.i(CATEGORIA, activityInfo()+".onStop();");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(CATEGORIA, activityInfo()+".onDestroy();");
    }

    public void pause(View view){
        this.onPause();
    }

    public void resume(View view){
        this.onResume();
    }

    public void back(View view){
        finish();
    }
}
