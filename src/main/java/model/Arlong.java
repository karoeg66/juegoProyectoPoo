package model;

import model.enums.DireccionVisual;

public class Arlong extends Boss {

    private static final int VIDA_MAX = 500;
    private static final long COOLDOWN_ATAQUE = 2500;
    private static final long DURACION_ATAQUE = 600;
    private static final long DURACION_IDLE = 800;
    // Intervalo mínimo entre repeticiones de la animación "reaccion" del jefe
    private static final long INTERVALO_REACCION = 12000; // 12 s
    private static final int VEL_PERSEGUIR = 2;
    private long tiempoUltimoAtaque;
    private boolean enAnimacionAtaque = false;
    private boolean enAnimacionIdle = false;
    private long tiempoInicioAnimAtaque = 0;
    private long tiempoInicioIdle = 0;
    private long tiempoUltimaReaccion = 0;

    public Arlong(int x, int y) {
        super(x, y, 80, 80, VIDA_MAX, VEL_PERSEGUIR, 330, 165, 80);
        this.tiempoUltimoAtaque = 0;
        this.dirVisual = DireccionVisual.IZQUIERDA;
        // Número de frames del sprite de muerte de Arlong (ajustar al real)
        this.totalFramesMuerte = 8;
    }

    @Override
    public void update() {
        // Si ya murió solo actualizar animación (para reproducir el sprite de muerte completo)
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
                // "reaccion" = reacción post-ataque del jefe (ciclo, pero no seguido)
                // Solo activar si ha pasado suficiente tiempo desde la última
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
        if (p.getY() < y) dy = -1;
        else if (p.getY() > y) dy = 1;
        mover(dx, dy);
    }

    @Override
    public void atacar() {
        long ahora = System.currentTimeMillis();
        if (!enAnimacionAtaque && !enAnimacionIdle &&
                ahora - tiempoUltimoAtaque >= COOLDOWN_ATAQUE) {
            tiempoUltimoAtaque = ahora;
            tiempoInicioAnimAtaque = ahora;
            enAnimacionAtaque = true;
            // Usar ataque de la fase actual: "atacar1", "atacar2" o "atacar3"
            // Sprites:
            //   Fase 1 → sprites/arlong/arlongataque1.png  (una vez)
            //   Fase 2 → sprites/arlong/arlongataque2.png  (una vez)
            //   Fase 3 → sprites/arlong/arlongataque3.png  (una vez)
            setEstadoAnimacion(claveAtaqueFase());
        }
    }

    public int getDanoAtaque() {
        return switch (fase) {
            case 1 -> 6;
            case 2 -> 9;
            case 3 -> 13;
            default -> 6;
        };
    }

    public boolean puedeAtacar() {
        return !estadoAnimacion.equals("morir") &&
                !enAnimacionAtaque && !enAnimacionIdle &&
                System.currentTimeMillis() - tiempoUltimoAtaque >= COOLDOWN_ATAQUE;
    }

    public boolean estaAtacando() {
        return enAnimacionAtaque;
    }

    @Override
    public void patronAtaque() {
    }

    @Override
    public void animacionIdle() {
        // Reacción del jefe: se repite pero no seguido
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
        return "Arlong";
    }
}
