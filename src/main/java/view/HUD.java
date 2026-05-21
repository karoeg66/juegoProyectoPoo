package view;

import model.Boss;
import model.Player;

import java.awt.*;


/**
 * Clase encargada de renderizar la Interfaz de Usuario (HUD) durante la partida.
 * Se ocupa de dibujar componentes visuales en la pantalla como la barra de vida
 * dinámica del jugador, los indicadores de inventario (habilidades especiales)
 * y la puntuación actual acumulada.
 */


public class HUD {

    private Player player;
    private Boss jefe;

    /**
     * Constructor del HUD.
     * @param player El jugador del cual se obtendra la vida y el inventario
     * @param jefe El jefe del nivel para controlar sus barras de estado
     */

    public HUD(Player player, Boss jefe) {
        this.player = player;
        this.jefe = jefe;
    }

    /**
     * Actualiza la referencia del jugador.
     * @param player Nueva instancia o estado del jugador
     */

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Actualiza la referencia del jefe de nivel.
     * @param jefe Nueva instancia o estado del jefe
     */

    public void setJefe(Boss jefe) {
        this.jefe = jefe;
    }

    /**
     * Dibuja la barra de vida del jugador en la esquina superior izquierda.
     * Cambia de color (Verde, Amarillo, Rojo) dinamicamente segun el porcentaje
     * de salud restante y muestra el texto con los puntos de vida exactos.
     * @param g Objeto Graphics para renderizar en pantalla
     */

    public void dibujarBarraVidaPlayer(Graphics g) {
        if (player == null) return;

        int barX = 10, barY = 10, barW = 500, barH = 20;

        // Fondo
        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, barW, barH);


        float porcentaje = (float) player.getVida() / player.getVidaMax();
        Color colorVida = porcentaje > 0.5f ? Color.GREEN :
                porcentaje > 0.25f ? Color.YELLOW : Color.RED;
        g.setColor(colorVida);
        g.fillRect(barX, barY, (int) (barW * porcentaje), barH);


        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barW, barH);


        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("HP: " + player.getVida() + "/" + player.getVidaMax(),
                barX + 5, barY + 15);


        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(player.getTipo().name(), barX, barY - 4);
    }

    /**
     * Espacio reservado para la gestion visual de la barra del jefe de nivel.
     * Nota: Actualmente los jefes manejan su propio renderizado en su metodo draw.
     * @param g Objeto Graphics para renderizar en pantalla
     */

    public void dibujarBossBar(Graphics g) {
        if (jefe == null || !jefe.estaVivo()) return;
        // La barra del boss se dibuja en el propio Boss.draw()
        // Aquí se puede poner el nombre encima si se desea
    }

    /**
     * Dibuja los espacios de inventario o habilidades en la pantalla.
     * Muestra un recuadro luminoso si el ataque especial esta cargado/disponible,
     * junto con el indicador de la tecla correspondiente.
     * @param g Objeto Graphics para renderizar en pantalla
     */

    public void mostrarInventario(Graphics g) {
        if (player == null) return;

        int iconX = 10, iconY = 45, iconSize = 30;

        // Slot especial
        g.setColor(player.tieneEspecial() ? new Color(255, 215, 0) : Color.DARK_GRAY);
        g.fillRect(iconX, iconY, iconSize, iconSize);
        g.setColor(Color.WHITE);
        g.drawRect(iconX, iconY, iconSize, iconSize);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("ESP", iconX + 3, iconY + 20);
        g.drawString("[K]", iconX + 3, iconY + 42);
    }

    /**
     * Renderiza el puntaje acumulado por el jugador en la esquina superior derecha.
     * @param g Objeto Graphics para renderizar en pantalla
     * @param puntaje Puntuacion numerica actual del juego
     */


    public void mostrarPuntaje(Graphics g, int puntaje) {

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Pts: " + puntaje, GamePanel.WIDTH - 70, 40);
    }
}
