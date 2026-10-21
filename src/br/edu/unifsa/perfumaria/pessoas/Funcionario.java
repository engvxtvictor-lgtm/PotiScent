package br.edu.unifsa.perfumaria.pessoas;

public abstract class Funcionario {
    private final String nome;

    public Funcionario(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome do funcionário é obrigatório."
            );
        }
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public abstract String getCargo();

    @Override
    public String toString() {
        return getCargo() + "{nome='" + nome + "'}";
    }
}