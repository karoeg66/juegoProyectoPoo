package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    /**
     * Posicion horizontal del objeto
     */
    protected int x;

    /**
     * Posicion vertical del objeto
     */
    protected int y;

    /**
     * Ancho del objeto
     */
    protected int width;

    /**
     * Alto del objeto
     */
    protected int height;

    /**
     * Tiempo en el que el objeto aparecio
     */
    protected long tiempoAparicion;

    /**
     * Duracion maxima del objeto en pantalla
     */
    protected static final long DURACION = 6000;

    /**
     * Sprite del objeto
     */
    protected BufferedImage sprite;

    /**
     * Constructor de la clase GameObject
     *
     * @param x posicion horizontal inicial
     * @param y posicion vertical inicial
     * @param width ancho del objeto
     * @param height alto del objeto
     */
    public GameObject(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.tiempoAparicion = System.currentTimeMillis();
    }

    /**
     * Aplica el efecto del objeto al jugador
     *
     * @param p jugador afectado
     */
    public abstract void aplicarEfecto(Player p);

    /**
     * Verifica si el objeto ya expiro
     *
     * @return true si el tiempo del objeto termino
     */
    public boolean haExpirado() {

        return System.currentTimeMillis()
                - tiempoAparicion >= DURACION;
    }

    /**
     * Obtiene el area de colision del objeto
     *
     * @return rectangulo de colision
     */
    public Rectangle getBounds() {

        return new Rectangle(x, y, width, height);
    }

    /**
     * Dibuja el objeto en pantalla
     *
     * @param g objeto Graphics usado para dibujar
     */
    public void draw(Graphics g) {

        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        }
    }

    /**
     * Obtiene la posicion horizontal
     *
     * @return posicion en x
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la posicion vertical
     *
     * @return posicion en y
     */
    public int getY() {
        return y;
    }
}
