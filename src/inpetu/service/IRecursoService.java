
package inpetu.service;

import inpetu.model.Recurso;

import java.util.Optional;

public interface IRecursoService {

    void gravar(Recurso recurso);

    void remover(Recurso recurso);

    Optional<Recurso> buscarPorId(int id);
}
