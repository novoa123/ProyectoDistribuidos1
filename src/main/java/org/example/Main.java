package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Morfología Matemática - Erosión y Dilatación ===");
        System.out.println("1. Ejecutar operación individual");
        System.out.println("2. Experimento completo (Problema 2)");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        if (opcion == 1) {
            ejecutarOperacionIndividual(scanner);
        } else if (opcion == 2) {
            ejecutarExperimentoCompleto(scanner);
        } else {
            System.out.println("Opción no válida.");
        }

        scanner.close();
    }

  
    private static void limpiarCarpetaResultados() {
        File carpetaResultados = new File("resultados");
        
        // si no existe, al carpeta de imahenes, se crea
        if (!carpetaResultados.exists()) {
            carpetaResultados.mkdirs();
            System.out.println("Carpeta 'resultados' creada.");
            return;
        }
        
        // se obtienen los archvivos de la carpeta
        File[] archivos = carpetaResultados.listFiles();
        
        if (archivos != null) {
            int archivosEliminados = 0;
            for (File archivo : archivos) {
                // solo se eliminan archivos de formatos para imagen
                if (archivo.isFile() && 
                    (archivo.getName().toLowerCase().endsWith(".png") ||
                     archivo.getName().toLowerCase().endsWith(".jpg") ||
                     archivo.getName().toLowerCase().endsWith(".jpeg"))) {
                    
                    if (archivo.delete()) {
                        archivosEliminados++;
                        System.out.println("Eliminado: " + archivo.getName());
                    } else {
                        System.out.println("No se pudo eliminar: " + archivo.getName());
                    }
                }
            }
            
            if (archivosEliminados > 0) {
                System.out.println("Se eliminaron " + archivosEliminados + " imágenes de la carpeta resultados.");
            } else {
                System.out.println("No se encontraron imágenes para eliminar en la carpeta resultados.");
            }
        }
    }

    //se ejecuta solamente una operacion individual
    private static void ejecutarOperacionIndividual(Scanner scanner) {
        try {
            System.out.println("--- Operación Individual ---");
            System.out.print("Ingrese la ruta de la imagen: ");
            String rutaImagen = scanner.nextLine();
            Image procesador = new Image(rutaImagen);
            System.out.println("Imagen cargada: " + procesador.getLargo() + "x" + procesador.getAncho() + " píxeles");

            System.out.println("Selecciona el elemento estructurante:");
            System.out.println("1. Esquina Superior Derecha (3x3)");
            System.out.println("2. Esquina Inferior Derecha (3x3)");
            System.out.println("3. Superior (3x3)");
            System.out.println("4. Medio (3x3)");
            System.out.println("5. Cruz (3x3)");
            System.out.print("Opción: ");
            int opcionEstructurante = scanner.nextInt();
            scanner.nextLine();

            Estructurante estructurante = null;
            switch (opcionEstructurante) {
                case 1:
                    estructurante = Estructurante.ESTRUCTURANTE_1();
                    break;
                case 2:
                    estructurante = Estructurante.ESTRUCTURANTE_2();
                    break;      
                case 3:
                    estructurante = Estructurante.ESTRUCTURANTE_3();
                    break;
                case 4:
                    estructurante = Estructurante.ESTRUCTURANTE_4();          
                    break;
                case 5:
                    estructurante = Estructurante.ESTRUCTURANTE_5();
                    break;
                default:
                    System.out.println("Opción no válida.");
                    return;
            }

            System.out.println("Selecciona la operación morfológica:");
            System.out.println("1. Erosión");
            System.out.println("2. Dilatación");
            System.out.print("Opción: ");
            int opcionOperacion = scanner.nextInt();
            
            System.out.println("\nSeleccione el modo de procesamiento:");
            System.out.println("1. Secuencial");
            System.out.println("2. Paralelo");
            System.out.println("3. Comparación (ambos)");
            System.out.print("Opción: ");
            int opcionModo = scanner.nextInt();

            int[][][] rgbMatriz = procesador.toMatrizRGB();
            int[][][] resultado = null;

            if(opcionOperacion == 1) {
                if(opcionModo == 1) {
                    System.out.println("Aplicando Erosión Secuencial...");
                    resultado = RGB_Morfologia.aplicarErosionRGB(rgbMatriz, estructurante, false);
                } else if(opcionModo == 2) {
                    System.out.println("Aplicando Erosión Paralelo...");
                    resultado = RGB_Morfologia.aplicarErosionRGB(rgbMatriz, estructurante, true);
                } else if(opcionModo == 3) {
                    System.out.println("Aplicando Erosión Secuencial...");
                    int[][][] resultadoSeq = RGB_Morfologia.aplicarErosionRGB(rgbMatriz, estructurante, false);
                    System.out.println("Aplicando Erosión Paralelo...");
                    resultado = RGB_Morfologia.aplicarErosionRGB(rgbMatriz, estructurante, true);
                    MatrizComparator.compararMatrices(resultadoSeq, resultado);

                } else {
                    System.out.println("Opción de modo no válida.");
                    return;
                }
            } else if(opcionOperacion == 2) {
                if(opcionModo == 1) {
                    System.out.println("Aplicando Dilatación Secuencial...");
                    resultado = RGB_Morfologia.aplicarDilatacionRGB(rgbMatriz, estructurante, false);
                } else if(opcionModo == 2) {
                    System.out.println("Aplicando Dilatación Paralelo...");
                    resultado = RGB_Morfologia.aplicarDilatacionRGB(rgbMatriz, estructurante, true);
                } else if(opcionModo == 3) {
                    System.out.println("Aplicando Dilatación Secuencial...");
                    int[][][] resultadoSeq = RGB_Morfologia.aplicarDilatacionRGB(rgbMatriz, estructurante, false);
                    System.out.println("Aplicando Dilatación Paralelo...");
                    resultado = RGB_Morfologia.aplicarDilatacionRGB(rgbMatriz, estructurante, true);
                    MatrizComparator.compararMatrices(resultadoSeq, resultado);

                } else {
                    System.out.println("Opción de modo no válida.");
                    return;
                }
            } else {
                System.out.println("Opción de operación no válida.");
                return;
            }

            limpiarCarpetaResultados();

            String outputPath = "resultados/output_" + 
                               (opcionOperacion == 1 ? "erosion" : "dilation") + "_" +
                               System.currentTimeMillis() + ".png";
            procesador.matrizRGBaImagen(resultado, outputPath);
            System.out.println("Imagen procesada guardada en: " + outputPath);
            
        } catch (IOException e) {
            System.out.println("Error al procesar la imagen: " + e.getMessage());
        }
    }


    private static void ejecutarExperimentoCompleto(Scanner scanner) {
        try {
            System.out.println("--- Experimento Completo ---");

        System.out.println("Ingrese la ruta de la imagen: ");
        String rutaImagen = scanner.nextLine();

        Image procesador = new Image(rutaImagen);
        System.out.println("Imagen cargada: " + procesador.getLargo() + "x" + procesador.getAncho() + " píxeles");

        int[][][] rgbMatriz = procesador.toMatrizRGB();

        Estructurante[] estructurantes = {
            Estructurante.ESTRUCTURANTE_1(),
            Estructurante.ESTRUCTURANTE_2(),
            Estructurante.ESTRUCTURANTE_3(),
            Estructurante.ESTRUCTURANTE_4(),
            Estructurante.ESTRUCTURANTE_5()
        };

        String[] nombresEstructurantes = {
            "Esquina Superior Derecha",
            "Esquina Inferior Derecha",
            "Superior",
            "Medio",
            "Cruz"
        };

        ResultadosExperimento resultados = new ResultadosExperimento();

        // se limpia la carpeta de resultados antes de comenzar
        System.out.println("Limpiando carpeta de resultados...");
        limpiarCarpetaResultados();
        
        System.out.println("Iniciando experimento...");

        for (int i = 0; i < estructurantes.length; i++) {
            
            System.out.println(" --- Elemento Estructurante: " + nombresEstructurantes[i] + " --- ");

            System.out.println("Aplicando Erosión Secuencial...");
            long tiempoInicio = System.currentTimeMillis();
            int[][][] erosionSeq = RGB_Morfologia.aplicarErosionRGB(rgbMatriz, estructurantes[i], false);
            long tiempoSeq = System.currentTimeMillis() - tiempoInicio;

            System.out.println("Aplicando Erosión Paralelo...");
            tiempoInicio = System.currentTimeMillis();
            int[][][] erosionPar = RGB_Morfologia.aplicarErosionRGB(rgbMatriz, estructurantes[i], true);
            long tiempoPar = System.currentTimeMillis() - tiempoInicio;
            
            System.out.println("Comparando resultados de Erosión...");
            MatrizComparator.compararMatrices(erosionSeq, erosionPar);
            
            resultados.agregarResultado("Erosión", nombresEstructurantes[i], tiempoSeq, tiempoPar, procesador.getLargo(), procesador.getAncho());

            System.out.println("Aplicando Dilatación Secuencial...");
            tiempoInicio = System.currentTimeMillis();
            int[][][] dilatacionSeq = RGB_Morfologia.aplicarDilatacionRGB(rgbMatriz, estructurantes[i], false);
            tiempoSeq = System.currentTimeMillis() - tiempoInicio;

            System.out.println("Aplicando Dilatación Paralelo...");
            tiempoInicio = System.currentTimeMillis();
            int[][][] dilatacionPar = RGB_Morfologia.aplicarDilatacionRGB(rgbMatriz, estructurantes[i], true);
            tiempoPar = System.currentTimeMillis() - tiempoInicio;

            System.out.println("Comparando resultados de Dilatación...");
            MatrizComparator.compararMatrices(dilatacionSeq, dilatacionPar);

            resultados.agregarResultado("Dilatación", nombresEstructurantes[i], tiempoSeq, tiempoPar, procesador.getLargo(), procesador.getAncho());

            String outputErosion = "resultados/output_erosion_" + nombresEstructurantes[i].replace(" ", "_") + "_.png";
            String outputDilatacion = "resultados/output_dilatacion_" + nombresEstructurantes[i].replace(" ", "_") + "_.png";

            procesador.matrizRGBaImagen(erosionPar, outputErosion);
            procesador.matrizRGBaImagen(dilatacionPar, outputDilatacion);

            System.out.println("Imagenes guardadas: " + outputErosion + ", " + outputDilatacion);
            System.out.println();
            }

            resultados.mostrarResultados();

            resultados.guardarResultadosCSV("resultados_experimento.csv");
            System.out.println("Resultados guardados en resultados_experimento.csv");
            System.out.println("Experimento completado.");

        } catch (IOException e) {
            System.out.println("Error al procesar la imagen: " + e.getMessage());
        }
        
        

    }
}