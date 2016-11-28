package com.example.thiago.carrinhodecompras;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TelaPrincipal extends Lifecycle {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
    }

    protected void onResume(){
        super.onResume();
    }

    public void limparCarrinho(View view){
        new AlertDialog.Builder(this)
                .setTitle("LIMPAR CARRINHO")
                .setMessage("Você tem certeza que quer limpar seu carrinho?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor ed = userPrefs.edit();
                        String user = userPrefs.getString("name","");
                        int size = userPrefs.getInt(user + "array_size",0);
                        ed.remove(user + "array_size");
                        for(int i = 0;i < size; i++){
                            ed.remove(user + "p_" + i);
                            ed.remove(user + "n_" + i);
                            ed.remove(user + "q_" + i);
                        }
                        ed.commit();
                        Toast.makeText(getApplicationContext(),"Carrinho limpo.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

    //Vai para a tela de registro
    public void irCarrinho(View view){
        Intent intent = new Intent(this,CarrinhoDeComprasActivity.class);
        startActivity(intent);
    }

    //Vai para a tela de registro
    public void irEstoque(View view){
        Intent intent = new Intent(this,EstoqueCasaActivity.class);
        startActivity(intent);
    }

    //Vai para a tela de registro
    public void adicionarProduto(View view){
        Intent intent = new Intent(this,AdicionarBanco.class);
        startActivity(intent);
    }
}
