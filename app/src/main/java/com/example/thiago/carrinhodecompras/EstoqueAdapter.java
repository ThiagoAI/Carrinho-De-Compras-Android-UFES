package com.example.thiago.carrinhodecompras;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by administrador on 23/10/16.
 */

public class EstoqueAdapter extends RecyclerView.Adapter<EstoqueAdapter.ViewHolder>
{

    private List<Product> produtosEmEstoque;


    public EstoqueAdapter(List<Product> pd )
    {
        this.produtosEmEstoque = pd;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView textView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);


        }
    }


    @Override
    public EstoqueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
        //return null;
    }

    @Override
    public void onBindViewHolder(EstoqueAdapter.ViewHolder holder, int position)
    {
        holder.textView.setText(produtosEmEstoque.get(position).getName());

    }

    @Override
    public int getItemCount()
    {
        return produtosEmEstoque.size();
    }
}
