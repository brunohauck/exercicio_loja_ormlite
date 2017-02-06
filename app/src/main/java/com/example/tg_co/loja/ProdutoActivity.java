package com.example.tg_co.loja;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tg_co.loja.dao.BDHelper;
import com.example.tg_co.loja.model.Produto;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {
    private Dao<Produto, Long> produtoDAO;

    public EditText nome;
    public EditText descricao;
    public EditText preco;
    public Long produtoID;
    public String flagEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        nome = (EditText) findViewById(R.id.editTextNome);
        descricao = (EditText) findViewById(R.id.editTextDescricao);
        preco = (EditText) findViewById(R.id.editTextPreco);

        //--------------------------
        try {
            produtoDAO = BDHelper.getInstance(this).getProdutoDAO();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        //--------------------------

        Intent i = getIntent();
        // Instancia um objeto do tipo Bundle
        Bundle b = i.getExtras();
        //Log.d("DEBUG", "Entrou 04");
        long id = 0;
        flagEditar = "Cadastro";
        // Se for diferente de null
        if (b!=null){
            // Atribui a variável o id do usuário recuperado
            id = (Long) b.get("id");
            produtoID = id;
            flagEditar = (String) b.get("flag");
        }
        if (flagEditar.equals("Editar")){
            preencherValores(id);
        }
    }

    private void preencherValores(Long id){
        Produto p = new Produto();
        try{
            try {
                p = produtoDAO.queryForId(id);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        Log.d("DEBUG", p.getNome());

        Toast.makeText(getApplicationContext(), p.getNome().toString(), Toast.LENGTH_SHORT).show();
        nome.setText(p.getNome().toString());
        descricao.setText(p.getDescricao().toString());
        preco.setText(p.getPreco().toString());
    }

    public void onClickSalvar(View v){
        if (validaForm()){
            Produto produto = new Produto();
            if(flagEditar.equals("Editar")){
                produto.setProdutoId(produtoID);
            }
            produto.setNome(nome.getText().toString());
            produto.setDescricao(descricao.getText().toString());
            produto.setPreco(Double.valueOf(preco.getText().toString()));
            Toast.makeText(getApplicationContext(), produto.getNome(),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), produto.getPreco().toString(),Toast.LENGTH_SHORT).show();


            try {
                //produtoDAO.create(produto);
                produtoDAO.createOrUpdate(produto);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }


            List<Produto> produtos = new ArrayList<Produto>();

            try {
                produtos = produtoDAO.queryForAll();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }

            for (Produto pes : produtos){
                Toast.makeText(getApplicationContext(), pes.getNome().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), pes.getPreco().toString(), Toast.LENGTH_SHORT).show();
            }

            // Intent intent = new Intent(MainActivity.this, ListarProdutosActivity.class);
            Intent intent = new Intent(ProdutoActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }

    public Boolean validaForm(){
        nome.setError(null);
        View focusView = null;
        if (TextUtils.isEmpty(nome.getText().toString())){
            focusView = nome;
            focusView.requestFocus();
            Toast.makeText(getApplicationContext(), "Produto precisa ter nome! ;-)",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(preco.getText().toString())){
            focusView = preco;
            focusView.requestFocus();
            return false;
        }
        return true;
    }

    public void onClickMenu(View v){
        Intent i = new Intent(this, MenuActivity.class);
        startActivity(i);
        //finish();
    }

}
