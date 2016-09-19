package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TelaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
    }

    //Vai para a tela de registro
    public void irCarrinho(View view){
        Intent intent = new Intent(this,CarrinhoDeComprasActivity.class);
        startActivity(intent);
    }

    //Vai para a tela de registro
    public void irEstoque(View view){
        Intent intent = new Intent(this,AdicionarEstoqueActivity.class);
        startActivity(intent);
    }

    //Vai para a tela de registro
    public void adicionarProduto(View view){
        Intent intent = new Intent(this,AdicionarBanco.class);
        startActivity(intent);
    }
}
