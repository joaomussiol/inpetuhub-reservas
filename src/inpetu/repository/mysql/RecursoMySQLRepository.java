package inpetu.repository.mysql;

import inpetu.database.ConexaoMySQL;
import inpetu.model.Recurso;
import inpetu.model.TipoEspaco;
import inpetu.repository.IRecursoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecursoMySQLRepository implements IRecursoRepository {

    private static final String CONSULTA =
            "SELECT r.id, r.nome, r.descricao, r.capacidade, r.ativo, "
                    + "t.id AS tipo_id, t.nome AS tipo_nome, t.descricao AS tipo_descricao "
                    + "FROM recurso r INNER JOIN tipo_espaco t ON r.tipo_espaco_id = t.id";

    @Override
    public void gravar(Recurso recurso) {
        validarRecurso(recurso);
        if (recurso.getId() != 0) {
            throw new IllegalArgumentException(
                    "Um novo recurso deve ter ID zero. O MySQL gera o ID automaticamente."
            );
        }

        String sql = "INSERT INTO recurso (nome, descricao, capacidade, ativo, tipo_espaco_id) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherDados(comando, recurso);
            comando.executeUpdate();

            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O MySQL não retornou o ID do recurso inserido.");
                }
                recurso.setId(chaves.getInt(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao gravar o recurso.", e);
        }
    }

    @Override
    public void atualizar(Recurso recurso) {
        validarRecurso(recurso);
        validarId(recurso.getId());
        String sql = "UPDATE recurso SET nome = ?, descricao = ?, capacidade = ?, "
                + "ativo = ?, tipo_espaco_id = ? WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            preencherDados(comando, recurso);
            comando.setInt(6, recurso.getId());

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Recurso não encontrado.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar o recurso.", e);
        }
    }

    @Override
    public void remover(Recurso recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException("O recurso não pode ser nulo.");
        }
        validarId(recurso.getId());
        String sql = "DELETE FROM recurso WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, recurso.getId());

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Recurso não encontrado.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao remover o recurso.", e);
        }
    }

    @Override
    public Optional<Recurso> buscarPorId(int id) {
        validarId(id);
        String sql = CONSULTA + " WHERE r.id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(lerRecurso(resultado));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar o recurso por ID.", e);
        }
    }

    @Override
    public List<Recurso> listarTodos() {
        String sql = CONSULTA + " ORDER BY r.id";
        List<Recurso> recursos = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                recursos.add(lerRecurso(resultado));
            }
            return recursos;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar os recursos.", e);
        }
    }

    private Recurso lerRecurso(ResultSet resultado) throws SQLException {
        TipoEspaco tipo = new TipoEspaco(
                resultado.getInt("tipo_id"),
                resultado.getString("tipo_nome"),
                resultado.getString("tipo_descricao")
        );
        Recurso recurso = new Recurso(
                resultado.getString("nome"),
                resultado.getString("descricao"),
                resultado.getInt("capacidade"),
                tipo
        );
        recurso.setId(resultado.getInt("id"));
        recurso.setAtivo(resultado.getBoolean("ativo"));
        return recurso;
    }

    private void preencherDados(PreparedStatement comando, Recurso recurso) throws SQLException {
        comando.setString(1, recurso.getNome());
        comando.setString(2, recurso.getDescricao());
        comando.setInt(3, recurso.getCapacidade());
        comando.setBoolean(4, recurso.isAtivo());
        comando.setInt(5, recurso.getTipoEspaco().getId());
    }

    private void validarRecurso(Recurso recurso) {
        if (recurso == null) {
            throw new IllegalArgumentException("O recurso não pode ser nulo.");
        }
        if (recurso.getTipoEspaco() == null || recurso.getTipoEspaco().getId() <= 0) {
            throw new IllegalArgumentException("Informe um tipo de espaço cadastrado.");
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }
    }
}
