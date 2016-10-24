package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class EstoqueCasaActivity extends Lifecycle
{


    private FloatingActionButton botaoAdicionar;
    private List<Product> produtosEmEstoque;
    private EstoqueAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque_casa);

        produtosEmEstoque = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.estoqueRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 0 ) );
        adapter = new EstoqueAdapter(produtosEmEstoque);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDivider(this));

        botaoAdicionar = (FloatingActionButton) findViewById(R.id.botaoAdicionar);
        botaoAdicionar.setOnClickListener(clickListenerBotaoAdicionar);
    }

    private final View.OnClickListener clickListenerBotaoAdicionar = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            //Intent intent = new Intent(this, AdicionarEstoqueActivity.class ); //Intent(this,AdicionarEstoqueActivity.class);
            //startActivity(intent);
        }
    };
}
