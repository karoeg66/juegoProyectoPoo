package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase utilitaria para la gestion y manipulacion de archivos de texto.
 * Permite leer el contenido de un archivo y escribir listas de texto en el.
 */

public class FileManager {

    /**
     * Lee el contenido de un archivo de texto linea por linea.
     * Si el archivo no existe o no se puede abrir, muestra un error y devuelve una lista vacia.
     * * @param file El archivo que se desea leer
     * @return Lista de cadenas con el contenido de cada linea del archivo
     */

    public static List<String> leerFile(File file) {
        final var lista = new ArrayList<String>(); //lista del contenido del file
        try {
            final var scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                lista.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Error al abrir el file");
            System.err.printf("FileNotFoundException: %s%n", fileNotFoundException.getLocalizedMessage());
        }
        return lista;
    }

    /**
     * Escribe una lista de cadenas de texto en un archivo, sobrescribiendo su contenido.
     * Añade un salto de linea automaticamente despues de cada elemento escrito.
     * * @param file El archivo en el cual se va a escribir
     * @param lista La lista de cadenas con las lineas a escribir
     */


    public static void escribirFile(File file, List<String> lista) {
        try {
            final var fileWriter = new FileWriter(file);

            for (var linea : lista) {
                final var lineaEscribir = String.format("%s%n", linea); //escribo la línea y un salto de línea
                fileWriter.write(lineaEscribir);
            }

            fileWriter.close();
        } catch (IOException ioException) {
            System.out.println("Error al escribir en el file");
            System.err.printf("IOException: %s%n", ioException.getLocalizedMessage());
        }
    }
}
