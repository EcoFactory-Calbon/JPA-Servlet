package com.example.servletcalbon.modelEmpresa;

public class Setor {

    private int id;
    private String nome;
    private int idEmpresa;

    // Construtores
    public Setor() {}

    public Setor(int id, String nome, int idEmpresa) {
        this.id = id;
        this.nome = nome;
        this.idEmpresa = idEmpresa;
    }

    public Setor(String nome, int idEmpresa) {
        this.nome = nome;
        this.idEmpresa = idEmpresa;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }

    @Override
    public String toString() {
        return "Setor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idEmpresa=" + idEmpresa +
                '}';
    }
}
