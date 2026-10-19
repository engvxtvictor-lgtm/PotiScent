
package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CamadaOlfativa {

    private String tipo;
    private BigDecimal percentualNaFormula;
    private List<IngredienteFormula> ingredientes;

    public CamadaOlfativa(
            String tipo,
            BigDecimal percentualNaFormula,
            List<IngredienteFormula> ingredientes) {

        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O tipo da camada não pode ser vazio.");
        }

        if (percentualNaFormula == null
                || percentualNaFormula.compareTo(BigDecimal.ZERO) <= 0
                || percentualNaFormula.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException(
                    "O percentual da camada deve estar entre 0 e 100.");
        }

        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new IllegalArgumentException(
                    "A camada deve possuir pelo menos um ingrediente.");
        }

        BigDecimal soma = BigDecimal.ZERO;

        for (IngredienteFormula ingrediente : ingredientes) {
            if (ingrediente == null) {
                throw new IllegalArgumentException(
                        "A lista não pode conter ingredientes nulos.");
            }

            soma = soma.add(ingrediente.getPercentualNaCamada());
        }

        if (soma.compareTo(new BigDecimal("100")) != 0) {
            throw new IllegalArgumentException(
                    "Os percentuais dos ingredientes da camada devem somar 100%.");
        }

        this.tipo = tipo.trim();
        this.percentualNaFormula = percentualNaFormula;
        this.ingredientes = new ArrayList<>(ingredientes);
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getPercentualNaFormula() {
        return percentualNaFormula;
    }

    public List<IngredienteFormula> getIngredientes() {
        return Collections.unmodifiableList(ingredientes);
    }

    @Override
    public String toString() {
        return "CamadaOlfativa{" +
                "tipo='" + tipo + '\'' +
                ", percentualNaFormula=" + percentualNaFormula +
                ", ingredientes=" + ingredientes +
                '}';
    }
}
