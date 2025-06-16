package detran.ValidCPF_PLACA;

public class validadorCPF {

    /*
    * FUNÇÂO: validar um CPF
    * */

    public static boolean validar(String cpf) {
        cpf = limparCPF(cpf);

        /*
        * Verifica-se o tamanho e se todos os dígitos são iguais (ex: 111.111.111-11)
        * CPFs com todos os dígitos iguais são inválidos pela regra de validação,
        * mas passariam pelos cálculos, então são tratados separadamente.
        * */

        if (cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) {
            return false;
        }

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (10 - i) * Character.getNumericValue(cpf.charAt(i));
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 > 9) digito1 = 0;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (11 - i) * Character.getNumericValue(cpf.charAt(i));
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 > 9) digito2 = 0;

            return (Character.getNumericValue(cpf.charAt(9)) == digito1) &&
                    (Character.getNumericValue(cpf.charAt(10)) == digito2);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
    * formata o CPF com pontos e hífen para exibição XXX.XXX.XXX-XX
    * */

    public static String limparCPF(String cpf) {return cpf.replaceAll("[^0-9]", "");
    }

    public static String formatarCPF(String cpf) {
        cpf = limparCPF(cpf);
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}