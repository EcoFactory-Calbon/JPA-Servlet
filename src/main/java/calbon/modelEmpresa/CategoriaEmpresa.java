package calbon.modelEmpresa;

import java.util.Objects;

public class CategoriaEmpresa {
    private Long id;
    private String nome;
    private String descricao;
    private String porte;


    public CategoriaEmpresa() {
    }

    public CategoriaEmpresa(Long id, String nome, String descricao, String porte) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.porte = porte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    // toString Method
    @Override
    public String toString() {
        return "CategoriaEmpresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", porte='" + porte + '\'' +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaEmpresa that = (CategoriaEmpresa) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}