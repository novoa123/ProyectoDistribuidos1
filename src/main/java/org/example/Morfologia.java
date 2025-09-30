package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


// clase base para operaciones morfológicas (erosion / dilatacion)
// define estructura comun y metodos para correr en forma secuencial o paralela

public abstract class Morfologia {
 protected final int[][] matriz;        // entrada (solo lectura)
    protected final Estructurante estructurante;
    protected final int[][] resultado;     // salida (escritura)
    protected final int height, width;
    protected final int threshold;         // filas por bloque

    // ctor por defecto (threshold=100)
    public Morfologia(int[][] matriz, Estructurante estructurante) {
        this(matriz, estructurante, 100);
    }

    // ctor con threshold configurable
    public Morfologia(int[][] matriz, Estructurante estructurante, int threshold) {
        this.matriz = matriz;
        this.estructurante = estructurante;
        this.height = matriz.length;
        this.width = matriz[0].length;
        this.resultado = new int[height][width];
        this.threshold = (threshold > 0 ? threshold : 100);
    }

    // operación concreta (erosión/dilatación)
    protected abstract int operacion(int x, int y);

    // versión secuencial: recorre todo pixel a pixel
    public int[][] aplicarSecuencial() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                resultado[y][x] = operacion(x, y);
            }
        }
        return resultado;
    }

    // versión paralela con pool externo (fork/join por filas)
    public int[][] aplicarParalelo(ForkJoinPool pool) {
        pool.invoke(new MorfologiaTask(0, height));
        return resultado;
    }

    // versión paralela con pool interno (fork/join por filas)
    private class MorfologiaTask extends RecursiveAction {
        private final int startRow, endRow;

        MorfologiaTask(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected void compute() {
            int rows = endRow - startRow;
            if (rows <= threshold) {
                for (int y = startRow; y < endRow; y++) {
                    for (int x = 0; x < width; x++) {
                        resultado[y][x] = operacion(x, y);
                    }
                }
            } else {
                int mid = startRow + rows / 2;
                invokeAll(new MorfologiaTask(startRow, mid),
                          new MorfologiaTask(mid, endRow));
            }
        }
    }
}
