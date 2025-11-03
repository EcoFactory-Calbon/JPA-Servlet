package com.example.servletcalbon.modelFuncionario;

public class Cargo {

    private Long id;
    private String nome;
    private int idSetor;

    // Construtores
    public Cargo() {}

    public Cargo(Long id, String nome, int idSetor) {
        this.id = id;
        this.nome = nome;
        this.idSetor = idSetor;
    }

    public Cargo(String nome, int idSetor) {
        this.nome = nome;
        this.idSetor = idSetor;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdSetor() { return idSetor; }
    public void setIdSetor(int idSetor) { this.idSetor = idSetor; }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idSetor=" + idSetor +
                '}';
    }
}
