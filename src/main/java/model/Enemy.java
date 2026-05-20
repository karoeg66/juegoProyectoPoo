package model;

import model.enums.DireccionVisual;
import model.enums.TipoEnemigo;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.HashMap;

public abstract class Enemy extends Character{
    /**
     * Cantidad de dano que realiza el enemigo
     */
    protected int dano;

    /**
     * Distancia de deteccion del jugador
     */
    protected int deteccion;

    /**
     * Tiempo del ultimo ataque realizado
     */
    protected long ultimoAtaque;

    /**
     * Tiempo de espera entre ataques
     */
    protected long cooldownAtaque;

    /**
     * Tipo de enemigo
     */
    protected TipoEnemigo tipo;

    /**
     * Indica si el enemigo ataca a distancia
     */
    protected boolean esDistancia;

    /**
     * Mapa que almacena los sprites de las animaciones
     */
    protected Map<String, BufferedImage[]> sprites;

    /**
     * Constructor de la clase Enemy
     *
     * @param tipo tipo de enemigo
     * @param x posicion horizontal inicial
     * @param y posicion vertical inicial
     * @param width ancho del enemigo
     * @param height alto del enemigo
     * @param vida vida inicial
     * @param velocidad velocidad de movimiento
     * @param dano cantidad de dano
     * @param deteccion rango de deteccion
     * @param cooldownAtaque tiempo de espera entre ataques
     * @param esDistancia indica si el enemigo ataca a distancia
     */
    public Enemy(TipoEnemigo tipo, int x, int y, int width, int height,
                 int vida, int velocidad, int dano, int deteccion,
                 long cooldownAtaque, boolean esDistancia) {

        super(x, y, width, height, vida, velocidad);

        this.tipo = tipo;
        this.dano = dano;
        this.deteccion = deteccion;
        this.ultimoAtaque = 0;
        this.cooldownAtaque = cooldownAtaque;
        this.esDistancia = esDistancia;
        this.sprites = new HashMap<>();
        this.totalFramesMuerte = 6;
    }

    /**
     * Hace que el enemigo persiga al jugador
     *
     * @param p jugador objetivo
     */
    public void perseguirJugador(Player p) {

        if (estadoAnimacion.equals("morir")) return;

        if (!esDistancia) {

            int dx = 0;
            int dy = 0;

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

        } else {

            if (p.getX() < x) {
                dirVisual = DireccionVisual.IZQUIERDA;

            } else {
                dirVisual = DireccionVisual.DERECHA;
            }
        }
    }

    /**
     * Verifica si el enemigo puede atacar
     *
     * @return true si puede atacar
     */
    public boolean puedeAtacar() {

        return !estadoAnimacion.equals("morir") &&
                System.currentTimeMillis() - ultimoAtaque >= cooldownAtaque;
    }

    /**
     * Ejecuta el ataque del enemigo
     */
    @Override
    public void ataque() {
        ultimoAtaque = System.currentTimeMillis();
        setEstadoAnimacion("atacar");
    }

    /**
     * Reduce la vida del enemigo al recibir dano
     *
     * @param dano cantidad de dano recibido
     */

    @Override
    public void recibirDano(int dano) {

        vida = Math.max(0, vida - dano);

        if (!estaVivo()) {
            setEstadoAnimacion("morir");
        }
    }


    /**
     * Dibuja el enemigo y su barra de vida
     *
     * @param g objeto Graphics usado para dibujar
     */
    @Override
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

            g.setColor(Color.ORANGE);
            g.fillRect(x, y, width, height);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 10));

            g.drawString(tipo.name(), x + 2, y + 20);
            g.drawString(estadoAnimacion, x + 2, y + 35);
        }

        g.setColor(Color.RED);
        g.fillRect(x, y - 8, width, 5);

        g.setColor(Color.GREEN);

        int barraVida =
                (int)((vida / (double) getVidaMaximo()) * width);

        g.fillRect(x, y - 8, barraVida, 5);
    }

    /**
     * Obtiene la vida maxima del enemigo
     *
     * @return vida maxima
     */
    public abstract int getVidaMaximo();

    /**
     * Carga los sprites del enemigo
     *
     * @param sprites mapa de sprites
     */
    public void cargarSprites(Map<String, BufferedImage[]> sprites) {
        this.sprites = sprites;
    }

    /**
     * Obtiene el tipo de enemigo
     *
     * @return tipo de enemigo
     */
    public TipoEnemigo getTipo() {
        return tipo;
    }

    /**
     * Obtiene el dano del enemigo
     *
     * @return dano
     */
    public int getDano() {
        return dano;
    }

    /**
     * Indica si el enemigo ataca a distancia
     *
     * @return true si es a distancia
     */
    public boolean isEsDistancia() {
        return esDistancia;
    }

    /**
     * Obtiene el rango de deteccion
     *
     * @return rango de deteccion
     */
    public int getDeteccion() {
        return deteccion;
    }
}
