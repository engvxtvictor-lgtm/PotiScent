package br.edu.unifsa.perfumaria.pessoas;

public class Perfumista {

    private String nome;
    private String nivel;

    public Perfumista(String nome, String nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public String getNivel() {
        return nivel;
    }

    public boolean podeAssinarParfum() {
        return !nivel.equalsIgnoreCase("Junior");
    }
}