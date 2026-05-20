package model;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public abstract class Boss extends Character {
    /**
     * Fase actual del boss
     */
    protected int fase;

    /**
     * Vida necesaria para entrar en fase 1
     */
    protected int vidaFase1;

    /**
     * Vida necesaria para entrar en fase 2
     */
    protected int vidaFase2;

    /**
     * Vida necesaria para entrar en fase 3
     */
    protected int vidaFase3;

    /**
     * Mapa de sprites del boss
     */
    protected Map<String, BufferedImage[]> sprites;

    /**
     * Constructor de la clase entidad
     *
     * @param x         Atributo variable de la coordenada x
     * @param y         Atributo variable de la coordenada y
     * @param width     Atributo variable del ancho de la entidad
     * @param height    Atributo variable del alto de la entidad
     * @param live      Atributo variable de la vida de la entidad
     * @param velocidad
     */
    public Boss(int x, int y, int width, int height, int live, int velocidad) {
        super(x, y, width, height, live, velocidad);
    }

    /**
     * Constructor de la clase Boss
     *
     * @param x posicion horizontal
     * @param y posicion vertical
     * @param width ancho del boss
     * @param height alto del boss
     * @param vida vida inicial
     * @param velocidad velocidad de movimiento
     * @param vf1 vida para fase 1
     * @param vf2 vida para fase 2
     * @param vf3 vida para fase 3
     */
    public Boss(int x,
                int y,
                int width,
                int height,
                int vida,
                int velocidad,
                int vf1,
                int vf2,
                int vf3) {

        super(x, y, width, height, vida, velocidad);

        this.fase = 1;

        this.vidaFase1 = vf1;

        this.vidaFase2 = vf2;

        this.vidaFase3 = vf3;

        this.sprites = new HashMap<>();

        this.totalFramesMuerte = 8;
    }

    /**
     * Cambia la fase del boss segun la vida actual
     */
    public void cambiarFase() {

        if (vida <= vidaFase3 && fase < 3) {

            fase = 3;

        } else if (vida <= vidaFase2 && fase < 2) {

            fase = 2;
        }
    }

    /**
     * Obtiene la clave del ataque segun la fase
     *
     * @return nombre de la animacion de ataque
     */
    protected String claveAtaqueFase() {

        return "atacar" + fase;
    }

    /**
     * Ejecuta el patron de ataque del boss
     */
    public abstract void patronAtaque();

    /**
     * Ejecuta la animacion idle del boss
     */
    public abstract void animacionIdle();

    /**
     * Verifica si el boss esta en una fase especifica
     *
     * @param n fase a verificar
     * @return true si esta en esa fase
     */
    public boolean estaEnFase(int n) {

        return fase == n;
    }

    /**
     * Reduce la vida del boss
     *
     * @param dano cantidad de dano recibido
     */

    @Override
    public void recibirDano(int dano) {
        vida = Math.max(0, vida - dano);

        cambiarFase();

        if (!estaVivo()) {

            setEstadoAnimacion("morir");
        }
    }

    /**
     * Dibuja el boss en pantalla
     *
     * @param g objeto Graphics usado para dibujar
     */
    public void draw(Graphics g) {

        // Determina la clave de animacion
        String key;

        if (estadoAnimacion.equals("caminar")) {

            key = "caminar_" + dirVisual.name().toLowerCase();

        } else {

            key = estadoAnimacion;
        }

        BufferedImage[] frames = sprites.get(key);

        // Dibuja sprite si existe
        if (frames != null && frames.length > 0) {

            int idx;

            if (esCicloRepetible(estadoAnimacion)) {

                idx = frameAnimacion % frames.length;

            } else {

                idx = Math.min(frameAnimacion,
                        frames.length - 1);
            }

            g.drawImage(frames[idx],
                    x,
                    y,
                    width,
                    height,
                    null);

        } else {

            // Placeholder temporal
            g.setColor(Color.MAGENTA);

            g.fillRect(x, y, width, height);

            g.setColor(Color.WHITE);

            g.setFont(new Font("Arial",
                    Font.BOLD,
                    13));

            g.drawString(getNombre() + " F" + fase,
                    x + 5,
                    y + 25);

            g.drawString(estadoAnimacion,
                    x + 5,
                    y + 42);
        }

        // Barra de vida del boss
        int barW = 500;
        int barH = 22;

        int screenW =
                (g.getClipBounds() != null
                        ? g.getClipBounds().width
                        : 1280);

        int screenH =
                (g.getClipBounds() != null
                        ? g.getClipBounds().height
                        : 720);

        int barX =
                (screenW - barW) / 2;

        int barY =
                screenH - 45;

        g.setColor(new Color(20, 20, 20, 200));

        g.fillRect(barX - 4,
                barY - 18,
                barW + 8,
                barH + 24);

        g.setColor(Color.DARK_GRAY);

        g.fillRect(barX,
                barY,
                barW,
                barH);

        g.setColor(new Color(180, 0, 0));

        int llena =
                (int)((vida / (double)getVidaMaximo()) * barW);

        g.fillRect(barX,
                barY,
                llena,
                barH);

        g.setColor(Color.WHITE);

        g.drawRect(barX,
                barY,
                barW,
                barH);

        g.setFont(new Font("Arial",
                Font.BOLD,
                13));

        g.drawString(
                getNombre()
                        + " "
                        + vida
                        + "/"
                        + getVidaMaximo(),

                barX + 5,
                barY - 4
        );
    }

    /**
     * Carga los sprites del boss
     *
     * @param sprites mapa de sprites
     */
    public void cargarSprites(Map<String, BufferedImage[]> sprites) {

        this.sprites = sprites;
    }

    /**
     * Obtiene la vida maxima del boss
     *
     * @return vida maxima
     */
    public abstract int getVidaMaximo();

    /**
     * Obtiene el nombre del boss
     *
     * @return nombre del boss
     */
    public abstract String getNombre();

    /**
     * Obtiene la fase actual del boss
     *
     * @return fase actual
     */
    public int getFase() {

        return fase;
    }
}
