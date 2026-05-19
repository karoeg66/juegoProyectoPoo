package model;

import model.enums.TipoPersonaje;

import java.awt.*;

public class Zoro extends Player {

    public Zoro(int x, int y, int mapWidth, int mapHeight) {
        super(TipoPersonaje.ZORO, x, y, mapWidth, mapHeight, 3, 12);
    }

    /**
     * Zoro recibe daño normal de balas.
     */
    @Override
    public void recibirDanoBala(int dano) {
        super.recibirDano(dano);
    }

    /**
     * Zoro recibe daño normal de espadas.
     */
    @Override
    public void recibirDanoEspada(int dano) {
        super.recibirDano(dano);
    }

    @Override
    protected Color getColorPlaceholder() {
        return Color.GREEN;
    }
}
