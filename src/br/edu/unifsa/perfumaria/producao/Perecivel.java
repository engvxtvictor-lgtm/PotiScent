package br.edu.unifsa.perfumaria.producao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Capacidade de algo que possui prazo de validade.
 * Classes sem relação de herança entre si podem implementar esta interface.
 */
public interface Perecivel {

    LocalDate getValidade();

    default boolean estaVencido(LocalDate dataReferencia) {
        validarDataReferencia(dataReferencia);
        return dataReferencia.isAfter(getValidade());
    }

    default long diasParaVencer(LocalDate dataReferencia) {
        validarDataReferencia(dataReferencia);
        return ChronoUnit.DAYS.between(dataReferencia, getValidade());
    }

    private void validarDataReferencia(LocalDate dataReferencia) {
        if (dataReferencia == null) {
            throw new IllegalArgumentException(
                    "A data de referência é obrigatória para consultar a validade.");
        }
    }
}