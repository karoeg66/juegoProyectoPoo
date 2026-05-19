package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Representa un objeto coleccionable de tipo especial.
 * <p>
 * Este ítem otorga al jugador la posibilidad de utilizar su habilidad
 * especial, siempre y cuando aún no posea una.
 */
public class Especial extends GameObject {

    /**
     * Crea un nuevo ítem especial en la posición indicada.
     *
     * @param x posición en el eje X.
     * @param y posición en el eje Y.
     */
    public Especial(int x, int y) {
        super(x, y, 32, 32);
    }

    /**
     * Aplica el efecto del objeto sobre el jugador.
     * <p>
     * El jugador obtiene la habilidad especial al recoger este ítem.
     *
     * @param p jugador que recoge el objeto.
     */
    @Override
    public void aplicarEfecto(Player p) {
        p.recogerEspecial(this);
    }

    /**
     * Verifica si el jugador puede recoger el objeto.
     * <p>
     * Solo puede recogerse si el jugador aún no tiene una habilidad especial.
     *
     * @param p jugador que intenta recoger el ítem.
     * @return {@code true} si puede recogerlo, {@code false} en caso contrario.
     */
    public boolean sePuedeRecoger(Player p) {
        return !p.tieneEspecial();
    }

    /**
     * Dibuja el ítem especial en pantalla.
     * <p>
     * Si existe un sprite cargado, se utiliza dicha imagen.
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
            g.setColor(new Color(255, 215, 0));
            g.fillOval(x, y, width, height);
            g.setColor(Color.BLACK);
            g.drawString("ESP", x + 3, y + 20);
        }
    }

    /**
     * Asigna la imagen que se utilizará como sprite del objeto.
     *
     * @param img imagen que representa visualmente el ítem especial.
     */
    public void setSprite(BufferedImage img) {
        this.sprite = img;
    }
}
