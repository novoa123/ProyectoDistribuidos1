package org.example;

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
