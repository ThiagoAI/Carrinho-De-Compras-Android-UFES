package com.example.thiago.carrinhodecompras;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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
            editLogin.setText(user.getName());
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

        Intent intent = new Intent(this,TelaPrincipal.class);
        EditText editLogin = (EditText) findViewById(R.id.editLogin);
        EditText editSenha = (EditText) findViewById(R.id.editSenha);
        String login = editLogin.getText().toString();
        String senha = editSenha.getText().toString();

        if(login == null || senha == null||login.isEmpty() || senha.isEmpty()||login == "" || senha == "" || login == " " || senha == "" ){
            Toast.makeText(getApplicationContext(),"Digite sua senha e/ou login.",Toast.LENGTH_LONG).show();
            return;
        }

        String baseUrl = "http://10.0.2.2:9000/";
        String usuarioUrl = baseUrl + "usuario/";
        usuarioUrl = usuarioUrl + login;
        URL url = null;
        try
        {
            url = new URL(usuarioUrl);
        }
        catch ( Exception e )
        {
            Toast.makeText(getApplicationContext(), "Erro de conversão de URL", Toast.LENGTH_LONG ).show();
            return;
        }

        AcessoWebService acessoWebService = new AcessoWebService( login, senha,this );
        acessoWebService.execute( url );
        //acessoWebService.execute( url );
        //String resposta = acessoWebService.logarPrototipo( login, senha );

        setRemember();

        /*if(user.authenticate(login,senha)){
            startActivity(intent);
        }*/
        /*if ( resposta.equals("sucesso") )
            startActivity(intent);*/

    }

    //Vai para a tela de registro
    public void irRegistrar(View view){
        Intent intent = new Intent(this,Registrar.class);
        startActivity(intent);
    }

    private class AcessoWebService extends AsyncTask< URL, Void, JSONObject >
    {
        private String baseUrl = "http://localhost:9000/";
        private String usuarioUrl = baseUrl + "usuario/";
        private String loginUsuario;
        private String senhaUsuario;
        private boolean sucesso;
        private Activity act;

        public AcessoWebService( String login, String senha,Activity act )
        {
            this.act = act;
            loginUsuario = login;
            senhaUsuario = senha;
            setSucesso(false);
        }




        /*@RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public String logar(String login, String senha )
        {
            String urlBase = usuarioUrl + login;
            URL url = null;
            try
            {
                url = new URL(urlBase);
            }
            catch ( Exception e )
            {
                Toast.makeText(getApplicationContext(), "Erro de conversão de URL%nURL: " + urlBase, Toast.LENGTH_LONG ).show();
            }

            JSONObject jsonObject = this.execute(url);


        }*/

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public String logarPrototipo(String login, String senha )
        {
            HttpURLConnection connection = null;
            String urlBase = usuarioUrl + login;
            Toast.makeText(getApplicationContext(), urlBase, Toast.LENGTH_LONG ).show();
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
                    Toast.makeText(getApplicationContext(), "Erro:%nNão foi possível conctar ao WebService. Bad Conection", Toast.LENGTH_LONG ).show();
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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected JSONObject doInBackground(URL... params)
        {
            HttpURLConnection connection = null;

            try
            {
                connection = (HttpURLConnection) params[ 0 ].openConnection();
                int resposta = connection.getResponseCode();

                if ( resposta == HttpURLConnection.HTTP_OK )
                {
                    StringBuilder builder = new StringBuilder();

                    try ( BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream())))
                    {
                        String linha;

                        while ((linha = reader.readLine()) != null )
                            builder.append(linha);

                    }
                    catch ( IOException e )
                    {
                        System.out.println("ERRO NA LEITURA DO WEB SERVICE!");
                        //Toast.makeText(getApplicationContext(),"Erro de leitura dos dados do WebService", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "Testtttt", Toast.LENGTH_LONG ).show();
                    JSONObject objeto = new JSONObject(builder.toString() );
                    System.out.println(builder.toString() + "!!! ! !       !");
                    if(objeto == null) System.out.println("CUZ I DID NOTHING WRONG MAMA ????????????????????????????????");
                    else System.out.println("CUZ I DID RIGHT MAMA EEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

                    return objeto;
                }
                else
                {
                    System.out.println("ERRO BAD CONNECTION!");
                    //Toast.makeText(getApplicationContext(), "Erro:%nNão foi possível conctar ao WebService. Bad Conection", Toast.LENGTH_LONG ).show();
                }
            }
            catch ( Exception e )
            {
                System.out.println("ERRO NAO FOI POSSIVEL CONECTAR AO WEB SERVICE!");
                //Toast.makeText(getApplicationContext(), "Erro:%nNão foi possível conctar ao WebService", Toast.LENGTH_LONG ).show();
                e.printStackTrace();
            }
            finally
            {
                connection.disconnect();

            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject usuario )
        {



            try
            {
                if ( usuario.getString( "nome" ).equals( loginUsuario ) )
                {
                    System.out.println("eeee");
                    if ( usuario.getString("senha").equals(senhaUsuario) ) {
                        Toast.makeText(getApplicationContext(), "Login efetuado com sucesso", Toast.LENGTH_LONG ).show();
                        setSucesso(true);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login ou senha incorreto(s)", Toast.LENGTH_LONG ).show();
                        setSucesso(false);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login ou senha incorreto(s)", Toast.LENGTH_LONG ).show();
                    setSucesso(false);
                }
            }
            catch ( JSONException e )
            {
                Toast.makeText(getApplicationContext(), "Erro de processamento do JSON", Toast.LENGTH_LONG ).show();
                e.printStackTrace();

            }

            Intent intent = new Intent(act,TelaPrincipal.class);
            Lifecycle l = (Lifecycle) act;
            SharedPreferences.Editor ed = l.userPrefs.edit();

            if ( isSucesso() ) {
                try {
                    ed.remove("name");
                    ed.remove("email");
                    ed.remove("password");
                    ed.putString("name", usuario.getString("nome"));
                    ed.putString("email", usuario.getString("email"));
                    ed.putString("password", usuario.getString("senha"));
                    ed.commit();
                } catch (Exception e) {e.printStackTrace();};
                startActivity(intent);
            }
            else{
                //Toast.makeText(getApplicationContext(),"Login ou senha inválido.",Toast.LENGTH_LONG).show();
            }
            /*JSONObject jsonObject = new JSONObject(builder.toString());
            if ( jsonObject.getString("nome").equals(login) )
            {
                if ( jsonObject.getString("senha").equals(senha) )
                    return "sucesso";
                else
                    return "fail";
            }
            else
                return "fail";*/

        }


        public boolean isSucesso() {
            return sucesso;
        }

        public void setSucesso(boolean sucesso) {
            this.sucesso = sucesso;
        }
    }


}
