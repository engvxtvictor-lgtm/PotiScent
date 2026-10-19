package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class EDP extends Perfume {

    public EDP(String nome, BigDecimal custoProducao,
               BigDecimal concentracaoEssencia) {
        super(nome, custoProducao, concentracaoEssencia);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 45;
    }

    @Override
    public BigDecimal getConcentracaoMinima() {
        return new BigDecimal("15");
    }

    @Override
    public BigDecimal getConcentracaoMaxima() {
        return new BigDecimal("20");
    }
}
