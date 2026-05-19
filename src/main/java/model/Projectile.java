package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Representa un proyectil disparado por un personaje o por un jefe.
 * <p>
 * Un proyectil se desplaza en línea recta hacia un objetivo definido al momento
 * de su creación, puede detectar colisiones con entidades y desaparece después
 * de un tiempo determinado.
 */
public class Projectile {

    /**
     * Tiempo máximo de vida del proyectil en milisegundos.
     */
    private static final long DURACION = 10000;

    /**
     * Velocidad de desplazamiento del proyectil.
     */
    private final int velocidad;

    /**
     * Momento en el que fue creado el proyectil.
     */
    private final long tiempoCreacion;

    /**
     * Componente horizontal del movimiento.
     */
    private final int dx;

    /**
     * Componente vertical del movimiento.
     */
    private final int dy;

    /**
     * Cantidad de daño que inflige el proyectil.
     */
    private final int dano;

    /**
     * Indica si el proyectil pertenece a un jefe.
     * Si es {@code true}, no puede esquivarse.
     */
    private final boolean esDeBoss;

    /**
     * Posición actual del proyectil en el eje X.
     */
    private int x;

    /**
     * Posición actual del proyectil en el eje Y.
     */
    private int y;

    /**
     * Imagen utilizada para representar visualmente el proyectil.
     */
    private BufferedImage sprite;

    /**
     * Crea un nuevo proyectil dirigido hacia una posición objetivo.
     *
     * @param x         posición inicial en el eje X.
     * @param y         posición inicial en el eje Y.
     * @param targetX   posición objetivo en el eje X.
     * @param targetY   posición objetivo en el eje Y.
     * @param velocidad velocidad de desplazamiento.
     * @param dano      daño que inflige al impactar.
     * @param esDeBoss  {@code true} si el proyectil fue generado por un jefe.
     */
    public Projectile(int x, int y, int targetX, int targetY,
                      int velocidad, int dano, boolean esDeBoss) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.dano = dano;
        this.esDeBoss = esDeBoss;
        this.tiempoCreacion = System.currentTimeMillis();

        // Calcular dirección hacia el objetivo.
        double distancia = Math.sqrt(Math.pow(targetX - x, 2)
                + Math.pow(targetY - y, 2));

        if (distancia > 0) {
            this.dx = (int) ((targetX - x) / distancia * velocidad);
            this.dy = (int) ((targetY - y) / distancia * velocidad);
        } else {
            this.dx = velocidad;
            this.dy = 0;
        }
    }

    /**
     * Asigna la imagen que se utilizará como sprite del proyectil.
     *
     * @param sprite imagen del proyectil.
     */
    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    /**
     * Actualiza la posición del proyectil según su dirección y velocidad.
     */
    public void mover() {
        x += dx;
        y += dy;
    }

    /**
     * Verifica si el proyectil colisiona con una entidad.
     *
     * @param e entidad con la que se desea comprobar la colisión.
     * @return {@code true} si hay intersección, {@code false} en caso contrario.
     */
    public boolean verificarColision(Entity e) {
        Rectangle bala = new Rectangle(x - 5, y - 5, 10, 10);
        return bala.intersects(e.getBounds());
    }

    /**
     * Determina si el proyectil ha excedido su tiempo de vida.
     *
     * @return {@code true} si debe eliminarse.
     */
    public boolean haExpirado() {
        return System.currentTimeMillis() - tiempoCreacion >= DURACION;
    }

    /**
     * Dibuja el proyectil en pantalla.
     * <p>
     * Si existe un sprite, se utiliza dicha imagen. En caso contrario,
     * se dibuja un círculo de color.
     *
     * @param g contexto gráfico utilizado para el dibujo.
     */
    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x - 8, y - 8, 16, 16, null);
        } else {
            g.setColor(esDeBoss ? new Color(180, 0, 180) : Color.YELLOW);
            g.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    /**
     * Retorna la posición actual en el eje X.
     *
     * @return coordenada X.
     */
    public int getX() {
        return x;
    }

    /**
     * Retorna la posición actual en el eje Y.
     *
     * @return coordenada Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Retorna el daño que inflige el proyectil.
     *
     * @return cantidad de daño.
     */
    public int getDano() {
        return dano;
    }

    /**
     * Indica si el proyectil pertenece a un jefe.
     *
     * @return {@code true} si fue generado por un boss.
     */
    public boolean isEsDeBoss() {
        return esDeBoss;
    }
}