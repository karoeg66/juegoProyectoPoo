package controller;


import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Historial {
    private static final File ARCHIVO = new File("historial_puntajes.txt");

    public static void guardar(String nombre, int puntaje) {
        List<String> lineas = FileManager.leerFile(ARCHIVO);

        lineas.add(nombre + ";" + puntaje);

        FileManager.escribirFile(ARCHIVO, lineas);
    }

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

    private static int extraerPuntaje(String linea) {
        String[] partes = linea.split(";");

        try {
            return Integer.parseInt(partes[1]);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
