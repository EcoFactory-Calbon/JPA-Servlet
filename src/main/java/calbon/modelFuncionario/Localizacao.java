package calbon.modelFuncionario;



public class Localizacao {
    private Long id;
    private String estado;
    private String pais;
    private String end_cidade;
    private String end_bairro;
    private String end_rua;
    private String end_numero;
    private String complemento;

    public Localizacao(Long id, String estado, String pais, String end_cidade, String end_bairro, String end_rua, String end_numero, String complemento) {
        this.id = id;
        this.estado = estado;
        this.pais = pais;
        this.end_cidade = end_cidade;
        this.end_bairro = end_bairro;
        this.end_rua = end_rua;
        this.end_numero = end_numero;
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                ", end_cidade='" + end_cidade + '\'' +
                ", end_bairro='" + end_bairro + '\'' +
                ", end_rua='" + end_rua + '\'' +
                ", end_numero='" + end_numero + '\'' +
                ", complemento='" + complemento + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEnd_cidade() {
        return end_cidade;
    }

    public void setEnd_cidade(String end_cidade) {
        this.end_cidade = end_cidade;
    }

    public String getEnd_bairro() {
        return end_bairro;
    }

    public void setEnd_bairro(String end_bairro) {
        this.end_bairro = end_bairro;
    }

    public String getEnd_rua() {
        return end_rua;
    }

    public void setEnd_rua(String end_rua) {
        this.end_rua = end_rua;
    }

    public String getEnd_numero() {
        return end_numero;
    }

    public void setEnd_numero(String end_numero) {
        this.end_numero = end_numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
