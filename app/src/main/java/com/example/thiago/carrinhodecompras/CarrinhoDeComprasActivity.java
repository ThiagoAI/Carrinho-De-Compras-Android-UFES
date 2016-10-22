package com.example.thiago.carrinhodecompras;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoDeComprasActivity extends AppCompatActivity
{
    private static final String SEARCHES = "searches";

    private EditText nomeProdutoCarrinhoEditText;
    private EditText precoProdutoCarrinhoEditText;
    private EditText quantidadeProdutoCarrinhoEditText;
    private SharedPreferences savedSearches;
    private List<String> produtosASeremExbidos; // Usado para preencher o quadro do carrinho

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_de_compras);

        nomeProdutoCarrinhoEditText = ((TextInputLayout) findViewById(R.id.nomeProdutoCarrinhoTextInputLayout)).getEditText();
        precoProdutoCarrinhoEditText = ((TextInputLayout)findViewById(R.id.precoProdutoCarrinhoTextInputLayout)).getEditText();
        quantidadeProdutoCarrinhoEditText = ((TextInputLayout)findViewById(R.id.quantidadeProdutoCarrinhoTextInputLayout)).getEditText();


        savedSearches = getSharedPreferences(SEARCHES, MODE_PRIVATE);

        produtosASeremExbidos =  new ArrayList<>(savedSearches.getAll().keySet());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.carrinhoRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private final TextWatcher textWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            analisaConfirmaBotao();

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    // A ATUALIZAR
    private void analisaConfirmaBotao()
    {
        if ( nomeProdutoCarrinhoEditText.getText().toString().isEmpty() ||
                precoProdutoCarrinhoEditText.getText().toString().isEmpty() ||
                quantidadeProdutoCarrinhoEditText.getText().toString().isEmpty() )
            ;
    }

    private void excluiProdutoLista( final String nomeProduto )
    {
        AlertDialog.Builder confirmButton = new AlertDialog.Builder(this);
        confirmButton.setMessage(getString(R.string.confirm_message_carrinho, nomeProduto) );

        confirmButton.setNegativeButton(getString(R.string.cancel_carrinho), null);

        confirmButton.setPositiveButton(getString(R.string.exclui_produto_carrinho), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                produtosASeremExbidos.remove(nomeProduto);

            }
        });


        confirmButton.create().show();
    }




}
