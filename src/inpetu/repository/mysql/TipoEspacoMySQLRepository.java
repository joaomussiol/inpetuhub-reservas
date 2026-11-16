package inpetu.repository.mysql;

import inpetu.database.ConexaoMySQL;
import inpetu.model.TipoEspaco;
import inpetu.repository.ITipoEspacoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TipoEspacoMySQLRepository implements ITipoEspacoRepository {

    @Override
    public Optional<TipoEspaco> buscarPorId(int id) {
        String sql = "SELECT id, nome, descricao FROM tipo_espaco WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    TipoEspaco tipoEspaco = new TipoEspaco(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("descricao")
                    );
                    return Optional.of(tipoEspaco);
                }
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar o tipo de espaço.", e);
        }
    }

    @Override
    public List<TipoEspaco> listarTodos() {
        String sql = "SELECT id, nome, descricao FROM tipo_espaco ORDER BY id";
        List<TipoEspaco> tipos = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                TipoEspaco tipoEspaco = new TipoEspaco(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("descricao")
                );
                tipos.add(tipoEspaco);
            }

            return tipos;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar os tipos de espaço.", e);
        }
    }
}
