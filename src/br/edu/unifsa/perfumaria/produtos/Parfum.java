package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class Parfum extends Perfume {

    public Parfum(String nome, BigDecimal custoProducao,
                  BigDecimal concentracaoEssencia) {
        super(nome, custoProducao, concentracaoEssencia);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 60;
    }

    @Override
    public BigDecimal getConcentracaoMinima() {
        return new BigDecimal("20");
    }

    @Override
    public BigDecimal getConcentracaoMaxima() {
        return new BigDecimal("30");
    }
}
