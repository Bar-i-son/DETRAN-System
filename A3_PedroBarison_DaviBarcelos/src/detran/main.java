package detran;

import detran.SistemaBase.sistemaDoDetran;
import java.sql.SQLException;


/*
* FUNÇÂO: iniciar o sistema
* */

public class main {
    public static void main(String[] args) {

        /*
        * 1 - tenta se conectar ao banco de dados
        * 2 - inicia o sistema chamando os métodos necessários
        * */

        try {
            sistemaDoDetran sistema = new sistemaDoDetran();
            sistema.iniciar();

            /*
            * catch responsável por "capturar" erros de banco de dados
            * */

        } catch (SQLException e) {
            System.err.println("Erro de banco de dados: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}