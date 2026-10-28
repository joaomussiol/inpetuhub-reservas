
package inpetu.service;

import inpetu.model.Recurso;
import inpetu.repository.IRecursoRepository;

import java.util.Optional;

public class RecursoService implements IRecursoService {

    private final IRecursoRepository repository;

    public RecursoService(IRecursoRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException(
                    "O repositório de recursos é obrigatório."
            );
        }

        this.repository = repository;
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
            throw new IllegalArgumentException("O recurso não pode ser nulo.");
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
}
