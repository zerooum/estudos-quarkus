package com.alura.agencias.domain;

public class Agencia {

    Agencia(String nome, String razaoSocial, String cnpj, Endereco endereco) {
        this.nome = nome;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }

    private Long id;
    private final String nome;
    private final String razaoSocial;
    private final String cnpj;
    private final Endereco endereco;

    public Long getId() {
        return 1L;
    }

    public String getNome() {
        return nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Endereco getEndereco() {
        return endereco;
    }
}
