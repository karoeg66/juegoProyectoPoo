package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Pantalla de inicio: dibuja el menú y expone los rectángulos de los botones
 * para que GamePanel los use en su único MouseListener centralizado.
 */


public class PantallaInicio extends JPanel {
    /**
     * Boton de seleccion de Luffy
     */
    Rectangle btnLuffy = new Rectangle(0, 0, 1, 1);

    /**
     * Boton de seleccion de Zoro
     */
    Rectangle btnZoro = new Rectangle(0, 0, 1, 1);

    /**
     * Boton de seleccion de Sanji
     */
    Rectangle btnSanji = new Rectangle(0, 0, 1, 1);

    /**
     * Boton de instrucciones
     */
    Rectangle btnInstrucciones = new Rectangle(0, 0, 1, 1);

    /**
     * Imagen del logo de la UAM
     */
    Image logo = new ImageIcon("resources/LOGO.png").getImage().getScaledInstance(150,150,Image.SCALE_DEFAULT);

    Image fondo = new ImageIcon("resources/FONDO.png").getImage();

    /**
     * Dibuja el menu principal
     *
     * @param g objeto Graphics usado para dibujar
     * @param w ancho de la ventana
     * @param h alto de la ventana
     */
    public void draw(Graphics g, int w, int h) {

        // Fondo del menu
        g.setColor(new Color(10, 20, 60));

        g.fillRect(0, 0, w, h);

        g.drawImage(fondo,-200, 0, this);


        // Titulo principal
        g.setColor(new Color(255, 200, 0));

        g.setFont(new Font("Impact", Font.BOLD, 64));

        FontMetrics fm = g.getFontMetrics();

        String titulo = "ONE PIECE";

        g.drawString(titulo,
                (w - fm.stringWidth(titulo)) / 2,
                h / 4);


        // Subtitulo
        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.BOLD, 22));

        fm = g.getFontMetrics();

        String sub = "Elige tu personaje";

        g.drawString(sub,
                (w - fm.stringWidth(sub)) / 2,
                h / 4 + 60);

        // Calculo de posiciones de botones
        int btnW = 160;
        int btnH = 55;
        int gap = 40;

        int totalW = btnW * 3 + gap * 2;

        int startX = (w - totalW) / 2;

        int btnY = h / 2 - 30;

        btnLuffy =
                new Rectangle(startX,
                        btnY,
                        btnW,
                        btnH);

        btnZoro =
                new Rectangle(startX + btnW + gap,
                        btnY,
                        btnW,
                        btnH);

        btnSanji =
                new Rectangle(startX + (btnW + gap) * 2,
                        btnY,
                        btnW,
                        btnH);

        // Boton Luffy
        dibujarBoton(g,
                btnLuffy,
                "LUFFY",
                new Color(220, 30, 30));

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 12));

        g.drawString("Vel Media",
                btnLuffy.x + 18,
                btnLuffy.y + btnH + 18);

        g.drawString("Inmune balas",
                btnLuffy.x + 10,
                btnLuffy.y + btnH + 32);

        // Boton Zoro
        dibujarBoton(g,
                btnZoro,
                "ZORO",
                new Color(20, 150, 50));

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 12));

        g.drawString("Mas daño",
                btnZoro.x + 22,
                btnZoro.y + btnH + 18);

        g.drawString("Mas lento",
                btnZoro.x + 22,
                btnZoro.y + btnH + 32);

        // Boton Sanji
        dibujarBoton(g,
                btnSanji,
                "SANJI",
                new Color(200, 180, 0));

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 12));

        g.drawString("Mas rapido",
                btnSanji.x + 15,
                btnSanji.y + btnH + 18);

        g.drawString("Menos daño",
                btnSanji.x + 15,
                btnSanji.y + btnH + 32);

        // Boton de instrucciones
        int instrW = 220;
        int instrH = 45;

        btnInstrucciones =
                new Rectangle((w - instrW) / 2,
                        btnY + btnH + 80,
                        instrW,
                        instrH);

        dibujarBoton(g,
                btnInstrucciones,
                "INSTRUCCIONES",
                new Color(60, 60, 180));

        // Controles del juego
        g.setColor(Color.LIGHT_GRAY);

        g.setFont(new Font("Arial", Font.PLAIN, 13));

        String ctrl =
                "WASD Mover  |  J Atacar  |  K Especial  |  E Recoger  |  ESC Pausa";

        fm = g.getFontMetrics();

        g.drawString(ctrl,
                (w - fm.stringWidth(ctrl)) / 2,
                h - 20);

        g.setColor(new Color(255, 255, 255));

        g.setFont(new Font("Arial", Font.PLAIN, 12));

        g.drawString("Karolay Garcia Vasquez", 10 , 20);
        g.drawString("Elkin Alzate Londoño", 10 , 35);
        g.drawString("Santiago Sanchez²", 10 , 50);

        g.setColor(new Color(255, 255, 255));

        g.drawString("Programacion orientada a objetos", 10 , 70);


        g.drawImage(logo, 0, 65, this );


    }

    /**
     * Dibuja un boton personalizado
     *
     * @param g objeto Graphics usado para dibujar
     * @param r rectangulo del boton
     * @param texto texto mostrado
     * @param color color del boton
     */
    private void dibujarBoton(Graphics g,
                              Rectangle r,
                              String texto,
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

        g.setFont(new Font("Arial", Font.BOLD, 19));

        FontMetrics fm = g.getFontMetrics();

        int tx =
                r.x + (r.width - fm.stringWidth(texto)) / 2;

        int ty =
                r.y + (r.height + fm.getAscent()) / 2 - 4;

        g.drawString(texto, tx, ty);
    }
}
