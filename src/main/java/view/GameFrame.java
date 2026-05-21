package view;

import javax.swing.*;

/**
 * Ventana principal del juego (JFrame).
 * Se encarga de inicializar el contenedor principal, configurar las propiedades
 * basicas de la ventana (titulo, dimensiones, cierre) y alojar el panel del juego.
 */


public class GameFrame extends JFrame {

    private GamePanel gamePanel;

    /**
     * Constructor del marco de la ventana.
     * Llama al metodo de inicializacion para configurar los componentes.
     */

    public GameFrame() {
        iniciarVentana();
    }

    /**
     * Configura los parametros de la ventana, añade el panel del juego,
     * empaqueta los componentes, centra la ventana en la pantalla y le da foco.
     */

    public void iniciarVentana() {
        setTitle("One Piece - Enemies in sight");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);
        pack();

        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);

        gamePanel.requestFocusInWindow();
    }

    /**
     * Obtiene el panel de juego asociado a esta ventana.
     * @return El objeto GamePanel que se esta ejecutando
     */

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
