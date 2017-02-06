package com.example.tg_co.loja.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tg_co.loja.R;
import com.example.tg_co.loja.model.Pessoa;

import java.util.List;

/**
 * Created by tg_co on 21/01/2017.
 */

public class PessoasAdapter extends BaseAdapter{
    /*
    * Esta classe ira auxiliar na criação da lista com mais de um valor
    * */
    private Context ctx;
    private List<Pessoa> pessoas;

    public PessoasAdapter(Context ctx, List<Pessoa> pessoas){
        super();
        this.ctx = ctx;
        this.pessoas = pessoas;
    }

    @Override
    public int getCount(){ return pessoas.size(); }

    @Override
    public Object getItem(int position){
        // TODO Auto-generated method stub
        return pessoas.get(position);
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
        View v = inflater.inflate(R.layout.lista, null);
        TextView txtId = (TextView) v.findViewById(R.id.id);
        TextView txtNome = (TextView) v.findViewById(R.id.txtNome);
        TextView txtEmail = (TextView) v.findViewById(R.id.txtEmail);

        // TextView txtStatus = (TextView) v.findViewById(R.id.txtStatus);

        Pessoa c = pessoas.get(position);

        txtId.setText(String.valueOf(c.getPessoaId()));
        txtNome.setText(c.getNome());
        txtEmail.setText(c.getEmail());

        // txtStatus.setText(String.valueOf(c.getStatus()));
        // Log.d("DEBUG", "Adapter - Entrou 11");

        return v;
    }
}
