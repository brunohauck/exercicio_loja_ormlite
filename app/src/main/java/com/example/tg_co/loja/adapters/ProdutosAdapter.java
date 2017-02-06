package com.example.tg_co.loja.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tg_co.loja.R;
import com.example.tg_co.loja.model.Produto;

import java.util.List;

/**
 * Created by tg_co on 21/01/2017.
 */

public class ProdutosAdapter extends BaseAdapter{
    /*
    * Esta classe ira auxiliar na criação da lista com mais de um valor
    * */
    private Context ctx;
    private List<Produto> produtos;

    public ProdutosAdapter(Context ctx, List<Produto> produtos){
        super();
        this.ctx = ctx;
        this.produtos = produtos;
    }

    @Override
    public int getCount(){ return produtos.size(); }

    @Override
    public Object getItem(int position){
        // TODO Auto-generated method stub
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position){
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // TODO Auto-generated method stub
        // Log.d("DEBUG", "Adapter - Entrou 01");
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.lista_produto, null);
        TextView txtId = (TextView) v.findViewById(R.id.id);
        TextView txtNome = (TextView) v.findViewById(R.id.txtNome);
        TextView txtPreco = (TextView) v.findViewById(R.id.txtPreco);

        // TextView txtStatus = (TextView) v.findViewById(R.id.txtStatus);

        Produto c = produtos.get(position);

        txtId.setText(String.valueOf(c.getProdutoId()));
        txtNome.setText(c.getNome());
        txtPreco.setText(String.valueOf(c.getPreco().toString()));

        // txtStatus.setText(String.valueOf(c.getStatus()));
        // Log.d("DEBUG", "Adapter - Entrou 11");

        return v;
    }
}
