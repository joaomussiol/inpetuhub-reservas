package inpetu.repository.mysql;

import inpetu.database.ConexaoMySQL;
import inpetu.model.TipoContato;
import inpetu.repository.ITipoContatoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TipoContatoMySQLRepository implements ITipoContatoRepository {

    @Override
    public Optional<TipoContato> buscarPorId(int id) {
        String sql = "SELECT id, nome, descricao FROM tipo_contato WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    TipoContato tipoContato = new TipoContato(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("descricao")
                    );
                    return Optional.of(tipoContato);
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar o tipo de contato.", e);
        }
    }

    @Override
    public List<TipoContato> listarTodos() {
        String sql = "SELECT id, nome, descricao FROM tipo_contato ORDER BY id";
        List<TipoContato> tipos = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                TipoContato tipoContato = new TipoContato(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("descricao")
                );
                tipos.add(tipoContato);
            }

            return tipos;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar os tipos de contato.", e);
        }
    }
}
