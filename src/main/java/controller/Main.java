package controller;

import view.GameFrame;

import javax.swing.*;

/**
 * Clase principal que inicializa el juego
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameFrame();
        });
    }
}
