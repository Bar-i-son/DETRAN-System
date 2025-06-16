package detran.SistemaBase;

import detran.ValidCPF_PLACA.validadorCPF;
import detran.ValidCPF_PLACA.validarPlacas;
import detran.SistemaBase.conversorPlacas;
import detran.DAOextends.proprietarioDAO;
import detran.DAOextends.veiculoDAO;
import detran.DAOextends.transferenciaDAO;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class sistemaDoDetran {

    /*
    * DAOs, responsaveis pela comunicação com o banco de dados
    * */

    private proprietarioDAO propDAO;
    private veiculoDAO veicDAO;
    private transferenciaDAO transfDAO;
    private Scanner scanner;

    /*
    * Construtor que inicia as DAOs
    * */

    public sistemaDoDetran() throws SQLException {
        this.propDAO = new proprietarioDAO();
        this.veicDAO = new veiculoDAO();
        this.transfDAO = new transferenciaDAO();
        this.scanner = new Scanner(System.in);
    }

    /*
    * Metodo principal, inicia o menu
    * */

    public void iniciar() {
        System.out.println("\n=== DETRAN ===");
        int opcao = 0;
        while (opcao != 6) {
            System.out.println("\n=== DETRAN ===");
            System.out.println("1. Cadastrar veículo");
            System.out.println("2. Transferir veículo");
            System.out.println("3. Ver veículos");
            System.out.println("4. Ver transferências");
            System.out.println("5. Dar baixa em veículo");
            System.out.println("6. Sair");
            System.out.print("Escolha: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 1) {
                    cadastrarVeiculo();
                } else if (opcao == 2) {
                    transferirVeiculo();
                } else if (opcao == 3) {
                    listarVeiculos();
                } else if (opcao == 4) {
                    listarTransferencias();
                } else if (opcao == 5) {
                    darBaixaVeiculo();
                } else if (opcao == 6) {
                    System.out.println("Saindo do sistema Detran. Até mais!");
                } else {
                    System.out.println("Opção inválida. Por favor, escolha uma opção de 1 a 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            } catch (SQLException e) {
                System.err.println("Erro de banco de dados: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    //metodo pra formatar nomes
    private String formatarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return nome;
        }

        String[] palavras = nome.trim().split("\\s+");
        StringBuilder nomeFormatado = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                if (palavra.length() <= 2 &&
                        (palavra.equalsIgnoreCase("de") ||
                                palavra.equalsIgnoreCase("da") ||
                                palavra.equalsIgnoreCase("do") ||
                                palavra.equalsIgnoreCase("dos") ||
                                palavra.equalsIgnoreCase("das"))) {
                    nomeFormatado.append(palavra.toLowerCase()).append(" ");
                } else {
                    nomeFormatado.append(Character.toUpperCase(palavra.charAt(0)))
                            .append(palavra.substring(1).toLowerCase())
                            .append(" ");
                }
            }
        }
        return nomeFormatado.toString().trim();
    }


    //Metodo pra solicitar e validar placas
    private String solicitarPlaca() {
        String placa;
        boolean placaConfirmada = false;

        do {
            System.out.print("Digite a placa do veículo (formato ABC1234 ou ABC1D23): ");
            placa = this.scanner.nextLine();

            if (!validarPlacas.validarPlaca(placa)) {
                System.out.println("Placa inválida! Tente novamente.");
                continue;
            }

            String placaFormatada = validarPlacas.formatarPlaca(placa);
            System.out.println("Placa digitada: " + placaFormatada.substring(0, 3) + "-" + placaFormatada.substring(3));
            System.out.print("Confirmar placa? (S/N): ");
            String resposta = this.scanner.nextLine();

            placaConfirmada = resposta.equalsIgnoreCase("S");

        } while (!placaConfirmada);

        return validarPlacas.formatarPlaca(placa);
    }


    //Metodo pra solicitar e validar o ano do veiculo
    private int solicitarAno() {
        int ano = 0;
        boolean anoValido = false;
        final int ANO_ATUAL = java.time.LocalDate.now().getYear();
        final int PRIMEIRO_ANO_CARRO = 1886;

        do {
            try {
                System.out.print("Ano do veículo (" + PRIMEIRO_ANO_CARRO + "-" + (ANO_ATUAL + 1) + "): ");
                String input = scanner.nextLine();
                ano = Integer.parseInt(input);

                if (ano >= PRIMEIRO_ANO_CARRO && ano <= ANO_ATUAL + 1) {
                    anoValido = true;
                } else {
                    System.out.println("Ano inválido! Digite entre " + PRIMEIRO_ANO_CARRO + " e " + (ANO_ATUAL + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números!");
            }
        } while (!anoValido);

        return ano;
    }

    /*
    * Cadastra um novo veículo (um novo proprietario também, dependendo)
    * */

    private void cadastrarVeiculo() throws SQLException {
        System.out.println("\n=== CADASTRO DE VEICULO ===");

        String nomeProprietario;
        do {
            System.out.print("Nome completo do proprietário: ");
            nomeProprietario = scanner.nextLine().trim();

            if (nomeProprietario.isEmpty() || nomeProprietario.length() < 3) {
                System.out.println("Nome inválido! Deve ter pelo menos 3 caracteres.");
                continue;
            }


            nomeProprietario = formatarNome(nomeProprietario);
            System.out.println("Nome formatado: " + nomeProprietario);
            System.out.print("Confirmar nome (S/N)? ");
        } while (!scanner.nextLine().equalsIgnoreCase("S"));


        String cpfProprietario;
        do {
            System.out.print("CPF do proprietário (apenas números): ");
            cpfProprietario = scanner.nextLine();

            if (!validadorCPF.validar(cpfProprietario)) {
                System.out.println("CPF inválido! Digite novamente.");
                System.out.println("Exemplo válido: 123.456.789-09 (apenas números aceitos na entrada)");
            } else {
                System.out.println("CPF formatado: " + validadorCPF.formatarCPF(cpfProprietario));
                System.out.print("Confirmar CPF (S/N)? ");
                if (scanner.nextLine().equalsIgnoreCase("S")) {
                    break;
                }
            }
        } while (true);


        proprietario donoExistente = propDAO.lerPorCpf(cpfProprietario);
        proprietario donoAtual;

        if (donoExistente == null) {
            donoAtual = new proprietario(nomeProprietario, cpfProprietario);
            propDAO.criar(donoAtual);
            System.out.println("Novo proprietário cadastrado no banco: " + donoAtual.getNome() + " (ID: " + donoAtual.getId() + ")");
        } else {
            System.out.println("Proprietário com CPF " + validadorCPF.formatarCPF(cpfProprietario) + " já existe no sistema.");


            String nomeExistenteFormatado = formatarNome(donoExistente.getNome());
            String nomeDigitadoFormatado = formatarNome(nomeProprietario);

            if (nomeExistenteFormatado.equalsIgnoreCase(nomeDigitadoFormatado)) {

                donoAtual = donoExistente;
                System.out.println("Associando veículo ao proprietário existente: " + donoAtual.getNome() + " (ID: " + donoAtual.getId() + ")");


                System.out.print("Deseja atualizar os dados (nome) do proprietário existente (para '" + nomeDigitadoFormatado + "')? (S/N): ");
                if (scanner.nextLine().equalsIgnoreCase("S")) {
                    donoAtual.setNome(nomeDigitadoFormatado); // Garante que o nome esteja como o usuário confirmou
                    propDAO.atualizar(donoAtual);
                    System.out.println("Dados do proprietário atualizados.");
                }

            } else {
                System.out.println("ERRO: O CPF " + validadorCPF.formatarCPF(cpfProprietario) +
                        " já está cadastrado em nome de '" + donoExistente.getNome() + "'.");
                System.out.println("Não é permitido cadastrar um veículo em nome de '" + nomeProprietario + "' com este CPF.");
                System.out.println("Por favor, verifique os dados ou utilize o CPF correto.");
                return;
            }
        }


        String placa = solicitarPlaca();

        if (veicDAO.lerPorPlaca(placa) != null) {
            System.out.println("Erro: Já existe um veículo cadastrado com esta placa.");
            return;
        }

        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        int ano = solicitarAno();
        System.out.print("Cor: ");
        String cor = scanner.nextLine();


        veiculo v = new veiculo(placa, marca, modelo, ano, cor, donoAtual);
        veicDAO.criar(v);
        System.out.println("Veículo cadastrado com sucesso: " + v.getPlaca() + " (ID: " + v.getId() + ")");
    }


    //transfere um veiculo para outro proprietario
    private void transferirVeiculo() throws SQLException {
        System.out.println("\n=== TRANSFERÊNCIA DE VEÍCULO ===");
        System.out.print("Placa do veículo a transferir: ");
        String placaDigitada = scanner.nextLine();
        String placaFormatada = validarPlacas.formatarPlaca(placaDigitada);

        veiculo veiculoParaTransferir = veicDAO.lerPorPlaca(placaFormatada);

        if (veiculoParaTransferir == null) {
            System.out.println("Veículo não encontrado com a placa " + placaFormatada + ".");
            return;
        }

        System.out.println("DEBUG: Veículo encontrado. ID: " + veiculoParaTransferir.getId() + ", Placa: " + veiculoParaTransferir.getPlaca());
        if (veiculoParaTransferir.getId() == 0) {
            System.err.println("ERRO LÓGICO: Veículo carregado sem ID válido do banco de dados. Verifique veiculoDAO.lerPorPlaca().");
            return;
        }

        proprietario antigoDono = veiculoParaTransferir.getDono();
        if (antigoDono == null || antigoDono.getId() == 0) {
            System.err.println("ERRO LÓGICO: Proprietário antigo do veículo sem ID válido. Não é possível transferir.");
            return;
        }
        System.out.println("DEBUG: Antigo Dono. ID: " + antigoDono.getId() + ", Nome: " + antigoDono.getNome());


        if (conversorPlacas.ehPlacaAntiga(veiculoParaTransferir.getPlaca())) {
            String novaPlacaMercosul = conversorPlacas.converterParaMercosul(veiculoParaTransferir.getPlaca());
            System.out.println("Placa antiga detectada. Convertendo para Mercosul: " + novaPlacaMercosul);
            veiculoParaTransferir.setPlaca(novaPlacaMercosul);

            veicDAO.atualizar(veiculoParaTransferir);
            System.out.println("Placa atualizada no sistema e no banco de dados para " + veiculoParaTransferir.getPlaca());
        }

        System.out.println("Veículo encontrado: " + veiculoParaTransferir.getPlaca() + " (Dono atual: " + veiculoParaTransferir.getDono().getNome() + ")");


        System.out.print("Nome completo do novo proprietário: ");
        String nomeNovoDono = scanner.nextLine().trim();
        nomeNovoDono = formatarNome(nomeNovoDono);

        String cpfNovoDono;
        do {
            System.out.print("CPF do novo proprietário (apenas números): ");
            cpfNovoDono = scanner.nextLine();
            if (!validadorCPF.validar(cpfNovoDono)) {
                System.out.println("CPF inválido! Digite novamente.");
            }
        } while (!validadorCPF.validar(cpfNovoDono));


        proprietario novoProprietario = propDAO.lerPorCpf(cpfNovoDono);

        if (novoProprietario == null) {
            novoProprietario = new proprietario(nomeNovoDono, cpfNovoDono);
            propDAO.criar(novoProprietario);
            System.out.println("Novo proprietário cadastrado no banco para a transferência: " + novoProprietario.getNome() + " (ID: " + novoProprietario.getId() + ")");
        } else {
            System.out.println("Novo proprietário já existe no banco: " + novoProprietario.getNome() + " (ID: " + novoProprietario.getId() + ")");
            if (novoProprietario.getId() == 0) {
                System.err.println("ERRO LÓGICO: Novo proprietário existente carregado sem ID válido. Verifique proprietarioDAO.lerPorCpf()");
                return;
            }
        }



        if (novoProprietario.getId() == antigoDono.getId()) {
            System.out.println("O novo proprietário não pode ser o mesmo que o antigo proprietário.");
            return;
        }


        veiculoParaTransferir.setDono(novoProprietario);
        veicDAO.atualizar(veiculoParaTransferir);
        System.out.println("DEBUG: Proprietário do veículo (ID: " + veiculoParaTransferir.getId() + ") atualizado no DB para o novo dono (ID: " + novoProprietario.getId() + ").");


        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataTransferencia = dataAtual.format(formatter);
        System.out.println("Data da transferência (gerada automaticamente): " + dataTransferencia);

        try {
            transferencia t = new transferencia(veiculoParaTransferir, antigoDono, novoProprietario, dataTransferencia);
            transfDAO.criar(t);
            System.out.println("Transferência de " + veiculoParaTransferir.getPlaca() + " para " + novoProprietario.getNome() + " em " + dataTransferencia + " realizada e SALVA NO BANCO DE DADOS com sucesso!");
        } catch (SQLException e) {
            System.err.println("ERRO AO SALVAR A TRANSFERÊNCIA NO BANCO DE DADOS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void listarVeiculos() throws SQLException{
        List<veiculo> veiculosDoBanco = veicDAO.listarTodos();

        if (veiculosDoBanco.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado no sistema.");
        } else {
            System.out.println("\n--- Lista de Veículos ---");
            for (veiculo v : veiculosDoBanco) {
                System.out.println(
                        "ID: " + v.getId() +
                                " | Placa: " + v.getPlaca() +
                                " | Marca: " + v.getMarca() +
                                " | Modelo: " + v.getModelo() +
                                " | Ano: " + v.getAno() +
                                " | Cor: " + v.getCor() +
                                " | Dono: " + (v.getDono() != null ? v.getDono().getNome() + " (CPF: " + validadorCPF.formatarCPF(v.getDono().getCpf()) + ")" : "N/A"));
            }
        }
    }

    private void listarTransferencias() {
        System.out.println("\n--- Lista de Transferências ---\n");
        try {
            List<transferencia> transferenciasDoBanco = transfDAO.listarTodos();

            if (transferenciasDoBanco.isEmpty()) {
                System.out.println("Nenhuma transferência registrada no banco de dados.");
            } else {
                for (transferencia t : transferenciasDoBanco) {
                    System.out.println(t.getResumo());
                    System.out.println("------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar transferências do banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void darBaixaVeiculo() {
        System.out.println("\n=== DAR BAIXA EM VEÍCULO ===");
        System.out.print("Digite a placa do veículo a ser baixado: ");
        String placaDigitada = scanner.nextLine();
        String placaFormatada = validarPlacas.formatarPlaca(placaDigitada);

        try {
            veiculo veiculoParaBaixar = veicDAO.lerPorPlaca(placaFormatada);

            if (veiculoParaBaixar == null) {
                System.out.println("Veículo não encontrado com a placa " + placaFormatada + ".");
                return;
            }

            System.out.println("Veículo encontrado: " + veiculoParaBaixar.getPlaca() +
                    " | Dono atual: " + veiculoParaBaixar.getDono().getNome() +
                    " | ID: " + veiculoParaBaixar.getId());

            System.out.print("Tem certeza que deseja dar baixa (remover) no veículo " + veiculoParaBaixar.getPlaca() + "? Esta ação é irreversível. (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                veicDAO.deletar(veiculoParaBaixar.getId()); // Chama o método deletar do veiculoDAO
                System.out.println("Veículo " + veiculoParaBaixar.getPlaca() + " baixado com sucesso!");
            } else {
                System.out.println("Operação de baixa cancelada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar dar baixa no veículo: " + e.getMessage());
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número válido para o ID.");
            scanner.nextLine();
        }
    }
}