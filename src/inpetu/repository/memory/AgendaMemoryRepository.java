
package inpetu.repository.memory;

import inpetu.model.Agenda;
import inpetu.repository.IAgendaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AgendaMemoryRepository implements IAgendaRepository {

    private final Map<Integer, Agenda> armazenamento = new HashMap<>();

    @Override
    public void gravar(Agenda agenda) {
        armazenamento.put(agenda.getId(), agenda);
    }

    @Override
    public void remover(Agenda agenda) {
        if (agenda != null) {
            armazenamento.remove(agenda.getId());
        }
    }

    @Override
    public Optional<Agenda> buscarPorId(int id) {
        return Optional.ofNullable(armazenamento.get(id));
    }

    @Override
    public List<Agenda> listarTodos() {
        return new ArrayList<>(armazenamento.values());
    }
}
