package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Carne extends GameObject {

    private final int cantidadVida;

    public Carne(int x, int y) {
        super(x, y, 32, 32);
        this.cantidadVida = 75;
    }

    @Override
    public void aplicarEfecto(Player p) {
        p.recogerCarne(this);
    }

    public boolean sePuedeRecoger(Player p) {
        return !p.estaVidaLlena();
    }

    public int getCantidadVida() {
        return cantidadVida;
    }

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

    // El GameController llama esto al crear la carne si el SpriteManager tiene el sprite
    public void setSprite(BufferedImage img) {
        this.sprite = img;
    }
}
