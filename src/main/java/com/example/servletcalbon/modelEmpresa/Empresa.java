package com.example.servletcalbon.modelEmpresa;

public class Empresa {

    // ====== ATRIBUTOS ======
    private int id;
    private String cnpj;
    private String nome;
    private String senha;
    private int idLocalizacao;
    private int idCategoria;
    private int idPorte;

    // Campos auxiliares para exibição (do JOIN)
    private String categoriaNome;
    private String categoriaDescricao;
    private String porteNome;
    private String estado;
    private String cidade;

    // ====== CONSTRUTORES ======
    public Empresa() {}

    public Empresa(String nome, String cnpj, String senha) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.senha = senha;
    }

    // ====== GETTERS ======
    public int getId() { return id; }
    public String getCnpj() { return cnpj; }
    public String getNome() { return nome; }
    public String getSenha() { return senha; }
    public int getIdLocalizacao() { return idLocalizacao; }
    public int getIdCategoria() { return idCategoria; }
    public int getIdPorte() { return idPorte; }

    public String getCategoriaNome() { return categoriaNome; }
    public String getCategoriaDescricao() { return categoriaDescricao; }
    public String getPorteNome() { return porteNome; }
    public String getEstado() { return estado; }
    public String getCidade() { return cidade; }

    // ====== SETTERS ======
    public void setId(int id) { this.id = id; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public void setNome(String nome) { this.nome = nome; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setIdLocalizacao(int idLocalizacao) { this.idLocalizacao = idLocalizacao; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }
    public void setIdPorte(int idPorte) { this.idPorte = idPorte; }

    public void setCategoriaNome(String categoriaNome) { this.categoriaNome = categoriaNome; }
    public void setCategoriaDescricao(String categoriaDescricao) { this.categoriaDescricao = categoriaDescricao; }
    public void setPorteNome(String porteNome) { this.porteNome = porteNome; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    // ====== TO STRING ======
    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", senha='" + senha + '\'' +
                ", idLocalizacao=" + idLocalizacao +
                ", idCategoria=" + idCategoria +
                ", idPorte=" + idPorte +
                ", categoriaNome='" + categoriaNome + '\'' +
                ", categoriaDescricao='" + categoriaDescricao + '\'' +
                ", porteNome='" + porteNome + '\'' +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
