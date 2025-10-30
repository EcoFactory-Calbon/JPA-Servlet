package com.example.servletcalbon.modelFuncionario;

import java.util.Objects;
public class Funcionario {
//    ATRIBUTOS
    private Long id;
    private String numeroCracha;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean isGestor;
    private Long idCargo;
    private Long idLocalizacao;

    public Funcionario() {
    }

//    CONSTRUTOR
    public Funcionario(Long id, String numeroCracha, String nome, String sobrenome, String email, String senha,
                       boolean isGestor, Long idCargo, Long idLocalizacao) {
        this.id = id;
        this.numeroCracha = numeroCracha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.isGestor = isGestor;
        this.idCargo = idCargo;
        this.idLocalizacao = idLocalizacao;
    }

    public Funcionario(String numeroCracha, String nome, String sobrenome, String email, String senha,
                       boolean isGestor, Long idCargo, Long idLocalizacao) {
        this.numeroCracha = numeroCracha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.isGestor = isGestor;
        this.idCargo = idCargo;
        this.idLocalizacao = idLocalizacao;
    }

//    GETTERS E SETTERS
    public Long getId() {
        return id;
    }
    public String getNumeroCracha() {
        return numeroCracha;
    }
    public String getNome() {
        return nome;
    }
    public String getSobrenome() {
        return sobrenome;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }
    public Long getIdCargo() {
        return idCargo;
    }
    public Long getIdLocalizacao() {
        return idLocalizacao;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNumeroCracha(String numeroCracha) {
        this.numeroCracha = numeroCracha;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public boolean isGestor() {
        return isGestor;
    }
    public void setGestor(boolean gestor) {
        isGestor = gestor;
    }
    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }
    public void setIdLocalizacao(Long idLocalizacao) {
        this.idLocalizacao = idLocalizacao;
    }

//    TOSTRING
    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", numeroCracha='" + numeroCracha + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", isGestor=" + isGestor +
                ", idCargo=" + idCargo +
                ", idLocalizacao=" + idLocalizacao +
                '}';
    }

//    EQUALS E HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
