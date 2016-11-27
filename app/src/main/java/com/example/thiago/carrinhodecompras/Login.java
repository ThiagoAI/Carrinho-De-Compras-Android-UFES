package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void logar(View view){
        AcessoWebService acessoWebService = new AcessoWebService();
        Intent intent = new Intent(this,TelaPrincipal.class);
        EditText editLogin = (EditText) findViewById(R.id.editLogin);
        EditText editSenha = (EditText) findViewById(R.id.editSenha);
        String login = editLogin.getText().toString();
        String senha = editSenha.getText().toString();
        String resposta = acessoWebService.logar( login, senha );

        if(!user.exits()){
            Toast.makeText(getApplicationContext(),"Não há usuário registrado.", Toast.LENGTH_LONG).show();
            return;
        }

        if(login == "" || senha == ""){
            Toast.makeText(getApplicationContext(),"Digite sua senha e/ou login.",Toast.LENGTH_LONG).show();
            return;
        }

        setRemember();

        /*if(user.authenticate(login,senha)){
            startActivity(intent);
        }*/
        if ( resposta.equals("sucesso") )
            startActivity(intent);
        else{
            Toast.makeText(getApplicationContext(),"Login ou senha inválido.",Toast.LENGTH_LONG).show();
        }
    }

    //Vai para a tela de registro
    public void irRegistrar(View view){
        Intent intent = new Intent(this,Registrar.class);
        startActivity(intent);
    }

    private class AcessoWebService
    {
        private String baseUrl = "http://localhost:9000/";
        private String usuarioUrl = baseUrl + "usuario/";
        /*public AcessoWebService()
        {
            URL url = cr
        }*/

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public String logar(String login, String senha )
        {
            HttpURLConnection connection = null;
            String urlBase = usuarioUrl + login;
            URL url = null;
            try {
                try {
                     url = new URL(urlBase);
                } catch ( Exception e )
                {
                    Toast.makeText(getApplicationContext(), "Erro de conversão de URL", Toast.LENGTH_LONG ).show();
                }


                connection = (HttpURLConnection) url.openConnection();
                int resposta = connection.getResponseCode();
                if ( resposta == HttpURLConnection.HTTP_OK )
                {
                    StringBuilder builder = new StringBuilder();

                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream())
                    ))
                    {
                        String linha;

                        while ( ( linha = reader.readLine()) != null )
                            builder.append(linha);

                        JSONObject jsonObject = new JSONObject(builder.toString());
                        if ( jsonObject.getString("nome").equals(login) )
                        {
                            if ( jsonObject.getString("senha").equals(senha) )
                                return "sucesso";
                            else
                                return "fail";
                        }
                        else
                            return "fail";


                    }
                    catch ( IOException e )
                    {
                        Toast.makeText(getApplicationContext(),"Erro de leitura dos dados do WebService", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Erro:%nNão foi possível conctar ao WebService", Toast.LENGTH_LONG ).show();
                }
            }
            catch ( Exception e )
            {
                Toast.makeText(getApplicationContext(), "Erro:%nNão foi possível conctar ao WebService", Toast.LENGTH_LONG ).show();
                e.printStackTrace();
            }
            finally {
                connection.disconnect();
            }

            return "fail";

        }

    }
}
