package br.com.backendjwt;

public class Dashboard {

    private int id;
    private String nome;
    private String email;

    public Dashboard() {
        super();
    }

    public Dashboard(int id, String nome, String email) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Dashboard(int i) {
        super();
        this.id = i;
        this.nome = "Nome " + i;
        this.email = "email" + i + "@email.com";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Dashboard [id=" + id + ", nome=" + nome + ", email=" + email + "]";
    }

}
