
package inpetu.repository;

import inpetu.model.Agenda;

import java.util.List;
import java.util.Optional;

public interface IAgendaRepository {

    void gravar(Agenda agenda);

    void remover(Agenda agenda);

    Optional<Agenda> buscarPorId(int id);

    List<Agenda> listarTodos();
}
