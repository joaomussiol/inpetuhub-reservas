
package inpetu.model;

import java.time.LocalDateTime;

public class Agenda {

    private int id;
    private Contato contato;
    private Recurso recurso;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String finalidade;
    private LocalDateTime criadoEm;
    private String status;
    private String motivoRecusa;

    public Agenda(int id, Contato contato, Recurso recurso,
                  LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.contato = contato;
        this.recurso = recurso;
        this.inicio = inicio;
        this.fim = fim;
        this.criadoEm = LocalDateTime.now();
        this.status = "PENDENTE";
    }

    public Agenda(Contato contato, Recurso recurso,
                  LocalDateTime inicio, LocalDateTime fim,
                  String finalidade) {

        this.contato = contato;
        this.recurso = recurso;
        this.inicio = inicio;
        this.fim = fim;
        this.finalidade = finalidade;
        this.criadoEm = LocalDateTime.now();
        this.status = "PENDENTE";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public void setFinalidade(String finalidade) {
        this.finalidade = finalidade;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    @Override
    public String toString() {
        return "Agenda{" +
                "id=" + id +
                ", contato=" + contato +
                ", recurso=" + recurso +
                ", inicio=" + inicio +
                ", fim=" + fim +
                ", finalidade='" + finalidade + '\'' +
                ", criadoEm=" + criadoEm +
                ", status='" + status + '\'' +
                ", motivoRecusa='" + motivoRecusa + '\'' +
                '}';
    }
}
