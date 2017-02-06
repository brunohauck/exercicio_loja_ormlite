package com.example.tg_co.loja.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tg_co on 28/01/2017.
 */
@DatabaseTable(tableName = "produtos")
public class Produto {
    @DatabaseField(generatedId = true)
    private Long produtoId;
    @DatabaseField()
    private String nome;
    @DatabaseField(dataType = DataType.DOUBLE_OBJ)
    private Double preco;
    @DatabaseField()
    private String descricao;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
