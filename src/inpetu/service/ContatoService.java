
package inpetu.service;

import inpetu.model.Contato;
import inpetu.repository.IContatoRepository;

import java.util.Optional;

public class ContatoService implements IContatoService {

    private final IContatoRepository repository;

    public ContatoService(IContatoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void gravar(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("O contato não pode ser nulo.");
        }

        if (contato.getNome() == null || contato.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do contato é obrigatório.");
        }

        repository.gravar(contato);
    }

    @Override
    public void remover(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("O contato não pode ser nulo.");
        }

        repository.remover(contato);
    }

    @Override
    public Optional<Contato> buscarPorId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }

        return repository.buscarPorId(id);
    }

    @Override
    public Optional<Contato> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome para busca é obrigatório.");
        }

        return repository.buscarPorNome(nome.trim());
    }
}
