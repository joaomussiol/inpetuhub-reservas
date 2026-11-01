package inpetu.repository.memory;

import inpetu.model.Contato;
import inpetu.repository.IContatoRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class ContatoMemoryRepository implements
        IContatoRepository {

    private final Map<Integer, Contato> armazenamento;
    private int proximoId = 1;

    public ContatoMemoryRepository() {
        this.armazenamento = new LinkedHashMap<>();
    }

    @Override
    public Optional<Contato> buscarPorId(int id) {
        return Optional.ofNullable(this.armazenamento.get(id));
    }

    @Override
    public Optional<Contato> buscarPorNome(String nome) {
        for (Contato contato : armazenamento.values()) {
            if (nome.equals(contato.getNome())) {
                return Optional.of(contato);
            }
        }
        return Optional.empty();
    }

    @Override
    public void gravar(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("O contato não pode ser nulo.");
        }

        if (contato.getId() < 0) {
            throw new IllegalArgumentException("O ID não pode ser negativo.");
        }

        if (contato.getId() == 0) {
            contato.setId(this.proximoId++);
        }

        if (this.armazenamento.containsKey(contato.getId())) {
            throw new IllegalArgumentException("Já existe um contato cadastrado com esse ID.");
        }

        this.armazenamento.put(contato.getId(), contato);
    }

    @Override
    public void remover(Contato contato) {
        if (contato != null) {
            this.armazenamento.remove(contato.getId());
        }
    }

    @Override
    public void atualizar(Contato contato) {
        this.armazenamento.put(contato.getId(), contato);
    }

    @Override
    public List<Contato> listarTodos() {
        return new ArrayList<>(this.armazenamento.values());
    }

}
