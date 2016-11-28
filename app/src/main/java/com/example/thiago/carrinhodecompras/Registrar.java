package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        /*SharedPreferences.Editor ed = userPrefs.edit();
        ed.putString("name",nome);
        ed.putString("email",email);
        ed.putString("password",senha);
        ed.commit();*/
        /*
         *   ADICIONA AQUI
         */

        String urlCadastrar = "http://10.0.2.2:9000/cadastrar/" + nome + "/" + senha;
        URL url = null;
        try
        {
            url = new URL(urlCadastrar);
        }
        catch ( Exception e )
        {
            System.err.println("Erro ao converter URL");
        }
        WebServiceAdicionaLogin webServiceAdicionaLogin = new WebServiceAdicionaLogin( nome, senha );
        webServiceAdicionaLogin.execute( url );

       /* if ( webServiceAdicionaLogin.isSucesso() )
            Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso", Toast.LENGTH_LONG ).show();
        else
            Toast.makeText(getApplicationContext(), "Usúario já existe", Toast.LENGTH_LONG ).show();*/




        startActivity(intent);
    }





    private class WebServiceAdicionaLogin extends AsyncTask< URL, Void, JSONObject >
    {
        private String loginUsuario;
        private String senhaUsuario;
        private boolean sucesso;

        public WebServiceAdicionaLogin( String login, String senha )
        {
            this.loginUsuario = login;
            this.senhaUsuario = senha;
            this.setSucesso(false);
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected JSONObject doInBackground(URL... params)
        {
            HttpURLConnection connection = null;

            try
            {
                connection = (HttpURLConnection) params[ 0 ].openConnection();
                //connection.
                int resposta = connection.getResponseCode();

                if ( resposta == HttpURLConnection.HTTP_OK )
                {
                    StringBuilder builder = new StringBuilder();

                    try ( BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
                    {
                        String linha;

                        while ( (linha = reader.readLine()) != null )
                            builder.append(linha);
                    }
                    catch ( IOException e )
                    {
                        System.out.println("Erro de leitura no web service!");
                        e.printStackTrace();
                    }
                    JSONObject jsonObject = new JSONObject( builder.toString() );
                    //jsonObject.

                    return new JSONObject(builder.toString());
                }
                else
                {
                    System.err.println("Bad Connection");
                }
            }
            catch ( Exception e )
            {
                System.err.println("Erro ao conectar com o WebService");
                e.printStackTrace();
            }
            finally {
                connection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute( JSONObject usuario )
        {
            if ( usuario != null )
            {
                try
                {
                    Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso", Toast.LENGTH_LONG ).show();
                    this.setSucesso(usuario.getString("nome").equals("sucesso"));
                    return;
                }
                catch ( JSONException e )
                {
                    System.err.println("Erro ao processar JSONObject");
                    e.printStackTrace();
                }

            }
            else
                System.err.println("JSON OBject é nulo");
            Toast.makeText(getApplicationContext(), "Erro ao registrar usuario", Toast.LENGTH_LONG ).show();

        }


        public boolean isSucesso() {
            return sucesso;
        }

        public void setSucesso(boolean sucesso) {
            this.sucesso = sucesso;
        }
    }
}
