package view;

import controller.GameController;

import java.awt.*;
import java.util.List;

/**
 * Clase que representa la pantalla de victoria
 */


public class  PantallaVictoria {
    /**
     * Muestra la pantalla de victoria
     *
     * @param g objeto Graphics usado para dibujar
     */
    public void mostrarVictoria(Graphics g, int puntaje, String nombreJugador, List <String> top3) {

        g.setColor(new Color(10, 60, 10));

        g.fillRect(0,
                0,
                GamePanel.WIDTH,
                GamePanel.HEIGHT);

        g.setColor(new Color(255, 200, 0));

        g.setFont(new Font("Impact", Font.BOLD, 70));

        g.drawString("VICTORIA",
                GamePanel.WIDTH / 2 - 160,
                GamePanel.HEIGHT / 2 - 30);

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 22));

        g.drawString("Derrotaste al JEFE",
                GamePanel.WIDTH / 2 - 140,
                GamePanel.HEIGHT / 2 + 40);

        g.setFont(new Font("Arial", Font.PLAIN, 16));

        g.drawString("Nombre del jugador: "+ nombreJugador,  GamePanel.WIDTH / 2 - 140, GamePanel.HEIGHT / 2 +70 );
        g.drawString("Puntaje:" + puntaje , GamePanel.WIDTH / 2 - 140, GamePanel.HEIGHT / 2 + 95 );

        g.drawString("Haz clic para volver al menu",
                GamePanel.WIDTH / 2 - 140,
                GamePanel.HEIGHT / 2 + 240);

        int y = GamePanel.HEIGHT / 2 + 130;

        g.drawString("TOP 3", GamePanel.WIDTH / 2 - 140, y);
        y += 30;

        for (int i = 0; i < top3.size(); i++) {
            String[] partes = top3.get(i).split(";");

            g.drawString(
                    (i + 1) + ". " + partes[0] + " - " + partes[1],
                    GamePanel.WIDTH / 2 - 110,
                    y
            );

            y += 30;
        }
    }


}

