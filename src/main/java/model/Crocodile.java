package model;

import model.enums.DireccionVisual;

public class Crocodile extends Boss {

    private static final int VIDA_MAX = 700;
    private static final long COOLDOWN_ATAQUE = 2000;
    private static final long DURACION_ATAQUE = 700;
    private static final long DURACION_IDLE = 1000;
    // Intervalo mínimo entre repeticiones de la animación "reaccion" del jefe
    private static final long INTERVALO_REACCION = 10000; // 10 s
    private final java.util.Random rng = new java.util.Random();
    private final int limiteArriba;
    private final int limiteAbajo;
    private final int mapHeight;
    private int movimientoVertical;
    private long tiempoUltimoAtaque;
    private boolean enAnimacionAtaque = false;
    private boolean enAnimacionIdle = false;
    private long tiempoInicioAnimAtaque = 0;
    private long tiempoInicioIdle = 0;
    private long tiempoUltimaReaccion = 0;

    public Crocodile(int mapWidth, int mapHeight) {
        super(mapWidth - 120, mapHeight / 2 - 50, 100, 100, VIDA_MAX, 3, 460, 230, 100);
        this.movimientoVertical = 1;
        this.tiempoUltimoAtaque = 0;
        this.mapHeight = mapHeight;
        this.limiteArriba = 50;
        this.limiteAbajo = mapHeight - 150;
        this.dirVisual = DireccionVisual.IZQUIERDA;
        // Número de frames del sprite de muerte de Crocodile (ajustar al real)
        this.totalFramesMuerte = 8;
    }

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

    private void actualizarFaseAnimacion() {
        long ahora = System.currentTimeMillis();

        if (enAnimacionAtaque) {
            if (ahora - tiempoInicioAnimAtaque >= DURACION_ATAQUE) {
                enAnimacionAtaque = false;
                enAnimacionIdle = true;
                tiempoInicioIdle = ahora;
                // "reaccion" = reacción post-ataque del jefe (no se repite seguido)
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
        // Mientras se mueve usa reaccion (idle), no caminar
        if (!estadoAnimacion.equals("atacar1") && !estadoAnimacion.equals("atacar2")
                && !estadoAnimacion.equals("atacar3") && !estadoAnimacion.equals("morir")) {
            if (!estadoAnimacion.equals("reaccion")) setEstadoAnimacion("reaccion");
        }
    }

    public void atacarArea() {
        long ahora = System.currentTimeMillis();
        if (!enAnimacionAtaque && !enAnimacionIdle &&
                ahora - tiempoUltimoAtaque >= COOLDOWN_ATAQUE) {
            tiempoUltimoAtaque = ahora;
            tiempoInicioAnimAtaque = ahora;
            enAnimacionAtaque = true;
            // Ataque por fase: "atacar1", "atacar2", "atacar3"
            // Sprites:
            //   Fase 1 → sprites/crocodile/crocodileataque1.png  (una vez)
            //   Fase 2 → sprites/crocodile/crocodileataque2.png  (una vez)
            //   Fase 3 → sprites/crocodile/crocodileataque3.png  (una vez)
            setEstadoAnimacion(claveAtaqueFase());
        }
    }

    public boolean puedeAtacar() {
        return !estadoAnimacion.equals("morir") &&
                !enAnimacionAtaque && !enAnimacionIdle &&
                System.currentTimeMillis() - tiempoUltimoAtaque >= COOLDOWN_ATAQUE;
    }

    public boolean estaAtacando() {
        return enAnimacionAtaque;
    }

    public int getDanoAtaqueGlobal() {
        return switch (fase) {
            case 1 -> 10;
            case 2 -> 16;
            case 3 -> 22;
            default -> 10;
        };
    }

    public boolean jugadorPuedeGolpear(Player p) {
        int distX = Math.abs(p.getX() - x);
        int distY = Math.abs(p.getY() - y);
        return distX < 120 && distY < 120;
    }

    @Override
    public void atacar() {
        atacarArea();
    }

    @Override
    public void patronAtaque() {
        if (fase == 2) velocidad = 4;
        if (fase == 3) velocidad = 5;
    }

    @Override
    public void animacionIdle() {
        long ahora = System.currentTimeMillis();
        if (ahora - tiempoUltimaReaccion >= INTERVALO_REACCION) {
            setEstadoAnimacion("reaccion");
            tiempoUltimaReaccion = ahora;
        }
    }

    @Override
    public int getVidaMaximo() {
        return VIDA_MAX;
    }

    @Override
    public String getNombre() {
        return "Crocodile";
    }
}
