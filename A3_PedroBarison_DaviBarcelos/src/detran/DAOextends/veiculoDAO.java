package detran.DAOextends;

import detran.SQL.DataAccessObject;
import detran.SistemaBase.veiculo;
import detran.SistemaBase.proprietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Estende DataAccessObject, especificando que trabalhará com objetos de tipo 'veiculo'
public class veiculoDAO extends DataAccessObject<veiculo> {

    private proprietarioDAO propDAO;


    /*
    * Construtor da classe
    * */

    public veiculoDAO() throws SQLException {

        // Chama o construtor da classe pai (DataAccessObject), que estabelece a conexão com o banco de dados
        super();

        try {

            /*
            * Inicializa a proprietarioDAO. Isso permite que veiculoDAO use os métodos de propDAO
            * para obter informações sobre o proprietário de um veículo ao ler do banco de dados
            * */

            this.propDAO = new proprietarioDAO();
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar proprietarioDAO em veiculoDAO: " + e.getMessage());
            throw e;
        }
    }


    /*
    *
    * Implementação dos Métodos CRUD herdados de DataAccessObject
    *
    * */

    @Override
    public void criar(veiculo v) throws SQLException {
        String sql = "INSERT INTO veiculos (placa, marca, modelo, ano, cor, id_proprietario) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, v.getPlaca());
            stmt.setString(2, v.getMarca());
            stmt.setString(3, v.getModelo());
            stmt.setInt(4, v.getAno());
            stmt.setString(5, v.getCor());


            if (v.getDono() != null && v.getDono().getId() > 0) {
                stmt.setInt(6, v.getDono().getId());
            } else {
                throw new SQLException("O proprietário do veículo não foi definido ou não possui um ID válido.");
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar veículo, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    v.setId(generatedKeys.getInt(1));
                    System.out.println("Veículo com placa " + v.getPlaca() + " inserido com sucesso! ID: " + v.getId());
                } else {
                    System.out.println("Erro ao obter o ID gerado para o veículo " + v.getPlaca());
                }
            }
        }
    }


    @Override
    public veiculo lerPorId(int id) throws SQLException {
        String sql = "SELECT id, placa, marca, modelo, ano, cor, id_proprietario FROM veiculos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    veiculo v = new veiculo();
                    v.setId(rs.getInt("id"));
                    v.setPlaca(rs.getString("placa"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setAno(rs.getInt("ano"));
                    v.setCor(rs.getString("cor"));
                    int idProprietario = rs.getInt("id_proprietario");

                    proprietario dono = propDAO.lerPorId(idProprietario);
                    v.setDono(dono);
                    return v;
                }
            }
        }
        return null;
    }


    public veiculo lerPorPlaca(String placa) throws SQLException {
        String sql = "SELECT id, placa, marca, modelo, ano, cor, id_proprietario FROM veiculos WHERE placa = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, placa);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    veiculo v = new veiculo();
                    v.setId(rs.getInt("id"));
                    v.setPlaca(rs.getString("placa"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setAno(rs.getInt("ano"));
                    v.setCor(rs.getString("cor"));

                    int idProprietario = rs.getInt("id_proprietario");
                    proprietario dono = propDAO.lerPorId(idProprietario);
                    v.setDono(dono);
                    return v;
                }
            }
        }
        return null;
    }


    @Override
    public void atualizar(veiculo v) throws SQLException {
        String sql = "UPDATE veiculos SET placa = ?, marca = ?, modelo = ?, ano = ?, cor = ?, id_proprietario = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, v.getPlaca());
            stmt.setString(2, v.getMarca());
            stmt.setString(3, v.getModelo());
            stmt.setInt(4, v.getAno());
            stmt.setString(5, v.getCor());
            if (v.getDono() != null && v.getDono().getId() > 0) {
                stmt.setInt(6, v.getDono().getId());
            } else {
                throw new SQLException("O proprietário do veículo não foi definido ou não possui um ID válido para atualização.");
            }
            stmt.setInt(7, v.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Veículo com placa " + v.getPlaca() + " atualizado com sucesso!");
            } else {
                System.out.println("Nenhum veículo encontrado com ID " + v.getId() + " para atualizar.");
            }
        }
    }


    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM veiculos WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Veículo com ID " + id + " deletado com sucesso!");
            } else {
                System.out.println("Nenhum veículo encontrado com ID " + id + " para deletar.");
            }
        }
    }


    @Override
    public List<veiculo> listarTodos() throws SQLException {
        List<veiculo> veiculos = new ArrayList<>();
        String sql = "SELECT id, placa, marca, modelo, ano, cor, id_proprietario FROM veiculos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                veiculo v = new veiculo();
                v.setId(rs.getInt("id"));
                v.setPlaca(rs.getString("placa"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAno(rs.getInt("ano"));
                v.setCor(rs.getString("cor"));

                int idProprietario = rs.getInt("id_proprietario");
                proprietario dono = propDAO.lerPorId(idProprietario);
                v.setDono(dono);

                veiculos.add(v);
            }
        }
        return veiculos;
    }
}