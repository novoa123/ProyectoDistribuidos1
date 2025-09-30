package org.example;

// Clase que implementa la operación de Dilatación morfológica
// Hereda de la clase base Morfologia
// Implementa el método abstracto 'operacion' para definir la lógica específica de la dilatación
// La dilatación expande las regiones brillantes en una imagen, agregando píxeles en los bordes de las regiones
// El valor de cada píxel en la imagen resultante es el valor máximo de los píxeles cubiertos por el elemento estructurante
// Utiliza el elemento estructurante para determinar cómo se aplica la dilatación en la imagen
// Maneja los bordes de la imagen utilizando padding con valor mínimo
// Si el elemento estructurante no cubre ningún píxel válido, se mantiene el valor original del píxel

public class Dilatacion extends Morfologia {

    public Dilatacion(int[][] matriz, Estructurante ee) {
        super(matriz, ee);
    }

    public Dilatacion(int[][] matriz, Estructurante ee, int threshold) {
        super(matriz, ee, threshold);
    }

    @Override
    protected int operacion(int x, int y) {
        int max = Integer.MIN_VALUE;
        for (int ky = 0; ky < estructurante.getHeight(); ky++) {
            for (int kx = 0; kx < estructurante.getWidth(); kx++) {
                if (estructurante.getKernel()[ky][kx] == 1) {
                    int ix = x + kx - estructurante.getCenterX();
                    int iy = y + ky - estructurante.getCenterY();
                    if (ix >= 0 && ix < width && iy >= 0 && iy < height) {
                        max = Math.max(max, matriz[iy][ix]);
                    }
                }
            }
        }
        return (max == Integer.MIN_VALUE) ? matriz[y][x] : max;
    }
}
