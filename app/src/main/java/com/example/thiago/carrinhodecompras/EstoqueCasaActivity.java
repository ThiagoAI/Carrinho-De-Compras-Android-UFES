package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EstoqueCasaActivity extends Lifecycle
{


    private FloatingActionButton botaoAdicionar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque_casa);

        botaoAdicionar = (FloatingActionButton) findViewById(R.id.botaoAdicionar);
        botaoAdicionar.setOnClickListener(clickListenerBotaoAdicionar);
    }

    private final View.OnClickListener clickListenerBotaoAdicionar = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            /*Intent intent = new Intent(this,AdicionarEstoqueActivity.class);
            startActivity(intent);*/
        }
    };
}
