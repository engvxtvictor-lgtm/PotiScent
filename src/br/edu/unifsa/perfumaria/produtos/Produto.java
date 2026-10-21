package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public abstract class Produto implements Precificavel {

    private final String nome;
    private final BigDecimal custoProducao;

    public Produto(String nome, BigDecimal custoProducao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "O nome do produto não pode ser vazio.");
        }

        if (custoProducao == null
                || custoProducao.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "O custo de produção não pode ser nulo nem negativo.");
        }

        this.nome = nome.trim();
        this.custoProducao = custoProducao;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getCustoProducao() {
        return custoProducao;
    }

    @Override
    public BigDecimal calcularPrecoMinimo() {
        return custoProducao.multiply(new BigDecimal("1.40"));
    }

    protected String formatarDadosComuns() {
        return "nome='" + nome + "', custoProducao=" + custoProducao;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{" + formatarDadosComuns() + "}";
    }
}