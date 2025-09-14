package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// Clase base abstracta para operaciones morfológicas
// Implementa la lógica para aplicar la operación de manera secuencial y paralela
// Utiliza ForkJoinPool para la ejecución paralela
// Contiene un método abstracto 'operacion' que debe ser implementado por las subclases
// para definir la operación específica (Erosión o Dilatación)


public abstract class Morfologia {
    protected int [][] matriz;
    protected Estructurante estructurante;
    protected int [][] resultado;

    public Morfologia(int[][] matriz, Estructurante estructurante) {
        this.matriz = matriz;
        this.estructurante = estructurante;
        this.resultado = new int[matriz.length][matriz[0].length];
    }

    protected abstract int operacion(int x, int y);

    public int[][] aplicarSecuencial() {
        long tiempoInicio = System.currentTimeMillis();

        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[0].length; x++) {
                resultado[y][x] = operacion(x, y);
            }
        }

        long tiempoFin = System.currentTimeMillis();

        System.out.println("Tiempo de ejecución secuencial: " + (tiempoFin - tiempoInicio) + " ms");
        return resultado;
    }

    public int[][] aplicarParalelo() {
        long tiempoInicio = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();

        MorfologiaTask task = new MorfologiaTask(0, matriz.length);
        pool.invoke(task);
        pool.shutdown();

        long tiempoFin = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución paralelo: " + (tiempoFin - tiempoInicio) + " ms");
        return resultado;
    }

    private class MorfologiaTask extends RecursiveTask<Void> {
        private static final int THRESHHOLD = 100; // Umbral para dividir tareas
        private int startRow;
        private int endRow;

        public MorfologiaTask(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        @Override
        protected Void compute() {
            if (endRow - startRow <= THRESHHOLD) {
                for (int y = startRow; y < endRow; y++) {
                    for (int x = 0; x < matriz[0].length; x++) {
                        resultado[y][x] = operacion(x, y);
                    }
                }
            } else {
                int mid = startRow + (endRow - startRow) / 2;
                MorfologiaTask task1 = new MorfologiaTask(startRow, mid);
                MorfologiaTask task2 = new MorfologiaTask(mid, endRow);
                
                task1.fork();
                task2.fork();

                task1.join();
                task2.join();
            }
            return null;
        }
    }
}
