package inpetu.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    public static Connection conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/inpetu_hub";
        String usuario = System.getenv("INPETU_DB_USUARIO");
        String senha = System.getenv("INPETU_DB_SENHA");

        if (usuario == null || usuario.isBlank() || senha == null) {
            throw new SQLException(
                    "Configure as variáveis INPETU_DB_USUARIO e INPETU_DB_SENHA."
            );
        }

        return DriverManager.getConnection(url, usuario, senha);
    }
}
