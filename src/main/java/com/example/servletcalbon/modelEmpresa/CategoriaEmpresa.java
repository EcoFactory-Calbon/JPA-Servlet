package com.example.servletcalbon.modelEmpresa;

import java.util.Objects;

public class CategoriaEmpresa {

    // ATRIBUTOS
    private Long id;
    private String nome;
    private String descricao;

    // CONSTRUTOR
    public CategoriaEmpresa(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    // GETTERS E SETTERS
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }

    public void setId(Long id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    // TOSTRING
    @Override
    public String toString() {
        return "CategoriaEmpresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    // EQUALS E HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaEmpresa)) return false;
        CategoriaEmpresa that = (CategoriaEmpresa) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
