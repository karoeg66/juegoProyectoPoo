package model;

import model.enums.TipoPersonaje;
import java.awt.Color;

/**
 * Representa al personaje jugable Luffy.
 * <p>
 * Luffy posee habilidades especiales derivadas de su cuerpo de goma:
 * es inmune a los ataques de bala, pero recibe el doble de daño
 * cuando es atacado con espadas.
 */
public class Luffy extends Player {

    /**
     * Crea una nueva instancia de Luffy.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     * @param mapWidth ancho total del mapa.
     * @param mapHeight altura total del mapa.
     */
    public Luffy(int x, int y, int mapWidth, int mapHeight) {
        super(TipoPersonaje.LUFFY, x, y, mapWidth, mapHeight, 4, 8);
    }

    /**
     * Luffy es inmune a los ataques de bala debido a su cuerpo de goma.
     *
     * @param dano cantidad de daño que recibiría normalmente.
     */
    @Override
    public void recibirDanoBala(int dano) {
        // Inmune a las balas.
    }

    /**
     * Luffy recibe el doble de daño cuando es atacado con espadas.
     *
     * @param dano daño base del ataque.
     */
    @Override
    public void recibirDanoEspada(int dano) {
        super.recibirDano(dano * 2);
    }

    /**
     * Retorna el color utilizado como representación temporal
     * del personaje cuando no se dispone de un sprite.
     *
     * @return color rojo.
     */
    @Override
    protected Color getColorPlaceholder() {
        return Color.RED;
    }

    @Override
    public void ataque() {

    }

    @Override
    public void recibirdano(int damage) {

    }
}
