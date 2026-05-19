package model;

import model.enums.TipoPersonaje;

import java.awt.*;

/**
 * Representa al personaje jugable Zoro.
 * <p>
 * Zoro no posee resistencias ni debilidades especiales frente a los
 * ataques de bala o espada, por lo que recibe el daño normal en ambos casos.
 */
public class Zoro extends Player {

    /**
     * Crea una nueva instancia de Zoro.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param mapWidth ancho total del mapa.
     * @param mapHeight altura total del mapa.
     */
    public Zoro(int x, int y, int mapWidth, int mapHeight) {
        super(TipoPersonaje.ZORO, x, y, mapWidth, mapHeight, 3, 12);
    }

    /**
     * Aplica daño por un ataque de bala.
     * Zoro recibe el daño completo sin modificaciones.
     *
     * @param dano cantidad de daño recibida.
     */
    @Override
    public void recibirDanoBala(int dano) {
        super.recibirDano(dano);
    }

    /**
     * Aplica daño por un ataque de espada.
     * Zoro recibe el daño completo sin modificaciones.
     *
     * @param dano cantidad de daño recibida.
     */
    @Override
    public void recibirDanoEspada(int dano) {
        super.recibirDano(dano);
    }

    /**
     * Retorna el color utilizado como representación temporal del personaje
     * cuando no se dispone de un sprite.
     *
     * @return color verde.
     */
    @Override
    protected Color getColorPlaceholder() {
        return Color.GREEN;
    }

    @Override
    public void ataque() {

    }

    @Override
    public void recibirdano(int damage) {

    }
}
