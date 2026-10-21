package br.edu.unifsa.perfumaria.produtos;

public interface Personalizavel {
    String getGravacao();

    boolean permiteDevolucao();

    void devolver();
}