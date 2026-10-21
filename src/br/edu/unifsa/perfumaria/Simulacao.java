package br.edu.unifsa.perfumaria;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import br.edu.unifsa.perfumaria.pessoas.Funcionario;
import br.edu.unifsa.perfumaria.pessoas.Perfumista;
import br.edu.unifsa.perfumaria.pessoas.PerfumistaJunior;
import br.edu.unifsa.perfumaria.pessoas.PerfumistaSenior;
import br.edu.unifsa.perfumaria.producao.EstoquePerfumes;
import br.edu.unifsa.perfumaria.producao.Frasco;
import br.edu.unifsa.perfumaria.producao.LoteProducao;
import br.edu.unifsa.perfumaria.producao.MateriaPrima;
import br.edu.unifsa.perfumaria.produtos.CamadaOlfativa;
import br.edu.unifsa.perfumaria.produtos.Colonia;
import br.edu.unifsa.perfumaria.produtos.DifusorAromatico;
import br.edu.unifsa.perfumaria.produtos.EDP;
import br.edu.unifsa.perfumaria.produtos.EDT;
import br.edu.unifsa.perfumaria.produtos.FormulaOlfativa;
import br.edu.unifsa.perfumaria.produtos.IngredienteFormula;
import br.edu.unifsa.perfumaria.produtos.Parfum;
import br.edu.unifsa.perfumaria.produtos.Perfume;
import br.edu.unifsa.perfumaria.produtos.PerfumePersonalizado;
import br.edu.unifsa.perfumaria.produtos.VelaAromatica;

public class Simulacao {

    private static final LocalDate INICIO = LocalDate.of(2026, 1, 1);
    private static final LocalDate ENVASE = LocalDate.of(2026, 3, 10);
    private static final LocalDate VALIDADE = LocalDate.of(2027, 1, 1);

    public static void main(String[] args) {
        System.out.println("=== ESSÊNCIA DO POTI ===");
        System.out.println("Simulação: Um dia na perfumaria");

        testarHierarquiaEPolimorfismo();

        testarRN01();
        testarRN02();
        testarRN03();
        testarRN04();
        testarRN05();
        testarRN06ERn07();
        testarRN08();
        testarRN09();
        testarRN10();
        testarRN11();

        System.out.println("\n=== SIMULAÇÃO CONCLUÍDA ===");
    }

    private static void testarHierarquiaEPolimorfismo() {
        System.out.println("\n[POO] Hierarquia e polimorfismo");

        Funcionario junior = new PerfumistaJunior("João");
        Funcionario senior = new PerfumistaSenior("Maria");

        System.out.println("Funcionário: " + junior);
        System.out.println("Funcionário: " + senior);

        Perfume[] perfumes = {
            new Parfum("Poti Intenso", bd("100"), bd("25")),
            new EDP("Poti Elegante", bd("80"), bd("18")),
            new EDT("Poti Leve", bd("50"), bd("10")),
            new Colonia("Poti Fresco", bd("30"), bd("4"))
        };

        for (Perfume perfume : perfumes) {
            System.out.println(perfume);
        }

        System.out.println("PASSOU: hierarquia e polimorfismo demonstrados.");
    }

    private static void testarRN01() {
        testarRecusa("[RN01] Parfum com concentração acima do limite",
                () -> new Parfum("Inválido", bd("100"), bd("31")));
    }

    private static void testarRN02() {
        System.out.println("\n[RN02] Fórmulas e camadas olfativas");

        try {
            FormulaOlfativa formula = criarFormulaValida();
            System.out.println("PASSOU: fórmula válida aceita.");
            System.out.println(formula);
        } catch (RuntimeException e) {
            falhou(e);
        }

        testarRecusa("[RN02] Camadas totalizando apenas 90%",
                () -> new FormulaOlfativa(
                        criarCamada("saída", "20", "Bergamota"),
                        criarCamada("coração", "30", "Rosa"),
                        criarCamada("fundo", "40", "Cedro")));

        testarRecusa("[RN02] Camada com percentual zero",
                () -> criarCamada("saída", "0", "Bergamota"));

        testarRecusa("[RN02] Camada sem ingredientes",
                () -> new CamadaOlfativa(
                        "saída", bd("25"), Arrays.asList()));

        testarRecusa("[RN02] Ingrediente com percentual negativo",
                () -> new IngredienteFormula("Bergamota", bd("-5")));
    }

    private static void testarRN03() {
        testarRecusa("[RN03] Frasco de 40 ml",
                () -> new Frasco(40));

        LoteProducao lote = criarLote("RN03-001");
        lote.envasar(30, 2, ENVASE);

        testarRecusa("[RN03] Tentar envasar o mesmo lote novamente",
                () -> lote.envasar(50, 2, ENVASE));

        System.out.println("PASSOU: volume definido no primeiro envase: "
                + lote.getFrasco());
    }

    private static void testarRN04() {
        LoteProducao lote = criarLote("RN04-001");

        testarRecusa("[RN04] Envase antes dos 60 dias de maceração",
                () -> lote.envasar(30, 2, LocalDate.of(2026, 1, 20)));

        try {
            lote.envasar(30, 2, ENVASE);
            System.out.println("PASSOU: envase autorizado após a maceração.");
        } catch (RuntimeException e) {
            falhou(e);
        }
    }

    private static void testarRN05() {
        testarRecusa("[RN05] Criar lote sem código",
                () -> new LoteProducao(
                        " ", criarParfum(), INICIO, VALIDADE));

        LoteProducao lote = criarLote("LOTE-IMUTAVEL");
        System.out.println("[RN05] Código do lote: " + lote.getCodigo());
        System.out.println("PASSOU: código declarado final e sem setter.");
    }

    private static void testarRN06ERn07() {
        System.out.println("\n[RN06/RN07] Estoque e vendas");

        Parfum perfume = criarParfum();
        LoteProducao lote = criarLote("VENDA-001");
        lote.envasar(50, 5, ENVASE);

        EstoquePerfumes estoque = new EstoquePerfumes();
        estoque.registrarEnvase(perfume, lote);

        testarRecusa("[RN06] Preço abaixo do mínimo de 40%",
                () -> estoque.registrarVenda(perfume, 1, bd("139.99")));

        try {
            estoque.registrarVenda(perfume, 1, bd("140.00"));
            System.out.println("PASSOU: venda válida registrada.");
        } catch (RuntimeException e) {
            falhou(e);
        }

        testarRecusa("[RN07] Venda acima do estoque disponível",
                () -> estoque.registrarVenda(perfume, 99, bd("140.00")));

        System.out.println("Estoque final de " + perfume.getNome()
                + ": " + estoque.consultarEstoque(perfume) + " unidade(s).");
        System.out.println(estoque);
    }

    private static void testarRN08() {
        MateriaPrima vencida = new MateriaPrima(
                "Essência vencida", 100,
                LocalDate.of(2026, 2, 1));

        testarRecusa("[RN08] Usar matéria-prima vencida",
                () -> vencida.consumir(10, ENVASE));

        System.out.println("Estoque preservado após recusa: "
                + vencida.getQuantidadeDisponivel());
    }

    private static void testarRN09() {
        FormulaOlfativa formula = criarFormulaValida();
        Parfum parfum = criarParfum();

        Perfumista junior = new PerfumistaJunior("João");
        Perfumista senior = new PerfumistaSenior("Maria");

        testarRecusa("[RN09] Júnior assinando fórmula de Parfum",
                () -> formula.assinar(junior, parfum));

        try {
            formula.assinar(senior, parfum);
            System.out.println("PASSOU: sênior pode assinar fórmula de Parfum.");
        } catch (RuntimeException e) {
            falhou(e);
        }
    }

    private static void testarRN10() {
        testarRecusa("[RN10] Vela com fragrância acima de 10%",
                () -> new VelaAromatica(
                        "Vela inválida", bd("20"), bd("200"), bd("10.1")));

        testarRecusa("[RN10] Difusor com apenas 3 varetas",
                () -> new DifusorAromatico(
                        "Difusor inválido", bd("20"), 3));

        try {
            VelaAromatica vela = new VelaAromatica(
                    "Vela Poti", bd("20"), bd("200"), bd("8"));

            DifusorAromatico difusor = new DifusorAromatico(
                    "Difusor Poti", bd("25"), 6);

            System.out.println("PASSOU: " + vela);
            System.out.println("Fragrância necessária: "
                    + vela.calcularFragranciaGramas() + " g");
            System.out.println("PASSOU: " + difusor);
        } catch (RuntimeException e) {
            falhou(e);
        }
    }

    private static void testarRN11() {
        testarRecusa("[RN11] Gravação acima de 20 caracteres",
                () -> new PerfumePersonalizado(
                        "Poti Personalizado", bd("100"), bd("18"),
                        "GravacaoMuitoLongaMaisDeVinte"));

        PerfumePersonalizado personalizado = new PerfumePersonalizado(
                "Poti Personalizado", bd("100"), bd("18"), "Para Ana");

        System.out.println("Encomenda aceita: " + personalizado);

        testarRecusa("[RN11] Devolver perfume personalizado",
                personalizado::devolver);
    }

    private static void testarRecusa(String descricao, Runnable operacao) {
        System.out.println("\n" + descricao);

        try {
            operacao.run();
            System.out.println("FALHOU: a operação inválida foi aceita.");
        } catch (RuntimeException e) {
            System.out.println("PASSOU: operação recusada — "
                    + e.getMessage());
        }
    }

    private static void falhou(RuntimeException e) {
        System.out.println("FALHOU: " + e.getMessage());
    }

    private static BigDecimal bd(String valor) {
        return new BigDecimal(valor);
    }

    private static Parfum criarParfum() {
        return new Parfum("Poti Intenso", bd("100"), bd("25"));
    }

    private static LoteProducao criarLote(String codigo) {
        return new LoteProducao(
                codigo, criarParfum(), INICIO, VALIDADE);
    }

    private static FormulaOlfativa criarFormulaValida() {
        CamadaOlfativa saida = new CamadaOlfativa(
                "saída", bd("25"), Arrays.asList(
                        new IngredienteFormula("Bergamota", bd("60")),
                        new IngredienteFormula("Limão", bd("40"))));

        CamadaOlfativa coracao = new CamadaOlfativa(
                "coração", bd("35"), Arrays.asList(
                        new IngredienteFormula("Rosa", bd("50")),
                        new IngredienteFormula("Jasmim", bd("50"))));

        CamadaOlfativa fundo = new CamadaOlfativa(
                "fundo", bd("40"), Arrays.asList(
                        new IngredienteFormula("Cedro", bd("70")),
                        new IngredienteFormula("Baunilha", bd("30"))));

        return new FormulaOlfativa(saida, coracao, fundo);
    }

    private static CamadaOlfativa criarCamada(
            String tipo, String percentual, String nomeIngrediente) {

        return new CamadaOlfativa(
                tipo, bd(percentual), Arrays.asList(
                        new IngredienteFormula(nomeIngrediente, bd("100"))));
    }
}