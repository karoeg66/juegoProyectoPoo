package model;

import model.enums.TipoPersonaje;

import java.awt.*;

public class Sanji extends Player {

    public Sanji(int x, int y, int mapWidth, int mapHeight) {
        super(TipoPersonaje.SANJI, x, y, mapWidth, mapHeight, 5, 7);
    }

    /**
     * Sanji recibe daño normal de balas.
     */
    @Override
    public void recibirDanoBala(int dano) {
        super.recibirDano(dano);
    }

    /**
     * Sanji recibe daño normal de espadas.
     */
    @Override
    public void recibirDanoEspada(int dano) {
        super.recibirDano(dano);
    }

    @Override
    protected Color getColorPlaceholder() {
        return Color.YELLOW;
    }
}