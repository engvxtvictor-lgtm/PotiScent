package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class EDT extends Perfume {

    public EDT(String nome, BigDecimal custoProducao,
               BigDecimal concentracaoEssencia) {
        super(nome, custoProducao, concentracaoEssencia);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 30;
    }

    @Override
    public BigDecimal getConcentracaoMinima() {
        return new BigDecimal("5");
    }

    @Override
    public BigDecimal getConcentracaoMaxima() {
        return new BigDecimal("15");
    }
}
