package com.example.tg_co.loja.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tg_co.loja.model.Endereco;
import com.example.tg_co.loja.model.Pessoa;
import com.example.tg_co.loja.model.Produto;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by tg_co on 21/01/2017.
 */

public class BDHelper extends OrmLiteSqliteOpenHelper {
    private Dao<Pessoa, Long> pessoaDAO = null;
    private Dao<Produto, Long> produtoDAO = null;
    private Dao<Endereco, Long> enderecoDAO = null;

    public static BDHelper instance;
    public static BDHelper getInstance(Context ctx){
        if(instance == null){
            instance = new BDHelper(ctx);
        }
        return instance;
    }

    public BDHelper(Context context){
        super(context, "loja.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1){
        try{
            Log.i(BDHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Pessoa.class);
            TableUtils.createTable(connectionSource, Produto.class);
            TableUtils.createTable(connectionSource, Endereco.class);
        }
        catch (SQLException e){
            Log.e(BDHelper.class.getName(), "Erro ao criar o Banco de Dados", e);
            throw new RuntimeException(e);

        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3){
        // TODO Auto-generated method stub
    }

    // otimizar a quantidade de objetos dao criados
    public Dao<Pessoa, Long> getPessoaDAO() throws SQLException{
        if (pessoaDAO == null){
            pessoaDAO = getDao(Pessoa.class);
        }
        return pessoaDAO;
    }

    // otimizar a quantidade de objetos dao criados
    public Dao<Produto, Long> getProdutoDAO() throws SQLException{
        if (produtoDAO == null){
            produtoDAO = getDao(Produto.class);
        }
        return produtoDAO;
    }

}
