package inpetu.repository;

import inpetu.model.TipoEspaco;

import java.util.List;
import java.util.Optional;

public interface ITipoEspacoRepository {

    Optional<TipoEspaco> buscarPorId(int id);

    List<TipoEspaco> listarTodos();
}
