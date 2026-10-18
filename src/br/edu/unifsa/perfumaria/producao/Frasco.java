package br.edu.unifsa.perfumaria.producao;

/**
 * Recipiente de envase.
 * RN03: só existem frascos de 30, 50 e 100 ml. O volume é definido na criação
 * (no envase) e não tem setter, portanto não pode ser alterado depois.
 */
public class Frasco {

    private final int volumeMl;

    public Frasco(int volumeMl) {
        this.volumeMl = validarVolume(volumeMl);
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    private static int validarVolume(int volumeMl) {
        if (volumeMl != 30 && volumeMl != 50 && volumeMl != 100) {
            throw new IllegalArgumentException("[RN03] Volume de frasco inválido: " + volumeMl
                    + " ml. Volumes permitidos: 30, 50 ou 100 ml.");
        }
        return volumeMl;
    }

    @Override
    public String toString() {
        return "Frasco{" + volumeMl + " ml}";
    }
}