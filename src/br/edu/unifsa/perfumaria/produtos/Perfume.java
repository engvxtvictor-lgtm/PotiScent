package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public abstract class Perfume extends Produto {

    public Perfume(String nome, BigDecimal custoProducao) {
        super(nome, custoProducao);
    }

    public abstract int getTempoMinimoMaceracaoDias();

}
