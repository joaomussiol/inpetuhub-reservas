
package inpetu.repository;

import inpetu.model.Recurso;

import java.util.Optional;

public interface IRecursoRepository {

    void gravar(Recurso recurso);

    void remover(Recurso recurso);

    Optional<Recurso> buscarPorId(int id);
}
