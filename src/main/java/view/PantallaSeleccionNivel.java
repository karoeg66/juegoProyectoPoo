package view;
import java.awt.*;

/**
 * Clase que representa la pantalla de seleccion de nivel
 */

public class PantallaSeleccionNivel {

    /**
     * Boton del nivel 1
     */
    Rectangle btnNivel1 = new Rectangle(0, 0, 1, 1);

    /**
     * Boton del nivel 2
     */
    Rectangle btnNivel2 = new Rectangle(0, 0, 1, 1);

    /**
     * Boton para volver al menu
     */
    Rectangle btnVolver = new Rectangle(0, 0, 1, 1);

    /**
     * Dibuja la pantalla de seleccion de nivel
     *
     * @param g objeto Graphics usado para dibujar
     * @param w ancho de la ventana
     * @param h alto de la ventana
     * @param nombrePersonaje personaje seleccionado
     */
    public void draw(Graphics g,
                     int w,
                     int h,
                     String nombrePersonaje) {


        g.setColor(new Color(10, 20, 60));

        g.fillRect(0, 0, w, h);


        g.setColor(new Color(255, 200, 0));

        g.setFont(new Font("Impact", Font.BOLD, 52));

        FontMetrics fm = g.getFontMetrics();

        String titulo = "SELECCIONA NIVEL";

        g.drawString(titulo,
                (w - fm.stringWidth(titulo)) / 2,
                h / 4 + 10);


        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.BOLD, 18));

        fm = g.getFontMetrics();

        String desc =
                "Personaje " + nombrePersonaje;

        g.drawString(desc,
                (w - fm.stringWidth(desc)) / 2,
                h / 4 + 52);


        int btnW = 230;
        int btnH = 75;
        int gap = 60;

        int startX =
                (w - btnW * 2 - gap) / 2;

        int btnY =
                h / 2 - btnH / 2;


        btnNivel1 =
                new Rectangle(startX,
                        btnY,
                        btnW,
                        btnH);


        btnNivel2 =
                new Rectangle(startX + btnW + gap,
                        btnY,
                        btnW,
                        btnH);


        dibujarBotonNivel(g,
                btnNivel1,
                "NIVEL 1",
                "Enemigos y Arlong",
                new Color(30, 100, 200));


        dibujarBotonNivel(g,
                btnNivel2,
                "NIVEL 2",
                "Directo contra Crocodile",
                new Color(180, 60, 10));


        int vW = 160;
        int vH = 42;

        btnVolver =
                new Rectangle((w - vW) / 2,
                        btnY + btnH + 45,
                        vW,
                        vH);

        g.setColor(new Color(60, 60, 60));

        g.fillRoundRect(btnVolver.x,
                btnVolver.y,
                btnVolver.width,
                btnVolver.height,
                10,
                10);

        g.setColor(Color.WHITE);

        g.drawRoundRect(btnVolver.x,
                btnVolver.y,
                btnVolver.width,
                btnVolver.height,
                10,
                10);

        g.setFont(new Font("Arial", Font.BOLD, 16));

        fm = g.getFontMetrics();

        String volver = "VOLVER";

        g.drawString(volver,
                btnVolver.x
                        + (btnVolver.width
                        - fm.stringWidth(volver)) / 2,

                btnVolver.y
                        + (btnVolver.height
                        + fm.getAscent()) / 2 - 4);
    }

    /**
     * Dibuja un boton de seleccion de nivel
     *
     * @param g objeto Graphics usado para dibujar
     * @param r rectangulo del boton
     * @param titulo titulo del boton
     * @param subtitulo subtitulo del boton
     * @param color color del boton
     */
    private void dibujarBotonNivel(Graphics g,
                                   Rectangle r,
                                   String titulo,
                                   String subtitulo,
                                   Color color) {

        g.setColor(color);

        g.fillRoundRect(r.x,
                r.y,
                r.width,
                r.height,
                14,
                14);

        g.setColor(Color.WHITE);

        g.drawRoundRect(r.x,
                r.y,
                r.width,
                r.height,
                14,
                14);


        g.setFont(new Font("Arial", Font.BOLD, 24));

        FontMetrics fm = g.getFontMetrics();

        g.drawString(titulo,
                r.x + (r.width
                        - fm.stringWidth(titulo)) / 2,
                r.y + 32);


        g.setFont(new Font("Arial", Font.PLAIN, 13));

        fm = g.getFontMetrics();

        g.drawString(subtitulo,
                r.x + (r.width
                        - fm.stringWidth(subtitulo)) / 2,
                r.y + 56);
    }

}
