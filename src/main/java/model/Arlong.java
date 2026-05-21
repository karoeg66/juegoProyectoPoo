package model;

import model.enums.DireccionVisual;

/**
 * Representa al jefe final Arlong.
 * <p>
 * Este enemigo hereda de {@link Boss} y posee un sistema de fases que modifica
 * el daño de sus ataques. Además, controla animaciones de ataque, caminata,
 * reacción e inactividad.
 */
public class Arlong extends Boss {

    /**
     * Vida máxima del jefe.
     */
    private static final int VIDA_MAX = 10;

    /**
     * Tiempo mínimo entre ataques consecutivos en milisegundos.
     */
    private static final long COOLDOWN_ATAQUE = 2500;

    /**
     * Duración de la animación de ataque en milisegundos.
     */
    private static final long DURACION_ATAQUE = 600;

    /**
     * Duración del estado de inactividad después de atacar en milisegundos.
     */
    private static final long DURACION_IDLE = 800;

    /**
     * Intervalo mínimo entre repeticiones de la animación de reacción.
     */
    private static final long INTERVALO_REACCION = 12000;

    /**
     * Velocidad con la que Arlong persigue al jugador.
     */
    private static final int VEL_PERSEGUIR = 2;

    /**
     * Momento en que se realizó el último ataque.
     */
    private long tiempoUltimoAtaque;

    /**
     * Indica si Arlong se encuentra ejecutando la animación de ataque.
     */
    private boolean enAnimacionAtaque = false;

    /**
     * Indica si Arlong se encuentra en estado de inactividad.
     */
    private boolean enAnimacionIdle = false;

    /**
     * Momento en que comenzó la animación de ataque.
     */
    private long tiempoInicioAnimAtaque = 0;

    /**
     * Momento en que comenzó el estado de inactividad.
     */
    private long tiempoInicioIdle = 0;

    /**
     * Momento en que se reprodujo por última vez la animación de reacción.
     */
    private long tiempoUltimaReaccion = 0;

    /**
     * Crea una nueva instancia del jefe Arlong.
     *
     * @param x posición inicial en el eje X.
     * @param y posición inicial en el eje Y.
     */
    public Arlong(int x, int y) {
        super(x, y, 80, 80, VIDA_MAX, VEL_PERSEGUIR, 330, 165, 80);
        this.tiempoUltimoAtaque = 0;
        this.dirVisual = DireccionVisual.IZQUIERDA;
        this.totalFramesMuerte = 8;
    }

    /**
     * Actualiza el estado general del jefe.
     * <p>
     * Si Arlong ha muerto, únicamente actualiza la animación de muerte.
     * En caso contrario, actualiza la animación actual, la fase de animación
     * y la fase del combate.
     */

    public void update() {
        if (estadoAnimacion.equals("morir")) {
            actualizarAnimacion();
            return;
        }
        actualizarAnimacion();
        actualizarFaseAnimacion();
        cambiarFase();
    }

    /**
     * Controla las transiciones entre las animaciones de ataque,
     * inactividad y caminata.
     */
    private void actualizarFaseAnimacion() {
        long ahora = System.currentTimeMillis();

        if (enAnimacionAtaque) {
            if (ahora - tiempoInicioAnimAtaque >= DURACION_ATAQUE) {
                enAnimacionAtaque = false;
                enAnimacionIdle = true;
                tiempoInicioIdle = ahora;

                if (ahora - tiempoUltimaReaccion >= INTERVALO_REACCION) {
                    setEstadoAnimacion("reaccion");
                    tiempoUltimaReaccion = ahora;
                } else {
                    setEstadoAnimacion("caminar");
                }
            }
            return;
        }

        if (enAnimacionIdle) {
            if (ahora - tiempoInicioIdle >= DURACION_IDLE) {
                enAnimacionIdle = false;
                setEstadoAnimacion("caminar");
            }
            return;
        }

        setEstadoAnimacion("caminar");
    }

    /**
     * Hace que Arlong persiga al jugador ajustando su dirección y posición.
     *
     * @param p jugador al que debe perseguir.
     */
    public void perseguirJugador(Player p) {
        if (estadoAnimacion.equals("morir")) return;
        if (enAnimacionAtaque || enAnimacionIdle) return;

        int dx = 0, dy = 0;

        if (p.getX() < x) {
            dx = -1;
            dirVisual = DireccionVisual.IZQUIERDA;
        } else if (p.getX() > x) {
            dx = 1;
            dirVisual = DireccionVisual.DERECHA;
        }

        if (p.getY() < y) {
            dy = -1;
        } else if (p.getY() > y) {
            dy = 1;
        }

        mover(dx, dy);
    }



    /**
     * Retorna el daño del ataque según la fase actual del jefe.
     *
     * @return daño infligido por Arlong.
     */
    public int getDanoAtaque() {
        return switch (fase) {
            case 1 -> 2;
            case 2 -> 4;
            case 3 -> 8;
            default -> 2;
        };
    }

    /**
     * Indica si Arlong está listo para atacar.
     *
     * @return {@code true} si puede atacar, {@code false} en caso contrario.
     */
    public boolean puedeAtacar() {
        return !estadoAnimacion.equals("morir") &&
                !enAnimacionAtaque &&
                !enAnimacionIdle &&
                System.currentTimeMillis() - tiempoUltimoAtaque >= COOLDOWN_ATAQUE;
    }

    /**
     * Indica si actualmente se está reproduciendo la animación de ataque.
     *
     * @return {@code true} si Arlong está atacando.
     */
    public boolean estaAtacando() {
        return enAnimacionAtaque;
    }



    /**
     * Activa la animación de reacción si ha transcurrido
     * el tiempo mínimo desde la última vez.
     */
    @Override
    public void animacionIdle() {
        long ahora = System.currentTimeMillis();
        if (ahora - tiempoUltimaReaccion >= INTERVALO_REACCION) {
            setEstadoAnimacion("reaccion");
            tiempoUltimaReaccion = ahora;
        }
    }

    @Override
    public void patronAtaque() {
        //No
    }

    /**
     * Retorna la vida máxima de Arlong.
     *
     * @return vida máxima del jefe.
     */
    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }

    /**
     * Retorna el nombre del jefe.
     *
     * @return cadena con el nombre "Arlong".
     */
    @Override
    public String getNombre() {
        return "Arlong";
    }

    /**
     * Inicia el ataque de Arlong si el tiempo de recarga ha terminado.
     * La animación utilizada depende de la fase actual del jefe.
     */


    @Override
    public void ataque() {

        long ahora = System.currentTimeMillis();

        if (!enAnimacionAtaque && !enAnimacionIdle &&
                ahora - tiempoUltimoAtaque >= COOLDOWN_ATAQUE) {

            tiempoUltimoAtaque = ahora;
            tiempoInicioAnimAtaque = ahora;
            enAnimacionAtaque = true;

            setEstadoAnimacion(claveAtaqueFase());
        }

    }
}
