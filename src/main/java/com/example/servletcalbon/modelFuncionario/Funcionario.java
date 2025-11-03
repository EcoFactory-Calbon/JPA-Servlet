package com.example.servletcalbon.modelFuncionario;

public class Funcionario {

    private int numeroCracha;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private boolean isGestor;
    private Long idCargo;
    private Long idLocalizacao;

    // Construtor principal
    public Funcionario(int numeroCracha, String nome, String sobrenome, String email, String senha,
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

    public Funcionario() {}

    // Getters e Setters
    public int getNumeroCracha() { return numeroCracha; }
    public void setNumeroCracha(int numeroCracha) { this.numeroCracha = numeroCracha; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSobrenome() { return sobrenome; }
    public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public boolean isGestor() { return isGestor; }
    public void setGestor(boolean gestor) { isGestor = gestor; }

    public Long getIdCargo() { return idCargo; }
    public void setIdCargo(Long idCargo) { this.idCargo = idCargo; }

    public Long getIdLocalizacao() { return idLocalizacao; }
    public void setIdLocalizacao(Long idLocalizacao) { this.idLocalizacao = idLocalizacao; }

    @Override
    public String toString() {
        return "Funcionario{" +
                "numeroCracha=" + numeroCracha +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", isGestor=" + isGestor +
                ", idCargo=" + idCargo +
                ", idLocalizacao=" + idLocalizacao +
                '}';
    }
}
