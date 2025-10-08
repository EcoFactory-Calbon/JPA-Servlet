package calbon.modelFuncionario;

import java.time.LocalDate;

public class Funcionario {
    private Long id;
    private String numeroCracha;
    private String nome;
    private String sobrenome;
    private String email;
    private String senhaHash;
    private LocalDate dataNascimento;
    private LocalDate dataContratacao;
    private boolean isGestor;
    private String telefone;

    // Construtor completo
    public Funcionario(Long id, String numeroCracha, String nome, String sobrenome, String email, String senhaHash,
                       LocalDate dataNascimento, LocalDate dataContratacao, boolean isGestor, String telefone) {
        this.id = id;
        this.numeroCracha = numeroCracha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.dataNascimento = dataNascimento;
        this.dataContratacao = dataContratacao;
        this.isGestor = isGestor;
        this.telefone = telefone;
    }

    // Construtor sem ID
    public Funcionario(String numeroCracha, String nome, String sobrenome, String email, String senhaHash,
                       LocalDate dataNascimento, LocalDate dataContratacao, boolean isGestor, String telefone) {
        this.numeroCracha = numeroCracha;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.dataNascimento = dataNascimento;
        this.dataContratacao = dataContratacao;
        this.isGestor = isGestor;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCracha() {
        return numeroCracha;
    }

    public void setNumeroCracha(String numeroCracha) {
        this.numeroCracha = numeroCracha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public boolean isGestor() {
        return isGestor;
    }

    public void setGestor(boolean gestor) {
        isGestor = gestor;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
