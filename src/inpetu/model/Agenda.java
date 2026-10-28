
package inpetu.model;

import java.time.LocalDateTime;

public class Agenda {

    private int id;
    private Contato contato;
    private Recurso recurso;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public Agenda(int id, Contato contato, Recurso recurso,
                  LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.contato = contato;
        this.recurso = recurso;
        this.inicio = inicio;
        this.fim = fim;
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

    @Override
    public String toString() {
        return "Agenda{" +
                "id=" + id +
                ", contato=" + contato +
                ", recurso=" + recurso +
                ", inicio=" + inicio +
                ", fim=" + fim +
                '}';
    }
}
