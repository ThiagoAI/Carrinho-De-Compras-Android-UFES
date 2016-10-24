package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TelaPrincipal extends Lifecycle {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
    }

    protected void onResume(){
        super.onResume();
        TextView tv = (TextView) findViewById(R.id.textNome);
        tv.setText(userPrefs.getString("name","")+".");
    }

    //Vai para a tela de registro
    public void irCarrinho(View view){
        Intent intent = new Intent(this,CarrinhoDeComprasActivity.class);
        startActivity(intent);
    }

    //Vai para a tela de registro
    public void irEstoque(View view){
        Intent intent = new Intent(this,EstoqueCasaActivity.class);
        startActivity(intent);
    }

    //Vai para a tela de registro
    public void adicionarProduto(View view){
        Intent intent = new Intent(this,AdicionarBanco.class);
        startActivity(intent);
    }
}
