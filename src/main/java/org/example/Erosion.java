package org.example;

// Clase que implementa la operación de Erosión morfológica
// Hereda de la clase base Morfologia
// Implementa el método abstracto 'operacion' para definir la lógica específica de la erosión
// La erosión reduce las regiones brillantes en una imagen, eliminando píxeles en los bordes de las regiones
// Utiliza el elemento estructurante para determinar cómo se aplica la erosión en la imagen
// El valor de cada píxel en la imagen resultante es el valor mínimo de los píxeles cubiertos por el elemento estructurante
// Maneja los bordes de la imagen utilizando padding con valor máximo
// Si el elemento estructurante no cubre ningún píxel válido, se mantiene el valor original del píxel

public class Erosion extends Morfologia {
    
    public Erosion(int[][] matriz, Estructurante estructurante) {
        super(matriz, estructurante);
    }

    @Override
    protected int operacion(int x, int y) {
        int min = Integer.MAX_VALUE;
        int[][] kernel = estructurante.getKernel();

        for (int ky = 0; ky < estructurante.getHeight(); ky++) {
            for (int kx = 0; kx < estructurante.getWidth(); kx++) {
                if (kernel[ky][kx] == 1) {
                    int imageX = x + kx - estructurante.getCenterX();
                    int imageY = y + ky - estructurante.getCenterY();
                    
                    if (imageX >= 0 && imageX < matriz[0].length && 
                        imageY >= 0 && imageY < matriz.length) {
                        min = Math.min(min, matriz[imageY][imageX]);
                    }
                }
               
            }
        }

        return min == Integer.MAX_VALUE ? matriz[y][x] : min;
    }
}
