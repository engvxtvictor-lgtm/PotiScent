
package br.edu.unifsa.perfumaria.producao;

import br.edu.unifsa.perfumaria.produtos.Perfume;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoteProducao implements Perecivel {

    private final String codigo;
    private final String produto;
    private final int diasMinimosMaceracao;
    private final LocalDate dataInicio;
    private final LocalDate validade;
    private final List<String> insumosUsados = new ArrayList<>();

    private Frasco frasco;
    private int unidadesEnvasadas;

    public LoteProducao(String codigo, Perfume perfume,
                        LocalDate dataInicio, LocalDate validade) {

        if (perfume == null) {
            throw new IllegalArgumentException("O perfume é obrigatório.");
        }

        validarDados(codigo, perfume.getNome(), dataInicio, validade);

        int diasMaceracao = perfume.getTempoMinimoMaceracaoDias();

        if (diasMaceracao <= 0) {
            throw new IllegalArgumentException(
                    "O tempo mínimo de maceração deve ser positivo.");
        }

        this.codigo = codigo.trim();
        this.produto = perfume.getNome();
        this.diasMinimosMaceracao = diasMaceracao;
        this.dataInicio = dataInicio;
        this.validade = validade;
    }

    public LoteProducao(String codigo, LocalDate dataInicio,
                        LocalDate validade, Perfume perfume) {
        this(codigo, perfume, dataInicio, validade);
    }

    private static void validarDados(String codigo, String produto,
                                     LocalDate dataInicio,
                                     LocalDate validade) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "[RN05] Todo lote precisa de um código.");
        }

        if (produto == null || produto.isBlank()) {
            throw new IllegalArgumentException(
                    "O produto do lote é obrigatório.");
        }

        if (dataInicio == null || validade == null) {
            throw new IllegalArgumentException(
                    "A data de início e a validade são obrigatórias.");
        }

        if (validade.isBefore(dataInicio)) {
            throw new IllegalArgumentException(
                    "A validade não pode ser anterior ao início do lote.");
        }
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

    public int getDiasMinimosMaceracao() {
        return diasMinimosMaceracao;
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
        validarDataReferencia(dataReferencia);

        if (dataReferencia.isBefore(dataInicio)) {
            throw new IllegalArgumentException(
                    "A data de referência não pode ser anterior ao início do lote.");
        }

        return ChronoUnit.DAYS.between(dataInicio, dataReferencia);
    }

    public boolean podeEnvasar(LocalDate dataReferencia) {
        validarDataReferencia(dataReferencia);

        if (dataReferencia.isBefore(dataInicio)
                || dataReferencia.isAfter(validade)) {
            return false;
        }

        return diasDeMaceracao(dataReferencia) >= diasMinimosMaceracao;
    }

    public void usarMateriaPrima(MateriaPrima materiaPrima,
                                 double quantidade,
                                 LocalDate dataProducao) {

        if (materiaPrima == null) {
            throw new IllegalArgumentException(
                    "A matéria-prima é obrigatória.");
        }

        if (dataProducao == null) {
            throw new IllegalArgumentException(
                    "A data da produção é obrigatória.");
        }

        if (!dataProducao.equals(dataInicio)) {
            throw new IllegalArgumentException(
                    "A data da produção deve corresponder ao início do lote.");
        }

        materiaPrima.consumir(quantidade, dataProducao);
        insumosUsados.add(materiaPrima.getNome() + ": " + quantidade);
    }

    public void envasar(int volumeMl, int unidades,
                        LocalDate dataEnvase) {

        if (isEnvasado()) {
            throw new IllegalStateException(
                    "[RN03] O lote " + codigo
                    + " já foi envasado em frascos de "
                    + frasco.getVolumeMl()
                    + " ml; o volume não pode ser alterado.");
        }

        validarDataReferencia(dataEnvase);

        if (unidades <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade de unidades deve ser positiva.");
        }

        if (!podeEnvasar(dataEnvase)) {
            throw new IllegalStateException(
                    "[RN04] Envase recusado: "
                    + diasDeMaceracao(dataEnvase)
                    + " dias de maceração; mínimo exigido: "
                    + diasMinimosMaceracao
                    + " dias. Verifique também a validade do lote.");
        }

        Frasco novoFrasco = new Frasco(volumeMl);

        this.frasco = novoFrasco;
        this.unidadesEnvasadas = unidades;
    }

    private void validarDataReferencia(LocalDate dataReferencia) {
        if (dataReferencia == null) {
            throw new IllegalArgumentException(
                    "A data de referência é obrigatória.");
        }
    }

    @Override
    public String toString() {
        return "LoteProducao{codigo='" + codigo
                + "', produto='" + produto
                + "', inicio=" + dataInicio
                + ", validade=" + validade
                + ", " + (isEnvasado()
                ? unidadesEnvasadas + " unidades em " + frasco
                : "não envasado")
                + "}";
    }
}
