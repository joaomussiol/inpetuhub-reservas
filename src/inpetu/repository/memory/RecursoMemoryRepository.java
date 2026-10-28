
package inpetu.repository.memory;

import inpetu.model.Recurso;
import inpetu.repository.IRecursoRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RecursoMemoryRepository implements IRecursoRepository {

    private final Map<Integer, Recurso> armazenamento = new HashMap<>();

    @Override
    public void gravar(Recurso recurso) {
        armazenamento.put(recurso.getId(), recurso);
    }

    @Override
    public void remover(Recurso recurso) {
        if (recurso != null) {
            armazenamento.remove(recurso.getId());
        }
    }

    @Override
    public Optional<Recurso> buscarPorId(int id) {
        return Optional.ofNullable(armazenamento.get(id));
    }
}
