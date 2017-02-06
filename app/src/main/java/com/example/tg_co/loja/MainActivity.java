package com.example.tg_co.loja;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tg_co.loja.dao.BDHelper;
import com.example.tg_co.loja.model.Endereco;
import com.example.tg_co.loja.model.Pessoa;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Dao<Pessoa, Long> pessoaDAO;

    public EditText nome;
    public EditText email;
    public EditText logradouro;
    public EditText numero;
    public EditText cidade;
    public EditText estado;
    public Long pessoaID;
    public String flagEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome = (EditText) findViewById(R.id.editTextNome);
        email = (EditText) findViewById(R.id.editTextEmail);
        logradouro = (EditText) findViewById(R.id.editTextLogradouro);
        numero = (EditText) findViewById(R.id.editTextNumero);
        cidade = (EditText) findViewById(R.id.editTextCidade);
        estado = (EditText) findViewById(R.id.editTextUf);

        //--------------------------
        try{
            pessoaDAO = BDHelper.getInstance(this).getPessoaDAO();
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage(), e);
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
            pessoaID = id;
            flagEditar = (String) b.get("flag");
        }
        if (flagEditar.equals("Editar")){
            preencherValores(id);
        }
    }

    private void preencherValores(Long id){
        Pessoa p = new Pessoa();
        try{
            p = pessoaDAO.queryForId(id);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        Log.d("DEBUG", p.getNome());

        Toast.makeText(getApplicationContext(), p.getNome().toString(), Toast.LENGTH_SHORT).show();
        nome.setText(p.getNome().toString());
        email.setText(p.getEmail().toString());
    }

    public void onClickSalvar(View v){
        if (validaForm()){
            Pessoa pessoa = new Pessoa();
            Endereco endereco = new Endereco();
            pessoa.setNome(nome.getText().toString());
            pessoa.setEmail(email.getText().toString());
            Toast.makeText(getApplicationContext(), pessoa.getNome(),Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), pessoa.getEmail(),Toast.LENGTH_SHORT).show();

            endereco.setLogradouro(logradouro.getText().toString());
            endereco.setNumero(numero.getText().toString());
            endereco.setCidade(cidade.getText().toString());
            endereco.setEstado(estado.getText().toString());
            pessoa.setEndereco(endereco);
            try{
                pessoaDAO.create(pessoa);
            }
            catch (SQLException e){
                e.printStackTrace();
            }

            List<Pessoa> pessoas = new ArrayList<Pessoa>();
            try{
                pessoas = pessoaDAO.queryForAll();
            }catch (SQLException e){
                e.printStackTrace();
            }
            for (Pessoa pes : pessoas){
                Toast.makeText(getApplicationContext(), pes.getNome().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), pes.getEmail().toString(), Toast.LENGTH_SHORT).show();
                Endereco endereco1 = new Endereco();
                endereco1 = pes.getEndereco();
                Toast.makeText(getApplicationContext(), endereco1.getLogradouro().toString(), Toast.LENGTH_SHORT).show();
            }

            // Intent intent = new Intent(MainActivity.this, ListarPessoasActivity.class);
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }

    public void onClickExcluir(View v){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
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
                        Toast.makeText(getApplicationContext(),
                                "Entrou no excluir", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Não",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // Se clicar no Nâo não vai realizar ação nenhuma
                        Toast.makeText(getApplicationContext(),
                                "Entrou no Não", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });
        // criando o Alerta
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public Boolean validaForm(){
        nome.setError(null);
        View focusView = null;
        if (TextUtils.isEmpty(nome.getText().toString())){
            focusView = nome;
            focusView.requestFocus();
            Toast.makeText(getApplicationContext(), "cê num tem nome, naum?!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(email.getText().toString())){
            focusView = email;
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
