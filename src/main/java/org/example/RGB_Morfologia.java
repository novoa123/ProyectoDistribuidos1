package org.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class RGB_Morfologia {
    // aplica erosion a rgb (3 hilos: r, g, b)
    public static int[][][] aplicarErosionRGB(int[][][] rgbMatriz,
                                              Estructurante ee,
                                              boolean paralelo) {
        int alto = rgbMatriz[0].length;
        int ancho = rgbMatriz[0][0].length;
        int[][][] out = new int[3][alto][ancho];

        // 3 hilos fijos, uno por canal
        ExecutorService exec = Executors.newFixedThreadPool(3);

        // pool fork/join compartido para las filas (solo si paralelo)
        ForkJoinPool pool = paralelo
                ? new ForkJoinPool(Math.min(Runtime.getRuntime().availableProcessors(), 8))
                : null;

        Callable<int[][]> tareaR = () -> {
            Morfologia m = new Erosion(rgbMatriz[0], ee);
            // dentro del canal decide si secuencial o paralelo por filas (pasando el pool)
            return paralelo ? m.aplicarParalelo(pool) : m.aplicarSecuencial();
        };

        Callable<int[][]> tareaG = () -> {
            Morfologia m = new Erosion(rgbMatriz[1], ee);
            return paralelo ? m.aplicarParalelo(pool) : m.aplicarSecuencial();
        };

        Callable<int[][]> tareaB = () -> {
            Morfologia m = new Erosion(rgbMatriz[2], ee);
            return paralelo ? m.aplicarParalelo(pool) : m.aplicarSecuencial();
        };

        try {
            Future<int[][]> fR = exec.submit(tareaR);
            Future<int[][]> fG = exec.submit(tareaG);
            Future<int[][]> fB = exec.submit(tareaB);

            out[0] = fR.get();
            out[1] = fG.get();
            out[2] = fB.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            exec.shutdown();
            if (pool != null) pool.shutdown();
        }

        return out;
    }

    // aplica dilatacion a rgb (3 hilos: r, g, b)
    public static int[][][] aplicarDilatacionRGB(int[][][] rgbMatriz,
                                                 Estructurante ee,
                                                 boolean paralelo) {
        int alto = rgbMatriz[0].length;
        int ancho = rgbMatriz[0][0].length;
        int[][][] out = new int[3][alto][ancho];

        ExecutorService exec = Executors.newFixedThreadPool(3);

        ForkJoinPool pool = paralelo
                ? new ForkJoinPool(Math.min(Runtime.getRuntime().availableProcessors(), 8))
                : null;

        Callable<int[][]> tareaR = () -> {
            Morfologia m = new Dilatacion(rgbMatriz[0], ee);
            return paralelo ? m.aplicarParalelo(pool) : m.aplicarSecuencial();
        };

        Callable<int[][]> tareaG = () -> {
            Morfologia m = new Dilatacion(rgbMatriz[1], ee);
            return paralelo ? m.aplicarParalelo(pool) : m.aplicarSecuencial();
        };

        Callable<int[][]> tareaB = () -> {
            Morfologia m = new Dilatacion(rgbMatriz[2], ee);
            return paralelo ? m.aplicarParalelo(pool) : m.aplicarSecuencial();
        };

        try {
            Future<int[][]> fR = exec.submit(tareaR);
            Future<int[][]> fG = exec.submit(tareaG);
            Future<int[][]> fB = exec.submit(tareaB);

            out[0] = fR.get();
            out[1] = fG.get();
            out[2] = fB.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            exec.shutdown();
            if (pool != null) pool.shutdown();
        }

        return out;
    }

    // alias opcionales si en tu main los llamas como 'erosionRGB' o 'dilatacionRGB'
    public static int[][][] erosionRGB(int[][][] rgbMatriz, Estructurante ee, boolean paralelo) {
        return aplicarErosionRGB(rgbMatriz, ee, paralelo);
    }
    public static int[][][] dilatacionRGB(int[][][] rgbMatriz, Estructurante ee, boolean paralelo) {
        return aplicarDilatacionRGB(rgbMatriz, ee, paralelo);
    }
}
