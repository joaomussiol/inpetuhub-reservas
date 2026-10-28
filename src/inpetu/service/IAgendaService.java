
package inpetu.service;

import inpetu.model.Agenda;

import java.util.List;
import java.util.Optional;

public interface IAgendaService {

    void gravar(Agenda agenda);

    void remover(Agenda agenda);

    Optional<Agenda> buscarPorId(int id);

    List<Agenda> listarTodos();
}
