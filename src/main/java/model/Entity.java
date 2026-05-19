package model;
import java.awt.*;
/**
 * Clase abstracta que representa una entidad del juego
 * Contiene posicion tamaño y vida
 */


public abstract class Entity {
    // Posicion de la entidad
    protected int x;
    protected int y;

    // Tamaño de la entidad
    protected int width;
    protected int height;

    // Vida de la entidad
    protected int vida;

    /**
     * Constructor de la entidad
     * @param x posicion en x
     * @param y posicion en y
     * @param width ancho de la entidad
     * @param height alto de la entidad
     * @param vida vida de la entidad
     */
    public Entity(int x, int y, int width, int height, int vida) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vida = vida;
    }

    /**
     * Actualiza la logica de la entidad
     */
    public abstract void update();

    /**
     * Dibuja la entidad en pantalla
     * @param g objeto graphics
     */
    public abstract void draw(Graphics g);

    /**
     * Obtiene el area de colision de la entidad
     * @return rectangulo de colision
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Verifica si la entidad sigue viva
     * @return true si tiene vida
     */
    public boolean estaVivo() {
        return vida > 0;
    }

    /**
     * Obtiene la posicion x
     * @return posicion x
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la posicion y
     * @return posicion y
     */
    public int getY() {
        return y;
    }

    /**
     * Obtiene el ancho
     * @return ancho
     */
    public int getWidth() {
        return width;
    }

    /**
     * Obtiene el alto
     * @return alto
     */
    public int getHeight() {
        return height;
    }

    /**
     * Obtiene la vida
     * @return vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Modifica la vida
     * @param vida nueva vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Modifica la posicion x
     * @param x nueva posicion x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Modifica la posicion y
     * @param y nueva posicion y
     */
    public void setY(int y) {
        this.y = y;
    }

}
