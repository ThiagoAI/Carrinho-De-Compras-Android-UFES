package com.example.thiago.carrinhodecompras;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by administrador on 22/10/16.
 */

public class ProdutoCarrinhoAdapter extends RecyclerView.Adapter<ProdutoCarrinhoAdapter.ViewHolder>
{

    private final View.OnClickListener clickListener;
    private final View.OnLongClickListener longClickListener;


    private final List<String> produtosASeremExibidos;

    public ProdutoCarrinhoAdapter(List<String> produtos, View.OnClickListener clickListener, View.OnLongClickListener longClickListener )
    {
        this.produtosASeremExibidos = produtos;
        this.clickListener = clickListener;
        this.longClickListener  = longClickListener;
    }






    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView textView;

        public ViewHolder(View itemView, View.OnClickListener clickListener, View.OnLongClickListener longClickListener)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(clickListener);
            itemView.setOnLongClickListener(longClickListener);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount()
    {
        return produtosASeremExibidos.size();
    }
}
