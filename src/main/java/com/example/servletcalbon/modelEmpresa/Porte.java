package com.example.servletcalbon.modelEmpresa;

public class Porte {

//    ATRIBUTOS
    private int id;
    private String nome;

//    CONSTRUTOR
    public Porte(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Porte(String nome) {
        this.nome = nome;
    }

//    GETTERS E SETTERS
    public int getId() { return id; }
    public String getNome() { return nome; }

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }

//    TOSTRING
    @Override
    public String toString() {
        return "Porte{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
