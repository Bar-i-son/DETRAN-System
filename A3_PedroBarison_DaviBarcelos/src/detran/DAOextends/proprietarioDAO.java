package detran.DAOextends;

import detran.SQL.DataAccessObject;
import detran.SistemaBase.proprietario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Estende DataAccessObject, tornando-a uma DAO para objetos do tipo 'proprietario'.
public class proprietarioDAO extends DataAccessObject<proprietario> {
    //Construtor
    public proprietarioDAO() throws SQLException {
        super();
    }

    /*
    *
    * Implementação dos Métodos CRUD herdados de DataAccessObject
    *
    * */

    @Override
    public void criar(proprietario p) throws SQLException {
        String sql = "INSERT INTO proprietarios (nome, cpf) VALUES (?, ?)";


        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar proprietário, nenhuma linha afetada.");
            }


            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    p.setId(generatedKeys.getInt(1));
                    System.out.println("Proprietário " + p.getNome() + " inserido com sucesso! ID: " + p.getId());
                } else {
                    System.out.println("Erro ao obter o ID gerado para o proprietário " + p.getNome());
                }
            }
        }
    }


    @Override
    public proprietario lerPorId(int id) throws SQLException {
        String sql = "SELECT id, nome, cpf FROM proprietarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    proprietario p = new proprietario();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    return p;
                }
            }
        }
        return null;
    }


    public proprietario lerPorCpf(String cpf) throws SQLException {
        String sql = "SELECT id, nome, cpf FROM proprietarios WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    proprietario p = new proprietario();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCpf(rs.getString("cpf"));
                    return p;
                }
            }
        }
        return null;
    }


    @Override
    public void atualizar(proprietario p) throws SQLException {
        String sql = "UPDATE proprietarios SET nome = ?, cpf = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getCpf());
            stmt.setInt(3, p.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Proprietário com ID " + p.getId() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum proprietário encontrado com ID " + p.getId() + " para atualizar.");
            }
        }
    }


    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM proprietarios WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Proprietário com ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum proprietário encontrado com ID " + id + " para deletar.");
            }
        }
    }


    @Override
    public List<proprietario> listarTodos() throws SQLException {
        List<proprietario> proprietarios = new ArrayList<>();
        String sql = "SELECT id, nome, cpf FROM proprietarios";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                proprietario p = new proprietario();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setCpf(rs.getString("cpf"));
                proprietarios.add(p);
            }
        }
        return proprietarios;
    }
}
