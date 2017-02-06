package com.example.tg_co.loja.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tg_co.loja.MainActivity;
import com.example.tg_co.loja.R;
import com.example.tg_co.loja.adapters.PessoasAdapter;
import com.example.tg_co.loja.dao.BDHelper;
import com.example.tg_co.loja.model.Pessoa;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarPessoasFragment extends Fragment {

    private Dao<Pessoa, Long> pessoaDAO;

    private ListView lstvPessoas;

    private View rootView;

    public ListarPessoasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_listar_pessoas, container, false);

        try{
            pessoaDAO = BDHelper.getInstance(rootView.getContext()).getPessoaDAO();
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage(), e);
        }

        // Atribuindo a variavel criadas anteriormente os ids do xml correspondente na interface
        lstvPessoas = (ListView) rootView.findViewById(R.id.lstvCliente);
        // Instanciando o objeto adapter passando as informações do usuário
        PessoasAdapter adapter = new PessoasAdapter(rootView.getContext(), getPessoas());
        // Atribui a lista o objeto adapter

        lstvPessoas.setAdapter(adapter);
        // Atribuindo a varial criadas anteriormente os ids do xml correspondente na interface

        // Função que ira criar o evento quando clicar na lista
        lstvPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // TODO Auto-generated method stub
                lstvlstvPessoasOnItemSelect(arg2);
            }
        });
        return rootView;
    }

    private void lstvlstvPessoasOnItemSelect(int posicao){
        // Pega a informação do adapter e coloca em outro objeto do mesmo tipo
        PessoasAdapter adapter = (PessoasAdapter)lstvPessoas.getAdapter();
        // Cria um objeto do tipo usuário que recebe as informações do usuario no adapter conforme sua posição
        Pessoa c = (Pessoa) adapter.getItem(posicao);
        // Cria um toast para mostrar o nome do usuário selecionado
        Toast.makeText(rootView.getContext(), "Usuário selecionado no ListView: "+c.getNome(), Toast.LENGTH_SHORT).show();
        // Instancia um objeto do tipo intent
        Intent in = new Intent(rootView.getContext(), MainActivity.class);
        // Atribui mais uma variável a esta intent onde é passado a posição do id para outra Activity
        in.putExtra("id", c.getPessoaId());
        in.putExtra("flag", "Editar");
        // Inicia a Activity
        startActivityForResult(in,100);
    }

    private List<Pessoa> getPessoas(){
        // Atribui a uma lista de objetos usuários as informações de todos os usuários
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        try{
            pessoas = pessoaDAO.queryForAll();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        // Retorna o objeto usuário
        return pessoas;
    }

}
