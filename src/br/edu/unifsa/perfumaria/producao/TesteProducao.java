
package br.edu.unifsa.perfumaria.producao;

import br.edu.unifsa.perfumaria.produtos.Colonia;
import br.edu.unifsa.perfumaria.produtos.EDP;
import br.edu.unifsa.perfumaria.produtos.EDT;
import br.edu.unifsa.perfumaria.produtos.Parfum;
import br.edu.unifsa.perfumaria.produtos.Perfume;
import br.edu.unifsa.perfumaria.produtos.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteProducao {

    public static void main(String[] args) {
        System.out.println("=== TESTES DO PROJETO ESSENCIA DO POTI ===");

        testarRN01Concentracoes();
        testarRN03Frascos();
        testarRN04Maceracao();
        testarRN05CodigoLote();
        testarRN06PrecoMinimo();
        testarRN07Estoque();
        testarRN08MateriaPrimaVencida();

        System.out.println("\n=== TESTES FINALIZADOS ===");
    }

    private static void testarRN01Concentracoes() {
        System.out.println("\n[RN01] Concentracoes");

        new Parfum("Parfum minimo", custo(), new BigDecimal("20"));
        new Parfum("Parfum maximo", custo(), new BigDecimal("30"));
        new EDP("EDP minimo", custo(), new BigDecimal("15"));
        new EDP("EDP maximo", custo(), new BigDecimal("20"));
        new EDT("EDT minimo", custo(), new BigDecimal("5"));
        new EDT("EDT maximo", custo(), new BigDecimal("15"));
        new Colonia("Colonia minima", custo(), new BigDecimal("2"));
        new Colonia("Colonia maxima", custo(), new BigDecimal("5"));

        passou("Limites permitidos aceitos");

        esperarErro(
                () -> new Parfum("Parfum invalido", custo(),
                        new BigDecimal("19")),
                "Parfum abaixo de 20% rejeitado");

        esperarErro(
                () -> new EDP("EDP invalido", custo(),
                        new BigDecimal("21")),
                "EDP acima de 20% rejeitado");

        esperarErro(
                () -> new EDT("EDT invalido", custo(),
                        new BigDecimal("4")),
                "EDT abaixo de 5% rejeitado");

        esperarErro(
                () -> new Colonia("Colonia invalida", custo(),
                        new BigDecimal("6")),
                "Colonia acima de 5% rejeitada");
    }

    private static void testarRN03Frascos() {
        System.out.println("\n[RN03] Volumes dos frascos");

        new Frasco(30);
        new Frasco(50);
        new Frasco(100);

        passou("Volumes de 30, 50 e 100 ml aceitos");

        esperarErro(
                () -> new Frasco(40),
                "Frasco de 40 ml rejeitado");
    }

    private static void testarRN04Maceracao() {
        System.out.println("\n[RN04] Maceracao e envase");

        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate validade = LocalDate.of(2027, 1, 1);

        testarPrazoMaceracao(
                "Parfum",
                new Parfum("Parfum teste", custo(),
                        new BigDecimal("25")),
                60, inicio, validade);

        testarPrazoMaceracao(
                "EDP",
                new EDP("EDP teste", custo(),
                        new BigDecimal("18")),
                45, inicio, validade);

        testarPrazoMaceracao(
                "EDT",
                new EDT("EDT teste", custo(),
                        new BigDecimal("10")),
                30, inicio, validade);

        testarPrazoMaceracao(
                "Colonia",
                new Colonia("Colonia teste", custo(),
                        new BigDecimal("3")),
                15, inicio, validade);
    }

    private static void testarPrazoMaceracao(
            String tipo, Perfume perfume, int dias,
            LocalDate inicio, LocalDate validade) {

        LoteProducao lote = new LoteProducao(
                "TESTE-" + tipo, perfume, inicio, validade);

        esperarErro(
                () -> lote.envasar(50, 1, inicio.plusDays(dias - 1)),
                tipo + ": envase antes de " + dias + " dias rejeitado");

        lote.envasar(50, 1, inicio.plusDays(dias));

        if (lote.isEnvasado()
                && lote.getUnidadesEnvasadas() == 1
                && lote.getFrasco().getVolumeMl() == 50) {
            passou(tipo + ": envase liberado apos " + dias + " dias");
        } else {
            falhou(tipo + ": envase nao registrado corretamente");
        }
    }

    private static void testarRN05CodigoLote() {
        System.out.println("\n[RN05] Codigo do lote");

        LoteProducao lote = new LoteProducao(
                "LOTE-RN05",
                new EDT("Perfume lote", custo(), new BigDecimal("10")),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2027, 1, 1));

        if ("LOTE-RN05".equals(lote.getCodigo())) {
            passou("Lote criado com codigo definido");
        } else {
            falhou("Codigo do lote incorreto");
        }
    }

    private static void testarRN06PrecoMinimo() {
        System.out.println("\n[RN06] Preco minimo");

        Produto produto = criarEDT("Perfume para venda");
        EstoquePerfumes estoque = new EstoquePerfumes();

        esperarErro(
                () -> estoque.registrarVenda(
                        produto, 1, new BigDecimal("139.99")),
                "Venda abaixo do minimo de R$ 140 rejeitada");

        if (estoque.consultarEstoque(produto) == 0
                && estoque.getVendas().isEmpty()) {
            passou("Venda recusada sem alterar estoque ou vendas");
        } else {
            falhou("Dados alterados apos venda recusada");
        }
    }

    private static void testarRN07Estoque() {
        System.out.println("\n[RN07] Controle de estoque");

        LocalDate inicio = LocalDate.of(2026, 1, 1);

        Produto produto = criarEDT("Perfume estoque");
        LoteProducao lote = new LoteProducao(
                "LOTE-RN07",
                (Perfume) produto,
                inicio,
                LocalDate.of(2027, 1, 1));

        lote.envasar(50, 2, inicio.plusDays(30));

        EstoquePerfumes estoque = new EstoquePerfumes();
        estoque.registrarEnvase(produto, lote);

        if (estoque.consultarEstoque(produto) == 2) {
            passou("Duas unidades registradas no estoque");
        } else {
            falhou("Quantidade inicial do estoque incorreta");
        }

        esperarErro(
                () -> estoque.registrarVenda(
                        produto, 3, new BigDecimal("140.00")),
                "Venda de 3 unidades com apenas 2 em estoque rejeitada");

        if (estoque.consultarEstoque(produto) == 2
                && estoque.getVendas().isEmpty()) {
            passou("Estoque preservado apos recusa da venda");
        } else {
            falhou("Estoque alterado indevidamente");
        }
    }

    private static void testarRN08MateriaPrimaVencida() {
        System.out.println("\n[RN08] Materia-prima vencida");

        LocalDate validade = LocalDate.of(2026, 1, 10);
        MateriaPrima materia = new MateriaPrima(
                "Essencia de teste", 100, validade);

        esperarErro(
                () -> materia.consumir(
                        10, LocalDate.of(2026, 1, 11)),
                "Materia-prima vencida rejeitada");

        if (materia.getQuantidadeDisponivel() == 100) {
            passou("Quantidade preservada apos recusa");
        } else {
            falhou("Quantidade alterada indevidamente");
        }
    }

    private static Produto criarEDT(String nome) {
        return new EDT(nome, custo(), new BigDecimal("10"));
    }

    private static BigDecimal custo() {
        return new BigDecimal("100.00");
    }

    private static void esperarErro(Runnable acao, String descricao) {
        try {
            acao.run();
        } catch (IllegalArgumentException | IllegalStateException erro) {
            passou(descricao);
            return;
        }

        falhou(descricao + " (a operacao foi aceita)");
    }

    private static void passou(String mensagem) {
        System.out.println("PASSOU: " + mensagem);
    }

    private static void falhou(String mensagem) {
        throw new AssertionError("FALHOU: " + mensagem);
    }
}
