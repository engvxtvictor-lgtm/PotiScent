
package br.edu.unifsa.perfumaria.produtos;

import java.math.BigDecimal;

public class DifusorAromatico extends Produto {

    private final int quantidadeVaretas;

    public DifusorAromatico(
            String nome,
            BigDecimal custoProducao,
            int quantidadeVaretas) {

        super(nome, custoProducao);

        if (quantidadeVaretas < 4 || quantidadeVaretas > 10) {
            throw new IllegalArgumentException(
                    "[RN10] O difusor deve ter entre 4 e 10 varetas.");
        }

        this.quantidadeVaretas = quantidadeVaretas;
    }

    public int getQuantidadeVaretas() {
        return quantidadeVaretas;
    }
}
