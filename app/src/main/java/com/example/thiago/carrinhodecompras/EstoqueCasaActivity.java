package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EstoqueCasaActivity extends Lifecycle
{


    private FloatingActionButton botaoAdicionar;
    private List<Product> produtosEmEstoque;
    private EstoqueAdapter adapter;
    private ProductDAO acessorBanco;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque_casa);
        acessorBanco = new ProductDAO(getApplicationContext());

        Cursor c = acessorBanco.getProducts();

        produtosEmEstoque = new ArrayList<>();
        for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
        {
            Product novoProduto = new Product(c.getString(c.getColumnIndex("name")), c.getDouble(c.getColumnIndex("price")));
            produtosEmEstoque.add(novoProduto);
        }

        //Collections.sort();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.estoqueRecyclerView);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new GridLayoutManager(this, 0 ) );
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
