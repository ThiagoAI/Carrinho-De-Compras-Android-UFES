package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Lifecycle {

    Boolean remember;
    CheckBox ch_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    protected void onResume(){
        super.onResume();
        restoreInfo();
    }

    protected void restoreInfo(){
        remember = sysPrefs.getBoolean("remember",false);

        ch_remember = (CheckBox) findViewById(R.id.checkLembrar);
        ch_remember.setChecked(remember);

        if(remember && user.exits()){
            EditText editLogin = (EditText) findViewById(R.id.editLogin);
            EditText editSenha = (EditText) findViewById(R.id.editSenha);
            editLogin.setText(user.getEmail());
            editSenha.setText(user.getPassword());
        }
    }

    protected void setRemember(){
        SharedPreferences.Editor ed = sysPrefs.edit();
        ed.putBoolean("remember", ch_remember.isChecked());
        ed.commit();
    }

    //Função que verifica login e se correto entra na tela principal
    public void logar(View view){
        Intent intent = new Intent(this,TelaPrincipal.class);
        EditText editLogin = (EditText) findViewById(R.id.editLogin);
        EditText editSenha = (EditText) findViewById(R.id.editSenha);
        String login = editLogin.getText().toString();
        String senha = editSenha.getText().toString();

        if(!user.exits()){
            Toast.makeText(getApplicationContext(),"Não há usuário registrado.", Toast.LENGTH_LONG).show();
            return;
        }

        if(login == "" || senha == ""){
            Toast.makeText(getApplicationContext(),"Digite sua senha e/ou login.",Toast.LENGTH_LONG).show();
            return;
        }

        setRemember();

        if(user.authenticate(login,senha)){
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Login ou senha inválido.",Toast.LENGTH_LONG).show();
        }
    }

    //Vai para a tela de registro
    public void irRegistrar(View view){
        Intent intent = new Intent(this,Registrar.class);
        startActivity(intent);
    }
}
