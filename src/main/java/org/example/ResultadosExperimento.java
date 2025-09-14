package org.example;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ResultadosExperimento {
    private List<Resultado> resultados;

    static class Resultado {
        String element;      // Nombre del elemento estructurante
        String operation;    // Tipo de operación (Erosión/Dilatación)
        long seqTime;       // Tiempo de ejecución secuencial en nanosegundos
        long parTime;       // Tiempo de ejecución paralelo en nanosegundos
        int width;          // Ancho de la imagen
        int height;         // Alto de la imagen
        double speedup;     // Factor de mejora (tiempo_seq / tiempo_par)
        int threads;        // Número de threads disponibles
        long memoryUsed;    // Memoria utilizada en bytes

        Resultado(String element, String operation, long seqTime, long parTime, int width, int height) {
            this.element = element;
            this.operation = operation;
            this.seqTime = seqTime;
            this.parTime = parTime;
            this.width = width;
            this.height = height;
            // Calcular speedup (mejora de rendimiento)
            this.speedup = (double) seqTime / parTime;
            // Obtener número de procesadores disponibles
            this.threads = Runtime.getRuntime().availableProcessors();
            // Estimar memoria utilizada
            Runtime runtime = Runtime.getRuntime();
            this.memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        }
    }

    public void agregarResultado(String element, String operation, long seqTime, long parTime, int width, int height) {
        if(resultados == null) {
            resultados = new ArrayList<Resultado>();
        }

        resultados.add(new Resultado(element, operation, seqTime, parTime, width, height));
    }

    public void mostrarResultados() {
        System.out.printf("%-20s %-12s %-15s %-15s %-10s %-10s %-10s %-10s %-15s%n", 
                          "Elemento", "Operación", "Tiempo Seq (ns)", "Tiempo Par (ns)", 
                          "Ancho", "Alto", "Speedup", "Threads", "Memoria (bytes)");
        for (Resultado r : resultados) {
            System.out.printf("%-20s %-12s %-15d %-15d %-10d %-10d %-10.2f %-10d %-15d%n", 
                              r.element, r.operation, r.seqTime, r.parTime, 
                              r.width, r.height, r.speedup, r.threads, r.memoryUsed);
        }
    }

    public void guardarResultadosCSV(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("Elemento,Operación,Tiempo Seq (ns),Tiempo Par (ns),Ancho,Alto,Speedup,Threads,Memoria (bytes)");
            for (Resultado r : resultados) {
                writer.printf("%s,%s,%d,%d,%d,%d,%.2f,%d,%d%n", 
                              r.element, r.operation, r.seqTime, r.parTime, 
                              r.width, r.height, r.speedup, r.threads, r.memoryUsed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
