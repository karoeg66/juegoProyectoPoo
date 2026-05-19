package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Representa un objeto coleccionable de tipo carne.
 * <p>
 * Este objeto permite restaurar una cantidad fija de vida al jugador,
 * siempre y cuando este no tenga su salud al máximo.
 */
public class Carne extends GameObject {

    /**
     * Cantidad de vida que recupera el jugador al recoger este objeto.
     */
    private final int cantidadVida;

    /**
     * Crea una nueva carne en la posición indicada.
     *
     * @param x posición en el eje X.
     * @param y posición en el eje Y.
     */
    public Carne(int x, int y) {
        super(x, y, 32, 32);
        this.cantidadVida = 75;
    }

    /**
     * Aplica el efecto de la carne sobre el jugador.
     * <p>
     * El jugador recupera vida al recoger este objeto.
     *
     * @param p jugador que recoge la carne.
     */
    @Override
    public void aplicarEfecto(Player p) {
        p.recogerCarne(this);
    }

    /**
     * Verifica si el jugador puede recoger la carne.
     * <p>
     * Solo se puede recoger si la vida del jugador no está completa.
     *
     * @param p jugador que intenta recoger la carne.
     * @return {@code true} si el jugador puede recogerla,
     * {@code false} en caso contrario.
     */
    public boolean sePuedeRecoger(Player p) {
        return !p.estaVidaLlena();
    }

    /**
     * Retorna la cantidad de vida que restaura este objeto.
     *
     * @return cantidad de puntos de vida recuperados.
     */
    public int getCantidadVida() {
        return cantidadVida;
    }

    /**
     * Dibuja la carne en pantalla.
     * <p>
     * Si existe un sprite cargado, se utiliza dicho sprite.
     * En caso contrario, se dibuja un marcador temporal.
     *
     * @param g contexto gráfico utilizado para el dibujo.
     */
    @Override
    public void draw(Graphics g) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, width, height, null);
        } else {
            // Placeholder mientras no haya sprite cargado
            g.setColor(new Color(220, 50, 50));
            g.fillOval(x, y, width, height);
            g.setColor(Color.WHITE);
            g.drawString("+HP", x + 3, y + 20);
        }
    }

    /**
     * Asigna la imagen que se utilizará como sprite del objeto.
     *
     * @param img imagen que representa visualmente la carne.
     */
    public void setSprite(BufferedImage img) {
        this.sprite = img;
    }
}