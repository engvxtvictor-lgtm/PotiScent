package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Capacidade de algo que tem prazo de validade (matéria-prima, lote produzido).
 * Classes sem relação de herança entre si podem implementá-la.
 */
public interface Perecivel {

    LocalDate getValidade();

    default boolean estaVencido(LocalDate dataReferencia) {
        return dataReferencia.isAfter(getValidade());
    }

    default long diasParaVencer(LocalDate dataReferencia) {
        return ChronoUnit.DAYS.between(dataReferencia, getValidade());
    }
}