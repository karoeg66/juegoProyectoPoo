package view;

import java.awt.*;

/**
 * Clase que representa la pantalla de victoria
 */


public class  PantallaVictoria {
    /**
     * Muestra la pantalla de victoria
     *
     * @param g objeto Graphics usado para dibujar
     */
    public void mostrarVictoria(Graphics g) {

        g.setColor(new Color(10, 60, 10));

        g.fillRect(0,
                0,
                GamePanel.WIDTH,
                GamePanel.HEIGHT);

        g.setColor(new Color(255, 200, 0));

        g.setFont(new Font("Impact", Font.BOLD, 70));

        g.drawString("VICTORIA",
                GamePanel.WIDTH / 2 - 185,
                GamePanel.HEIGHT / 2 - 30);

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 22));

        g.drawString("Derrotaste a Crocodile",
                GamePanel.WIDTH / 2 - 180,
                GamePanel.HEIGHT / 2 + 40);

        g.setFont(new Font("Arial", Font.PLAIN, 16));

        g.drawString("Haz clic para volver al menu",
                GamePanel.WIDTH / 2 - 90,
                GamePanel.HEIGHT / 2 + 90);
    }
}

