package org.example;

public class MatrizComparator {

    public static boolean comparar(int[][][] matriz1, int[][][] matriz2) {
        // verifica que las dimensiones sean iguales
        if (matriz1.length != matriz2.length) {
            System.out.println("Error: Número de canales diferente");
            return false;
        }
        
        // compara cada canal 
        for (int c = 0; c < matriz1.length; c++) {
            if (matriz1[c].length != matriz2[c].length) {
                System.out.println("Error: Altura diferente en canal " + c);
                return false;
            }

            // compara cada pixel
            for (int y = 0; y < matriz1[c].length; y++) {
                if (matriz1[c][y].length != matriz2[c][y].length) {
                    System.out.println("Error: Ancho diferente en canal " + c + ", fila " + y);
                    return false;
                }
                
                for (int x = 0; x < matriz1[c][y].length; x++) {
                    // si se encuentra una diferencia, se imprime la posicion y los valores
                    if (matriz1[c][y][x] != matriz2[c][y][x]) {
                        String channelName = c == 0 ? "Rojo" : (c == 1 ? "Verde" : "Azul");
                        System.out.println("Diferencia encontrada en canal " + channelName + 
                                         " posición (" + x + "," + y + "): " +
                                         matriz1[c][y][x] + " vs " + matriz2[c][y][x]);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    // metodo para comparar dos matrices 3D e imprimir si son iguales o no (solo imprime el resultado)
    public static void compararMatrices(int[][][] sequential, int[][][] parallel) {
        if (comparar(sequential, parallel)) {
            System.out.println(" Verificacion exitosa: resultados identicos");
        } else {
            System.out.println(" Error: los resultados difieren entre version secuencial y paralela");
        }
    }
    



}
