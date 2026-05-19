package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    protected static final long DURACION = 6000; // 6 segundos
    protected int x, y, width, height;
    protected long tiempoAparicion;
    protected BufferedImage sprite;

    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.tiempoAparicion = System.currentTimeMillis();
    }

    public abstract void aplicarEfecto(Player p);

    public boolean haExpirado() {
        return System.currentTimeMillis() - tiempoAparicion >= DURACION;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
