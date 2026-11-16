
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
    private int proximoId = 1;

    @Override
    public void gravar(Agenda agenda) {
        if (agenda == null) {
            throw new IllegalArgumentException("A reserva não pode ser nula.");
        }

        if (agenda.getId() < 0) {
            throw new IllegalArgumentException("O ID não pode ser negativo.");
        }

        if (agenda.getId() == 0) {
            agenda.setId(proximoId++);
        }

        if (armazenamento.containsKey(agenda.getId())) {
            throw new IllegalArgumentException("Já existe uma reserva cadastrada com esse ID.");
        }

        if (agenda.getId() >= proximoId) {
            proximoId = agenda.getId() + 1;
        }

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

    @Override
    public void atualizar(Agenda agenda) {
        if (agenda != null && armazenamento.containsKey(agenda.getId())) {
            armazenamento.put(agenda.getId(), agenda);
        }
    }
}
