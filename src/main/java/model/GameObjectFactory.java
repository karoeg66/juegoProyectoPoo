package model;

import model.enums.TipoObjeto;

/**
 * Fábrica encargada de crear instancias de objetos del juego
 * según el tipo solicitado.
 * <p>
 * Esta clase centraliza la lógica de creación de los objetos
 * coleccionables, facilitando la extensibilidad y el mantenimiento
 * del código.
 */
public class GameObjectFactory {

    /**
     * Crea un objeto del juego del tipo indicado en la posición especificada.
     *
     * @param tipo tipo de objeto que se desea crear.
     * @param x    posición en el eje X.
     * @param y    posición en el eje Y.
     * @return una instancia del objeto correspondiente al tipo indicado.
     */
    public static GameObject crearObjeto(TipoObjeto tipo, int x, int y) {
        return switch (tipo) {
            case CARNE -> new Carne(x, y);
            case ESPECIAL -> new Especial(x, y);
        };
    }
}