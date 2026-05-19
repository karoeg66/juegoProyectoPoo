package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile {

    private static final long DURACION = 10000; // 10 segundos
    private final int velocidad;
    private final long tiempoCreacion;
    private final int dx;
    private final int dy; // Dirección normalizada * velocidad
    private final int dano;
    private final boolean esDeBoss; // true = no se puede esquivar (Crocodile)
    private int x, y;
    private BufferedImage sprite;

    public Projectile(int x, int y, int targetX, int targetY, int velocidad, int dano, boolean esDeBoss) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.dano = dano;
        this.esDeBoss = esDeBoss;
        this.tiempoCreacion = System.currentTimeMillis();

        // Calcular dirección hacia el objetivo
        double distancia = Math.sqrt(Math.pow(targetX - x, 2) + Math.pow(targetY - y, 2));
        if (distancia > 0) {
            this.dx = (int) ((targetX - x) / distancia * velocidad);
            this.dy = (int) ((targetY - y) / distancia * velocidad);
        } else {
            this.dx = velocidad;
            this.dy = 0;
        }
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public void mover() {
        x += dx;
        y += dy;
    }

    public boolean verificarColision(Entity e) {
        Rectangle bala = new Rectangle(x - 5, y - 5, 10, 10);
        return bala.intersects(e.getBounds());
    }

    public boolean haExpirado() {
        return System.currentTimeMillis() - tiempoCreacion >= DURACION;
    }

    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x - 8, y - 8, 16, 16, null);
        } else {
            g.setColor(esDeBoss ? new Color(180, 0, 180) : Color.YELLOW);
            g.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDano() {
        return dano;
    }

    public boolean isEsDeBoss() {
        return esDeBoss;
    }

}
