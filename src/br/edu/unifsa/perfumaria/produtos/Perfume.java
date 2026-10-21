package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public abstract class Perfume extends Produto {

    private final BigDecimal concentracaoEssencia;

    public Perfume(String nome, BigDecimal custoProducao,
                   BigDecimal concentracaoEssencia) {
        super(nome, custoProducao);

        if (concentracaoEssencia == null) {
            throw new IllegalArgumentException(
                    "A concentração de essência não pode ser nula.");
        }

        if (concentracaoEssencia.compareTo(getConcentracaoMinima()) < 0
                || concentracaoEssencia.compareTo(getConcentracaoMaxima()) > 0) {
            throw new IllegalArgumentException(
                    "[RN01] Concentração fora da faixa permitida para "
                            + getClass().getSimpleName() + ".");
        }

        this.concentracaoEssencia = concentracaoEssencia;
    }

    public abstract int getTempoMinimoMaceracaoDias();

    public abstract BigDecimal getConcentracaoMinima();

    public abstract BigDecimal getConcentracaoMaxima();

    public BigDecimal getConcentracaoEssencia() {
        return concentracaoEssencia;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{" + formatarDadosComuns()
                + ", concentracaoEssencia=" + concentracaoEssencia
                + "%, maceracaoMinima="
                + getTempoMinimoMaceracaoDias() + " dias}";
    }
}