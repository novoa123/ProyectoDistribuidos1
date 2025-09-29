package org.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

// clase para manejar lectura y  conversion de las imagenes
// se maneja la conversion RGB

public class Image {

    private BufferedImage image;
    private int ancho;
    private int largo;

    public Image(String resourcePath) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("No se encontró la imagen en resources: " + resourcePath);
            }
            this.image = ImageIO.read(is);
            this.ancho = image.getWidth();
            this.largo = image.getHeight();
        }
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

    public int getAncho() {
        return ancho;
    }

    public int getLargo() {
        return largo;
    }
}
