package calbon.modelEmpresa;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Empresa {
    private String cnpj;
    private String nome;
    private Date dtCadastro;
    private CategoriaEmpresa categoria; // Associação com CategoriaEmpresa
    private List<Setor> setores;      // Associação com múltiplos Setores
    

    public Empresa() {

    }
    public Empresa(String cnpj, String nome, Date dtCadastro, CategoriaEmpresa categoria, List<Setor> setores) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.dtCadastro = dtCadastro;
        this.categoria = categoria;
        this.setores = setores;
    }

    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }

    public CategoriaEmpresa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEmpresa categoria) {
        this.categoria = categoria;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

    // Método toString
    @Override
    public String toString() {
        return "Empresa{" +
                "cnpj='" + cnpj + '\'' +
                ", nome='" + nome + '\'' +
                ", dtCadastro=" + dtCadastro +
                ", categoria=" + categoria +
                ", setores=" + setores +
                '}';
    }

    // Métodos equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(cnpj, empresa.cnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnpj);
    }
}