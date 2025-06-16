package detran.SistemaBase;

public class transferencia {

    /*
    * Atributos relacionados a transferencia do veiculo
    * */

    private int id;
    private veiculo veiculo;
    private proprietario antigoDono;
    private proprietario novoDono;
    private String dataTransferencia;

    /*
    * Construtor principal
    * */

    public transferencia(veiculo veiculo, proprietario antigoDono, proprietario novoDono, String dataTransferencia) {
        this.veiculo = veiculo;
        this.antigoDono = antigoDono;
        this.novoDono = novoDono;
        this.dataTransferencia = dataTransferencia;
    }

    /*
    * GETTERS
    * */

    public int getId() {
        return id;
    }

    public veiculo getVeiculo() {
        return veiculo;
    }
    public proprietario getAntigoDono() {
        return antigoDono;
    }
    public proprietario getNovoDono() {
        return novoDono;
    }
    public String getDataTransferencia() {
        return dataTransferencia;
    }

    /*
    * SETTERS
    * */

    public void setId(int id) {
        this.id = id;
    }
    public void setVeiculo(veiculo veiculo) {
        this.veiculo = veiculo;
    }
    public void setAntigoDono(proprietario antigoDono) {
        this.antigoDono = antigoDono;
    }
    public void setNovoDono(proprietario novoDono) {
        this.novoDono = novoDono;
    }
    public void setDataTransferencia(String dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }

    public String getResumo() {
        return "ID Transferência: " + this.id +
                "\n  Veículo: " + this.veiculo.getPlaca() +
                " (" + this.veiculo.getMarca() + " " + this.veiculo.getModelo() + ")" +
                "\n  De: " + this.antigoDono.getNome() + " (CPF: " + this.antigoDono.getCpf() + ")" +
                "\n  Para: " + this.novoDono.getNome() + " (CPF: " + this.novoDono.getCpf() + ")" +
                "\n  Data: " + this.dataTransferencia;


    }
}