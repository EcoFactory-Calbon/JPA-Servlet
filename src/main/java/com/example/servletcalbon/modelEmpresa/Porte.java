package com.example.servletcalbon.modelEmpresa;

public class Porte {
    private int id;
    private String nome;

    public Porte(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Porte(String nome) {
        this.nome = nome;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return "Porte{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
