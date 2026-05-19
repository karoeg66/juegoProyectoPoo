package model;

import model.enums.TipoEnemigo;

/**
 * Representa al enemigo Pirata 3.
 * <p>
 * Este enemigo combate a distancia utilizando un arma de fuego.
 * A diferencia de los otros piratas, puede disparar proyectiles al
 * jugador desde una mayor distancia.
 * <p>
 * Características principales:
 * - Tipo: PIRATA3
 * - Vida máxima: 35 puntos
 * - Tamaño: 48x48 píxeles
 * - Velocidad de movimiento: 1
 * - Daño por ataque: 12
 * - Alcance del ataque: 300 píxeles
 * - Tiempo de recarga entre ataques: 1500 ms
 * - Usa proyectiles: sí
 * <p>
 * Aunque posee menos vida que otros enemigos, su capacidad de atacar
 * a distancia lo convierte en un adversario peligroso.
 * <p>
 * Hereda de la clase {@code Enemy}, que implementa la lógica general
 * de movimiento, combate, colisiones y animaciones.
 *
 */
class Pirata3 extends Enemy {

    /**
     * Cantidad máxima de vida del enemigo.
     */
    private static final int VIDA_MAX = 35;

    /**
     * Constructor de la clase Pirata3.
     * <p>
     * Crea un enemigo de tipo {@code PIRATA3} en la posición indicada.
     * <p>
     * Parámetros enviados al constructor de {@code Enemy}:
     * - TipoEnemigo.PIRATA3
     * - Posición inicial (x, y)
     * - Ancho: 48 px
     * - Alto: 48 px
     * - Vida máxima: 35
     * - Velocidad: 1
     * - Daño: 12
     * - Alcance: 300 px
     * - Cooldown entre ataques: 1500 ms
     * - Usa proyectiles: true
     *
     * @param x Coordenada horizontal inicial del enemigo.
     * @param y Coordenada vertical inicial del enemigo.
     */
    public Pirata3(int x, int y) {
        super(
                TipoEnemigo.PIRATA3,
                x,
                y,
                48,
                48,
                VIDA_MAX,
                1,
                12,
                300,
                1500,
                true
        );
    }

    /**
     * Actualiza el estado del enemigo.
     * <p>
     * En esta implementación, únicamente se actualiza la animación
     * del sprite según el estado actual del enemigo.
     */
    @Override
    public void update() {
        actualizarAnimacion();
    }

    /**
     * Retorna la vida máxima del enemigo.
     *
     * @return Valor máximo de vida de Pirata3.
     */
    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }
}
