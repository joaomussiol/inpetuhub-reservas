package inpetu.database;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) throws SQLException {
        try (Connection conexao = ConexaoMySQL.conectar()) {
            if (conexao.isClosed()) {
                throw new SQLException("A conexão está fechada.");
            }

            System.out.println("Conexão com o banco inpetu_hub realizada com sucesso!");
        }
    }
}
