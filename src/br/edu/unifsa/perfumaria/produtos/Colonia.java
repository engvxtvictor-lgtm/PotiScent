package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class Colonia extends Perfume {

    public Colonia(String nome, BigDecimal custoProducao) {
        super(nome, custoProducao);
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 15;
    }
}
