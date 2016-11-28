package com.example.thiago.carrinhodecompras;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by administrador on 22/10/16.
 */

public class ProdutoCarrinhoAdapter extends RecyclerView.Adapter<ProdutoCarrinhoAdapter.ViewHolder>
{
    private SharedPreferences userPrefs;
    private final List<Product> produtosASeremExibidos;
    private static final Locale ptBr = new Locale("pt", "BR");
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(ptBr);
    int selected_position;

    public ProdutoCarrinhoAdapter( List<Product> produtos,SharedPreferences userPrefs)
    {
        this.userPrefs = userPrefs;
        this.produtosASeremExibidos = produtos;
        this.selected_position = 0;
    }

    public void setUserPrefs(SharedPreferences userPrefs){
        this.userPrefs = userPrefs;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //public final TextView textView;
        public final TextView nomeTextView;
        public final TextView precoTextView;
        public final TextView quantidadeTextView;
        public SharedPreferences userPrefs;


        public ViewHolder(View itemView,SharedPreferences userPrefs)
        {
            super(itemView);
            this.userPrefs = userPrefs;
            //textView = (TextView)itemView.findViewById(R.id.textView);
            nomeTextView = (TextView) itemView.findViewById(R.id.nomeProdutoTextView);
            precoTextView = (TextView) itemView.findViewById(R.id.precoProdutoCarrinhoTextView);
            quantidadeTextView = (TextView) itemView.findViewById(R.id.quantidadeProdutoTextView);

        }
    }


    @Override
    public ProdutoCarrinhoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false );
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_grid_layout, parent, false );


        return ( new ViewHolder(view,userPrefs));
    }

    @Override
    public void onBindViewHolder(ProdutoCarrinhoAdapter.ViewHolder holder, final int position) {
        Product item = produtosASeremExibidos.get(position);
        if (selected_position == position) {

        }

        holder.nomeTextView.setText(produtosASeremExibidos.get(position).getName());
        holder.precoTextView.setText(currencyFormat.format(produtosASeremExibidos.get(position).getPrice()));
        holder.quantidadeTextView.setText(String.valueOf(produtosASeremExibidos.get(position).getToBuy()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            public boolean onLongClick(final View v) {
                selected_position = position;
                new android.app.AlertDialog.Builder(v.getContext())
                        .setTitle("REMOVER ITEM")
                        .setMessage("Você tem certeza que quer remover este item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor ed = userPrefs.edit();
                                String user = userPrefs.getString("name","");
                                int size = userPrefs.getInt(user + "array_size",0);
                                ed.remove(user + "array_size");
                                ed.putInt(user + "array_size",size-1);
                                ed.remove(user + "p_" + position);
                                ed.remove(user + "n_" + position);
                                ed.remove(user + "q_" + position);

                                ed.commit();
                                Toast.makeText(v.getContext(),"Item removido", Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

                produtosASeremExibidos.remove(position);

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtosASeremExibidos.size();
    }

    //private final


}
