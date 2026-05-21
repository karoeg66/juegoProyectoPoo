package view;

import controller.GameController;
import controller.Historial;
import controller.InputController;
import controller.SoundManager;
import model.enums.EstadoJuego;
import model.enums.TipoPersonaje;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * Panel principal del juego (JPanel) que actua como el nucleo de la vista.
 * Implementa Runnable para gestionar el bucle principal del juego (Game Loop) a 60 FPS.
 * Controla la navegacion entre pantallas, la captura de clicks de raton,
 * la actualizacion logica y el renderizado de los componentes y escenarios.
 */

public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    private static final int FPS = 60;

    private GameController controller;
    private InputController inputController;
    private SoundManager soundManager;
    private HUD hud;

    // Pantallas
    private PantallaInicio pantallaInicio;
    private PantallaInstrucciones pantallaInstrucciones;
    private PantallaSeleccionNivel pantallaSeleccionNivel;
    private PantallaCarga pantallaCarga;
    private PantallaVictoria pantallaVictoria;
    private PantallaDerrota pantallaDerrota;
    private PantallaNombre pantallaNombre;

    private Thread gameThread;
    private boolean corriendo;

    private Image fondoNivel1;
    private Image fondoNivel2;


    /**
     * Constructor del panel de juego.
     * Define el tamaño preferido, el color de fondo por defecto y activa el enfoque
     * junto con el doble buffer para evitar parpadeos en el renderizado.
     */

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        inicializar();
    }

    /**
     * Inicializa los controladores, las pantallas secundarias, los oyentes de eventos
     * de teclado/raton, carga los recursos visuales e inicia el bucle del juego.
     */

    private void inicializar() {
        soundManager = new SoundManager();
        inputController = new InputController();
        controller = new GameController(WIDTH, HEIGHT, inputController, soundManager);
        hud = new HUD(null, null);

        pantallaInicio = new PantallaInicio();
        pantallaInstrucciones = new PantallaInstrucciones();
        pantallaSeleccionNivel = new PantallaSeleccionNivel();
        pantallaVictoria = new PantallaVictoria();
        pantallaDerrota = new PantallaDerrota();
        pantallaNombre = new PantallaNombre();

        addKeyListener(inputController);

        // ── Único MouseListener centralizado ──────────────────────────────────
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
                int mx = e.getX(), my = e.getY();
                EstadoJuego estado = controller.getEstado();

                switch (estado) {
                    case MENU -> {
                        if (pantallaInicio.btnLuffy.contains(mx, my)) {
                            seleccionarPersonaje(TipoPersonaje.LUFFY);
                        } else if (pantallaInicio.btnZoro.contains(mx, my)) {
                            seleccionarPersonaje(TipoPersonaje.ZORO);
                        } else if (pantallaInicio.btnSanji.contains(mx, my)) {
                            seleccionarPersonaje(TipoPersonaje.SANJI);
                        } else if (pantallaInicio.btnInstrucciones.contains(mx, my)) {
                            mostrarInstrucciones();
                        }
                    }
                    case INGRESAR_NOMBRE -> {
                        if (pantallaNombre.btnContinuar.contains(mx, my)) {
                            confirmarNombre();
                        }
                    }
                    case SELECCION_NIVEL -> {
                        TipoPersonaje tipo = controller.getPersonajeSeleccionado();
                        if (tipo == null) {
                            volverAlMenu();
                            return;
                        }
                        if (pantallaSeleccionNivel.btnNivel1.contains(mx, my)) {
                            iniciarJuego(tipo, 1);
                        } else if (pantallaSeleccionNivel.btnNivel2.contains(mx, my)) {
                            iniciarJuego(tipo, 2);
                        } else if (pantallaSeleccionNivel.btnVolver.contains(mx, my)) {
                            volverAlMenu();
                        }
                    }
                    case INSTRUCCIONES, VICTORIA -> volverAlMenu();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
            }
        });

        iniciarGameLoop();


        try {
            fondoNivel1 = ImageIO.read(new File("resources/LEVEL1.png"));
            fondoNivel2 = ImageIO.read(new File("resources/LEVEL2.png"));
        } catch (IOException e) {
            System.err.println("No se pudo cargar el fondo");
        }
    }

    /**
     * Crea e inicia el hilo secundario dedicado a ejecutar el bucle principal del juego.
     */

    public void iniciarGameLoop() {
        corriendo = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Ciclo de ejecucion principal (Game Loop) utilizando el metodo Delta.
     * Garantiza actualizaciones de logica y repintado de pantalla constantes a 60 FPS.
     */

    @Override
    public void run() {
        double intervalo = 1_000_000_000.0 / FPS;
        double delta = 0;
        long ultimoTiempo = System.nanoTime();

        while (corriendo) {
            long ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo) / intervalo;
            ultimoTiempo = ahora;

            if (delta >= 1) {
                actualizar();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Actualiza el estado de la logica del juego dependiendo de la pantalla actual activa.
     */

    public void actualizar() {
        EstadoJuego estado = controller.getEstado();

        switch (estado) {
            case NIVEL1, NIVEL2, PAUSA -> controller.actualizar();
            case INSTRUCCIONES -> {
                if (inputController.quiereVolverAlMenu()) {
                    inputController.limpiarEdges();
                    volverAlMenu();
                }
            }
            case INGRESAR_NOMBRE -> {
                if (inputController.quiereConfirmarNombre()) {
                    confirmarNombre();
                }
            }
            case CARGANDO -> {
                // PantallaCarga ejecuta onCompleto en su propio hilo al terminar
            }
            case DERROTA -> {
                if (pantallaDerrota.debeVolverAlMenu()) {
                    pantallaDerrota = new PantallaDerrota();
                    volverAlMenu();
                }

            }
        }
    }

    /**
     * Se encarga de dibujar graficamente la interfaz de usuario en pantalla,
     * delegando el renderizado a la pantalla correspondiente segun el estado del juego.
     * @param g Objeto Graphics utilizado para pintar los componentes
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        EstadoJuego estado = controller.getEstado();

        switch (estado) {
            case MENU -> pantallaInicio.draw(g, getWidth(), getHeight());
            case INSTRUCCIONES -> pantallaInstrucciones.draw(g, getWidth(), getHeight());

            case INGRESAR_NOMBRE ->
                    pantallaNombre.draw(g, getWidth(), getHeight(), inputController.getTextoNombre());

            case SELECCION_NIVEL -> {
                TipoPersonaje tipo = controller.getPersonajeSeleccionado();
                String nombre = (tipo != null) ? tipo.name() : "?";
                pantallaSeleccionNivel.draw(g, getWidth(), getHeight(), nombre);
            }
            case CARGANDO -> {
                if (pantallaCarga != null) pantallaCarga.draw(g, getWidth(), getHeight());
            }
            case NIVEL1, NIVEL2 -> {
                dibujarFondo(g, estado);
                controller.renderizar(g);
                hud.setPlayer(controller.getPlayer());
                hud.setJefe(controller.getJefe());
                hud.dibujarBarraVidaPlayer(g);
                hud.dibujarBossBar(g);
                hud.mostrarInventario(g);
                hud.mostrarPuntaje(g, controller.getPuntaje());
                dibujarTiempo(g);
            }
            case PAUSA -> dibujarPausa(g);
            case VICTORIA -> pantallaVictoria.mostrarVictoria(g, controller.getPuntaje(), inputController.getTextoNombre(),Historial.obtenerTop3());
            case DERROTA -> pantallaDerrota.mostrarDerrota(g, controller.getTiempoTranscurrido(), controller.getPuntaje(),Historial.obtenerTop3());
        }

        g.dispose();
    }

    /**
     * Dibuja la imagen de fondo correspondiente al nivel activo. Si la imagen falla,
     * genera un fondo de color solido alternativo.
     * @param g Objeto Graphics para dibujar
     * @param estado Estado del juego para determinar el nivel
     */

    private void dibujarFondo(Graphics g, EstadoJuego estado) {
        Image fondo = (estado == EstadoJuego.NIVEL1) ? fondoNivel1 : fondoNivel2;
        if (fondo != null) {
            g.drawImage(fondo, 0, 0, WIDTH, HEIGHT, null);
        } else {
            g.setColor(estado == EstadoJuego.NIVEL1 ?
                    new Color(30, 80, 150) : new Color(180, 140, 60));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
    }

    /**
     * Dibuja una capa semitransparente oscura sobre el juego junto con el texto de pausa.
     * @param g Objeto Graphics para dibujar
     */

    private void dibujarPausa(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics fm = g.getFontMetrics();
        String txt = "PAUSA";
        g.drawString(txt, (WIDTH - fm.stringWidth(txt)) / 2, HEIGHT / 2);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        fm = g.getFontMetrics();
        String sub = "Presiona ESC para continuar";
        g.drawString(sub, (WIDTH - fm.stringWidth(sub)) / 2, HEIGHT / 2 + 40);
    }

    /**
     * Muestra el temporizador de la partida en formato MM:SS en la esquina superior derecha.
     * @param g Objeto Graphics para dibujar
     */

    private void dibujarTiempo(Graphics g) {
        long ms = controller.getTiempoTranscurrido();
        long seg = (ms / 1000) % 60;
        long min = ms / 60000;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(String.format("%02d:%02d", min, seg), WIDTH - 70, 20);
    }


    /**
     * Asigna el personaje seleccionado en el controlador y activa la entrada de texto para el nombre.
     * @param tipo El tipo de personaje seleccionado (Luffy, Zoro, Sanji)
     */


    public void seleccionarPersonaje(TipoPersonaje tipo) {
        controller.seleccionarPersonaje(tipo);
        inputController.iniciarCapturaNombre();
    }

    /**
     * Inicia de forma asincrona la pantalla de carga e inicializa el nivel indicado.
     * @param tipo Personaje seleccionado
     * @param nivel Numero de nivel a cargar (1 o 2)
     */

    public void iniciarJuego(TipoPersonaje tipo, int nivel) {
        pantallaCarga = new PantallaCarga(() -> controller.iniciarJuegoEnNivel(tipo, nivel));
        pantallaCarga.iniciar();
        controller.cambiarEstado(EstadoJuego.CARGANDO);
    }

    /**
     * Cambia el estado del juego para desplegar las instrucciones y solicita el foco del teclado.
     */

    public void mostrarInstrucciones() {
        controller.cambiarEstado(EstadoJuego.INSTRUCCIONES);
        requestFocusInWindow();
    }

    /**
     * Regresa al estado del menu de inicio principal del juego.
     */

    public void volverAlMenu() {
        controller.cambiarEstado(EstadoJuego.MENU);
    }

    /**
     * Obtiene el controlador de logica central del juego.
     * @return Instancia actual de GameController
     */

    public GameController getController() {
        return controller;
    }

    /**
     * Valida el nombre ingresado por el usuario, detiene la captura por teclado
     * y avanza a la pantalla de seleccion de nivel.
     */

    private void confirmarNombre() {
        String nombre = inputController.getTextoNombre().trim();

        if (!nombre.isEmpty()) {
            controller.setNombreJugador(nombre);
            inputController.detenerCapturaNombre();
            controller.cambiarEstado(EstadoJuego.SELECCION_NIVEL);
        }
    }
}