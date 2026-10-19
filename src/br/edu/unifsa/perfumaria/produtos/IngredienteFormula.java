package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class IngredienteFormula {

    private String nome;
    private BigDecimal percentualNaCamada;

    public IngredienteFormula(String nome, BigDecimal percentualNaCamada) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "O nome do ingrediente não pode ser vazio."
            );
        }

        if (percentualNaCamada == null) {
            throw new IllegalArgumentException(
                "O percentual do ingrediente não pode ser nulo."
            );
        }

        if (percentualNaCamada.compareTo(BigDecimal.ZERO) <= 0
                || percentualNaCamada.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException(
                "O percentual do ingrediente deve ser maior que 0 e menor ou igual a 100."
            );
        }

        this.nome = nome.trim();
        this.percentualNaCamada = percentualNaCamada;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPercentualNaCamada() {
        return percentualNaCamada;
    }

    @Override
    public String toString() {
        return "IngredienteFormula{" +
                "nome='" + nome + '\'' +
                ", percentualNaCamada=" + percentualNaCamada +
                '}';
    }
}