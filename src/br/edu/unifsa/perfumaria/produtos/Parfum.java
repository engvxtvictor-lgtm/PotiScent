package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class Parfum extends Perfume {

    public Parfum(String nome, BigDecimal custoProducao) {
        super(nome, custoProducao);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 60;
    }
}
