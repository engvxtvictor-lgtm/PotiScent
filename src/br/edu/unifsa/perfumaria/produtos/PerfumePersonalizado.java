package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class PerfumePersonalizado extends Perfume
        implements Precificavel, Personalizavel {

    private final String gravacao;

    public PerfumePersonalizado(
            String nome,
            BigDecimal custoProducao,
            BigDecimal concentracaoEssencia,
            String gravacao) {

        super(nome, custoProducao, concentracaoEssencia);

        if (gravacao == null || gravacao.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "[RN11] A gravação é obrigatória.");
        }

        if (gravacao.length() > 20) {
            throw new IllegalArgumentException(
                    "[RN11] A gravação não pode ultrapassar 20 caracteres.");
        }

        this.gravacao = gravacao.trim();
    }

    @Override
    public String getGravacao() {
        return gravacao;
    }

    @Override
    public boolean permiteDevolucao() {
        return false;
    }

    @Override
    public void devolver() {
        throw new IllegalStateException(
                "[RN11] Perfumes personalizados não podem ser devolvidos.");
    }

    @Override
    public int getTempoMinimoMaceracaoDias() {
        return 45;
    }

    @Override
    public BigDecimal getConcentracaoMinima() {
        return new BigDecimal("15");
    }

    @Override
    public BigDecimal getConcentracaoMaxima() {
        return new BigDecimal("20");
    }

    @Override
    public String toString() {
        return super.toString()
                .replaceFirst("\\}$", "")
                + ", gravacao='" + gravacao + "'}";
    }
}