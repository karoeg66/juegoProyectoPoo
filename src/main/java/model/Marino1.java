package model;

/**
 * Representa el enemigo básico Marino 1.
 * <p>
 * Este enemigo pertenece a la Marina y utiliza espada,
 * por lo que combate a corta distancia (cuerpo a cuerpo).
 * <p>
 * Características principales:
 * - Vida máxima: 50 puntos.
 * - Tamaño del sprite: 48x48 píxeles.
 * - Velocidad de movimiento: 1.
 * - Daño de ataque: 10 puntos.
 * - Alcance de ataque: 220 píxeles.
 * - Tiempo de recarga entre ataques: 900 ms.
 * - No utiliza ataques a distancia.
 * <p>
 * La animación del enemigo se actualiza en cada ciclo del juego
 * mediante el método update().
 *
 */
class Marino1 extends Enemy {

    /**
     * Cantidad máxima de vida del enemigo.
     */
    private static final int VIDA_MAX = 50;

    /**
     * Constructor del enemigo Marino 1.
     * <p>
     * Crea una instancia del enemigo en la posición indicada y
     * configura todos sus atributos base.
     * <p>
     * Parámetros enviados al constructor de la clase Enemy:
     * - Tipo: TipoEnemigo.MARINE1
     * - Posición inicial: (x, y)
     * - Ancho: 48 píxeles
     * - Alto: 48 píxeles
     * - Vida máxima: 50
     * - Velocidad: 1
     * - Daño: 10
     * - Alcance: 220 píxeles
     * - Cooldown de ataque: 900 ms
     * - Ataque a distancia: false
     *
     * @param x Posición horizontal inicial del enemigo.
     * @param y Posición vertical inicial del enemigo.
     */
    public Marino1(int x, int y) {
        super(TipoEnemigo.MARINE1, x, y, 48, 48, VIDA_MAX, 1, 10, 220, 900, false);
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
     * @return Vida máxima del Marino 1.
     */
    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }
}
