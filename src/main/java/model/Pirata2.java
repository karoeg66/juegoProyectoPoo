package model;

/**
 * Representa al enemigo Pirata 2.
 * <p>
 * Este enemigo es un pirata de combate cuerpo a cuerpo que utiliza espada.
 * Es ligeramente más resistente y poderoso que {@code Pirata1}.
 * <p>
 * Características principales:
 * - Tipo: PIRATA2
 * - Vida máxima: 45 puntos
 * - Tamaño: 48x48 píxeles
 * - Velocidad de movimiento: 1
 * - Daño por ataque: 9
 * - Alcance del ataque: 200 píxeles
 * - Tiempo de recarga entre ataques: 1000 ms
 * - No utiliza proyectiles
 * <p>
 * Hereda de la clase {@code Enemy}, la cual proporciona la lógica
 * general de movimiento, combate, animación y colisiones.
 *
 */
class Pirata2 extends Enemy {

    /**
     * Cantidad máxima de vida del enemigo.
     */
    private static final int VIDA_MAX = 45;

    /**
     * Constructor de la clase Pirata2.
     * <p>
     * Crea un enemigo de tipo {@code PIRATA2} en la posición indicada.
     * <p>
     * Parámetros enviados al constructor de {@code Enemy}:
     * - TipoEnemigo.PIRATA2
     * - Posición inicial (x, y)
     * - Ancho: 48 px
     * - Alto: 48 px
     * - Vida máxima: 45
     * - Velocidad: 1
     * - Daño: 9
     * - Alcance: 200 px
     * - Cooldown entre ataques: 1000 ms
     * - Usa proyectiles: false
     *
     * @param x Coordenada horizontal inicial del enemigo.
     * @param y Coordenada vertical inicial del enemigo.
     */
    public Pirata2(int x, int y) {
        super(
                TipoEnemigo.PIRATA2,
                x,
                y,
                48,
                48,
                VIDA_MAX,
                1,
                9,
                200,
                1000,
                false
        );
    }

    /**
     * Actualiza el estado del enemigo.
     * <p>
     * En esta implementación, únicamente se actualiza la animación
     * del sprite según el estado actual del personaje.
     */
    @Override
    public void update() {
        actualizarAnimacion();
    }

    /**
     * Retorna la vida máxima del enemigo.
     *
     * @return Valor máximo de vida de Pirata2.
     */
    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }
}
