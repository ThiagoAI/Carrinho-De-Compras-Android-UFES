package com.example.thiago.carrinhodecompras;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by administrador on 22/10/16.
 */

public class ProdutoCarrinhoAdapter extends RecyclerView.Adapter<ProdutoCarrinhoAdapter.ViewHolder>
{
    private final View.OnLongClickListener longClickListener;
    private final List<Product> produtosASeremExibidos;

    public ProdutoCarrinhoAdapter( List<Product> produtos, View.OnLongClickListener longClickListener )
    {
        this.produtosASeremExibidos = produtos;
        this.longClickListener = longClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView textView;


        public ViewHolder(View itemView, View.OnLongClickListener longClickListener)
        {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.textView);

            itemView.setOnLongClickListener(longClickListener);
        }
    }


    @Override
    public ProdutoCarrinhoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false );


        return ( new ViewHolder(view, longClickListener) );
    }

    @Override
    public void onBindViewHolder(ProdutoCarrinhoAdapter.ViewHolder holder, int position)
    {
        holder.textView.setText(produtosASeremExibidos.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return produtosASeremExibidos.size();
    }

    //private final


}
