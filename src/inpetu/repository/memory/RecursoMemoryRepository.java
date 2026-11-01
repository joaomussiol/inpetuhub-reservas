
package inpetu.repository.memory;

import inpetu.model.Recurso;
import inpetu.repository.IRecursoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

public class RecursoMemoryRepository implements IRecursoRepository {

    private final Map<Integer, Recurso> armazenamento = new HashMap<>();

    @Override
    public void gravar(Recurso recurso) {
        if (recurso != null) {
            armazenamento.put(recurso.getId(), recurso);
        }
    }

    @Override
    public void remover(Recurso recurso) {
        if (recurso != null) {
            armazenamento.remove(recurso.getId());
        }
    }

    @Override
    public void atualizar(Recurso recurso) {
        if (recurso != null) {
            armazenamento.put(recurso.getId(), recurso);
        }
    }

    @Override
    public Optional<Recurso> buscarPorId(int id) {
        return Optional.ofNullable(armazenamento.get(id));
    }

    @Override
    public List<Recurso> listarTodos() {
        return new ArrayList<>(armazenamento.values());
    }
}
