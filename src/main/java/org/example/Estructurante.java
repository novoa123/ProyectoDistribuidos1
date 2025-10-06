package org.example;

public class Estructurante {
    
    private int [][] kernel;
    private int centerX;
    private int centerY;


    public static Estructurante CRUZ_3X3 () {
        int[][] kernel = {
            {0, 1, 0},
            {1, 1, 1},
            {0, 1, 0}
        };

        return new Estructurante(kernel, 1, 1);
    }

    public static Estructurante CUADRADO_3X3 () {
        int[][] kernel = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        };

        return new Estructurante(kernel, 1, 1);
    }


    public static Estructurante LINEA_VERTICAL_3X3 () {
        int[][] kernel = {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
        };

        return new Estructurante(kernel, 1, 1);
    }

    public static Estructurante ESTRUCTURANTE_1 () {
        int[][] kernel = {
            {0, 0, 0},
            {1, 1, 0},
            {0, 1, 0}
        };

        return new Estructurante(kernel, 1, 1);
    }

    public static Estructurante ESTRUCTURANTE_2 () {
        int[][] kernel = {
            {0, 1, 0},
            {1, 1, 0},
            {0, 0, 0}
        };

        return new Estructurante(kernel, 1, 1);
    }

    public static Estructurante ESTRUCTURANTE_3 () {
        int[][] kernel = {
            {0, 0, 0},
            {1, 1, 1},
            {0, 0, 0}
        };

        return new Estructurante(kernel, 1, 1);
    }

    public static Estructurante ESTRUCTURANTE_4 () {
        int[][] kernel = {
            {0, 0, 0},
            {0, 1, 0},
            {0, 1, 0}
        };

        return new Estructurante(kernel, 1, 1);
    }

    public static Estructurante ESTRUCTURANTE_5 () {
        int[][] kernel = {
            {1, 0, 1},
            {0, 1, 0},
            {1, 0, 1}
        };

        return new Estructurante(kernel, 1, 1);
    }

    public Estructurante(int[][] kernel, int centerX, int centerY) {
        this.kernel = kernel;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public int[][] getKernel() {
        return kernel;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getWidth() {
        return kernel[0].length;
    }

    public int getHeight() {
        return kernel.length;
    }

}
