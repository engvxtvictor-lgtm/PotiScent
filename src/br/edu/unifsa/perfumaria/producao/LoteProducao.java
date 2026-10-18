package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lote de produção de um perfume.
 * RN05: o código é definido na criação e nunca muda (atributo final, sem setter).
 * RN04: o envase só é permitido após a maceração mínima da categoria.
 * RN03: o volume do frasco é definido no envase e não pode ser alterado depois.
 * RN08: insumo vencido não pode ser usado (verificado por MateriaPrima.consumir).
 *
 * As datas são recebidas por parâmetro para que a simulação possa "avançar o tempo".
 */
public class LoteProducao implements Perecivel {

    private final String codigo;
    private final String produto;
    private final int diasMinimosMaceracao;
    private final LocalDate dataInicio;
    private final LocalDate validade;
    private final List<String> insumosUsados = new ArrayList<>();

    private Frasco frasco;
    private int unidadesEnvasadas;

    public LoteProducao(String codigo, String produto, int diasMinimosMaceracao,
                        LocalDate dataInicio, LocalDate validade) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("[RN05] Todo lote precisa de um código na criação.");
        }
        if (produto == null || produto.isBlank()) {
            throw new IllegalArgumentException("O produto do lote é obrigatório.");
        }
        if (diasMinimosMaceracao < 0) {
            throw new IllegalArgumentException("Dias mínimos de maceração não podem ser negativos.");
        }
        if (dataInicio == null || validade == null) {
            throw new IllegalArgumentException("Data de início e validade do lote são obrigatórias.");
        }
        if (validade.isBefore(dataInicio)) {
            throw new IllegalArgumentException("A validade do lote não pode ser anterior ao início.");
        }
        this.codigo = codigo.trim();
        this.produto = produto.trim();
        this.diasMinimosMaceracao = diasMinimosMaceracao;
        this.dataInicio = dataInicio;
        this.validade = validade;
    }

    // Construtor sobrecarregado: produtos sem maceração (vela, difusor).
    public LoteProducao(String codigo, String produto, LocalDate dataInicio, LocalDate validade) {
        this(codigo, produto, 0, dataInicio, validade);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProduto() {
        return produto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    @Override
    public LocalDate getValidade() {
        return validade;
    }

    public boolean isEnvasado() {
        return frasco != null;
    }

    public int getUnidadesEnvasadas() {
        return unidadesEnvasadas;
    }

    public Frasco getFrasco() {
        return frasco;
    }

    public List<String> getInsumosUsados() {
        return Collections.unmodifiableList(insumosUsados);
    }

    public long diasDeMaceracao(LocalDate dataReferencia) {
        return ChronoUnit.DAYS.between(dataInicio, dataReferencia);
    }

    public boolean podeEnvasar(LocalDate dataReferencia) {
        return diasDeMaceracao(dataReferencia) >= diasMinimosMaceracao;
    }

    /** RN08 acontece dentro de MateriaPrima.consumir; o lote só registra o uso se der certo. */
    public void usarMateriaPrima(MateriaPrima materiaPrima, double quantidade, LocalDate dataProducao) {
        materiaPrima.consumir(quantidade, dataProducao);
        insumosUsados.add(materiaPrima.getNome() + ": " + quantidade);
    }

    /**
     * Envasa o lote. Se qualquer regra for violada, o lote continua como estava.
     */
    public void envasar(int volumeMl, int unidades, LocalDate dataEnvase) {
        if (isEnvasado()) {
            throw new IllegalStateException("[RN03] O lote " + codigo
                    + " já foi envasado em frascos de " + frasco.getVolumeMl()
                    + " ml; o volume não pode ser alterado.");
        }
        if (unidades <= 0) {
            throw new IllegalArgumentException("A quantidade de unidades envasadas deve ser positiva.");
        }
        if (!podeEnvasar(dataEnvase)) {
            throw new IllegalStateException("[RN04] Envase recusado: " + diasDeMaceracao(dataEnvase)
                    + " dias de maceração, mínimo exigido " + diasMinimosMaceracao + ".");
        }
        Frasco novoFrasco = new Frasco(volumeMl); // RN03: lança exceção se o volume for inválido
        this.frasco = novoFrasco;
        this.unidadesEnvasadas = unidades;
    }

    @Override
    public String toString() {
        return "LoteProducao{codigo='" + codigo + "', produto='" + produto
                + "', inicio=" + dataInicio + ", validade=" + validade
                + ", " + (isEnvasado() ? unidadesEnvasadas + " un. em " + frasco : "não envasado") + "}";
    }
}