package com.example.tg_co.loja.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tg_co.loja.ProdutoActivity;
import com.example.tg_co.loja.R;
import com.example.tg_co.loja.adapters.ProdutosAdapter;
import com.example.tg_co.loja.dao.BDHelper;
import com.example.tg_co.loja.model.Produto;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarProdutosFragment extends Fragment {

    private Dao<Produto, Long> produtoDAO;

    private ListView lstvProdutos;

    private View rootView;

    public ListarProdutosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_listar_produtos, container, false);


        try {
            produtoDAO = BDHelper.getInstance(rootView.getContext()).getProdutoDAO();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }


        // Atribuindo a variavel criadas anteriormente os ids do xml correspondente na interface
        lstvProdutos = (ListView) rootView.findViewById(R.id.lstvCliente);
        // Instanciando o objeto adapter passando as informações do usuário
        ProdutosAdapter adapter = new ProdutosAdapter(rootView.getContext(), getProdutos());
        // Atribui a lista o objeto adapter

        lstvProdutos.setAdapter(adapter);
        // Atribuindo a varial criadas anteriormente os ids do xml correspondente na interface

        // Função que ira criar o evento quando clicar na lista
        lstvProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){
                // TODO Auto-generated method stub
                lstvlstvProdutosOnItemSelect(arg2);
            }
        });

        lstvProdutos.setLongClickable(true);

        lstvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                Log.v("long clicked","pos: " + pos);

                lstvProdutoDelete(pos);
                return true;
            }
        });

        return rootView;

    }

    private void lstvProdutoDelete(int posicao){
        // Pega a informação do adapter e coloca em outro objeto do mesmo tipo
        ProdutosAdapter adapter = (ProdutosAdapter)lstvProdutos.getAdapter();
        // Cria um objeto do tipo usuário que recebe as informações do usuario no adapter conforme sua posição
        final Produto c = (Produto) adapter.getItem(posicao);
        // Cria um toast para mostrar o nome do usuário selecionado
        Toast.makeText(rootView.getContext(), "Produto que vai ser deletado "+c.getNome(), Toast.LENGTH_SHORT).show();
        // Instancia um objeto do tipo intent


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                rootView.getContext());
        // incluindo o título da mensagem
        alertDialogBuilder.setTitle("Você deseja realmente excluir");
        // incluindo as opções do diálogo
        alertDialogBuilder
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Sim",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Se clicar no sim vai matar a Activity
                        //MainActivity.this.finish();
                        try {
                            produtoDAO.delete(c);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Não",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Se clicar no Nâo não vai realizar ação nenhuma
                        Toast.makeText(rootView.getContext(),
                                "OK Juvenal", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
        // criando o Alerta
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();




    }

    private void lstvlstvProdutosOnItemSelect(int posicao){
        // Pega a informação do adapter e coloca em outro objeto do mesmo tipo
        ProdutosAdapter adapter = (ProdutosAdapter)lstvProdutos.getAdapter();
        // Cria um objeto do tipo usuário que recebe as informações do usuario no adapter conforme sua posição
        Produto c = (Produto) adapter.getItem(posicao);
        // Cria um toast para mostrar o nome do usuário selecionado
        Toast.makeText(rootView.getContext(), "Produto selecionado no ListView: "+c.getNome(), Toast.LENGTH_SHORT).show();
        // Instancia um objeto do tipo intent
        Intent in = new Intent(rootView.getContext(), ProdutoActivity.class);
        // Atribui mais uma variável a esta intent onde é passado a posição do id para outra Activity
        in.putExtra("id", c.getProdutoId());
        in.putExtra("flag", "Editar");
        // Inicia a Activity
        startActivityForResult(in,100);
    }

    private List<Produto> getProdutos(){
        // Atribui a uma lista de objetos usuários as informações de todos os usuários
        List<Produto> produtos = new ArrayList<Produto>();

        try {
            produtos = produtoDAO.queryForAll();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        // Retorna o objeto usuário
        return produtos;
    }


}
