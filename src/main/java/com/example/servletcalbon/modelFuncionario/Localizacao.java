package com.example.servletcalbon.modelFuncionario;

public class Localizacao {
    private Long id;
    private String estado;
    private String cidade;

    public Localizacao(Long id, String estado, String cidade) {
        this.id = id;
        this.estado = estado;
        this.cidade = cidade;
    }

    public Long getId() { return id; }  // ✅ Corrigido
    public void setId(Long id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    @Override
    public String toString() {
        return "Localizacao{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                '}';
    }
}
