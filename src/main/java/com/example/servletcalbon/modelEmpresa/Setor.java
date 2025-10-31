package com.example.servletcalbon.modelEmpresa;

public class Setor {

    // ATRIBUTOS
    private int id;
    private String nome;
    private int idEmpresa;

    // CONSTRUTORES
    public Setor() {
    }

    public Setor(int id, String nome, int idEmpresa) {
        this.id = id;
        this.nome = nome;
        this.idEmpresa = idEmpresa;
    }

    public Setor(String nome, int idEmpresa) {
        this.nome = nome;
        this.idEmpresa = idEmpresa;
    }

    // GETTERS E SETTERS
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    // TOSTRING
    @Override
    public String toString() {
        return "Setor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idEmpresa=" + idEmpresa +
                '}';
    }
}
