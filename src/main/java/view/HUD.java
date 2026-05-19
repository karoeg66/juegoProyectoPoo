package view;

import model.Boss;
import model.Player;

import java.awt.*;

public class HUD {

    private Player player;
    private Boss jefe;

    public HUD(Player player, Boss jefe) {
        this.player = player;
        this.jefe = jefe;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setJefe(Boss jefe) {
        this.jefe = jefe;
    }

    public void dibujarBarraVidaPlayer(Graphics g) {
        if (player == null) return;

        int barX = 10, barY = 10, barW = 500, barH = 20;

        // Fondo
        g.setColor(Color.DARK_GRAY);
        g.fillRect(barX, barY, barW, barH);

        // Vida actual
        float porcentaje = (float) player.getVida() / player.getVidaMax();
        Color colorVida = porcentaje > 0.5f ? Color.GREEN :
                porcentaje > 0.25f ? Color.YELLOW : Color.RED;
        g.setColor(colorVida);
        g.fillRect(barX, barY, (int) (barW * porcentaje), barH);

        // Borde
        g.setColor(Color.WHITE);
        g.drawRect(barX, barY, barW, barH);

        // Texto
        g.setFont(new Font("Arial", Font.BOLD, 13));
        g.drawString("HP: " + player.getVida() + "/" + player.getVidaMax(),
                barX + 5, barY + 15);

        // Nombre del personaje
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(player.getTipo().name(), barX, barY - 4);
    }

    public void dibujarBossBar(Graphics g) {
        if (jefe == null || !jefe.estaVivo()) return;
        // La barra del boss se dibuja en el propio Boss.draw()
        // Aquí se puede poner el nombre encima si se desea
    }

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

    public void mostrarPuntaje(Graphics g) {
        // El puntaje se pasa desde GamePanel vía controller
        // Se dibuja en la esquina superior derecha (complementa el timer)
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        // El texto lo dibuja GamePanel junto al timer
    }

    // Sobrecarga con puntaje explícito
    public void mostrarPuntaje(Graphics g, int puntaje) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Pts: " + puntaje, GamePanel.WIDTH - 70, 40);
    }
}
