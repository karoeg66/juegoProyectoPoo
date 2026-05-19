package model;

import model.enums.TipoObjeto;

public class GameObjectFactory {

    public static GameObject crearObjeto(TipoObjeto tipo, int x, int y) {
        return switch (tipo) {
            case CARNE -> new Carne(x, y);
            case ESPECIAL -> new Especial(x, y);
        };
    }
}