package model;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Clase abstracta que representa un personaje del juego, extiende de entidad
 */
public abstract class Character extends Entity {
    /**
     * Atributo CONSTANTE que controla el delay de cada frame
     */
    protected static final int DELAY_FRAME = 150; // ms entre frames

    /**
     * Atributo variable que controla la velocidad del personaje
     */
    protected int velocidad;
    /**
     * Atributo variable que controla la direccion del personaje
     */
    protected Direcccion direccion;
    /**
     * Atributo variable que controla la direccion visual del personaje
     */
    protected DireccionVisual dirVisual;
    /**
     * Atributo variable que controla si el personaje esta en pausa
     */
    protected boolean enCooldown;

    /**
     * Atributo variable que controla el estado de animacion del personaje
     */
    protected String estadoAnimacion;
    /**
     * Atributo variable que controla el frame de animacion
     */
    protected int frameAnimacion;
    /**
     * Atributo variable del ultimo frame
     */
    protected long tiempoUltimoFrame;
    /**
     * Atributo booleano que controla la animacion de muerte del personaje
     */
    protected boolean animacionMuerteTerminada = false;

    /**
     * Atributo booleano que controla los frames de la animacion de muerte
     */
    protected int totalFramesMuerte = 6; // valor por defecto; cada subclase lo sobreescribe

    /**
     * Constructor de la clase entidad
     *
     * @param x      Atributo variable de la coordenada x
     * @param y      Atributo variable de la coordenada y
     * @param width  Atributo variable del ancho de la entidad
     * @param height Atributo variable del alto de la entidad
     * @param live   Atributo variable de la vida de la entidad
     */
    public Character(int x, int y, int width, int height, int live, int velocidad) {
        super(x, y, width, height, live);
        this.velocidad = velocidad;
        this.direccion = direccion.DERECHA;
        this.dirVisual = dirVisual.DERECHA;
        this.enCooldown = false;
        this.estadoAnimacion = "caminar";
        this.frameAnimacion = 0;
        this.tiempoUltimoFrame = System.currentTimeMillis();
    }

    /**
     * Mueve la entidad en el eje X y Y según la velocidad actual.
     * También actualiza la dirección lógica y visual del personaje.
     *
     * @param dx desplazamiento horizontal.
     * @param dy desplazamiento vertical.
     */
    public void mover(int dx, int dy) {

        x += dx * velocidad;
        y += dy * velocidad;

        if (dx < 0) {
            dirVisual = DireccionVisual.IZQUIERDA;
        } else if (dx > 0) {
            dirVisual = DireccionVisual.DERECHA;
        }

        // Actualizar dirección lógica
        if (dx > 0 && dy == 0) {
            direccion = Direcccion.DERECHA;

        } else if (dx < 0 && dy == 0) {
            direccion = Direcccion.IZQUIERDA;

        } else if (dx == 0 && dy < 0) {
            direccion = Direcccion.ARRIBA;

        } else if (dx == 0 && dy > 0) {
            direccion = Direcccion.ABAJO;

        } else if (dx > 0 && dy < 0) {
            direccion = Direcccion.ARRIBA_DER;

        } else if (dx < 0 && dy < 0) {
            direccion = Direcccion.ARRIBA_IZQ;

        } else if (dx > 0 && dy > 0) {
            direccion = Direcccion.ABAJO_DER;

        } else if (dx < 0 && dy > 0) {
            direccion = Direcccion.ABAJO_IZQ;
        }
    }

    /**
     * Ejecuta el ataque principal de la entidad.
     * Debe ser implementado por las subclases.
     */
    public abstract void attack();

    /**
     * Aplica daño a la entidad.
     * Debe ser implementado por las subclases.
     *
     * @param damage cantidad de daño recibido.
     */
    public abstract void takeDamage(int damage);

    /**
     * Actualiza el frame de la animación según el tiempo configurado.
     * También detecta cuando la animación de muerte ha finalizado.
     */
    public void actualizarAnimacion() {

        long ahora = System.currentTimeMillis();

        if (ahora - tiempoUltimoFrame >= DELAY_FRAME) {

            frameAnimacion++;
            tiempoUltimoFrame = ahora;

            if (estadoAnimacion.equals("morir")
                    && !animacionMuerteTerminada) {

                if (frameAnimacion >= totalFramesMuerte) {

                    animacionMuerteTerminada = true;
                    frameAnimacion = totalFramesMuerte - 1;
                }
            }
        }
    }

    /**
     * Verifica si una animación no repetible
     * (como atacar o usar una habilidad especial)
     * ya terminó.
     *
     * @param sprites mapa que contiene los sprites por estado.
     * @param estado  estado de animación a verificar.
     * @return true si la animación terminó, false en caso contrario.
     */
    protected boolean animacionNoRepetiblTermino(
            Map<String, BufferedImage[]> sprites,
            String estado) {

        BufferedImage[] frames = sprites.get(estado);

        if (frames == null || frames.length == 0) {

            return System.currentTimeMillis()
                    - tiempoUltimoFrame > 600;
        }

        return frameAnimacion >= frames.length;
    }

    /**
     * Reproduce un sonido aleatorio asociado a la entidad
     * La implementación se realiza en las subclases
     * mediante SoundManager.
     */
    public void reproducirSonidoAleatorio() {

        // Implementado mediante SoundManager
    }

    /**
     * Determina si una animación debe repetirse en ciclo
     *
     * @param estado estado de animación.
     * @return true si la animación es repetible
     */
    protected boolean esCicloRepetible(String estado) {

        return estado.equals("caminar")
                || estado.equals("quieto")
                || estado.equals("reaccion");
    }

   // Getters y Setters


    /**
     * Obtiene la velocidad actual de la entidad.
     *
     * @return velocidad actual.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Obtiene la dirección lógica actual.
     *
     * @return dirección actual.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Obtiene la dirección visual actual.
     *
     * @return dirección visual.
     */
    public DireccionVisual getDirVisual() {
        return dirVisual;
    }

    /**
     * Verifica si la entidad está en cooldown.
     *
     * @return true si está en cooldown.
     */
    public boolean isEnCooldown() {
        return enCooldown;
    }

    /**
     * Define el estado de cooldown.
     *
     * @param enCooldown nuevo estado de cooldown.
     */
    public void setEnCooldown(boolean enCooldown) {
        this.enCooldown = enCooldown;
    }

    /**
     * Obtiene el estado actual de la animación.
     *
     * @return estado de animación.
     */
    public String getEstadoAnimacion() {
        return estadoAnimacion;
    }

    /**
     * Cambia el estado de animación actual.
     * Reinicia el frame de animación y controla
     * el estado de la animación de muerte.
     *
     * @param estadoAnimacion nuevo estado de animación.
     */
    public void setEstadoAnimacion(String estadoAnimacion) {

        if (!this.estadoAnimacion.equals(estadoAnimacion)) {

            this.estadoAnimacion = estadoAnimacion;
            this.frameAnimacion = 0;

            if (!estadoAnimacion.equals("morir")) {
                this.animacionMuerteTerminada = false;
            }
        }
    }

    /**
     * Obtiene el frame actual de la animación
     *
     * @return frame actual
     */
    public int getFrameAnimacion() {
        return frameAnimacion;
    }

    /**
     * Define la dirección visual de la entidad
     *
     * @param dirVisual nueva dirección visual
     */
    public void setDirVisual(DireccionVisual dirVisual) {
        this.dirVisual = dirVisual;
    }

    /**
     * Verifica si la animacion de muerte termino
     *
     * @return true si termino
     */
    public boolean isAnimacionMuerteTerminada() {
        return animacionMuerteTerminada;
    }

    /**
     * Define la cantidad total de frames
     * de la animación de muerte
     *
     * @param n cantidad total de frames
     */
    public void setTotalFramesMuerte(int n) {
        this.totalFramesMuerte = n;
    }
}