
package br.edu.unifsa.perfumaria;

import java.math.BigDecimal;
import java.util.Arrays;

import br.edu.unifsa.perfumaria.pessoas.Perfumista;
import br.edu.unifsa.perfumaria.produtos.CamadaOlfativa;
import br.edu.unifsa.perfumaria.produtos.FormulaOlfativa;
import br.edu.unifsa.perfumaria.produtos.IngredienteFormula;
import br.edu.unifsa.perfumaria.produtos.Parfum;

public class Simulacao {

    public static void main(String[] args) {
        System.out.println("=== Essencia do Poti ===");

        testarFormulaValida();
        testarSomaInvalidaDasCamadas();
        testarSomaInvalidaDosIngredientes();
        testarCamadaComPercentualZero();
        testarCamadaComPercentualNegativo();
        testarCamadaComIngredientesVazios();
        testarIngredienteComPercentualNegativo();
        testarIngredienteAcimaDe100();

        testarAssinaturaParfumJunior();
        testarAssinaturaParfumSenior();
    }

    private static void testarFormulaValida() {
        System.out.println("\n[RN02] Formula valida");

        try {
            FormulaOlfativa formula = criarFormulaValida();
            System.out.println("PASSOU: " + formula);
        } catch (IllegalArgumentException e) {
            System.out.println("FALHOU: " + e.getMessage());
        }
    }

    private static void testarSomaInvalidaDasCamadas() {
        System.out.println("\n[RN02] Camadas somam 90%");

        try {
            CamadaOlfativa saída = criarCamada(
                    "saída", "20",
                    new String[]{"Bergamota"},
                    new String[]{"100"});

            CamadaOlfativa coração = criarCamada(
                    "coração", "30",
                    new String[]{"Rosa"},
                    new String[]{"100"});

            CamadaOlfativa fundo = criarCamada(
                    "fundo", "40",
                    new String[]{"Cedro"},
                    new String[]{"100"});

            new FormulaOlfativa(saída, coração, fundo);
            System.out.println("FALHOU: formula invalida foi aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: formula rejeitada.");
        }
    }

    private static void testarSomaInvalidaDosIngredientes() {
        System.out.println("\n[RN02] Ingredientes somam 90%");

        try {
            criarCamada(
                    "saída", "25",
                    new String[]{"Bergamota", "Limao"},
                    new String[]{"50", "40"});

            System.out.println("FALHOU: camada invalida foi aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: camada rejeitada.");
        }
    }

    private static void testarCamadaComPercentualZero() {
        testarCamadaInvalida("Camada com percentual zero", "0");
    }

    private static void testarCamadaComPercentualNegativo() {
        testarCamadaInvalida("Camada com percentual negativo", "-10");
    }

    private static void testarCamadaInvalida(String descricao, String percentual) {
        System.out.println("\n[RN02] " + descricao);

        try {
            criarCamada(
                    "saída", percentual,
                    new String[]{"Bergamota"},
                    new String[]{"100"});

            System.out.println("FALHOU: camada invalida foi aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: camada rejeitada.");
        }
    }

    private static void testarCamadaComIngredientesVazios() {
        System.out.println("\n[RN02] Camada sem ingredientes");

        try {
            criarCamada(
                    "saída", "25",
                    new String[]{},
                    new String[]{});

            System.out.println("FALHOU: camada vazia foi aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: camada rejeitada.");
        }
    }

    private static void testarIngredienteComPercentualNegativo() {
        testarIngredienteInvalido("Percentual negativo", "-10");
    }

    private static void testarIngredienteAcimaDe100() {
        testarIngredienteInvalido("Percentual acima de 100", "110");
    }

    private static void testarIngredienteInvalido(
            String descricao, String percentual) {
        System.out.println("\n[RN02] Ingrediente: " + descricao);

        try {
            new IngredienteFormula(
                    "Bergamota",
                    new BigDecimal(percentual));

            System.out.println("FALHOU: ingrediente invalido foi aceito.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: ingrediente rejeitado.");
        }
    }

    private static void testarAssinaturaParfumJunior() {
        System.out.println("\n[RN09] Junior tentando assinar Parfum");

        FormulaOlfativa formula = criarFormulaValida();
        Perfumista junior = new Perfumista("Joao", "Junior");
        Parfum parfum = criarParfum();

        try {
            formula.assinar(junior, parfum);
            System.out.println("FALHOU: assinatura de junior foi permitida.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: assinatura de junior bloqueada.");
        }
    }

    private static void testarAssinaturaParfumSenior() {
        System.out.println("\n[RN09] Senior tentando assinar Parfum");

        FormulaOlfativa formula = criarFormulaValida();
        Perfumista senior = new Perfumista("Maria", "Senior");
        Parfum parfum = criarParfum();

        try {
            formula.assinar(senior, parfum);
            System.out.println("PASSOU: assinatura de senior permitida.");

        } catch (IllegalArgumentException e) {
            System.out.println("FALHOU: " + e.getMessage());
        }
    }

    private static Parfum criarParfum() {
        return new Parfum(
                "Poti Intenso",
                new BigDecimal("100"),
                new BigDecimal("25"));
    }

    private static FormulaOlfativa criarFormulaValida() {
        CamadaOlfativa saída = criarCamada(
                "saída", "25",
                new String[]{"Bergamota", "Limao"},
                new String[]{"60", "40"});

        CamadaOlfativa coração = criarCamada(
                "coração", "35",
                new String[]{"Rosa", "Jasmim"},
                new String[]{"50", "50"});

        CamadaOlfativa fundo = criarCamada(
                "fundo", "40",
                new String[]{"Cedro", "Baunilha"},
                new String[]{"70", "30"});

        return new FormulaOlfativa(saída, coração, fundo);
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
}
