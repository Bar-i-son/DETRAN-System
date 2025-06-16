package detran.SistemaBase;


/*
* Informações sobre o veiculo
* */

public class veiculo {

    private int id;

    /*
    * Atributos
    * */

    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;
    private proprietario dono;
    proprietario novoDono;


    /*
    * Construtor padrão
    * */

    public veiculo() {
    }

    /*
    * Construtor com parâmetros
    * */

    public veiculo(String placa, String marca, String modelo, int ano, String cor, proprietario dono) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.dono = dono;
    }



    //construtor com id
    public veiculo(int id) {
        this.id = id;
    }

    /*
    * GETTERS e SETTERS
    * */

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public proprietario getDono() {
        return dono;
    }
    public void setDono(proprietario dono) {
        this.dono = dono;
    }


    public proprietario getNovoDono() {
        return novoDono;
    }
    public void setNovoDono(proprietario novoDono) {
        this.novoDono = novoDono;
    }


    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }


    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }


    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }



    @Override
    public String toString() {
        return "Veiculo{" +
                "id=" + id +
                ", placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", ano=" + ano +
                ", cor='" + cor + '\'' +
                ", dono=" + (dono != null ? dono.getNome() : "N/A") + // Mostra o nome do dono
                '}';
    }
}