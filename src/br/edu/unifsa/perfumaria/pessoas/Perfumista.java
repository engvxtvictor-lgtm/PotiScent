package br.edu.unifsa.perfumaria.pessoas;

public abstract class Perfumista extends Funcionario {

    public Perfumista(String nome) {
        super(nome);
    }

    public abstract boolean podeAssinarParfum();

    @Override
    public String getCargo() {
        return "Perfumista";
    }
}