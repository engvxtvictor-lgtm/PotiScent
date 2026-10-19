
package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class VelaAromatica extends Produto {

    private final BigDecimal pesoCeraGramas;
    private final BigDecimal percentualFragrancia;

    public VelaAromatica(
            String nome,
            BigDecimal custoProducao,
            BigDecimal pesoCeraGramas,
            BigDecimal percentualFragrancia) {

        super(nome, custoProducao);

        if (pesoCeraGramas == null
                || pesoCeraGramas.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O peso da cera deve ser positivo.");
        }

        if (percentualFragrancia == null
                || percentualFragrancia.compareTo(BigDecimal.ZERO) < 0
                || percentualFragrancia.compareTo(new BigDecimal("10")) > 0) {
            throw new IllegalArgumentException(
                    "[RN10] A fragrância deve estar entre 0% e 10%.");
        }

        this.pesoCeraGramas = pesoCeraGramas;
        this.percentualFragrancia = percentualFragrancia;
    }

    public BigDecimal getPesoCeraGramas() {
        return pesoCeraGramas;
    }

    public BigDecimal getPercentualFragrancia() {
        return percentualFragrancia;
    }

    public BigDecimal calcularFragranciaGramas() {
        return pesoCeraGramas
                .multiply(percentualFragrancia)
                .divide(new BigDecimal("100"));
    }
}
