package br.edu.unifsa.perfumaria.pessoas;

public class PerfumistaJunior extends Perfumista {

    public PerfumistaJunior(String nome) {
        super(nome);
    }

    @Override
    public boolean podeAssinarParfum() {
        return false;
    }

    @Override
    public String getCargo() {
        return "Perfumista Júnior";
    }
}