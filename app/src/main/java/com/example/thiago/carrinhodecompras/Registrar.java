package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Registrar extends Lifecycle{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
    }

    public void registrarUsuario(View view){
        Intent intent = new Intent(this,Login.class);
        EditText editEmail = (EditText) findViewById(R.id.editEmail);
        EditText editSenha = (EditText) findViewById(R.id.editSenha);
        EditText editNome = (EditText) findViewById(R.id.editNome);
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String nome = editNome.getText().toString();

        SharedPreferences.Editor ed = userPrefs.edit();
        ed.putString("name",nome);
        ed.putString("email",email);
        ed.putString("password",senha);
        ed.commit();
        /*
         *   ADICIONA AQUI
         */

        startActivity(intent);
    }
}
