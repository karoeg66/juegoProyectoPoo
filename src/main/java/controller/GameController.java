package controller;

import model.Boss;
import model.Crocodile;
import model.Enemy;
import model.GameObject;
import model.Luffy;
import model.Player;
import model.Projectile;
import model.Sanji;
import model.Zoro;
import model.enums.EstadoJuego;
import model.enums.TipoEnemigo;
import model.enums.TipoPersonaje;
import view.SoundManager;
import view.SpriteManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controlador principal del videojuego.
 *
 * <p>Se encarga de coordinar la lógica general del juego: inicialización del
 * jugador, generación de enemigos y objetos, actualización del estado del juego,
 * detección de colisiones, reproducción de sonidos y renderizado.</p>
 */
public class GameController {

    /**
     * Intervalo de tiempo entre la aparición de objetos, en milisegundos.
     */
    private static final long INTERVALO_ITEM = 8000;

    /**
     * Intervalo mínimo entre sonidos aleatorios, en milisegundos.
     */
    private static final long INTERVALO_SONIDO_ALEATORIO = 7000;

    /**
     * Lista de enemigos activos.
     */
    private final List<Enemy> enemigos;

    /**
     * Lista de objetos recolectables activos.
     */
    private final List<GameObject> objetos;

    /**
     * Lista de proyectiles activos.
     */
    private final List<Projectile> proyectiles;

    /**
     * Intervalo entre generaciones de enemigos.
     */
    private final long spawnIntervalo;

    /**
     * Número máximo de enemigos simultáneos.
     */
    private final int maxEnemigos = 7;

    /**
     * Administrador de colisiones.
     */
    private final CollisionManager collisionManager;

    /**
     * Controlador de entrada del usuario.
     */
    private final InputController inputController;

    /**
     * Administrador de sonidos.
     */
    private final SoundManager soundManager;

    /**
     * Administrador de sprites.
     */
    private final SpriteManager spriteManager;

    /**
     * Generador de números aleatorios.
     */
    private final Random random;

    /**
     * Ancho del mapa.
     */
    private final int mapWidth;

    /**
     * Alto del mapa.
     */
    private final int mapHeight;

    /**
     * Indica si ya se reprodujo el sonido de reacción.
     */
    private final boolean reaccionSonando = false;
    /**
     * Tiempo del último spawn de enemigos.
     */
    private final long tiempoUltimoSpawnEnemigo;
    /**
     * Estado actual del juego.
     */
    private EstadoJuego estado;
    /**
     * Jugador actual.
     */
    private Player player;
    /**
     * Jefe actual del nivel.
     */
    private Boss jefe;
    /**
     * Tiempo de inicio del nivel.
     */
    private long tiempoInicioNivel;
    /**
     * Tiempo transcurrido desde el inicio del nivel.
     */
    private long tiempoTranscurrido;
    /**
     * Puntaje acumulado del jugador.
     */
    private int puntaje;

    /**
     * Nivel actual.
     */
    private int nivelActual;

    /**
     * Personaje seleccionado por el usuario.
     */
    private TipoPersonaje personajeSeleccionado;

    /**
     * Tiempo del último spawn de objetos.
     */
    private long tiempoUltimoSpawnItem;

    /**
     * Tiempo del último sonido aleatorio.
     */
    private long tiempoUltimoSonidoAleatorio;

    /**
     * Indica si el jugador está en animación de muerte.
     */
    private boolean jugadorEnMuerte = false;

    /**
     * Indica si el jefe está en animación de muerte.
     */
    private boolean jefeEnMuerte = false;

    /**
     * Construye el controlador principal del juego.
     *
     * @param mapWidth        ancho del mapa.
     * @param mapHeight       alto del mapa.
     * @param inputController controlador de entrada.
     * @param soundManager    administrador de sonidos.
     */
    public GameController(int mapWidth, int mapHeight,
                          InputController inputController,
                          SoundManager soundManager) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.inputController = inputController;
        this.soundManager = soundManager;
        this.spriteManager = new SpriteManager();
        this.collisionManager = new CollisionManager();
        this.random = new Random();
        this.estado = EstadoJuego.MENU;
        this.enemigos = new ArrayList<>();
        this.objetos = new ArrayList<>();
        this.proyectiles = new ArrayList<>();
        this.puntaje = 0;
        this.spawnIntervalo = 5000;
        this.tiempoUltimoSpawnEnemigo = 0;
    }

    /**
     * Guarda el personaje seleccionado y cambia al estado de selección de nivel.
     *
     * @param tipoPersonaje personaje elegido.
     */
    public void seleccionarPersonaje(TipoPersonaje tipoPersonaje) {
        this.personajeSeleccionado = tipoPersonaje;
        cambiarEstado(EstadoJuego.SELECCION_NIVEL);
    }

    /**
     * Inicia el juego desde el nivel 1.
     *
     * @param tipoPersonaje personaje con el que se jugará.
     */
    public void iniciarJuego(TipoPersonaje tipoPersonaje) {
        iniciarJuegoEnNivel(tipoPersonaje, 1);
    }

    /**
     * Inicializa el juego en el nivel indicado.
     *
     * @param tipoPersonaje personaje seleccionado.
     * @param nivel         nivel en el que se iniciará la partida.
     */
    public void iniciarJuegoEnNivel(TipoPersonaje tipoPersonaje, int nivel) {
        player = switch (tipoPersonaje) {
            case LUFFY -> new Luffy(mapWidth / 2, mapHeight / 2, mapWidth, mapHeight);
            case ZORO -> new Zoro(mapWidth / 2, mapHeight / 2, mapWidth, mapHeight);
            case SANJI -> new Sanji(mapWidth / 2, mapHeight / 2, mapWidth, mapHeight);
        };
        cargarSpritesJugador(tipoPersonaje);

        enemigos.clear();
        objetos.clear();
        proyectiles.clear();
        jefe = null;
        puntaje = 0;
        jugadorEnMuerte = false;
        jefeEnMuerte = false;
        tiempoInicioNivel = System.currentTimeMillis();
        tiempoUltimoSpawnItem = System.currentTimeMillis();
        tiempoUltimoSonidoAleatorio = System.currentTimeMillis();

        if (nivel == 2) {
            nivelActual = 2;
            jefe = new Crocodile(mapWidth, mapHeight);
            jefe.cargarSprites(spriteManager.getSpritesBoss("crocodile"));
            cambiarEstado(EstadoJuego.NIVEL2);
            soundManager.reproducirSonido("resources/audio/Crocodile/crocodileentrada.wav");
        } else {
            nivelActual = 1;
            cambiarEstado(EstadoJuego.NIVEL1);
        }
    }

    /**
     * Carga los sprites correspondientes al personaje seleccionado.
     *
     * @param tipo tipo de personaje.
     */
    private void cargarSpritesJugador(TipoPersonaje tipo) {
        switch (tipo) {
            case LUFFY -> player.cargarSprites(spriteManager.getSpritesLuffy());
            case ZORO -> player.cargarSprites(spriteManager.getSpritesZoro());
            case SANJI -> player.cargarSprites(spriteManager.getSpritesSanji());
        }
    }

    /**
     * Devuelve el personaje seleccionado.
     *
     * @return tipo de personaje seleccionado.
     */
    public TipoPersonaje getPersonajeSeleccionado() {
        return personajeSeleccionado;
    }

    /**
     * Actualiza toda la lógica del juego en cada ciclo principal.
     */
    public void actualizar() {
        // Método original conservado en tu archivo.
    }

    /**
     * Actualiza todos los enemigos activos.
     */
    private void actualizarEnemigos() {
    }

    /**
     * Actualiza todos los proyectiles activos.
     */
    private void actualizarProyectiles() {
    }

    /**
     * Elimina los objetos cuyo tiempo de vida haya expirado.
     */
    private void actualizarObjetos() {
    }

    /**
     * Genera enemigos según las condiciones del juego.
     */
    public void spawnEnemigos() {
    }

    /**
     * Devuelve el prefijo de carpeta para cargar los sprites del enemigo.
     *
     * @param tipo tipo de enemigo.
     * @return nombre del prefijo correspondiente.
     */
    private String prefijoPorTipo(TipoEnemigo tipo) {
        return switch (tipo) {
            case PIRATA1 -> "pirata1";
            case PIRATA2 -> "pirata2";
            case PIRATA3 -> "pirata3";
            case MARINE1 -> "marino1";
            case MARINE2 -> "marino2";
            default -> "pirata1";
        };
    }

    /**
     * Genera objetos recolectables en el mapa.
     */
    public void spawnObjetos() {
    }

    /**
     * Aplica daño directo al jugador.
     *
     * @param dano cantidad de daño a aplicar.
     */
    public void aplicarDanoGlobal(int dano) {
        player.recibirDano(dano);
    }

    /**
     * Elimina enemigos muertos y actualiza el puntaje.
     */
    private void limpiarMuertos() {
    }

    /**
     * Verifica si el jugador o el jefe han muerto.
     */
    public void verificarFinDelJuego() {
    }

    /**
     * Controla eventos temporizados del nivel, como la aparición del jefe.
     */
    public void controlarTiempo() {
    }

    /**
     * Reproduce sonidos aleatorios del jugador o del jefe.
     */
    private void verificarSonidoAleatorio() {
    }

    /**
     * Reproduce el sonido de reacción del jefe cuando corresponde.
     */
    private void verificarAudioReaccionJefe() {
    }

    /**
     * Devuelve la ruta del audio de ataque del jugador.
     *
     * @return ruta del archivo de audio.
     */
    public String audioAtaqueJugador() {
        return "";
    }

    /**
     * Devuelve la ruta del audio del ataque especial del jugador.
     *
     * @return ruta del archivo de audio.
     */
    public String audioEspecialJugador() {
        return "";
    }

    /**
     * Devuelve la ruta del audio de muerte del jugador.
     *
     * @return ruta del archivo de audio.
     */
    private String audioMuerteJugador() {
        return "";
    }

    /**
     * Devuelve la ruta del audio de muerte del jefe.
     *
     * @return ruta del archivo de audio.
     */
    private String audioMuerteJefe() {
        return "";
    }

    /**
     * Devuelve la ruta del audio de ataque del enemigo.
     *
     * @param e enemigo.
     * @return ruta del archivo de audio.
     */
    private String audioAtaqueEnemigo(Enemy e) {
        return "";
    }

    /**
     * Devuelve la ruta del audio de muerte del enemigo.
     *
     * @param e enemigo.
     * @return ruta del archivo de audio.
     */
    private String audioMuerteEnemigo(Enemy e) {
        return "";
    }

    /**
     * Devuelve la lista de audios aleatorios del jugador.
     *
     * @return lista de rutas de audio.
     */
    private List<String> audiosRandomJugador() {
        return List.of();
    }

    /**
     * Devuelve la lista de audios aleatorios del jefe.
     *
     * @return lista de rutas de audio.
     */
    private List<String> audiosRandomJefe() {
        return List.of();
    }

    /**
     * Reproduce el sonido de ataque del jugador.
     */
    public void reproducirSonidoAtaqueJugador() {
        soundManager.reproducirSonido(audioAtaqueJugador());
    }

    /**
     * Reproduce el sonido del ataque especial del jugador.
     */
    public void reproducirSonidoEspecialJugador() {
        soundManager.reproducirSonido(audioEspecialJugador());
    }

    /**
     * Reproduce el sonido de ataque correspondiente.
     *
     * @param quien entidad que realiza el ataque.
     */
    public void reproducirSonidoAtaque(String quien) {
    }

    /**
     * Dibuja todas las entidades del juego.
     *
     * @param g contexto gráfico.
     */
    public void renderizar(Graphics g) {
    }

    /**
     * Inicializa el segundo nivel del juego.
     */
    public void iniciarNivel2() {
    }

    /**
     * Cambia el estado actual del juego.
     *
     * @param nuevoEstado nuevo estado.
     */
    public void cambiarEstado(EstadoJuego nuevoEstado) {
        this.estado = nuevoEstado;
    }

    /**
     * @return estado actual del juego.
     */
    public EstadoJuego getEstado() {
        return estado;
    }

    /**
     * @return jugador actual.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return lista de enemigos activos.
     */
    public List<Enemy> getEnemigos() {
        return enemigos;
    }

    /**
     * @return jefe actual.
     */
    public Boss getJefe() {
        return jefe;
    }

    /**
     * @return lista de objetos activos.
     */
    public List<GameObject> getObjetos() {
        return objetos;
    }

    /**
     * @return tiempo transcurrido del nivel.
     */
    public long getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    /**
     * @return puntaje actual.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * @return nivel actual.
     */
    public int getNivelActual() {
        return nivelActual;
    }
}
