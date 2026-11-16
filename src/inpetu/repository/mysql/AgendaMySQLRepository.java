package inpetu.repository.mysql;

import inpetu.database.ConexaoMySQL;
import inpetu.model.Agenda;
import inpetu.model.Contato;
import inpetu.model.Recurso;
import inpetu.model.TipoContato;
import inpetu.model.TipoEspaco;
import inpetu.repository.IAgendaRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AgendaMySQLRepository implements IAgendaRepository {

    private static final String CONSULTA =
            "SELECT a.id, a.inicio, a.fim, a.finalidade, a.criado_em, a.status, a.motivo_recusa, "
                    + "c.id AS contato_id, c.nome AS contato_nome, c.cpf, c.cnpj, c.email, c.telefone, "
                    + "tc.id AS tipo_contato_id, tc.nome AS tipo_contato_nome, "
                    + "tc.descricao AS tipo_contato_descricao, "
                    + "r.id AS recurso_id, r.nome AS recurso_nome, r.descricao AS recurso_descricao, "
                    + "r.capacidade, r.ativo, te.id AS tipo_espaco_id, "
                    + "te.nome AS tipo_espaco_nome, te.descricao AS tipo_espaco_descricao "
                    + "FROM agenda a "
                    + "INNER JOIN contato c ON a.contato_id = c.id "
                    + "INNER JOIN tipo_contato tc ON c.tipo_contato_id = tc.id "
                    + "INNER JOIN recurso r ON a.recurso_id = r.id "
                    + "INNER JOIN tipo_espaco te ON r.tipo_espaco_id = te.id";

    @Override
    public void gravar(Agenda agenda) {
        validarAgenda(agenda);
        if (agenda.getId() != 0) {
            throw new IllegalArgumentException(
                    "Uma nova agenda deve ter ID zero. O MySQL gera o ID automaticamente."
            );
        }
        if (agenda.getCriadoEm() == null) {
            throw new IllegalArgumentException("A data de criação é obrigatória.");
        }

        String sql = "INSERT INTO agenda (contato_id, recurso_id, inicio, fim, finalidade, "
                + "status, motivo_recusa, criado_em) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherDados(comando, agenda);
            comando.setTimestamp(8, Timestamp.valueOf(agenda.getCriadoEm().withNano(0)));
            comando.executeUpdate();

            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (!chaves.next()) {
                    throw new SQLException("O MySQL não retornou o ID da agenda inserida.");
                }
                agenda.setId(chaves.getInt(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao gravar a agenda.", e);
        }
    }

    @Override
    public void atualizar(Agenda agenda) {
        validarAgenda(agenda);
        validarId(agenda.getId());
        // A data de criação permanece como foi gravada no cadastro.
        String sql = "UPDATE agenda SET contato_id = ?, recurso_id = ?, inicio = ?, fim = ?, "
                + "finalidade = ?, status = ?, motivo_recusa = ? WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            preencherDados(comando, agenda);
            comando.setInt(8, agenda.getId());

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Agenda não encontrada.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao atualizar a agenda.", e);
        }
    }

    @Override
    public void remover(Agenda agenda) {
        if (agenda == null) {
            throw new IllegalArgumentException("A agenda não pode ser nula.");
        }
        validarId(agenda.getId());
        String sql = "DELETE FROM agenda WHERE id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, agenda.getId());

            if (comando.executeUpdate() == 0) {
                throw new IllegalArgumentException("Agenda não encontrada.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao remover a agenda.", e);
        }
    }

    @Override
    public Optional<Agenda> buscarPorId(int id) {
        validarId(id);
        String sql = CONSULTA + " WHERE a.id = ?";

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            comando.setInt(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(lerAgenda(resultado));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao buscar a agenda por ID.", e);
        }
    }

    @Override
    public List<Agenda> listarTodos() {
        String sql = CONSULTA + " ORDER BY a.id";
        List<Agenda> agendas = new ArrayList<>();

        try (Connection conexao = ConexaoMySQL.conectar();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
            while (resultado.next()) {
                agendas.add(lerAgenda(resultado));
            }
            return agendas;
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao listar as agendas.", e);
        }
    }

    private Agenda lerAgenda(ResultSet resultado) throws SQLException {
        TipoContato tipoContato = new TipoContato(
                resultado.getInt("tipo_contato_id"),
                resultado.getString("tipo_contato_nome"),
                resultado.getString("tipo_contato_descricao")
        );
        Contato contato = new Contato(
                resultado.getString("contato_nome"),
                resultado.getString("cpf"),
                resultado.getString("cnpj"),
                resultado.getString("email"),
                resultado.getString("telefone"),
                tipoContato
        );
        contato.setId(resultado.getInt("contato_id"));

        TipoEspaco tipoEspaco = new TipoEspaco(
                resultado.getInt("tipo_espaco_id"),
                resultado.getString("tipo_espaco_nome"),
                resultado.getString("tipo_espaco_descricao")
        );
        Recurso recurso = new Recurso(
                resultado.getString("recurso_nome"),
                resultado.getString("recurso_descricao"),
                resultado.getInt("capacidade"),
                tipoEspaco
        );
        recurso.setId(resultado.getInt("recurso_id"));
        recurso.setAtivo(resultado.getBoolean("ativo"));

        Agenda agenda = new Agenda(
                resultado.getInt("id"), contato, recurso,
                resultado.getTimestamp("inicio").toLocalDateTime(),
                resultado.getTimestamp("fim").toLocalDateTime()
        );
        agenda.setFinalidade(resultado.getString("finalidade"));
        agenda.setCriadoEm(resultado.getTimestamp("criado_em").toLocalDateTime());
        agenda.setStatus(resultado.getString("status"));
        agenda.setMotivoRecusa(resultado.getString("motivo_recusa"));
        return agenda;
    }

    private void preencherDados(PreparedStatement comando, Agenda agenda) throws SQLException {
        comando.setInt(1, agenda.getContato().getId());
        comando.setInt(2, agenda.getRecurso().getId());
        // O DATETIME do esquema atual armazena os horários em segundos.
        comando.setTimestamp(3, Timestamp.valueOf(agenda.getInicio().withNano(0)));
        comando.setTimestamp(4, Timestamp.valueOf(agenda.getFim().withNano(0)));
        comando.setString(5, agenda.getFinalidade());
        comando.setString(6, agenda.getStatus());
        comando.setString(7, agenda.getMotivoRecusa());
    }

    private void validarAgenda(Agenda agenda) {
        if (agenda == null) {
            throw new IllegalArgumentException("A agenda não pode ser nula.");
        }
        if (agenda.getContato() == null || agenda.getContato().getId() <= 0) {
            throw new IllegalArgumentException("Informe um contato cadastrado.");
        }
        if (agenda.getRecurso() == null || agenda.getRecurso().getId() <= 0) {
            throw new IllegalArgumentException("Informe um recurso cadastrado.");
        }
        if (agenda.getInicio() == null || agenda.getFim() == null) {
            throw new IllegalArgumentException("Os horários são obrigatórios.");
        }
        if (!agenda.getInicio().withNano(0).isBefore(agenda.getFim().withNano(0))) {
            throw new IllegalArgumentException(
                    "O início deve ser anterior ao fim, considerando a precisão de segundos."
            );
        }
        if (agenda.getStatus() == null || agenda.getStatus().isBlank()) {
            throw new IllegalArgumentException("O status é obrigatório.");
        }
    }

    private void validarId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser maior que zero.");
        }
    }
}
