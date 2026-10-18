package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;

/**
 * Insumo usado na produção (óleo essencial, álcool de cereais, cera etc.).
 * RN08: matéria-prima vencida não pode ser usada em nova produção.
 */
public class MateriaPrima implements Perecivel {

    private final String nome;
    private final LocalDate validade;
    private double quantidadeDisponivel;

    public MateriaPrima(String nome, double quantidadeInicial, LocalDate validade) {
        this.nome = validarNome(nome);
        if (validade == null) {
            throw new IllegalArgumentException("A validade da matéria-prima é obrigatória.");
        }
        this.validade = validade;
        this.quantidadeDisponivel = validarQuantidade(quantidadeInicial, true);
    }

    // Construtor sobrecarregado: cadastro sem estoque inicial.
    public MateriaPrima(String nome, LocalDate validade) {
        this(nome, 0, validade);
    }

    public String getNome() {
        return nome;
    }

    @Override
    public LocalDate getValidade() {
        return validade;
    }

    public double getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void adicionarEstoque(double quantidade) {
        validarQuantidade(quantidade, false);
        quantidadeDisponivel += quantidade;
    }

    /**
     * Consome parte do estoque do insumo para uma produção.
     * Se a regra for violada, nada é alterado.
     */
    public void consumir(double quantidade, LocalDate dataProducao) {
        validarQuantidade(quantidade, false);
        if (estaVencido(dataProducao)) {
            throw new IllegalStateException("[RN08] Matéria-prima '" + nome
                    + "' vencida em " + validade + " não pode ser usada em nova produção.");
        }
        if (quantidade > quantidadeDisponivel) {
            throw new IllegalStateException("Quantidade insuficiente de '" + nome
                    + "': disponível " + quantidadeDisponivel + ", solicitado " + quantidade + ".");
        }
        quantidadeDisponivel -= quantidade;
    }

    private static String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da matéria-prima é obrigatório.");
        }
        return nome.trim();
    }

    // Método auxiliar privado de validação (aceitaZero só no estoque inicial).
    private static double validarQuantidade(double quantidade, boolean aceitaZero) {
        boolean invalida = aceitaZero ? quantidade < 0 : quantidade <= 0;
        if (invalida) {
            throw new IllegalArgumentException("Quantidade inválida: " + quantidade + ".");
        }
        return quantidade;
    }

    @Override
    public String toString() {
        return "MateriaPrima{nome='" + nome + "', disponivel=" + quantidadeDisponivel
                + ", validade=" + validade + "}";
    }
}