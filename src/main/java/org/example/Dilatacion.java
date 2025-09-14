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

    public Dilatacion(int[][] matriz, Estructurante estructurante) {
        super(matriz, estructurante);
    }

    @Override
    protected int operacion(int x, int y) {
        int max = Integer.MIN_VALUE;
        int[][] kernel = estructurante.getKernel();

        for (int ky = 0; ky < estructurante.getHeight(); ky++) {
            for (int kx = 0; kx < estructurante.getWidth(); kx++) {
                if (kernel[ky][kx] == 1) {
                    int imageX = x + kx - estructurante.getCenterX();
                    int imageY = y + ky - estructurante.getCenterY();
                    
                    if (imageX >= 0 && imageX < matriz[0].length && 
                        imageY >= 0 && imageY < matriz.length) {
                        max = Math.max(max, matriz[imageY][imageX]);
                    }
                }
               
            }
        }

        return max == Integer.MIN_VALUE ? matriz[y][x] : max;
    }
}
