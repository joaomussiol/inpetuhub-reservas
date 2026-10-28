
package inpetu.service;

import inpetu.model.Agenda;
import inpetu.repository.IAgendaRepository;

import java.util.List;
import java.util.Optional;

public class AgendaService implements IAgendaService {

    private final IAgendaRepository repository;

    public AgendaService(IAgendaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void gravar(Agenda agenda) {
        if (agenda == null) {
            throw new IllegalArgumentException("A reserva não pode ser nula.");
        }

        if (agenda.getId() <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        if (agenda.getContato() == null) {
            throw new IllegalArgumentException("O contato é obrigatório.");
        }

        if (agenda.getRecurso() == null) {
            throw new IllegalArgumentException("O recurso é obrigatório.");
        }

        if (agenda.getInicio() == null || agenda.getFim() == null) {
            throw new IllegalArgumentException("Os horários são obrigatórios.");
        }

        if (!agenda.getInicio().isBefore(agenda.getFim())) {
            throw new IllegalArgumentException(
                    "O início deve ser anterior ao fim da reserva."
            );
        }

        boolean conflito = repository.listarTodos().stream()
                .filter(existente -> existente.getId() != agenda.getId())
                .filter(existente ->
                        existente.getRecurso().getId()
                                == agenda.getRecurso().getId())
                .anyMatch(existente ->
                        agenda.getInicio().isBefore(existente.getFim())
                                && agenda.getFim().isAfter(existente.getInicio())
                );

        if (conflito) {
            throw new IllegalArgumentException(
                    "O recurso já possui uma reserva nesse horário."
            );
        }

        repository.gravar(agenda);
    }

    @Override
    public void remover(Agenda agenda) {
        if (agenda == null) {
            throw new IllegalArgumentException("A reserva não pode ser nula.");
        }

        repository.remover(agenda);
    }

    @Override
    public Optional<Agenda> buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        return repository.buscarPorId(id);
    }

    @Override
    public List<Agenda> listarTodos() {
        return repository.listarTodos();
    }
}
