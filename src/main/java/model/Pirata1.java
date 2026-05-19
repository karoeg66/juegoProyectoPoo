package model;

import model.enums.TipoEnemigo;

/**
 * Representa al enemigo Pirata 1.
 * <p>
 * Este enemigo es un pirata de combate cuerpo a cuerpo que utiliza espada.
 * Posee una cantidad moderada de vida, se mueve lentamente y realiza
 * ataques de corto alcance.
 * <p>
 * Características principales:
 * - Tipo: PIRATA1
 * - Vida máxima: 40 puntos
 * - Tamaño: 48x48 píxeles
 * - Velocidad de movimiento: 1
 * - Daño por ataque: 8
 * - Alcance del ataque: 200 píxeles
 * - Tiempo de recarga entre ataques: 1000 ms
 * - No utiliza proyectiles
 * <p>
 * Hereda de la clase {@code Enemy}, la cual proporciona toda la lógica
 * general de movimiento, combate, animación y detección de colisiones.
 *
 * @author Sebastian
 * @version 1.0
 */
class Pirata1 extends Enemy {

    /**
     * Cantidad máxima de vida del enemigo.
     * <p>
     * Este valor se utiliza tanto para inicializar la vida actual
     * como para mostrar la barra de salud.
     */
    private static final int VIDA_MAX = 40;

    /**
     * Constructor de la clase Pirata1.
     * <p>
     * Crea un enemigo de tipo PIRATA1 en la posición indicada.
     * <p>
     * Parámetros enviados al constructor de {@code Enemy}:
     * - TipoEnemigo.PIRATA1
     * - Posición inicial (x, y)
     * - Ancho: 48 px
     * - Alto: 48 px
     * - Vida máxima: 40
     * - Velocidad: 1
     * - Daño: 8
     * - Alcance: 200 px
     * - Cooldown entre ataques: 1000 ms
     * - Usa proyectiles: false
     *
     * @param x Coordenada horizontal inicial.
     * @param y Coordenada vertical inicial.
     */
    public Pirata1(int x, int y) {
        super(
                TipoEnemigo.PIRATA1,
                x,
                y,
                48,
                48,
                VIDA_MAX,
                1,
                8,
                200,
                1000,
                false
        );
    }

    /**
     * Actualiza el estado del enemigo.
     * <p>
     * En esta implementación, únicamente actualiza la animación
     * del sprite según el estado actual del enemigo.
     */
    @Override
    public void update() {
        actualizarAnimacion();
    }

    /**
     * Retorna la vida máxima del enemigo.
     *
     * @return Valor máximo de vida de Pirata1.
     */
    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }
}
