package model;

import model.enums.TipoPersonaje;

import java.awt.*;

/**
 * Representa al personaje jugable Sanji.
 * <p>
 * Sanji no posee resistencias ni debilidades especiales frente a los
 * distintos tipos de ataques, por lo que recibe el daño normal tanto
 * de balas como de espadas.
 */
public class Sanji extends Player {

    /**
     * Crea una nueva instancia de Sanji.
     *
     * @param x         posición inicial en el eje X.
     * @param y         posición inicial en el eje Y.
     * @param mapWidth  ancho total del mapa.
     * @param mapHeight altura total del mapa.
     */
    public Sanji(int x, int y, int mapWidth, int mapHeight) {
        super(TipoPersonaje.SANJI, x, y, mapWidth, mapHeight, 5, 7);
    }

    /**
     * Aplica daño por un ataque de bala.
     * Sanji recibe el daño completo sin modificaciones.
     *
     * @param dano cantidad de daño recibida.
     */
    @Override
    public void recibirDanoBala(int dano) {
        super.recibirDano(dano);
    }

    /**
     * Aplica daño por un ataque de espada.
     * Sanji recibe el daño completo sin modificaciones.
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
     * @return color amarillo.
     */
    @Override
    protected Color getColorPlaceholder() {
        return Color.YELLOW;
    }

    @Override
    public void ataque() {

    }

    @Override
    public void recibirdano(int damage) {

    }
}