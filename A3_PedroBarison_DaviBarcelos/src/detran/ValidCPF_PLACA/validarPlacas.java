package detran.ValidCPF_PLACA;

public class validarPlacas {

    /*
    * FUNÇÂO: formatar as placas de carro
    * Remove caracteres não alfanuméricos e converte para maiúsculas
    * O regex remove tudo que não for letra A-Z ou número 0-9
    * */

    public static String formatarPlaca(String placa) {
        return placa.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    /*
    * Verifica se a placa está no formato antigo (ABC1234) ou Mercosul (ABC1D23)
    * */

    public static boolean validarPlaca(String placa) {
        String placaLimpa = formatarPlaca(placa);
        return placaLimpa.matches("[A-Z]{3}[0-9]{4}") ||             // Antigo
                placaLimpa.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}"); // Mercosul
    }
}