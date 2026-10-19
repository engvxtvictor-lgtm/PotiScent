
package br.edu.unifsa.perfumaria;

import java.math.BigDecimal;
import java.util.Arrays;

import br.edu.unifsa.perfumaria.produtos.CamadaOlfativa;
import br.edu.unifsa.perfumaria.produtos.FormulaOlfativa;
import br.edu.unifsa.perfumaria.produtos.IngredienteFormula;

public class Simulacao {

    public static void main(String[] args) {
        System.out.println("=== Essência do Poti ===");
        System.out.println("Testes da RN02 - Fórmula olfativa");

        testarFormulaValida();
        testarSomaInvalidaDasCamadas();
        testarSomaInvalidaDosIngredientes();
        testarCamadaComPercentualZero();
        testarCamadaComPercentualNegativo();
        testarCamadaComIngredientesVazios();
        testarIngredienteComPercentualNegativo();
        testarIngredienteAcimaDe100();
    }

    private static void testarFormulaValida() {
        System.out.println("\nTeste 1: fórmula válida");

        try {
            CamadaOlfativa saida = criarCamada(
                    "saída", "25",
                    new String[]{"Bergamota", "Limão"},
                    new String[]{"60", "40"});

            CamadaOlfativa coracao = criarCamada(
                    "coração", "35",
                    new String[]{"Rosa", "Jasmim"},
                    new String[]{"50", "50"});

            CamadaOlfativa fundo = criarCamada(
                    "fundo", "40",
                    new String[]{"Cedro", "Baunilha"},
                    new String[]{"70", "30"});

            FormulaOlfativa formula =
                    new FormulaOlfativa(saida, coracao, fundo);

            System.out.println("PASSOU: fórmula aceita.");
            System.out.println(formula);

        } catch (IllegalArgumentException e) {
            System.out.println("FALHOU: " + e.getMessage());
        }
    }

    private static void testarSomaInvalidaDasCamadas() {
        System.out.println("\nTeste 2: camadas somam 90%");

        try {
            CamadaOlfativa saida = criarCamada(
                    "saída", "20",
                    new String[]{"Bergamota"},
                    new String[]{"100"});

            CamadaOlfativa coracao = criarCamada(
                    "coração", "30",
                    new String[]{"Rosa"},
                    new String[]{"100"});

            CamadaOlfativa fundo = criarCamada(
                    "fundo", "40",
                    new String[]{"Cedro"},
                    new String[]{"100"});

            new FormulaOlfativa(saida, coracao, fundo);

            System.out.println("FALHOU: fórmula inválida foi aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: fórmula rejeitada.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    private static void testarSomaInvalidaDosIngredientes() {
        System.out.println("\nTeste 3: ingredientes somam 90%");

        try {
            criarCamada(
                    "saída", "25",
                    new String[]{"Bergamota", "Limão"},
                    new String[]{"50", "40"});

            System.out.println("FALHOU: camada inválida foi aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: camada rejeitada.");
            System.out.println("Motivo: " + e.getMessage());
        }
    }

    private static CamadaOlfativa criarCamada(
            String tipo,
            String percentualCamada,
            String[] nomes,
            String[] percentuais) {

        IngredienteFormula[] ingredientes =
                new IngredienteFormula[nomes.length];

        for (int i = 0; i < nomes.length; i++) {
            ingredientes[i] = new IngredienteFormula(
                    nomes[i],
                    new BigDecimal(percentuais[i]));
        }

        return new CamadaOlfativa(
                tipo,
                new BigDecimal(percentualCamada),
                Arrays.asList(ingredientes));
    }
    
private static void testarCamadaComPercentualZero() {
    System.out.println("\n[RN02] Camada com percentual zero");

    try {
        criarCamada(
                "saída", "0",
                new String[]{"Bergamota"},
                new String[]{"100"});

        System.out.println("ERRO: camada com 0% foi aceita.");

    } catch (IllegalArgumentException e) {
        System.out.println("RECUSADO: " + e.getMessage());
    }
}

private static void testarCamadaComPercentualNegativo() {
    System.out.println("\n[RN02] Camada com percentual negativo");

    try {
        criarCamada(
                "saída", "-10",
                new String[]{"Bergamota"},
                new String[]{"100"});

        System.out.println("ERRO: camada com percentual negativo foi aceita.");

    } catch (IllegalArgumentException e) {
        System.out.println("RECUSADO: " + e.getMessage());
    }
}

private static void testarCamadaComIngredientesVazios() {
    System.out.println("\n[RN02] Camada sem ingredientes");

    try {
        criarCamada(
                "saída", "25",
                new String[]{},
                new String[]{});

        System.out.println("ERRO: camada sem ingredientes foi aceita.");

    } catch (IllegalArgumentException e) {
        System.out.println("RECUSADO: " + e.getMessage());
    }
}

private static void testarIngredienteComPercentualNegativo() {
    System.out.println("\n[RN02] Ingrediente com percentual negativo");

    try {
        new IngredienteFormula(
                "Bergamota",
                new BigDecimal("-10"));

        System.out.println("ERRO: ingrediente com percentual negativo foi aceito.");

    } catch (IllegalArgumentException e) {
        System.out.println("RECUSADO: " + e.getMessage());
    }
}
    private static void testarIngredienteAcimaDe100() {
    System.out.println("\n[RN02] Ingrediente acima de 100%");

    try {
        new IngredienteFormula(
                "Bergamota",
                new BigDecimal("110"));

        System.out.println("ERRO: ingrediente acima de 100% foi aceito.");

    } catch (IllegalArgumentException e) {
        System.out.println("RECUSADO: " + e.getMessage());
    }
}

}
