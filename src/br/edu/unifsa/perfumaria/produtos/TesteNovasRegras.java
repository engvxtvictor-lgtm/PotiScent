
package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class TesteNovasRegras {

    public static void main(String[] args) {
        testarVela();
        testarDifusor();
        testarPerfumePersonalizado();
    }

    private static void testarVela() {
        System.out.println("\n=== RN10: VELA ===");

        try {
            VelaAromatica vela = new VelaAromatica(
                    "Vela de Lavanda",
                    new BigDecimal("20"),
                    new BigDecimal("200"),
                    new BigDecimal("10"));

            System.out.println("PASSOU: vela com 10% aceita.");
            System.out.println("Fragrância: "
                    + vela.calcularFragranciaGramas() + " g");

        } catch (IllegalArgumentException e) {
            System.out.println("FALHOU: " + e.getMessage());
        }

        try {
            new VelaAromatica(
                    "Vela inválida",
                    new BigDecimal("20"),
                    new BigDecimal("200"),
                    new BigDecimal("11"));

            System.out.println("FALHOU: vela acima de 10% aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: vela acima de 10% rejeitada.");
        }
    }

    private static void testarDifusor() {
        System.out.println("\n=== RN10: DIFUSOR ===");

        new DifusorAromatico(
                "Difusor",
                new BigDecimal("30"),
                4);

        System.out.println("PASSOU: difusor com 4 varetas aceito.");

        try {
            new DifusorAromatico(
                    "Difusor inválido",
                    new BigDecimal("30"),
                    11);

            System.out.println("FALHOU: difusor com 11 varetas aceito.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: 11 varetas rejeitadas.");
        }
    }

    private static void testarPerfumePersonalizado() {
        System.out.println("\n=== RN11: PERSONALIZADO ===");

        new PerfumePersonalizado(
                "Perfume personalizado",
                new BigDecimal("50"),
                new BigDecimal("15"),
                "Meu Perfume");

        System.out.println("PASSOU: gravação com até 20 caracteres aceita.");

        try {
            new PerfumePersonalizado(
                    "Perfume personalizado",
                    new BigDecimal("50"),
                    new BigDecimal("15"),
                    "Esta gravação tem mais de vinte");

            System.out.println("FALHOU: gravação acima de 20 aceita.");

        } catch (IllegalArgumentException e) {
            System.out.println("PASSOU: gravação acima de 20 rejeitada.");
        }

        PerfumePersonalizado perfume = new PerfumePersonalizado(
                "Perfume personalizado",
                new BigDecimal("50"),
                new BigDecimal("15"),
                "Meu Perfume");

        if (!perfume.permiteDevolucao()) {
            System.out.println("PASSOU: produto indica que não permite devolução.");
        } else {
            System.out.println("FALHOU: produto permite devolução.");
        }
    }
}
