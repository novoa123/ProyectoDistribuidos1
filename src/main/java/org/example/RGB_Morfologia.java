package org.example;

public class RGB_Morfologia {

    public static int[][][] aplicarErosionRGB(int[][][] rgbMatriz, Estructurante estructurante, boolean paralelo) {
        int[][][] resultado = new int[3][rgbMatriz[0].length][rgbMatriz[0][0].length];

        for (int canal = 0; canal < 3; canal++) {
            String canalName = canal == 0 ? "Rojo" : (canal == 1 ? "Verde" : "Azul");
            System.out.println("  Procesando canal " + canalName + "...");

            Morfologia morfologia = new Erosion(rgbMatriz[canal], estructurante);
            if (paralelo) {
                resultado[canal] = morfologia.aplicarParalelo();
            } else {
                resultado[canal] = morfologia.aplicarSecuencial();
            }
        }

        return resultado;
    }

    public static int[][][] aplicarDilatacionRGB(int[][][] rgbMatriz, Estructurante estructurante, boolean paralelo) {
        int[][][] resultado = new int[3][rgbMatriz[0].length][rgbMatriz[0][0].length];

        for (int canal = 0; canal < 3; canal++) {
            String canalName = canal == 0 ? "Rojo" : (canal == 1 ? "Verde" : "Azul");
            System.out.println("  Procesando canal " + canalName + "...");

            Morfologia morfologia = new Dilatacion(rgbMatriz[canal], estructurante);
            if (paralelo) {
                resultado[canal] = morfologia.aplicarParalelo();
            } else {
                resultado[canal] = morfologia.aplicarSecuencial();
            }
        }

        return resultado;
    }

}
