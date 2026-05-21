package controller;


import java.io.File;
import java.util.List;


public class Historial {
    FileManager fileManager = new FileManager();

    public void cargarHistorial() {
        File file = new File("resources/utilities/historial");
        List <String> lineas = fileManager.leerFile(file);
        for (String linea : lineas) {
            String[] lineaSplit = linea.split(";");
            String nombre = lineaSplit[0];
            int puntaje = Integer.parseInt(lineaSplit[1]);
        }
    }

    public void guardarHistorial() {
        File file = new File("resources/utilities/historial");

    }
}
