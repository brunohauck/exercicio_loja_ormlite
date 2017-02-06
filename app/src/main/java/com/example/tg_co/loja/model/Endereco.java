package com.example.tg_co.loja.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tg_co on 14/01/2017.
 */
@DatabaseTable(tableName = "endereco")
public class Endereco {
    @DatabaseField(generatedId = true)
    private Long enderecoId;
    @DatabaseField()
    private String logradouro;
    @DatabaseField()
    private String numero;
    @DatabaseField()
    private String complemento;
    @DatabaseField()
    private String cidade;
    @DatabaseField()
    private String estado;
    @DatabaseField()
    private String cep;

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
