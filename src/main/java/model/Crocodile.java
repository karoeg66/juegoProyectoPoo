package model;

import model.enums.DireccionVisual;

/**
 * Representa al jefe Crocodile.
 * <p>
 * Este enemigo se desplaza verticalmente por el mapa y realiza ataques
 * de área que aumentan su daño a medida que cambia de fase.
 */
public class Crocodile extends Boss {

    /**
     * Vida máxima del jefe.
     */
    private static final int VIDA_MAX = 700;

    /**
     * Tiempo mínimo entre ataques consecutivos en milisegundos.
     */
    private static final long COOLDOWN_ATAQUE = 2000;

    /**
     * Duración de la animación de ataque en milisegundos.
     */
    private static final long DURACION_ATAQUE = 700;

    /**
     * Duración del estado de inactividad tras un ataque.
     */
    private static final long DURACION_IDLE = 1000;

    /**
     * Intervalo mínimo entre reproducciones de la animación de reacción.
     */
    private static final long INTERVALO_REACCION = 10000;

    /**
     * Generador de números aleatorios para futuras variaciones del comportamiento.
     */
    private final java.util.Random rng = new java.util.Random();

    /**
     * Límite superior del movimiento vertical.
     */
    private final int limiteArriba;

    /**
     * Límite inferior del movimiento vertical.
     */
    private final int limiteAbajo;

    /**
     * Altura total del mapa.
     */
    private final int mapHeight;

    /**
     * Dirección actual del movimiento vertical (1 abajo, -1 arriba).
     */
    private int movimientoVertical;

    /**
     * Momento en que se realizó el último ataque.
     */
    private long tiempoUltimoAtaque;

    /**
     * Indica si el jefe se encuentra ejecutando la animación de ataque.
     */
    private boolean enAnimacionAtaque = false;

    /**
     * Indica si el jefe está en estado de inactividad.
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
     * Crea una nueva instancia del jefe Crocodile.
     *
     * @param mapWidth  ancho total del mapa.
     * @param mapHeight altura total del mapa.
     */
    public Crocodile(int mapWidth, int mapHeight) {
        super(mapWidth - 120, mapHeight / 2 - 50,
                100, 100, VIDA_MAX, 3, 460, 230, 100);

        this.movimientoVertical = 1;
        this.tiempoUltimoAtaque = 0;
        this.mapHeight = mapHeight;
        this.limiteArriba = 50;
        this.limiteAbajo = mapHeight - 150;
        this.dirVisual = DireccionVisual.IZQUIERDA;
        this.totalFramesMuerte = 8;
    }

    /**
     * Actualiza el estado general del jefe.
     * Si ha muerto, únicamente reproduce la animación de muerte.
     */
    @Override
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
     * Controla la transición entre los estados de ataque,
     * inactividad y movimiento.
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
                    setEstadoAnimacion("reaccion");
                }
            }
            return;
        }

        if (enAnimacionIdle) {
            if (ahora - tiempoInicioIdle >= DURACION_IDLE) {
                enAnimacionIdle = false;
                setEstadoAnimacion("reaccion");
            }
            return;
        }

        moverVertical();
    }

    /**
     * Desplaza al jefe verticalmente entre dos límites del mapa.
     * Cuando alcanza uno de los extremos, invierte su dirección.
     */
    public void moverVertical() {
        y += movimientoVertical * velocidad;

        if (y <= limiteArriba) {
            y = limiteArriba;
            movimientoVertical = 1;
        } else if (y >= limiteAbajo) {
            y = limiteAbajo;
            movimientoVertical = -1;
        }

        dirVisual = DireccionVisual.IZQUIERDA;

        if (!estadoAnimacion.equals("atacar1")
                && !estadoAnimacion.equals("atacar2")
                && !estadoAnimacion.equals("atacar3")
                && !estadoAnimacion.equals("morir")) {

            if (!estadoAnimacion.equals("reaccion")) {
                setEstadoAnimacion("reaccion");
            }
        }
    }

    /**
     * Ejecuta el ataque de área si el tiempo de recarga lo permite.
     */
    public void atacarArea() {
        long ahora = System.currentTimeMillis();

        if (!enAnimacionAtaque && !enAnimacionIdle &&
                ahora - tiempoUltimoAtaque >= COOLDOWN_ATAQUE) {

            tiempoUltimoAtaque = ahora;
            tiempoInicioAnimAtaque = ahora;
            enAnimacionAtaque = true;

            setEstadoAnimacion(claveAtaqueFase());
        }
    }

    /**
     * Indica si el jefe puede atacar en este momento.
     *
     * @return {@code true} si puede atacar, {@code false} en caso contrario.
     */
    public boolean puedeAtacar() {
        return !estadoAnimacion.equals("morir")
                && !enAnimacionAtaque
                && !enAnimacionIdle
                && System.currentTimeMillis() - tiempoUltimoAtaque >= COOLDOWN_ATAQUE;
    }

    /**
     * Indica si actualmente se está reproduciendo la animación de ataque.
     *
     * @return {@code true} si Crocodile está atacando.
     */
    public boolean estaAtacando() {
        return enAnimacionAtaque;
    }

    /**
     * Retorna el daño del ataque global según la fase actual.
     *
     * @return daño del ataque.
     */
    public int getDanoAtaqueGlobal() {
        return switch (fase) {
            case 1 -> 5;
            case 2 -> 8;
            case 3 -> 11;
            default -> 5;
        };
    }

    /**
     * Determina si el jugador está lo suficientemente cerca
     * como para poder golpear al jefe.
     *
     * @param p jugador que intenta atacar.
     * @return {@code true} si está dentro del rango de impacto.
     */
    public boolean jugadorPuedeGolpear(Player p) {
        int distX = Math.abs(p.getX() - x);
        int distY = Math.abs(p.getY() - y);
        return distX < 120 && distY < 120;
    }


    /**
     * Modifica el patrón de ataque según la fase actual.
     * En fases avanzadas aumenta la velocidad del jefe.
     */
    @Override
    public void patronAtaque() {
        if (fase == 2) {
            velocidad = 4;
        }
        if (fase == 3) {
            velocidad = 5;
        }
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

    /**
     * Retorna la vida máxima del jefe.
     *
     * @return vida máxima de Crocodile.
     */
    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }

    /**
     * Retorna el nombre del jefe.
     *
     * @return cadena con el nombre "Crocodile".
     */
    @Override
    public String getNombre() {
        return "Crocodile";
    }

    /**
     * Ejecuta el ataque principal del jefe.
     */


    @Override
    public void ataque() {
        atacarArea();
    }


}
