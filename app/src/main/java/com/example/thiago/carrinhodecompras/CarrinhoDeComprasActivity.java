package com.example.thiago.carrinhodecompras;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.HashSet;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;

public class CarrinhoDeComprasActivity extends Lifecycle
{
    private static final Locale ptBr = new Locale("pt", "BR");
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(ptBr);

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

        adapter = new ProdutoCarrinhoAdapter(produtosASeremExbidos,userPrefs);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new ItemDivider(this));

    }

    //Pedro fiz essa aqui
    protected void onResume(){
        super.onResume();
        adapter.setUserPrefs(userPrefs);
        //Toast.makeText(getApplicationContext(),"oi", Toast.LENGTH_LONG).show();
        if (produtosASeremExbidos != null){
            String user = userPrefs.getString("name","");
            int size = userPrefs.getInt(user + "array_size",0);

            for(int i = 0;i < size; i++){
                float tp = userPrefs.getFloat(user + "p_" + i,0);
                String tn = userPrefs.getString(user + "n_" + i,"");
                int tq = userPrefs.getInt(user + "q_" + i,0);
                if(tn.equals("")) {
                size = size + 1;
                }
                else {
                    Product newp = new Product(tn, tp);
                    newp.setToBuy(tq);
                    produtosASeremExbidos.add(newp);
                }
            }

            float total = 0;
            for(Product p : produtosASeremExbidos) total += p.getPrice() * p.getToBuy();



           /*Set<String> nomes = new HashSet<String>();
            Set<String> precos = new HashSet<String>();
            Set<String> quantidades = new HashSet<String>();
            nomes = userPrefs.getStringSet("nomes",null);
            precos = userPrefs.getStringSet("precos",null);
            quantidades = userPrefs.getStringSet("quantidades",null);
            float total = userPrefs.getFloat("total",0);

            if(nomes != null && precos != null) {
                Iterator<String> iter = nomes.iterator();
                Iterator<String> iter2 = precos.iterator();

                while (iter.hasNext() && iter2.hasNext()) {
                    Product newp = new Product(iter.next(), Double.parseDouble(iter2.next()));

                    produtosASeremExbidos.add(newp);
                }
            }*/

                totalAPagarTextView.setText(currencyFormat.format(total));
        }
    }

    // Função responsável por adicionar produtos
    private void processaEntrada()
    {
        float total = 0;
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
            produtosASeremExbidos.add(novoProduto);
        }

        //Mexi aqui Pedro
        SharedPreferences.Editor ed = userPrefs.edit();

        int size = produtosASeremExbidos.size();
        String user = userPrefs.getString("name","");
        ed.remove(user + "array_size");
        ed.putInt(user + "array_size",size);
        for(int i = 0;i < size; i++){
            ed.remove(user + "p_" + i);
            ed.remove(user + "n_" + i);
            ed.remove(user + "q_" + i);
            ed.putString(user + "n_" + i,produtosASeremExbidos.get(i).getName());
            ed.putInt(user + "q_" + i,produtosASeremExbidos.get(i).getToBuy());
            ed.putFloat(user + "p_" + i,(float)produtosASeremExbidos.get(i).getPrice());
        }
        ed.commit();

        for(Product p : produtosASeremExbidos) total += p.getPrice() * p.getToBuy();

        /*ed.putFloat("total",total);

        Set<String> nomes = new HashSet<String>();
        Set<String> precos = new HashSet<String>();

        Iterator<Product> iter = produtosASeremExbidos.iterator();

        while (iter.hasNext()) {
            Product temp = iter.next();
            nomes.add(temp.getName());
            precos.add(String.valueOf(temp.getPrice()));
        }
        ed.putString("produtos_carrinho",ObjectSerializer.serialize(produtosASeremExbidos));
        ed.putStringSet("nomes",nomes);
        ed.putStringSet("precos",precos);
        ed.commit();*/
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
