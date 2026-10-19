package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class Colonia extends Perfume {

    public Colonia(String nome, BigDecimal custoProducao,
                   BigDecimal concentracaoEssencia) {
        super(nome, custoProducao, concentracaoEssencia);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 15;
    }

    @Override
    public BigDecimal getConcentracaoMinima() {
        return new BigDecimal("2");
    }

    @Override
    public BigDecimal getConcentracaoMaxima() {
        return new BigDecimal("5");
    }
}
