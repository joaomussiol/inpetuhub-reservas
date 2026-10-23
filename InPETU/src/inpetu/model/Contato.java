package inpetu.model;

public class Contato {
    private int id;
    private String nome;
    private String cnpj;
    private int telefone;

    public Contato(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() { return cnpj; }

    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public int getTelefone() { return telefone; }

    public void setTelefone(int telefone) { this.telefone = telefone; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
