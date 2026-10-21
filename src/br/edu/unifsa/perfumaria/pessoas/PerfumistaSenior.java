
package br.edu.unifsa.perfumaria.pessoas;

public class PerfumistaSenior extends Perfumista {

    public PerfumistaSenior(String nome) {
        super(nome);
    }

    @Override
    public boolean podeAssinarParfum() {
        return true;
    }

    @Override
    public String getCargo() {
        return "Perfumista Sênior";
    }
}
