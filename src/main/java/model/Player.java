package model;

import java.awt.image.BufferedImage;
import java.awt.*;


public abstract class Player extends Character {
    /**
     * Tiempo de espera entre ataques normales
     */
    protected static final long COOLDOWN_NORMAL = 300;

    /**
     * Tiempo de espera entre ataques especiales
     */
    protected static final long COOLDOWN_ESPECIAL = 3000;

    /**
     * Intervalo de regeneracion de vida
     */
    protected static final long INTERVALO_REGEN = 100;

    /**
     * Tiempo necesario para activar reaccion AFK
     */
    protected static final long TIEMPO_AFK = 5000;

    /**
     * Tiempo necesario para activar estado quieto
     */
    protected static final long TIEMPO_QUIETO = 2000;

    /**
     * Tipo de personaje
     */
    protected TipoPersonaje tipo;

    /**
     * Vida maxima del jugador
     */
    protected int vidaMax = 250;

    /**
     * Indica si el jugador tiene un especial en inventario
     */
    protected boolean inventarioEspecial;

    /**
     * Dano basico del personaje
     */
    protected int danoBasico;

    /**
     * Tiempo del ultimo ataque normal
     */
    protected long tiempoUltimoAtaqueNormal;

    /**
     * Tiempo del ultimo ataque especial
     */
    protected long tiempoUltimoAtaqueEspecial;

    /**
     * Cantidad de vida pendiente por regenerar
     */
    protected int vidaPendiente = 0;

    /**
     * Tiempo del ultimo tick de regeneracion
     */
    protected long ultimoTickRegen = 0;

    /**
     * Tiempo de la ultima accion realizada
     */
    protected long tiempoUltimaAccion;

    /**
     * Mapa que almacena los sprites del jugador
     */
    protected Map<String, BufferedImage[]> sprites;

    /**
     * Ancho del mapa
     */
    protected int mapWidth;

    /**
     * Alto del mapa
     */
    protected int mapHeight;

    /**
     * Indica si el especial fue activado
     */
    private boolean especialActivado = false;

    /**
     * Constructor de la clase Player
     *
     * @param tipo tipo de personaje
     * @param x posicion horizontal inicial
     * @param y posicion vertical inicial
     * @param mapWidth ancho del mapa
     * @param mapHeight alto del mapa
     * @param velocidad velocidad del jugador
     * @param danoBasico dano basico del personaje
     */
    public Player(TipoPersonaje tipo, int x, int y,
                  int mapWidth, int mapHeight,
                  int velocidad, int danoBasico) {

        super(x, y, 64, 64, 250, velocidad);

        this.tipo = tipo;
        this.vidaMax = 250;
        this.inventarioEspecial = false;
        this.danoBasico = danoBasico;
        this.tiempoUltimaAccion = System.currentTimeMillis();
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.sprites = new HashMap<>();
        this.totalFramesMuerte = 6;
    }

    /**
     * Actualiza el estado del jugador
     */
    public void update() {

        if (estadoAnimacion.equals("morir")) {
            actualizarAnimacion();
            return;
        }

        actualizarAnimacion();
        verificarAFK();
        aplicarRegenGradual();
        resetearEstadoAtaque();

        x = Math.max(0, Math.min(x, mapWidth - width));
        y = Math.max(0, Math.min(y, mapHeight - height));
    }

    /**
     * Restablece el estado de ataque al terminar la animacion
     */
    private void resetearEstadoAtaque() {

        if (!estadoAnimacion.equals("atacar")
                && !estadoAnimacion.equals("especial")) {
            return;
        }

        if (animacionNoRepetiblTermino(sprites, estadoAnimacion)) {
            setEstadoAnimacion("caminar");
        }
    }

    /**
     * Aplica regeneracion gradual de vida
     */
    private void aplicarRegenGradual() {

        if (vidaPendiente <= 0) return;

        long ahora = System.currentTimeMillis();

        if (ahora - ultimoTickRegen >= INTERVALO_REGEN) {

            int tick = Math.min(5, vidaPendiente);

            vida = Math.min(vida + tick, vidaMax);

            vidaPendiente -= tick;

            ultimoTickRegen = ahora;
        }
    }

    /**
     * Verifica si el jugador esta inactivo
     */
    private void verificarAFK() {

        if (!estaVivo()) return;

        long ahora = System.currentTimeMillis();

        long tiempoInactivo = ahora - tiempoUltimaAccion;

        if (tiempoInactivo >= TIEMPO_AFK) {

            if (!estadoAnimacion.equals("reaccion")) {
                setEstadoAnimacion("reaccion");
            }

        } else if (tiempoInactivo >= TIEMPO_QUIETO) {

            if (!estadoAnimacion.equals("quieto")
                    && !estadoAnimacion.equals("reaccion")) {

                setEstadoAnimacion("quieto");
            }
        }
    }

    /**
     * Registra una accion del jugador
     */
    public void registrarAccion() {

        tiempoUltimaAccion = System.currentTimeMillis();

        if (estadoAnimacion.equals("reaccion")
                || estadoAnimacion.equals("quieto")) {

            setEstadoAnimacion("caminar");
        }
    }

    /**
     * Ejecuta un ataque normal
     */
    public void atacar() {

        long ahora = System.currentTimeMillis();

        if (!enCooldown
                && ahora - tiempoUltimoAtaqueNormal >= COOLDOWN_NORMAL) {

            setEstadoAnimacion("atacar");

            tiempoUltimoAtaqueNormal = ahora;

            registrarAccion();
        }
    }

    /**
     * Ejecuta el ataque especial
     */
    public void usarEspecial() {

        especialActivado = true;

        long ahora = System.currentTimeMillis();

        if (inventarioEspecial
                && ahora - tiempoUltimoAtaqueEspecial >= COOLDOWN_ESPECIAL
                && !estadoAnimacion.equals("atacar")
                && !estadoAnimacion.equals("especial")
                && !estadoAnimacion.equals("morir")) {

            setEstadoAnimacion("especial");

            inventarioEspecial = false;

            tiempoUltimoAtaqueEspecial = ahora;

            especialActivado = true;
        }
    }

    /**
     * Recoge carne y aplica regeneracion
     *
     * @param carne objeto carne
     */
    public void recogerCarne(Carne carne) {

        if (!estaVidaLlena()) {

            vidaPendiente =
                    Math.min(vidaPendiente + carne.getCantidadVida(),
                            vidaMax - vida);

            ultimoTickRegen = System.currentTimeMillis();
        }
    }

    /**
     * Recoge un especial
     *
     * @param esp objeto especial
     */
    public void recogerEspecial(Especial esp) {

        if (!inventarioEspecial) {
            inventarioEspecial = true;
        }
    }

    /**
     * Reduce la vida del jugador
     *
     * @param dano dano recibido
     */
    public void recibirDano(int dano) {

        vida = Math.max(0, vida - dano);

        if (!estaVivo()) {
            setEstadoAnimacion("morir");
        }
    }

    /**
     * Recibe dano de bala
     *
     * @param dano dano recibido
     */
    public abstract void recibirDanoBala(int dano);

    /**
     * Recibe dano de espada
     *
     * @param dano dano recibido
     */
    public abstract void recibirDanoEspada(int dano);

    /**
     * Dibuja el jugador en pantalla
     *
     * @param g objeto Graphics usado para dibujar
     */
    public void draw(Graphics g) {

        String key;

        if (estadoAnimacion.equals("caminar")) {
            key = "caminar_" + dirVisual.name().toLowerCase();

        } else {
            key = estadoAnimacion;
        }

        BufferedImage[] frames = sprites.get(key);

        if (frames != null && frames.length > 0) {

            int idx;

            if (esCicloRepetible(estadoAnimacion)) {
                idx = frameAnimacion % frames.length;

            } else {
                idx = Math.min(frameAnimacion, frames.length - 1);
            }

            g.drawImage(frames[idx], x, y, width, height, null);

        } else {

            g.setColor(getColorPlaceholder());

            g.fillRect(x, y, width, height);

            g.setColor(Color.WHITE);

            g.setFont(new Font("Arial", Font.BOLD, 11));

            g.drawString(tipo.name(), x + 4, y + 20);

            g.drawString(estadoAnimacion, x + 4, y + 36);
        }
    }

    /**
     * Obtiene el color placeholder del personaje
     *
     * @return color placeholder
     */
    protected abstract Color getColorPlaceholder();

    /**
     * Carga los sprites del jugador
     *
     * @param sprites mapa de sprites
     */
    public void cargarSprites(Map<String, BufferedImage[]> sprites) {
        this.sprites = sprites;
    }

    /**
     * Verifica si el inventario especial esta lleno
     *
     * @return true si tiene especial
     */
    public boolean estaInventarioLleno() {
        return inventarioEspecial;
    }

    /**
     * Verifica si la vida esta llena
     *
     * @return true si la vida esta completa
     */
    public boolean estaVidaLlena() {
        return vida >= vidaMax;
    }

    /**
     * Obtiene el tipo de personaje
     *
     * @return tipo de personaje
     */
    public TipoPersonaje getTipo() {
        return tipo;
    }

    /**
     * Obtiene la vida maxima
     *
     * @return vida maxima
     */
    public int getVidaMax() {
        return vidaMax;
    }

    /**
     * Verifica si tiene especial
     *
     * @return true si tiene especial
     */
    public boolean tieneEspecial() {
        return inventarioEspecial;
    }

    /**
     * Obtiene el dano basico
     *
     * @return dano basico
     */
    public int getDanoBasico() {
        return danoBasico;
    }

    /**
     * Obtiene el dano especial
     *
     * @return dano especial
     */
    public int getDanoEspecial() {
        return danoBasico * 3;
    }

    /**
     * Consume el estado de especial activado
     *
     * @return true si el especial estaba activado
     */
    public boolean consumirEspecialActivado() {

        boolean val = especialActivado;

        especialActivado = false;

        return val;
    }
}
