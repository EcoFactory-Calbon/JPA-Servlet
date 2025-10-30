package com.example.servletcalbon.modelEmpresa;
public class Empresa {
//    ATRIBUTOS
    private int id;
    private String cnpj;
    private String nome;
    private int idLocalizacao;
    private int idCategoria;
    private String senha;
    private int idPorte;

//    CONSTRUTOR
    public Empresa() {}

    public Empresa(String nome, String cnpj, String senha) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.senha = senha;
    }

//    GETTERS E SETTERS
    public int getId() { return id; }
    public String getCnpj() { return cnpj; }
    public String getNome() { return nome; }
    public int getIdLocalizacao() { return idLocalizacao; }
    public int getIdCategoria() { return idCategoria; }
    public String getSenha() { return senha; }
    public int getIdPorte() { return idPorte; }

    public void setId(int id) { this.id = id; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public void setNome(String nome) { this.nome = nome; }
    public void setIdLocalizacao(int idLocalizacao) { this.idLocalizacao = idLocalizacao; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setIdPorte(int idPorte) { this.idPorte = idPorte; }


//    TO STRING
    @Override
    public String toString() {
        return "Empresa{" +
                "Id=" + id +
                ", Cnpj='" + cnpj + '\'' +
                ", Nome='" + nome + '\'' +
                ", ID Localizacao=" + idLocalizacao +
                ", ID Categoria=" + idCategoria +
                ", Senha='" + senha + '\'' +
                ", ID Porte=" + idPorte +
                '}';
    }
}
