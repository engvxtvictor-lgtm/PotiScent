
package br.edu.unifsa.perfumaria.producao;

import br.edu.unifsa.perfumaria.produtos.EDT;
import br.edu.unifsa.perfumaria.produtos.Produto;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TesteProducao {

    public static void main(String[] args) {
        System.out.println("=== Testes do modulo producao ===");

        testarFrascos();
        testarMateriaPrimaVencida();
        testarCodigoDoLote();
        testarMaceracaoEEnvase();
        testarPrecoMinimo();
        testarEstoqueInsuficiente();

        System.out.println("\n=== Testes finalizados ===");
    }

    private static void testarFrascos() {
        System.out.println("\n[RN03] Validacao dos frascos");

        new Frasco(30);
        new Frasco(50);
        new Frasco(100);
        System.out.println("PASSOU: volumes permitidos aceitos.");

        esperarErro(() -> new Frasco(40),
                "Frasco de 40 ml rejeitado");
    }

    private static void testarMateriaPrimaVencida() {
        System.out.println("\n[RN08] Materia-prima vencida");

        LocalDate validade = LocalDate.of(2026, 1, 10);
        MateriaPrima materia = new MateriaPrima(
                "Essencia de teste", 100, validade);

        esperarErro(
                () -> materia.consumir(10, LocalDate.of(2026, 1, 11)),
                "Consumo de materia-prima vencida rejeitado");

        if (materia.getQuantidadeDisponivel() == 100) {
            System.out.println("PASSOU: estoque preservado apos a recusa.");
        } else {
            throw new AssertionError("O estoque foi alterado indevidamente.");
        }
    }

    private static void testarCodigoDoLote() {
        System.out.println("\n[RN05] Codigo do lote");

        EDT perfume = criarPerfume("Perfume de teste");
        LoteProducao lote = new LoteProducao(
                "LOTE-001", perfume,
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2027, 1, 1));

        if ("LOTE-001".equals(lote.getCodigo())) {
            System.out.println("PASSOU: lote criado com codigo definido.");
        } else {
            throw new AssertionError("Codigo do lote incorreto.");
        }
    }

    private static void testarMaceracaoEEnvase() {
        System.out.println("\n[RN04] Maceracao e envase");

        LocalDate inicio = LocalDate.of(2026, 1, 1);
        LocalDate validade = LocalDate.of(2027, 1, 1);
        LoteProducao lote = new LoteProducao(
                "LOTE-002", criarPerfume("Perfume para envase"),
                inicio, validade);

        esperarErro(
                () -> lote.envasar(50, 2, inicio.plusDays(29)),
                "Envase antes de 30 dias rejeitado");

        lote.envasar(50, 2, inicio.plusDays(30));

        if (lote.isEnvasado()
                && lote.getUnidadesEnvasadas() == 2
                && lote.getFrasco().getVolumeMl() == 50) {
            System.out.println("PASSOU: envase permitido apos a maceracao.");
        } else {
            throw new AssertionError("Falha na verificacao do envase.");
        }

        esperarErro(
                () -> lote.envasar(100, 1, inicio.plusDays(31)),
                "Segundo envase rejeitado");
    }

    private static void testarPrecoMinimo() {
        System.out.println("\n[RN06] Preco minimo de venda");

        Produto produto = criarPerfume("Perfume para venda");
        EstoquePerfumes estoque = new EstoquePerfumes();

        esperarErro(
                () -> estoque.registrarVenda(
                        produto, 1, new BigDecimal("139.99")),
                "Venda abaixo do preco minimo rejeitada");

        if (estoque.consultarEstoque(produto) == 0
                && estoque.getVendas().isEmpty()) {
            System.out.println("PASSOU: venda recusada sem alterar dados.");
        } else {
            throw new AssertionError("Dados alterados apos venda recusada.");
        }
    }

    private static void testarEstoqueInsuficiente() {
        System.out.println("\n[RN07] Estoque insuficiente");

        LocalDate inicio = LocalDate.of(2026, 1, 1);
        Produto produto = criarPerfume("Perfume com estoque limitado");
        LoteProducao lote = new LoteProducao(
                "LOTE-003", (br.edu.unifsa.perfumaria.produtos.Perfume) produto,
                inicio, LocalDate.of(2027, 1, 1));

        lote.envasar(50, 2, inicio.plusDays(30));

        EstoquePerfumes estoque = new EstoquePerfumes();
        estoque.registrarEnvase(produto, lote);

        esperarErro(
                () -> estoque.registrarVenda(
                        produto, 3, new BigDecimal("140.00")),
                "Venda maior que o estoque rejeitada");

        if (estoque.consultarEstoque(produto) == 2
                && estoque.getVendas().isEmpty()) {
            System.out.println("PASSOU: estoque preservado apos a recusa.");
        } else {
            throw new AssertionError("Estoque alterado apos venda recusada.");
        }
    }

    private static EDT criarPerfume(String nome) {
        return new EDT(nome, new BigDecimal("100.00"),
                new BigDecimal("10"));
    }

    private static void esperarErro(Runnable acao, String descricao) {
        try {
            acao.run();
            throw new AssertionError(
                    "FALHOU: a operacao deveria ser recusada: " + descricao);
        } catch (IllegalArgumentException | IllegalStateException erro) {
            System.out.println("PASSOU: " + descricao);
        }
    }
}
