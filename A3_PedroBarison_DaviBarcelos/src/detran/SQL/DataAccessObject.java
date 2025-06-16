package detran.SQL;

import detran.SistemaBase.veiculo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;


/*
* <T> faz com que a classe trabalhe com qualquer tipo de objeto
* */

public abstract class DataAccessObject<T> {

    /*
    * protected = significa que subclasses podem acessar diretamente 'connection'.
    * */

    protected Connection connection;

    /*
    * Lança uma execução SQL quando uma subclasse é instanciada
    * Tenta estabelecer a conexão com o banco de dados usando a classe ConexaoSQL.
    * */

    public DataAccessObject() throws SQLException {
        this.connection = ConexaoSQL.conectar();
    }

    /*
    * Métodos abstratos, cada DAO deve saber como realizar estas operações
    * */

    // Cria um registro no banco de dados
    public abstract void criar(T entidade) throws SQLException;

    // Le um regitro por seu ID
    public abstract T lerPorId(int id) throws SQLException;

    // Atualiza um registro existente no banco de dados
    public abstract void atualizar(T entidade) throws SQLException;

    // Deleta um registro por seu ID
    public abstract void deletar(int id) throws SQLException;

    //Lista todos os registros
    public abstract List<T> listarTodos() throws SQLException;

}
