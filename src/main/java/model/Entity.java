package model;

import java.awt.*;

/**
 * Clase abstracta que representa a una entidad del juego
 */
public abstract class Entity {

    /**
     * Atributo variable de la coordenada x
     */
    protected int x;
    /**
     * Atributo variable de la coordenada y
     */
    protected int y;
    /**
     * Atributo variable del ancho de la entidad
     */
    protected int width;
    /**
     * Atributo variable del alto de la entidad
     */
    protected int height;
    /**
     * Atributo variable de la vida de la entidad
     */
    protected int live;

    /**
     * Constructor de la clase entidad
     * @param x Atributo variable de la coordenada x
     * @param y Atributo variable de la coordenada y
     * @param width Atributo variable del ancho de la entidad
     * @param height Atributo variable del alto de la entidad
     * @param live Atributo variable de la vida de la entidad
     */

    public Entity(int x, int y, int width, int height, int live) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.live = live;
    }

    /**
     * Metodo que permite verificar colisiones
     * @return Un nuevo rectangulo
     */

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Metodo que permite verificar si esta vivo
     * @return True si esta vivo, False si no esta vivo
     */
    public boolean isAlive(){
        return live>0;
    }

    //Getters y Setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLive() {
        return live;
    }

    public void setLive(int live) {
        this.live = live;
    }
}
