package detran.DAOextends;

import detran.SQL.ConexaoSQL;
import detran.SQL.DataAccessObject;
import detran.SistemaBase.proprietario;
import detran.SistemaBase.transferencia;
import detran.SistemaBase.veiculo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Estende DataAccessObject, especificando que esta DAO manipulará objetos do tipo 'transferencia'
public class transferenciaDAO extends DataAccessObject<transferencia> {

    /*
    * Atributos de outras DAOs
    * Necessários pois precisa dessas informações para uma transferencia
    * */

    private veiculoDAO veicDAO;
    private proprietarioDAO propDAO;

    /*
    * Construtores com inici
    * */

    public transferenciaDAO() throws SQLException {
        super();
        try {

            // Inicializa as DAOs de Veiculo e Proprietario.
            this.veicDAO = new veiculoDAO();
            this.propDAO = new proprietarioDAO();
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar DAOs em transferenciaDAO: " + e.getMessage());
            throw e;
        }
    }

    /*
    *
    * Implementação dos Métodos CRUD herdados de DataAccessObject
    *
    * */

    @Override
    public void criar(transferencia t) throws SQLException {
        String sql = "INSERT INTO transferencias (id_veiculo, id_proprietario_antigo, id_proprietario_novo, data_transferencia) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, t.getVeiculo().getId());
            stmt.setInt(2, t.getAntigoDono().getId());
            stmt.setInt(3, t.getNovoDono().getId());
            stmt.setString(4, t.getDataTransferencia());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar transferência, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    t.setId(generatedKeys.getInt(1)); // Define o ID gerado na transferência
                    System.out.println("Transferência registrada com sucesso! ID: " + t.getId());
                } else {
                    System.out.println("Erro ao obter o ID gerado para a transferência.");
                }
            }
        }
    }


    @Override
    public transferencia lerPorId(int id) throws SQLException {
        String sql = "SELECT id, id_veiculo, id_proprietario_antigo, id_proprietario_novo, data_transferencia FROM transferencias WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int idTransferencia = rs.getInt("id");
                    int idVeiculo = rs.getInt("id_veiculo");
                    int idProprietarioAntigo = rs.getInt("id_proprietario_antigo");
                    int idProprietarioNovo = rs.getInt("id_proprietario_novo");
                    String dataTransferencia = rs.getString("data_transferencia");

                    veiculo veic = veicDAO.lerPorId(idVeiculo);
                    proprietario antigoDono = propDAO.lerPorId(idProprietarioAntigo);
                    proprietario novoDono = propDAO.lerPorId(idProprietarioNovo);

                    if (veic != null && antigoDono != null && novoDono != null) {
                        transferencia t = new transferencia(veic, antigoDono, novoDono, dataTransferencia);
                        t.setId(idTransferencia);
                        return t;
                    }
                }
            }
        }
        return null;
    }



    @Override
    public void atualizar(transferencia entidade) throws SQLException {
        throw new UnsupportedOperationException("Atualização de transferência não suportada nesta DAO.");
    }

    @Override
    public void deletar(int id) throws SQLException {
        throw new UnsupportedOperationException("Exclusão de transferência não suportada nesta DAO.");
    }


    @Override
    public List<transferencia> listarTodos() throws SQLException {
        List<transferencia> listaTransferencias = new ArrayList<>();
        String sql = "SELECT id, id_veiculo, id_proprietario_antigo, id_proprietario_novo, data_transferencia FROM transferencias";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idVeiculo = rs.getInt("id_veiculo");
                int idProprietarioAntigo = rs.getInt("id_proprietario_antigo");
                int idProprietarioNovo = rs.getInt("id_proprietario_novo");
                String dataTransferencia = rs.getString("data_transferencia");

                veiculo veic = veicDAO.lerPorId(idVeiculo);
                proprietario antigoDono = propDAO.lerPorId(idProprietarioAntigo);
                proprietario novoDono = propDAO.lerPorId(idProprietarioNovo);

                if (veic != null && antigoDono != null && novoDono != null) {
                    transferencia t = new transferencia(veic, antigoDono, novoDono, dataTransferencia);
                    t.setId(id);
                    listaTransferencias.add(t);
                }
            }
        }
        return listaTransferencias;
    }
}