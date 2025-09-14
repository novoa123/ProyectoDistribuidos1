package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {

    private BufferedImage image;
    private int ancho;
    private int largo;

    public Image(String path) throws IOException {
        this.image = ImageIO.read(new File(path));
        this.ancho = image.getWidth();
        this.largo = image.getHeight();
    }


    public int[][] toMatrizGris(){
        int[][] matriz = new int[largo][ancho];
        
        for (int y = 0; y < largo; y++) {
            for (int x = 0; x < ancho; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                matriz[y][x] = (int)(0.299 * r + 0.587 * g + 0.114 * b);
            }
        }
        return matriz;
    }

    public int[][][] toMatrizRGB(){
        int[][][] rgbMatriz = new int[3][largo][ancho];

        for (int y = 0; y < largo; y++) {
            for (int x = 0; x < ancho; x++) {
                int rgb = image.getRGB(x, y);
                rgbMatriz[0][y][x] = (rgb >> 16) & 0xFF; // R
                rgbMatriz[1][y][x] = (rgb >> 8) & 0xFF;  // G
                rgbMatriz[2][y][x] = rgb & 0xFF;         // B
            }
        }

        return rgbMatriz;
    }

    public void matrizGrisAImagen(int[][] matriz, String outputPath) throws IOException {
        BufferedImage output = new BufferedImage(ancho, largo, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < largo; y++) {
            for (int x = 0; x < ancho; x++) {
                int gris = Math.max(0, Math.min(255, matriz[y][x]));
                int rgb = (gris << 16) | (gris << 8) | gris;
                output.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(output, "png", new File(outputPath));
    }

    public void matrizRGBaImagen(int[][][] matriz, String outputPath) throws IOException {
        BufferedImage output = new BufferedImage(ancho, largo, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < largo; y++) {
            for (int x = 0; x < ancho; x++) {
                int r = Math.max(0, Math.min(255, matriz[0][y][x]));
                int g = Math.max(0, Math.min(255, matriz[1][y][x]));
                int b = Math.max(0, Math.min(255, matriz[2][y][x]));
                int rgb = (r << 16) | (g << 8) | b;
                output.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(output, "png", new File(outputPath));
    }
}
