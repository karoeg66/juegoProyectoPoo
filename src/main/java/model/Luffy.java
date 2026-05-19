package model;

import model.enums.TipoPersonaje;
import java.awt.Color;

public class Luffy extends Player {

    public Luffy(int x, int y, int mapWidth, int mapHeight) {
        super(TipoPersonaje.LUFFY, x, y, mapWidth, mapHeight, 4, 8);
    }

    /**
     * Luffy es de goma: las balas no le hacen daño.
     */
    @Override
    public void recibirDanoBala(int dano) {
        // Inmune, no hace nada
    }

    /**
     * Las espadas le hacen el doble de daño a Luffy.
     */
    @Override
    public void recibirDanoEspada(int dano) {
        super.recibirDano(dano * 2);
    }

    @Override
    protected Color getColorPlaceholder() {
        return Color.RED;
    }
}
