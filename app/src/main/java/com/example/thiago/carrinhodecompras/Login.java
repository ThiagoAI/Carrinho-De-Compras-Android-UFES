package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends Lifecycle {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Função que verifica login e se correto entra na tela principal
    public void logar(View view){
        Intent intent = new Intent(this,TelaPrincipal.class);
        EditText editLogin = (EditText) findViewById(R.id.editLogin);
        EditText editSenha = (EditText) findViewById(R.id.editSenha);
        String login = editLogin.getText().toString();
        String senha = editSenha.getText().toString();

        /*
         *   VERIFICAÇÃO DE LOGIN SERÁ FEITA AQUI
         */

        startActivity(intent);
    }

    //Vai para a tela de registro
    public void irRegistrar(View view){
        Intent intent = new Intent(this,Registrar.class);
        startActivity(intent);
    }
}
