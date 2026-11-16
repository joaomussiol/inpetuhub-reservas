package inpetu.repository;

import inpetu.model.TipoContato;

import java.util.List;
import java.util.Optional;

public interface ITipoContatoRepository {

    Optional<TipoContato> buscarPorId(int id);

    List<TipoContato> listarTodos();
}
