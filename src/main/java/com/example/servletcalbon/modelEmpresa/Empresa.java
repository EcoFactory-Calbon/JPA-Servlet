package com.example.servletcalbon.modelEmpresa;

public class Empresa {
    // -------------------- ATRIBUTOS PRINCIPAIS --------------------
    private int id;
    private String cnpj;
    private String nome;
    private int idLocalizacao;
    private int idCategoria;
    private String senha;
    private int idPorte;

    // -------------------- ATRIBUTOS EXTRAS PARA EXIBIÇÃO --------------------
    private String categoriaNome;
    private String categoriaDescricao;
    private String porteNome;
    private String estado;
    private String cidade;

    // -------------------- CONSTRUTORES --------------------
    public Empresa() {}

    public Empresa(String nome, String cnpj, String senha) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.senha = senha;
    }

    // -------------------- GETTERS E SETTERS --------------------
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdLocalizacao() { return idLocalizacao; }
    public void setIdLocalizacao(int idLocalizacao) { this.idLocalizacao = idLocalizacao; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public int getIdPorte() { return idPorte; }
    public void setIdPorte(int idPorte) { this.idPorte = idPorte; }

    public String getCategoriaNome() { return categoriaNome; }
    public void setCategoriaNome(String categoriaNome) { this.categoriaNome = categoriaNome; }

    public String getCategoriaDescricao() { return categoriaDescricao; }
    public void setCategoriaDescricao(String categoriaDescricao) { this.categoriaDescricao = categoriaDescricao; }

    public String getPorteNome() { return porteNome; }
    public void setPorteNome(String porteNome) { this.porteNome = porteNome; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    // -------------------- TO STRING --------------------
    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", idLocalizacao=" + idLocalizacao +
                ", idCategoria=" + idCategoria +
                ", idPorte=" + idPorte +
                ", senha='" + senha + '\'' +
                ", categoriaNome='" + categoriaNome + '\'' +
                ", categoriaDescricao='" + categoriaDescricao + '\'' +
                ", porteNome='" + porteNome + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
