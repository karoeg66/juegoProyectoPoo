package view;

import javax.swing.*;

public class GameFrame extends JFrame {

    private GamePanel gamePanel;

    public GameFrame() {
        iniciarVentana();
    }

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

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
