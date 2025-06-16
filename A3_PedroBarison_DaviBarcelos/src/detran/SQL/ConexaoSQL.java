package detran.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


   /*
   * Classe base da conexão com o banco de dados
   * */

    public class ConexaoSQL {
        private static final String URL = "jdbc:mysql://localhost:3306/detran";
        private static final String USUARIO = "root";
        private static final String SENHA = "Buca@2409";

        /*
        * Se conecta com o banco de dados
        * */

        public static Connection conectar() throws SQLException {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

