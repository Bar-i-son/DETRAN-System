package detran.SistemaBase;

public class proprietario {

    /*
    * Atributos
    * */

    private int id;

    String nome;
    String cpf;

    /*
    * Construtor padrão
    * */

    public proprietario() {
    }

    /*
    * Construtor com parametros
    * */

    public proprietario(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
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


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    @Override
    public String toString() {
        return "Proprietario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}