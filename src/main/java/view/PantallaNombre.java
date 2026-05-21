package view;

import javax.swing.*;
import java.awt.*;


/**
 * Clase que representa la interfaz grafica para que el usuario introduzca su nombre.
 * Define las areas interactivas para la caja de texto y el boton de confirmacion,
 * y se encarga de renderizar de manera personalizada estos elementos en pantalla.
 */


public class PantallaNombre extends JPanel {

        public Rectangle inputBox = new Rectangle(440, 300, 400, 50);
        public Rectangle btnContinuar = new Rectangle(515, 390, 250, 50);

    /**
     * Dibuja los componentes visuales de la pantalla de ingreso de nombre,
     * como el fondo, el titulo, la caja de texto con el nombre actual y el boton continuar.
     * @param g Objeto Graphics para renderizar en el panel
     * @param w Ancho actual del panel de juego
     * @param h Alto actual del panel de juego
     * @param nombreActual El texto del nombre que el usuario va escribiendo en tiempo real
     */

    
        public void draw(Graphics g, int w, int h, String nombreActual) {
            g.setColor(new Color(10, 20, 60));
            g.fillRect(0, 0, w, h);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 42));
            g.drawString("Ingresa tu nombre", 430, 220);

            g.setColor(Color.WHITE);
            g.fillRect(inputBox.x, inputBox.y, inputBox.width, inputBox.height);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 26));
            g.drawString(nombreActual, inputBox.x + 15, inputBox.y + 34);

            g.setColor(new Color(40, 120, 220));
            g.fillRoundRect(btnContinuar.x, btnContinuar.y, btnContinuar.width, btnContinuar.height, 15, 15);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.drawString("CONTINUAR", btnContinuar.x + 55, btnContinuar.y + 33);
        }
    }
