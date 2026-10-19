package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class EDP extends Perfume {

    public EDP(String nome, BigDecimal custoProducao) {
        super(nome, custoProducao);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 45;
    }
}
