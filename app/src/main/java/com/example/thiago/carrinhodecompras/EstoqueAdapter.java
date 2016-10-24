package com.example.thiago.carrinhodecompras;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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

        public ViewHolder(View itemView)
        {
            super(itemView);

        }
    }


    @Override
    public EstoqueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(EstoqueAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
