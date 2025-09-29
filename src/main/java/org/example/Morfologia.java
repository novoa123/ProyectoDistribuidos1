package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

// clase base para operaciones morfológicas (erosion / dilatacion)
// define estructura comun y metodos para correr en forma secuencial o paralela

public abstract class Morfologia {
    protected int [][] matriz; // matriz original (imagen o canal)
    protected Estructurante estructurante; // elemento estructurante usado en la operacion
    protected int [][] resultado;     // matriz donde se guarda el resultado final

 
    public Morfologia(int[][] matriz, Estructurante estructurante) {
        this.matriz = matriz;
        this.estructurante = estructurante;
        this.resultado = new int[matriz.length][matriz[0].length];
    }

    // calcula valor nuevo de un pixel
    protected abstract int operacion(int x, int y);

    // recorre toda la matriz pixel a pixel
    public int[][] aplicarSecuencial() {
        long tiempoInicio = System.currentTimeMillis(); // inicia medicion

        // primero recorre filas, luego columnas
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[0].length; x++) {
                resultado[y][x] = operacion(x, y); // aplica cambio al pixel
            }
        }

        long tiempoFin = System.currentTimeMillis(); 
        System.out.println("Tiempo de ejecución secuencial: " + (tiempoFin - tiempoInicio) + " ms");
        return resultado; // devuelve la imagen procesada
    }

    // porcion paralela, divide el trabajo por rangos de filas
    public int[][] aplicarParalelo() {
        long tiempoInicio = System.currentTimeMillis(); // inicia medicion

        ForkJoinPool pool = new ForkJoinPool(); // crea grupo de hilos
        MorfologiaTask task = new MorfologiaTask(0, matriz.length); // tarea raiz con todas las filas
        pool.invoke(task); // lanza ejecucion
        pool.shutdown(); // cierra uso del pool

        long tiempoFin = System.currentTimeMillis(); // termina medicion
        System.out.println("Tiempo de ejecución paralelo: " + (tiempoFin - tiempoInicio) + " ms");
        return resultado; // devuelve la imagen procesada
    }

    // tarea recursiva: parte el rango de filas hasta un tamaño pequeño
    private class MorfologiaTask extends RecursiveTask<Void> {
        private static final int THRESHHOLD = 100; // umbral simple para decidir dividir
        private int startRow; // fila inicial incluida
        private int endRow;   // fila final excluida

        // guarda el rango a trabajar
        public MorfologiaTask(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        // ejecuta la tarea: procesa directo o divide en dos
        @Override
        protected Void compute() {
            // si el bloque es pequeño se procesa directamente
            if (endRow - startRow <= THRESHHOLD) {
                for (int y = startRow; y < endRow; y++) {
                    for (int x = 0; x < matriz[0].length; x++) {
                        resultado[y][x] = operacion(x, y); // aplica operacion al pixel
                    }
                }
            } else {
                // si es grande se parte el rango en dos mitades
                int mid = startRow + (endRow - startRow) / 2;
                MorfologiaTask task1 = new MorfologiaTask(startRow, mid); // primera mitad
                MorfologiaTask task2 = new MorfologiaTask(mid, endRow);   // segunda mitad
                
                task1.fork(); // lanza primera
                task2.fork(); // lanza segunda

                task1.join(); // espera primera
                task2.join(); // espera segunda
            }
            return null; // no devuelve dato (solo efectos en 'resultado')
        }
    }
}
