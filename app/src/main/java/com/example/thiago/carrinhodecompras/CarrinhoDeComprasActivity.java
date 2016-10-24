package com.example.thiago.carrinhodecompras;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class CarrinhoDeComprasActivity extends Lifecycle
{
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    private EditText nomeProdutoCarrinhoEditText;
    private EditText precoProdutoCarrinhoEditText;
    private EditText quantidadeProdutoEditText;
    private TextView totalAPagarTextView;
    private List<Product> produtosASeremExbidos;
    private ProdutoCarrinhoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_de_compras);

        nomeProdutoCarrinhoEditText = ((TextInputLayout)findViewById(R.id.nomeProdutoCarrinhoTextInputLayout)).getEditText();
        precoProdutoCarrinhoEditText = ((TextInputLayout)findViewById(R.id.precoProdutoCarrinhoTextInputLayout)).getEditText();
        quantidadeProdutoEditText = ((TextInputLayout)findViewById(R.id.quantidadeProdutoCarrinhoTextInputLayout)).getEditText();
        totalAPagarTextView = (TextView) findViewById(R.id.totalAPagarTextView);
        totalAPagarTextView.setText(currencyFormat.format(0));


        quantidadeProdutoEditText.setOnEditorActionListener(onEditorActionListener);


        produtosASeremExbidos = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.carrinhoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProdutoCarrinhoAdapter(produtosASeremExbidos, itemLongClickListener);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new ItemDivider(this));

    }

    //Pedro fiz essa aqui
    protected void onResume(){
        super.onResume();
        if (produtosASeremExbidos != null){
           Set<String> nomes = new HashSet<String>();
            Set<String> precos = new HashSet<String>();
            nomes = userPrefs.getStringSet("nomes",null);
            precos = userPrefs.getStringSet("precos",null);
            float total = userPrefs.getFloat("total",0);

            if(nomes != null && precos != null) {
                Iterator<String> iter = nomes.iterator();
                Iterator<String> iter2 = precos.iterator();

                while (iter.hasNext() && iter2.hasNext()) {
                    Product newp = new Product(iter.next(), Double.parseDouble(iter2.next()));
                    produtosASeremExbidos.add(newp);
                }
            }

                totalAPagarTextView.setText(currencyFormat.format(total));
        }
    }

    // Função responsável por adicionar produtos
    private void processaEntrada()
    {
        float total = userPrefs.getFloat("total",0);
        boolean primeraVez = true;
        //((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(onCreateView().getWindowToken());
        String nomeProduto = nomeProdutoCarrinhoEditText.getText().toString();
        double preco = Double.parseDouble(precoProdutoCarrinhoEditText.getText().toString());
        int quantidade = Integer.parseInt(quantidadeProdutoEditText.getText().toString());

        // Verifica se o produto já foi adicionado
        for ( Product p: produtosASeremExbidos )
        {
            if ( p.getName().equals(nomeProduto) )
            {
                p.setToBuy(p.getToBuy() + quantidade);
                p.setPrice(preco);
                primeraVez = false;
            }
        }

        if ( primeraVez )
        {
            Product novoProduto = new Product(nomeProduto, preco);
            novoProduto.setToBuy(quantidade);
            total += novoProduto.getPrice() * novoProduto.getToBuy();
            produtosASeremExbidos.add(novoProduto);
        }

        //Mexi aqui Pedro
        SharedPreferences.Editor ed = userPrefs.edit();

        ed.putFloat("total",total);

        Set<String> nomes = new HashSet<String>();
        Set<String> precos = new HashSet<String>();

        Iterator<Product> iter = produtosASeremExbidos.iterator();

        while (iter.hasNext()) {
            Product temp = iter.next();
            nomes.add(temp.getName());
            precos.add(String.valueOf(temp.getPrice()));
        }
        ed.putStringSet("nomes",nomes);
        ed.putStringSet("precos",precos);
        ed.commit();
        //Fim do que eu mexi

        totalAPagarTextView.setText(currencyFormat.format(total)); /*String.valueOf(total)*/

        adapter.notifyDataSetChanged();
        
        nomeProdutoCarrinhoEditText.setText("");
        precoProdutoCarrinhoEditText.setText("");
        quantidadeProdutoEditText.setText("");
        nomeProdutoCarrinhoEditText.requestFocus();

    }

    private void avaliaTextosInseridos()
    {
        if ( !nomeProdutoCarrinhoEditText.getText().toString().isEmpty() &&
                !precoProdutoCarrinhoEditText.getText().toString().isEmpty() &&
                !quantidadeProdutoEditText.getText().toString().isEmpty() )
            processaEntrada();

    }


    private final TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener()
    {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
        {
            if ( i == EditorInfo.IME_ACTION_DONE )
            {
                avaliaTextosInseridos();

            }
            return false;
        }
    };

    private final View.OnLongClickListener itemLongClickListener = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View view)
        {
            final String nomeProduto = ((TextView) view).getText().toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(CarrinhoDeComprasActivity.this);

            builder.setTitle("O que deseja fazer com o item \"" + nomeProduto + "\"?");

            builder.setItems(R.array.opcoes_list, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch ( i )
                    {
                        case 0: //Editar
                        {
                            for ( Product p: produtosASeremExbidos )
                            {
                                if ( p.getName().equals(nomeProduto) )
                                {
                                    nomeProdutoCarrinhoEditText.setText(nomeProduto);
                                    quantidadeProdutoEditText.setText(p.getToBuy());
                                    precoProdutoCarrinhoEditText.setText(String.valueOf(p.getPrice()));
                                    break;
                                }
                            }
                            break;
                        }
                        case 1: //Excluir
                            excluiProdutoLista(nomeProduto);

                    }
                }
            });

            builder.setNegativeButton(getString(R.string.cancel_carrinho), null);

            builder.create().show();
            return true;
        }
    };

    private void excluiProdutoLista(final String nomeProduto)
    {
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);
        confirmBuilder.setMessage(getString(R.string.confirm_message_carrinho, nomeProduto));

        confirmBuilder.setNegativeButton(getString(R.string.cancel_carrinho), null );

        confirmBuilder.setPositiveButton(getString(R.string.exclui_produto_carrinho), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for ( Product p: produtosASeremExbidos )
                {
                    if ( p.getName().equals(nomeProduto) )
                    {
                        produtosASeremExbidos.remove(p);
                        break;
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        confirmBuilder.create().show();

    }


}
