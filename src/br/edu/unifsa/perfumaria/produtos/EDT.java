package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class EDT extends Perfume {

    public EDT(String nome, BigDecimal custoProducao) {
        super(nome, custoProducao);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 30;
    }
}
