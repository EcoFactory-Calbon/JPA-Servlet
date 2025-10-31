package com.example.servletcalbon.modelFuncionario;

public class Cargo {

    //    ATRIBUTOS
    private int id;
    private String nome;
    private int idSetor;

    //    CONSTRUTORES
    public Cargo(int id, String nome, int idSetor) {
        this.id = id;
        this.nome = nome;
        this.idSetor = idSetor;
    }

    public Cargo(String nome, int idSetor) {
        this.nome = nome;
        this.idSetor = idSetor;
    }

    //    GETTERS E SETTERS
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    //    TOSTRING
    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idSetor=" + idSetor +
                '}';
    }
}
