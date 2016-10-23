package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdicionarBanco extends Lifecycle {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_banco);
    }

    public void registrarProduto(View view){
       //Intent intent = new Intent(this,TelaPrincipal.class);
        EditText editNome = (EditText) findViewById(R.id.editNome);
        EditText editPreco = (EditText) findViewById(R.id.editPreco);
        String nome = editNome.getText().toString();
        String preco = editPreco.getText().toString();

        ProductDAO dao = new ProductDAO(getApplicationContext());
        dao.open("write");

        dao.putProduct(nome,preco);

        dao.close();

        finish();
    }
}
