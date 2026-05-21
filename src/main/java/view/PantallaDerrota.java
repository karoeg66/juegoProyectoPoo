package view;
import java.awt.*;


/**
 * Clase que representa la pantalla de derrota
 */

public class PantallaDerrota {
    /**
     * Tiempo de bloqueo antes de volver al menu
     */
    private long tiempoBloqueo;

    /**
     * Constructor de la pantalla derrota
     */
    public PantallaDerrota() {

        this.tiempoBloqueo = 0;
    }

    /**
     * Muestra la pantalla de derrota
     *
     * @param g objeto Graphics usado para dibujar
     * @param tiempoJugado tiempo total jugado
     */
    public void mostrarDerrota(Graphics g,
                               long tiempoJugado, int puntaje) {

        if (tiempoBloqueo == 0) {
            tiempoBloqueo = System.currentTimeMillis();
        }

        g.setColor(new Color(60, 10, 10));

        g.fillRect(0,
                0,
                GamePanel.WIDTH,
                GamePanel.HEIGHT);

        g.setColor(Color.RED);

        g.setFont(new Font("Impact", Font.BOLD, 70));

        g.drawString("DERROTA",
                GamePanel.WIDTH / 2 - 175,
                GamePanel.HEIGHT / 2 - 40);

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 20));

        long seg =
                (tiempoJugado / 1000) % 60;

        long min =
                tiempoJugado / 60000;

        g.drawString(
                String.format("Tiempo %02d:%02d", min, seg),
                GamePanel.WIDTH / 2 - 60,
                GamePanel.HEIGHT / 2 + 20
        );

        g.drawString("Puntaje: " + puntaje,
                GamePanel.WIDTH / 2 - 60,
                GamePanel.HEIGHT / 2 + 50
        );

        long restante =
                10 - (System.currentTimeMillis()
                        - tiempoBloqueo) / 1000;

        g.setFont(new Font("Arial", Font.PLAIN, 16));

        g.drawString(
                "Volviendo al menu en "
                        + Math.max(0, restante)
                        + "s",
                GamePanel.WIDTH / 2 - 90,
                GamePanel.HEIGHT / 2 + 90
        );
    }

    /**
     * Verifica si debe volver al menu
     *
     * @return true si debe volver
     */
    public boolean debeVolverAlMenu() {

        if (tiempoBloqueo == 0) {
            return false;
        }

        return System.currentTimeMillis()
                - tiempoBloqueo >= 10000;
    }
}
