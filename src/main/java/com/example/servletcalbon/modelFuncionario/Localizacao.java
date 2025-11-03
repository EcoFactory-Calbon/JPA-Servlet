package com.example.servletcalbon.modelFuncionario;

public class Localizacao {
//    ATRIBUTOS
    private Long id;
    private String estado;
    private String cidade;

//    CONSTRUTOR
    public Localizacao(Long id, String estado, String cidade) {
        this.id = id;
        this.estado = estado;
        this.cidade = cidade;
    }

    public Localizacao(String estado, String cidade) {
    }

    //    GETTERS E SETTERS
    public Long getId() { return id; }
    public String getEstado() { return estado; }
    public String getCidade() { return cidade; }

    public void setEstado(String estado) { this.estado = estado; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public void setId(Long id) { this.id = id; }

//    TOSTRING
    @Override
    public String toString() {
        return "Localizacao{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
