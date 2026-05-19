package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Clase abstracta que representa un objeto del juego que puede ser recogido
 * por el jugador.
 * <p>
 * Todos los objetos derivados poseen una posición, dimensiones, un tiempo de
 * aparición y opcionalmente un sprite para su representación gráfica.
 */
public abstract class GameObject {

    /**
     * Tiempo máximo que el objeto permanece en el mapa, en milisegundos.
     */
    protected static final long DURACION = 6000;

    /**
     * Posición horizontal del objeto.
     */
    protected int x;

    /**
     * Posición vertical del objeto.
     */
    protected int y;

    /**
     * Ancho del objeto.
     */
    protected int width;

    /**
     * Alto del objeto.
     */
    protected int height;

    /**
     * Momento en que el objeto apareció en el mapa.
     */
    protected long tiempoAparicion;

    /**
     * Imagen utilizada para representar visualmente el objeto.
     */
    protected BufferedImage sprite;

    /**
     * Crea un nuevo objeto del juego en la posición indicada.
     *
     * @param x      posición en el eje X.
     * @param y      posición en el eje Y.
     * @param width  ancho del objeto.
     * @param height alto del objeto.
     */
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tiempoAparicion = System.currentTimeMillis();
    }

    /**
     * Aplica el efecto del objeto sobre el jugador.
     *
     * @param p jugador que recoge el objeto.
     */
    public abstract void aplicarEfecto(Player p);

    /**
     * Determina si el tiempo de vida del objeto ha expirado.
     *
     * @return {@code true} si el objeto debe desaparecer,
     * {@code false} en caso contrario.
     */
    public boolean haExpirado() {
        return System.currentTimeMillis() - tiempoAparicion >= DURACION;
    }

    /**
     * Retorna el rectángulo que representa el área de colisión del objeto.
     *
     * @return rectángulo con la posición y dimensiones del objeto.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Dibuja el objeto en pantalla si tiene un sprite asignado.
     *
     * @param g contexto gráfico utilizado para el dibujo.
     */
    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        }
    }

    /**
     * Retorna la posición horizontal del objeto.
     *
     * @return coordenada X.
     */
    public int getX() {
        return x;
    }

    /**
     * Retorna la posición vertical del objeto.
     *
     * @return coordenada Y.
     */
    public int getY() {
        return y;
    }
}
