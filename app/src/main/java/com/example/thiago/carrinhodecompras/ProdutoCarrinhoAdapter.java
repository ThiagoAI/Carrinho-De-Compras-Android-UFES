package com.example.thiago.carrinhodecompras;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by administrador on 22/10/16.
 */

public class ProdutoCarrinhoAdapter extends RecyclerView.Adapter<ProdutoCarrinhoAdapter.ViewHolder>
{
    private final View.OnLongClickListener longClickListener;
    private final List<Product> produtosASeremExibidos;
    private static final Locale ptBr = new Locale("pt", "BR");
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(ptBr);

    public ProdutoCarrinhoAdapter( List<Product> produtos, View.OnLongClickListener longClickListener )
    {
        this.produtosASeremExibidos = produtos;
        this.longClickListener = longClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        //public final TextView textView;
        public final TextView nomeTextView;
        public final TextView precoTextView;
        public final TextView quantidadeTextView;


        public ViewHolder(View itemView, View.OnLongClickListener longClickListener)
        {
            super(itemView);
            //textView = (TextView)itemView.findViewById(R.id.textView);
            nomeTextView = (TextView) itemView.findViewById(R.id.nomeProdutoTextView);
            precoTextView = (TextView) itemView.findViewById(R.id.precoProdutoCarrinhoTextView);
            quantidadeTextView = (TextView) itemView.findViewById(R.id.quantidadeProdutoTextView);

            itemView.setOnLongClickListener(longClickListener);
        }
    }


    @Override
    public ProdutoCarrinhoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false );
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_grid_layout, parent, false );


        return ( new ViewHolder(view, longClickListener) );
    }

    @Override
    public void onBindViewHolder(ProdutoCarrinhoAdapter.ViewHolder holder, int position)
    {
        holder.nomeTextView.setText(produtosASeremExibidos.get(position).getName());
        holder.precoTextView.setText(currencyFormat.format(produtosASeremExibidos.get(position).getPrice()));
        holder.quantidadeTextView.setText(String.valueOf(produtosASeremExibidos.get(position).getToBuy()));
        //holder.textView.setText(produtosASeremExibidos.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return produtosASeremExibidos.size();
    }

    //private final


}
