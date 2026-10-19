
package br.edu.unifsa.perfumaria.producao;

import br.edu.unifsa.perfumaria.produtos.Produto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EstoquePerfumes {

    private final Map<Produto, Integer> estoque = new LinkedHashMap<>();
    private final List<Venda> vendas = new ArrayList<>();

    public void registrarEnvase(Produto produto, LoteProducao lote) {
        if (produto == null || lote == null) {
            throw new IllegalArgumentException(
                    "O produto e o lote são obrigatórios.");
        }

        if (!lote.isEnvasado()) {
            throw new IllegalStateException(
                    "Não é possível adicionar ao estoque um lote não envasado.");
        }

        if (!produto.getNome().equalsIgnoreCase(lote.getProduto())) {
            throw new IllegalArgumentException(
                    "O produto informado não corresponde ao produto do lote.");
        }

        int unidades = lote.getUnidadesEnvasadas();
        estoque.merge(produto, unidades, Integer::sum);
    }

    public int consultarEstoque(Produto produto) {
        validarProduto(produto);
        return estoque.getOrDefault(produto, 0);
    }

    public void registrarVenda(
            Produto produto, int quantidade, BigDecimal precoUnitario) {

        validarProduto(produto);

        if (quantidade <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade da venda deve ser positiva.");
        }

        if (precoUnitario == null
                || precoUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "O preço de venda deve ser positivo.");
        }

        BigDecimal precoMinimo = produto.calcularPrecoMinimo();

        if (precoUnitario.compareTo(precoMinimo) < 0) {
            throw new IllegalArgumentException(
                    "[RN06] Venda recusada. O preço mínimo é R$ "
                    + precoMinimo.toPlainString() + ".");
        }

        int disponivel = consultarEstoque(produto);

        if (quantidade > disponivel) {
            throw new IllegalStateException(
                    "[RN07] Venda recusada. Estoque disponível: "
                    + disponivel + " unidade(s).");
        }

        BigDecimal total = precoUnitario.multiply(
                BigDecimal.valueOf(quantidade));

        Venda venda = new Venda(produto.getNome(), quantidade,
                precoUnitario, total);

        estoque.put(produto, disponivel - quantidade);
        vendas.add(venda);
    }

    public List<Venda> getVendas() {
        return Collections.unmodifiableList(vendas);
    }

    private void validarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException(
                    "O produto é obrigatório.");
        }
    }

    public static class Venda {

        private final String nomeProduto;
        private final int quantidade;
        private final BigDecimal precoUnitario;
        private final BigDecimal valorTotal;

        private Venda(String nomeProduto, int quantidade,
                      BigDecimal precoUnitario, BigDecimal valorTotal) {
            this.nomeProduto = nomeProduto;
            this.quantidade = quantidade;
            this.precoUnitario = precoUnitario;
            this.valorTotal = valorTotal;
        }

        public String getNomeProduto() {
            return nomeProduto;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public BigDecimal getPrecoUnitario() {
            return precoUnitario;
        }

        public BigDecimal getValorTotal() {
            return valorTotal;
        }

        @Override
        public String toString() {
            return "Venda{produto='" + nomeProduto
                    + "', quantidade=" + quantidade
                    + ", precoUnitario=R$ " + precoUnitario
                    + ", valorTotal=R$ " + valorTotal + "}";
        }
    }

    @Override
    public String toString() {
        return "EstoquePerfumes{produtos=" + estoque.size()
                + ", vendasRegistradas=" + vendas.size() + "}";
    }
}
