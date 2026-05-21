package controller;

import model.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class InputController implements KeyListener {

    /**
     * Boleano para saber si se esta capturando el nombre
     */
    private boolean capturandoNombre = false;

    /**
     * Tecla para atacar
     */
    public static final int TECLA_ATACAR = KeyEvent.VK_J;

    /**
     * Tecla para usar el ataque especial
     */
    public static final int TECLA_ESPECIAL = KeyEvent.VK_K;

    /**
     * Tecla para recoger objetos
     */
    public static final int TECLA_RECOGER = KeyEvent.VK_E;

    /**
     * Tecla para pausar el juego
     */
    public static final int TECLA_PAUSA = KeyEvent.VK_ESCAPE;

    /**
     * Tecla de movimiento hacia arriba
     */
    public static final int TECLA_ARRIBA = KeyEvent.VK_W;

    /**
     * Tecla de movimiento hacia abajo
     */
    public static final int TECLA_ABAJO = KeyEvent.VK_S;

    /**
     * Tecla de movimiento hacia la izquierda
     */
    public static final int TECLA_IZQ = KeyEvent.VK_A;

    /**
     * Tecla de movimiento hacia la derecha
     */
    public static final int TECLA_DER = KeyEvent.VK_D;

    /**
     * Conjunto de teclas actualmente presionadas
     */
    private final Set<Integer> teclasPresionadas;

    /**
     * Conjunto de teclas recien presionadas
     */
    private final Set<Integer> teclasRecienPresionadas;

    private String textoNombre = "";
    private boolean confirmarNombre = false;

    /**
     * Constructor del controlador de entrada
     */
    public InputController() {

        this.teclasPresionadas = new HashSet<>();

        this.teclasRecienPresionadas = new HashSet<>();
    }

    /**
     * Detecta cuando una tecla es presionada
     *
     * @param e evento del teclado
     */

    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Registrar solo el primer frame
        if (!teclasPresionadas.contains(code)) {

            teclasRecienPresionadas.add(code);
        }

        teclasPresionadas.add(code);
    }

    /**
     * Detecta cuando una tecla es liberada
     *
     * @param e evento del teclado
     */


    public void keyReleased(KeyEvent e) {

        teclasPresionadas.remove(e.getKeyCode());
    }

    /**
     * Metodo requerido por KeyListener
     *
     * @param e evento del teclado
     */

    @Override
    public void keyTyped(KeyEvent e) {
        if (!capturandoNombre) {
            return;
        }

        char c = e.getKeyChar();

        if (c == '\b') {
            if (!textoNombre.isEmpty()) {
                textoNombre = textoNombre.substring(0, textoNombre.length() - 1);
            }
            return;
        }

        if (c == '\n') {
            confirmarNombre = true;
            return;
        }

        if (Character.isLetterOrDigit(c) || c == ' ') {
            if (textoNombre.length() < 15) {
                textoNombre += c;
            }
        }
    }

    /**
     * Limpia las teclas recien presionadas
     *
     * Debe ejecutarse al final de cada frame
     */
    public void limpiarEdges() {

        teclasRecienPresionadas.clear();
    }

    /**
     * Procesa el input del jugador
     *
     * @param player jugador controlado
     */
    public void procesarInput(Player player) {

        if (player == null || !player.estaVivo()) {
            return;
        }

        int dx = 0;
        int dy = 0;

        // Movimiento vertical
        if (teclasPresionadas.contains(TECLA_ARRIBA)) {

            dy = -1;
        }

        if (teclasPresionadas.contains(TECLA_ABAJO)) {

            dy = 1;
        }

        // Movimiento horizontal
        if (teclasPresionadas.contains(TECLA_IZQ)) {

            dx = -1;
        }

        if (teclasPresionadas.contains(TECLA_DER)) {

            dx = 1;
        }

        // Movimiento del jugador
        if (dx != 0 || dy != 0) {

            player.mover(dx, dy);

            player.setEstadoAnimacion("caminar");

            player.registrarAccion();

        } else {

            // Mantener estado actual
            String estado =
                    player.getEstadoAnimacion();

            if (estado.equals("caminar")) {

                // El cambio a quieto o reaccion
                // es manejado por Player
            }
        }

        // Ataque normal
        if (teclasRecienPresionadas.contains(TECLA_ATACAR)) {

            player.atacar();
        }

        // Ataque especial
        if (teclasRecienPresionadas.contains(TECLA_ESPECIAL)) {

            player.usarEspecial();
        }
    }

    /**
     * Limpia todas las teclas registradas
     */
    public void limpiar() {

        teclasPresionadas.clear();

        teclasRecienPresionadas.clear();
    }

    /**
     * Verifica si una tecla esta presionada
     *
     * @param tecla codigo de la tecla
     * @return true si esta presionada
     */
    public boolean estaTeclaPresionada(int tecla) {

        return teclasPresionadas.contains(tecla);
    }

    /**
     * Verifica si el jugador quiere recoger un objeto
     *
     * @return true si presiona la tecla recoger
     */
    public boolean quiereRecoger() {

        return teclasPresionadas.contains(TECLA_RECOGER);
    }

    /**
     * Verifica si el jugador quiere pausar
     *
     * Solo devuelve true el primer frame
     *
     * @return true si quiere pausar
     */
    public boolean quierePausar() {

        return teclasRecienPresionadas.contains(TECLA_PAUSA);
    }

    /**
     * Verifica si el jugador quiere volver al menu
     *
     * @return true si alguna tecla fue presionada
     */
    public boolean quiereVolverAlMenu() {

        return !teclasRecienPresionadas.isEmpty();
    }

    public String getTextoNombre() {
        return textoNombre;
    }

    public boolean quiereConfirmarNombre() {
        return confirmarNombre;
    }

    public void limpiarConfirmarNombre() {
        confirmarNombre = false;
    }

    public void limpiarTextoNombre() {
        textoNombre = "";
        confirmarNombre = false;
    }

    public void iniciarCapturaNombre() {
        capturandoNombre = true;
        textoNombre = "";
        confirmarNombre = false;
    }

    public void detenerCapturaNombre() {
        capturandoNombre = false;
        confirmarNombre = false;
    }

    public void setTextoNombre(String textoNombre) {
        this.textoNombre = textoNombre;
    }
}
