package com.example.thiago.carrinhodecompras;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class AdicionarEstoqueActivity extends Lifecycle
{
    private EditText nomeProdutoEstoqueEditText;
    private EditText quantidadeProdutoEstoqueEditText;
    private Switch emFaltaSwitch;
    private FloatingActionButton salvarBotao;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_estoque);

        nomeProdutoEstoqueEditText = ((TextInputLayout)findViewById(R.id.nomeProdutoEstoqueTextInputLayout)).getEditText();
        quantidadeProdutoEstoqueEditText = ((TextInputLayout)findViewById(R.id.quantidadeProdutoEstoqueTextInputLayout)).getEditText();
        emFaltaSwitch = (Switch) findViewById(R.id.emFaltaEstoqueBotao);
        salvarBotao = (FloatingActionButton) findViewById(R.id.salvarFloatingActionButton);

        nomeProdutoEstoqueEditText.addTextChangedListener(textWatcher);
        quantidadeProdutoEstoqueEditText.addTextChangedListener(textWatcher2);
        salvarBotao.setOnClickListener(onClickListener);
        verificaBotao();
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ProductDAO acessorBanco = new ProductDAO(getApplicationContext());
            acessorBanco.open("write");
            //Product novoProduto = new Pro
            acessorBanco.putProduct(nomeProdutoEstoqueEditText.getText().toString(), String.valueOf(2) );
            acessorBanco.close();
            Intent intent = new Intent(this, EstoqueCasaActivity.class );
            startActivity(intent);

        }
    };


    private void verificaBotao()
    {
        if ( nomeProdutoEstoqueEditText.getText().toString().isEmpty() || quantidadeProdutoEstoqueEditText.getText().toString().isEmpty() )
            salvarBotao.hide();
        else
            salvarBotao.show();

    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            verificaBotao();

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private final TextWatcher textWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            verificaBotao();

        }
    };
}
