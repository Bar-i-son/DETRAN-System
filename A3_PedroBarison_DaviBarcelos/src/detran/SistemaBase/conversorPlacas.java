package detran.SistemaBase;

import java.util.Random;

public class conversorPlacas {

    private static final char[] DIGITO_PARA_LETRA = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'
    };


    public static String converterParaMercosul(String placaAntiga) {
        placaAntiga = placaAntiga.replaceAll("[^A-Z0-9]", "").toUpperCase();

        if (!placaAntiga.matches("[A-Z]{3}[0-9]{4}")) {
            throw new IllegalArgumentException("Formato de placa antiga inválido");
        }

        char[] caracteres = placaAntiga.toCharArray();
        int segundoNumero = Character.getNumericValue(caracteres[4]);
        char letraSubstituta = DIGITO_PARA_LETRA[segundoNumero];

        return String.format("%s%s%s%s%s%s%s",
                caracteres[0],
                caracteres[1],
                caracteres[2],
                caracteres[3],
                letraSubstituta,
                caracteres[5],
                caracteres[6]
        );
    }


    public static boolean ehPlacaAntiga(String placa) {
        return placa.replaceAll("[^A-Z0-9]", "").matches("[A-Z]{3}[0-9]{4}");
    }


    public static String gerarPlacaMercosul() {
        Random random = new Random();
        StringBuilder placa = new StringBuilder();


        for (int i = 0; i < 3; i++) {
            placa.append((char) (random.nextInt(26) + 'A'));
        }

        placa.append(random.nextInt(10));
        placa.append(DIGITO_PARA_LETRA[random.nextInt(10)]);
        placa.append(random.nextInt(10));
        placa.append(random.nextInt(10));

        return placa.toString();
    }
}