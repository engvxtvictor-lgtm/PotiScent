
package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;
import br.edu.unifsa.perfumaria.pessoas.Perfumista;

public class FormulaOlfativa {

    private CamadaOlfativa saida;
    private CamadaOlfativa coracao;
    private CamadaOlfativa fundo;

    public FormulaOlfativa(
            CamadaOlfativa saida,
            CamadaOlfativa coracao,
            CamadaOlfativa fundo) {

        if (saida == null || coracao == null || fundo == null) {
            throw new IllegalArgumentException(
                    "A fórmula deve possuir as três camadas olfativas.");
        }

        validarTipo(saida, "saída");
        validarTipo(coracao, "coração");
        validarTipo(fundo, "fundo");

        BigDecimal soma = saida.getPercentualNaFormula()
                .add(coracao.getPercentualNaFormula())
                .add(fundo.getPercentualNaFormula());

        if (soma.compareTo(new BigDecimal("100")) != 0) {
            throw new IllegalArgumentException(
                    "Os percentuais das três camadas devem somar exatamente 100%.");
        }

        this.saida = saida;
        this.coracao = coracao;
        this.fundo = fundo;
    }

    public void assinar(Perfumista perfumista, Produto produto) {
        if (perfumista == null || produto == null) {
            throw new IllegalArgumentException(
                    "Perfumista e produto são obrigatórios.");
        }

        if (produto instanceof Parfum && !perfumista.podeAssinarParfum()) {
            throw new IllegalArgumentException(
                    "Perfumista júnior não pode assinar fórmula de Parfum.");
        }
    }

    private void validarTipo(CamadaOlfativa camada, String tipoEsperado) {
        if (!camada.getTipo().equalsIgnoreCase(tipoEsperado)) {
            throw new IllegalArgumentException(
                    "A camada deveria ser do tipo: " + tipoEsperado + ".");
        }
    }

    public CamadaOlfativa getSaida() {
        return saida;
    }

    public CamadaOlfativa getCoracao() {
        return coracao;
    }

    public CamadaOlfativa getFundo() {
        return fundo;
    }

    @Override
    public String toString() {
        return "FormulaOlfativa{" +
                "saida=" + saida +
                ", coracao=" + coracao +
                ", fundo=" + fundo +
                '}';
    }
}
