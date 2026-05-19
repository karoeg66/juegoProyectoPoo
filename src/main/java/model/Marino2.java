package model;

/**
 * Representa el enemigo básico Marino 2.
 * <p>
 * Este enemigo pertenece a la Marina y utiliza arma de fuego,
 * por lo que combate a distancia.
 * <p>
 * Características principales:
 * - Vida máxima: 40 puntos.
 * - Tamaño del sprite: 48x48 píxeles.
 * - Velocidad de movimiento: 1.
 * - Daño de ataque: 14 puntos.
 * - Alcance de ataque: 320 píxeles.
 * - Tiempo de recarga entre ataques: 1400 ms.
 * - Utiliza ataques a distancia.
 * <p>
 * La animación del enemigo se actualiza en cada ciclo del juego
 * mediante el método update().
 *
 */
class Marino2 extends Enemy {

    /**
     * Cantidad máxima de vida del enemigo.
     */
    private static final int VIDA_MAX = 40;

    /**
     * Constructor del enemigo Marino 2.
     * <p>
     * Crea una instancia del enemigo en la posición indicada y
     * configura todos sus atributos base.
     * <p>
     * Parámetros enviados al constructor de la clase Enemy:
     * - Tipo: TipoEnemigo.MARINE2
     * - Posición inicial: (x, y)
     * - Ancho: 48 píxeles
     * - Alto: 48 píxeles
     * - Vida máxima: 40
     * - Velocidad: 1
     * - Daño: 14
     * - Alcance: 320 píxeles
     * - Cooldown de ataque: 1400 ms
     * - Ataque a distancia: true
     *
     * @param x Posición horizontal inicial del enemigo.
     * @param y Posición vertical inicial del enemigo.
     */
    public Marino2(int x, int y) {
        super(TipoEnemigo.MARINE2, x, y, 48, 48, VIDA_MAX, 1, 14, 320, 1400, true);
    }

    /**
     * Actualiza el estado del enemigo.
     * <p>
     * En este caso, únicamente actualiza la animación actual
     * del sprite.
     */
    @Override
    public void update() {
        actualizarAnimacion();
    }

    /**
     * Retorna la vida máxima del enemigo.
     *
     * @return Vida máxima del Marino 2.
     */
    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }
}