package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Especial extends GameObject {

    public Especial(int x, int y) {
        super(x, y, 32, 32);
    }

    @Override
    public void aplicarEfecto(Player p) {
        p.recogerEspecial(this);
    }

    public boolean sePuedeRecoger(Player p) {
        return !p.tieneEspecial();
    }

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

    // El GameController llama esto al crear el item si el SpriteManager tiene el sprite
    public void setSprite(BufferedImage img) {
        this.sprite = img;
    }
}
