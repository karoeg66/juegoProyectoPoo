package controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Clase encargada de gestionar el historial de puntajes de los jugadores.
 * Se ocupa de guardar las puntuaciones en un archivo de texto y de procesar los datos
 * para calcular y mostrar el Top 3 de las mejores puntuaciones de forma ordenada.
 */


public class Historial {
    private static final File ARCHIVO = new File("historial_puntajes.txt");


    /**
     * Guarda un nuevo registro de juego agregándolo al final del archivo de texto.
     * @param nombre Nombre del jugador
     * @param puntaje Puntuación obtenida por el jugador
     */

    public static void guardar(String nombre, int puntaje) {
        List<String> lineas = FileManager.leerFile(ARCHIVO);

        lineas.add(nombre + ";" + puntaje);

        FileManager.escribirFile(ARCHIVO, lineas);
    }

    /**
     * Lee el archivo de puntuaciones, filtra los datos correctos y devuelve las 3 mejores.
     * Los resultados se ordenan de mayor a menor puntuación.
     * @return Lista con un máximo de 3 cadenas ordenadas con formato "nombre;puntaje"
     */

    public static List<String> obtenerTop3() {
        List<String> lineas = FileManager.leerFile(ARCHIVO);
        List<String> validas = new ArrayList<>();

        for (String linea : lineas) {
            String[] partes = linea.split(";");

            if (partes.length == 2) {
                validas.add(linea);
            }
        }

        validas.sort(Comparator.comparingInt(Historial::extraerPuntaje).reversed());

        if (validas.size() > 3) {
            return validas.subList(0, 3);
        }

        return validas;
    }

    /**
     * Extrae y convierte la puntuación numérica contenida en una linea de texto.
     * @param linea Cadena de texto con formato "nombre;puntaje"
     * @return El valor entero de la puntuación, o 0 si ocurre un fallo de conversión
     */

    private static int extraerPuntaje(String linea) {
        String[] partes = linea.split(";");

        try {
            return Integer.parseInt(partes[1]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
