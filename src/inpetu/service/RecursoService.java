package inpetu.service;

import inpetu.model.Recurso;
import inpetu.model.Agenda;
import inpetu.repository.IRecursoRepository;

import java.util.Optional;
import java.util.List;

public class RecursoService implements IRecursoService {

    private final IRecursoRepository repository;
    private final IAgendaService agendaService;

    public RecursoService(IRecursoRepository repository, IAgendaService agendaService) {
        if (repository == null) {
            throw new IllegalArgumentException(
                    "O repositório de recursos é obrigatório."
            );
        }

        if (agendaService == null) {
            throw new IllegalArgumentException(
                    "O serviço de reservas é obrigatório."
            );
        }

        this.repository = repository;
        this.agendaService = agendaService;
    }

    @Override
    public void gravar(Recurso recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException("O recurso não pode ser nulo.");
        }

        if (recurso.getId() <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        if (recurso.getNome() == null || recurso.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do recurso é obrigatório.");
        }

        repository.gravar(recurso);
    }

    @Override
    public void remover(Recurso recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException(
                    "O recurso não pode ser nulo."
            );
        }

        if (recurso.getId() <= 0) {
            throw new IllegalArgumentException(
                    "O ID deve ser maior que zero."
            );
        }

        if (repository.buscarPorId(recurso.getId()).isEmpty()) {
            throw new IllegalArgumentException(
                    "O recurso informado não está cadastrado."
            );
        }

        boolean possuiReserva = agendaService.listarTodos().stream()
                .anyMatch(agenda ->
                        agenda.getRecurso().getId() == recurso.getId()
                );

        if (possuiReserva) {
            throw new IllegalArgumentException(
                    "Não é possível remover um recurso vinculado a uma reserva."
            );
        }

        repository.remover(recurso);
    }

    @Override
    public Optional<Recurso> buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        return repository.buscarPorId(id);
    }

    @Override
    public List<Recurso> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public void atualizar(Recurso recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException("O recurso não pode ser nulo.");
        }

        if (recurso.getId() <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        if (recurso.getNome() == null || recurso.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do recurso é obrigatório.");
        }

        if (repository.buscarPorId(recurso.getId()).isEmpty()) {
            throw new IllegalArgumentException("Recurso não encontrado.");
        }

        repository.atualizar(recurso);
    }
}