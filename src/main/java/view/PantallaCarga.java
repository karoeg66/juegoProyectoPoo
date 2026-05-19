package view;

import java.awt.*;

/**
 * Clase que representa la pantalla de carga
 */

public class PantallaCarga {

    /**
     * Hilo usado para la carga
     */
    private Thread hiloCarga;

    /**
     * Progreso actual de carga
     */
    private int progreso;

    /**
     * Indica si la carga termino
     */
    private boolean terminado;

    /**
     * Accion ejecutada al terminar la carga
     */
    private Runnable onCompleto;

    /**
     * Constructor de la pantalla de carga
     *
     * @param onCompleto accion ejecutada al finalizar
     */
    public PantallaCarga(Runnable onCompleto) {

        this.progreso = 0;

        this.terminado = false;

        this.onCompleto = onCompleto;
    }

    /**
     * Inicia el proceso de carga
     */
    public void iniciar() {

        terminado = false;

        progreso = 0;

        hiloCarga = new Thread(() -> {

            try {

                for (int i = 0; i <= 100; i++) {

                    progreso = i;

                    Thread.sleep(30);
                }

                if (onCompleto != null) {
                    onCompleto.run();
                }

                terminado = true;

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        });

        hiloCarga.setDaemon(true);

        hiloCarga.start();
    }

    /**
     * Dibuja la pantalla de carga
     *
     * @param g objeto Graphics usado para dibujar
     * @param w ancho de la ventana
     * @param h alto de la ventana
     */
    public void draw(Graphics g, int w, int h) {

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, w, h);

        g.setColor(new Color(255, 200, 0));

        g.setFont(new Font("Impact", Font.BOLD, 40));

        g.drawString("CARGANDO...",
                w / 2 - 120,
                h / 2 - 60);

        int barW = 400;
        int barH = 30;

        int barX = (w - barW) / 2;

        int barY = h / 2;

        g.setColor(Color.DARK_GRAY);

        g.fillRoundRect(barX,
                barY,
                barW,
                barH,
                10,
                10);

        g.setColor(new Color(255, 200, 0));

        int llena =
                (int)(barW * (progreso / 100.0));

        g.fillRoundRect(barX,
                barY,
                llena,
                barH,
                10,
                10);

        g.setColor(Color.WHITE);

        g.drawRoundRect(barX,
                barY,
                barW,
                barH,
                10,
                10);

        g.setFont(new Font("Arial", Font.BOLD, 18));

        g.drawString(progreso + "%",
                w / 2 - 15,
                barY + 22);
    }

    /**
     * Verifica si la carga termino
     *
     * @return true si termino
     */
    public boolean haTerminado() {
        return terminado;
    }

    /**
     * Obtiene el progreso actual
     *
     * @return porcentaje de progreso
     */
    public int getProgreso() {
        return progreso;
    }

}
