package inpetu.model;

public class Recurso {

    private int id;
    private String nome;
    private String descricao;
    private int capacidade;
    private boolean ativo;
    private TipoEspaco tipoEspaco;

    public Recurso(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = true;
    }

    public Recurso(String nome, String descricao,
                   int capacidade, TipoEspaco tipoEspaco) {
        this.nome = nome;
        this.descricao = descricao;
        this.capacidade = capacidade;
        this.tipoEspaco = tipoEspaco;
        this.ativo = true;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public TipoEspaco getTipoEspaco() {
        return tipoEspaco;
    }

    public void setTipoEspaco(TipoEspaco tipoEspaco) {
        this.tipoEspaco = tipoEspaco;
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", capacidade=" + capacidade +
                ", ativo=" + ativo +
                ", tipoEspaco=" + tipoEspaco +
                '}';
    }
}
