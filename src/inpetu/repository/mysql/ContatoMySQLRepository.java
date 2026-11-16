package inpetu.repository.mysql;

import inpetu.database.ConexaoMySQL;
import inpetu.model.Contato;
import inpetu.model.TipoContato;
import inpetu.repository.IContatoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContatoMySQLRepository implements IContatoRepository {

    private static final String CONSULTA =
            "SELECT c.id, c.nome, c.cpf, c.cnpj, c.email, c.telefone, "
                    + "t.id AS tipo_id, t.nome AS tipo_nome, t.descricao AS tipo_descricao "
                    + "FROM contato c INNER JOIN tipo_contato t ON c.tipo_contato_id = t.id";

    @Override
    public void gravar(Contato contato) {
        validarContato(contato);
        if (contato.getId() != 0) {
            throw new IllegalArgumentException(
                    "Um novo contato deve ter ID zero. O MySQL gera o ID automaticamente."
            );
        }

        String sql = "INSERT INTO contato (nome, cpf, cnpj, email, telefone, tipo_contato_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherDados(comando, contato);
            comando.executeUpdate();

            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O MySQL não retornou o ID do contato inserido.");
                }
                contato.setId(chaves.getInt(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao gravar o contato.", e);
        }
    }

    @Override
    public void atualizar(Contato contato) {
        validarContato(contato);
        validarId(contato.getId());
        String sql = "UPDATE contato SET nome = ?, cpf = ?, cnpj = ?, email = ?, "
                + "telefone = ?, tipo_contato_id = ? WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            preencherDados(comando, contato);
            comando.setInt(7, contato.getId());

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Contato não encontrado.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar o contato.", e);
        }
    }

    @Override
    public void remover(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("O contato não pode ser nulo.");
        }
        validarId(contato.getId());
        String sql = "DELETE FROM contato WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, contato.getId());

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Contato não encontrado.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao remover o contato.", e);
        }
    }

    @Override
    public Optional<Contato> buscarPorId(int id) {
        validarId(id);
        String sql = CONSULTA + " WHERE c.id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(lerContato(resultado));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar o contato por ID.", e);
        }
    }

    @Override
    public Optional<Contato> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome para busca é obrigatório.");
        }
        String sql = CONSULTA + " WHERE c.nome = ? ORDER BY c.id LIMIT 1";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setString(1, nome);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(lerContato(resultado));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar o contato por nome.", e);
        }
    }

    @Override
    public List<Contato> listarTodos() {
        String sql = CONSULTA + " ORDER BY c.id";
        List<Contato> contatos = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                contatos.add(lerContato(resultado));
            }
            return contatos;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar os contatos.", e);
        }
    }

    private Contato lerContato(ResultSet resultado) throws SQLException {
        TipoContato tipo = new TipoContato(
                resultado.getInt("tipo_id"),
                resultado.getString("tipo_nome"),
                resultado.getString("tipo_descricao")
        );
        Contato contato = new Contato(
                resultado.getString("nome"),
                resultado.getString("cpf"),
                resultado.getString("cnpj"),
                resultado.getString("email"),
                resultado.getString("telefone"),
                tipo
        );
        contato.setId(resultado.getInt("id"));
        return contato;
    }

    private void preencherDados(PreparedStatement comando, Contato contato) throws SQLException {
        comando.setString(1, contato.getNome());
        comando.setString(2, contato.getCpf());
        comando.setString(3, contato.getCnpj());
        comando.setString(4, contato.getEmail());
        comando.setString(5, contato.getTelefone());
        comando.setInt(6, contato.getTipoContato().getId());
    }

    private void validarContato(Contato contato) {
        if (contato == null) {
            throw new IllegalArgumentException("O contato não pode ser nulo.");
        }
        if (contato.getTipoContato() == null || contato.getTipoContato().getId() <= 0) {
            throw new IllegalArgumentException("Informe um tipo de contato cadastrado.");
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }
    }
}
