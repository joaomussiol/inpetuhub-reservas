package inpetu.repository;

import inpetu.model.Contato;

import java.util.List;
import java.util.Optional;

public interface IContatoRepository {
    void gravar(Contato contato);
    void atualizar(Contato contato);
    void remover(Contato contato);
    Optional<Contato> buscarPorId(int id);
    Optional<Contato> buscarPorNome(String nome);
    List<Contato> listarTodos();
}