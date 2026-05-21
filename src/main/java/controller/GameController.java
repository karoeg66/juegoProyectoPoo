package controller;

import model.*;
import model.enums.EstadoJuego;
import model.enums.TipoEnemigo;
import model.enums.TipoPersonaje;

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
     * Intervalo de tiempo para generar objetos
     */
    private static final long INTERVALO_ITEM = 8000;

    /**
     * Intervalo de tiempo para reproducir sonidos aleatorios
     */
    private static final long INTERVALO_SONIDO_ALEATORIO = 7000;

    /**
     * Lista de enemigos activos
     */
    private final List<Enemy> enemigos;

    /**
     * Lista de objetos del juego
     */
    private final List<GameObject> objetos;

    /**
     * Lista de proyectiles activos
     */
    private final List<Projectile> proyectiles;

    /**
     * Tiempo entre aparicion de enemigos
     */
    private final long spawnIntervalo;

    /**
     * Cantidad maxima de enemigos permitidos
     */
    private final int maxEnemigos = 7;

    /**
     * Administrador de colisiones
     */
    private final CollisionManager collisionManager;

    /**
     * Controlador de entradas del teclado
     */
    private final InputController inputController;

    /**
     * Administrador de sonidos
     */
    private final SoundManager soundManager;

    /**
     * Administrador de sprites
     */
    private final SpriteManager spriteManager;

    /**
     * Generador de numeros aleatorios
     */
    private final Random random;

    /**
     * Ancho del mapa
     */
    private final int mapWidth;

    /**
     * Alto del mapa
     */
    private final int mapHeight;

    /**
     * Indica si un sonido de reaccion esta reproduciendose
     */
    private boolean reaccionSonando = false;

    /**
     * Estado actual del juego
     */
    private EstadoJuego estado;

    /**
     * Jugador principal
     */
    private Player player;

    /**
     * Jefe actual del nivel
     */
    private Boss jefe;

    /**
     * Tiempo de inicio del nivel
     */
    private long tiempoInicioNivel;

    /**
     * Tiempo transcurrido desde el inicio
     */
    private long tiempoTranscurrido;

    /**
     * Tiempo del ultimo spawn de enemigo
     */
    private long tiempoUltimoSpawnEnemigo;

    /**
     * Puntaje actual del jugador
     */
    private int puntaje;

    /**
     * Nivel actual
     */
    private int nivelActual;

    /**
     * Personaje seleccionado por el jugador
     */
    private TipoPersonaje personajeSeleccionado;

    /**
     * Tiempo del ultimo spawn de objeto
     */
    private long tiempoUltimoSpawnItem;

    /**
     * Tiempo del ultimo sonido aleatorio
     */
    private long tiempoUltimoSonidoAleatorio;

    /**
     * Indica si el jugador esta en animacion de muerte
     */
    private boolean jugadorEnMuerte = false;

    /**
     * Indica si el jefe esta en animacion de muerte
     */
    private boolean jefeEnMuerte = false;

    private String nombreJugador = "";

    /**
     * Constructor principal del controlador del juego
     * @param mapWidth ancho del mapa
     * @param mapHeight alto del mapa
     * @param inputController controlador de entradas
     * @param soundManager administrador de sonidos
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
     * Guarda el personaje seleccionado
     * @param tipoPersonaje personaje elegido
     */
    public void seleccionarPersonaje(TipoPersonaje tipoPersonaje) {
        this.personajeSeleccionado = tipoPersonaje;
        cambiarEstado(EstadoJuego.INGRESAR_NOMBRE);
        inputController.iniciarCapturaNombre();
    }

    /**
     * Inicia el juego en el nivel 1
     * @param tipoPersonaje personaje seleccionado
     */
    public void iniciarJuego(TipoPersonaje tipoPersonaje) {
        iniciarJuegoEnNivel(tipoPersonaje, 1);
    }

    /**
     * Inicia el juego en un nivel especifico
     * @param tipoPersonaje personaje seleccionado
     * @param nivel nivel a iniciar
     */
    public void iniciarJuegoEnNivel(TipoPersonaje tipoPersonaje, int nivel) {
        // Crear jugador y cargarle sus sprites
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
        Carne carne = new Carne(500, 350);
        carne.setSprite(spriteManager.getItemCarne());
        objetos.add(carne);

        Especial especial = new Especial(560, 350);
        especial.setSprite(spriteManager.getItemEspecial());
        objetos.add(especial);



    }

    /**
     * Carga los sprites del jugador segun su tipo
     * @param tipo tipo de personaje
     */
    private void cargarSpritesJugador(TipoPersonaje tipo) {
        switch (tipo) {
            case LUFFY -> player.cargarSprites(spriteManager.getSpritesLuffy());
            case ZORO -> player.cargarSprites(spriteManager.getSpritesZoro());
            case SANJI -> player.cargarSprites(spriteManager.getSpritesSanji());
        }
    }

    /**
     * Obtiene el personaje seleccionado
     * @return personaje seleccionado
     */
    public TipoPersonaje getPersonajeSeleccionado() {
        return personajeSeleccionado;
    }

    /**
     * Actualiza toda la logica principal del juego
     */
    public void actualizar() {
        if (inputController.quierePausar()) {
            inputController.limpiarEdges();
            if (estado == EstadoJuego.PAUSA) {
                cambiarEstado(nivelActual == 1 ? EstadoJuego.NIVEL1 : EstadoJuego.NIVEL2);
            } else if (estado == EstadoJuego.NIVEL1 || estado == EstadoJuego.NIVEL2) {
                cambiarEstado(EstadoJuego.PAUSA);
            }
            return;
        }

        if (estado == EstadoJuego.PAUSA || estado == EstadoJuego.MENU) return;
        if (estado == EstadoJuego.VICTORIA || estado == EstadoJuego.DERROTA) return;

        tiempoTranscurrido = System.currentTimeMillis() - tiempoInicioNivel;

        if (jugadorEnMuerte) {
            player.update();
            if (player.isAnimacionMuerteTerminada()) {
                soundManager.reproducirSonido("resources/audio/Otros/derrota.wav");
                cambiarEstado(EstadoJuego.DERROTA);
            }
            if (player.getEstadoAnimacion().equals("reaccion") && !reaccionSonando) {
                reaccionSonando = true;
                String ruta = audiosRandomJugador().get(0);
                System.out.println("Intentando reproducir reaccion: " + ruta);
                soundManager.reproducirSonido(ruta);
            }
            return;
        }

        if (jefeEnMuerte && jefe != null) {
            jefe.update();

            if (jefe.isAnimacionMuerteTerminada()) {
                soundManager.reproducirSonido("resources/audio/Otros/victoria.wav");
                cambiarEstado(EstadoJuego.VICTORIA);
                jefe = null;
                jefeEnMuerte = false;
                return;
            }

            player.update();
            actualizarEnemigos();
            return;
        }

        inputController.procesarInput(player);
        if (player.consumirEspecialActivado()) {
            reproducirSonidoEspecialJugador();
        }
        player.update();
        actualizarEnemigos();
        actualizarProyectiles();
        actualizarObjetos();
        spawnEnemigos();
        spawnObjetos();

        collisionManager.verificarColisionesEnemigos(player, enemigos, this);
        collisionManager.verificarColisionesProyectiles(player, proyectiles, this);
        collisionManager.verificarColisionesObjetos(player, objetos, inputController.quiereRecoger());

        if (jefe instanceof Arlong arlong) {
            arlong.update();
            arlong.perseguirJugador(player);
            if (arlong.puedeAtacar()) {
                arlong.ataque();
                soundManager.reproducirSonido("resources/audio/Arlong/arlongataque.wav");
            }
            collisionManager.verificarColisionArlong(player, arlong, this);
        } else if (jefe instanceof Crocodile croc) {
            croc.update();
            if (croc.puedeAtacar()) {
                croc.ataque();
                Projectile balaBoss = new Projectile(
                        croc.getX() + croc.getWidth() / 2,
                        croc.getY() + croc.getHeight() / 2,
                        player.getX() + player.getWidth() / 2,
                        player.getY() + player.getHeight() / 2,
                        8,
                        35,
                        true
                );

                if (spriteManager.getItemBala() != null) {
                    balaBoss.setSprite(spriteManager.getItemBala());
                }

                proyectiles.add(balaBoss);
                soundManager.reproducirSonido("resources/audio/Crocodile/crocodileataque.wav");
            }
            collisionManager.verificarColisionCrocodile(player, croc, this);
        }

        verificarAudioReaccionJefe();
        verificarSonidoAleatorio();
        limpiarMuertos();
        verificarFinDelJuego();
        controlarTiempo();
        inputController.limpiarEdges();
    }

    /**
     * Actualiza enemigos y ataques
     */
    private void actualizarEnemigos() {
        for (Enemy e : enemigos) {
            e.update();
            if (e.isEsDistancia() && e.puedeAtacar()) {
                e.ataque();
                soundManager.reproducirSonido(audioAtaqueEnemigo(e));
                Projectile bala = new Projectile(
                        e.getX() + e.getWidth() / 2,
                        e.getY() + e.getHeight() / 2,
                        player.getX() + player.getWidth() / 2,
                        player.getY() + player.getHeight() / 2,
                        6, e.getDano(), false
                );
                if (spriteManager.getItemBala() != null)
                    bala.setSprite(spriteManager.getItemBala());
                proyectiles.add(bala);
            } else if (!e.isEsDistancia()) {
                e.perseguirJugador(player);
            }
        }
    }

    /**
     * Actualiza proyectiles activos
     */
    private void actualizarProyectiles() {
        java.util.Iterator<Projectile> it = proyectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.mover();
            if (p.haExpirado()) it.remove();
        }
    }

    /**
     * Actualiza objetos activos
     */
    private void actualizarObjetos() {
        objetos.removeIf(GameObject::haExpirado);
    }

    /**
     * Genera enemigos en el mapa
     */
    public void spawnEnemigos() {
        if (estado != EstadoJuego.NIVEL1) return;
        if (enemigos.size() >= maxEnemigos) return;
        if (tiempoTranscurrido >= 90000 && jefe != null) return;

        long ahora = System.currentTimeMillis();
        if (ahora - tiempoUltimoSpawnEnemigo >= spawnIntervalo) {
            tiempoUltimoSpawnEnemigo = ahora;
            int borde = random.nextInt(4);
            int ex, ey;
            switch (borde) {
                case 0 -> {
                    ex = random.nextInt(mapWidth);
                    ey = 0;
                }
                case 1 -> {
                    ex = random.nextInt(mapWidth);
                    ey = mapHeight - 48;
                }
                case 2 -> {
                    ex = 0;
                    ey = random.nextInt(mapHeight);
                }
                default -> {
                    ex = mapWidth - 48;
                    ey = random.nextInt(mapHeight);
                }
            }
            TipoEnemigo tipo = EnemyFactory.tipoAleatorio();
            Enemy e = EnemyFactory.crearEnemigo(tipo, ex, ey);
            // Cargar sprites al enemigo según su tipo
            e.cargarSprites(spriteManager.getSpritesEnemigo(prefijoPorTipo(tipo)));
            enemigos.add(e);
        }
    }

    /**
     * Obtiene el prefijo de sprites segun el tipo de enemigo
     * @param tipo tipo de enemigo
     * @return prefijo de sprites
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
     * Genera objetos aleatorios en el mapa
     */
    public void spawnObjetos() {
        long ahora = System.currentTimeMillis();
        if (ahora - tiempoUltimoSpawnItem >= INTERVALO_ITEM) {
            tiempoUltimoSpawnItem = ahora;
            int ox = 50 + random.nextInt(mapWidth - 100);
            int oy = 50 + random.nextInt(mapHeight - 100);
            if (!player.tieneEspecial() && random.nextInt(10) < 3) {
                Especial esp = new Especial(ox, oy);
                if (spriteManager.getItemEspecial() != null)
                    esp.setSprite(spriteManager.getItemEspecial());
                objetos.add(esp);
            } else {
                Carne carne = new Carne(ox, oy);
                if (spriteManager.getItemCarne() != null)
                    carne.setSprite(spriteManager.getItemCarne());
                objetos.add(carne);
            }
        }
    }

    /**
     * Aplica daño al jugador
     * @param dano cantidad de daño
     */
    public void aplicarDanoGlobal(int dano) {
        player.recibirDano(dano);
    }

    /**
     * Elimina enemigos muertos
     */
    private void limpiarMuertos() {
        java.util.Iterator<Enemy> it = enemigos.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            if (!e.estaVivo() && e.isAnimacionMuerteTerminada()) {
                puntaje += 10;
                soundManager.reproducirSonido(audioMuerteEnemigo(e));
                it.remove();
            }
        }
    }

    /**
     * Verifica si el juego termino
     */
    public void verificarFinDelJuego() {

        if (!player.estaVivo() && !jugadorEnMuerte) {
            jugadorEnMuerte = true;
            Historial.guardar(nombreJugador, puntaje);
            soundManager.reproducirSonido(audioMuerteJugador());
            return;
        }

        if (jefe != null && !jefe.estaVivo() && !jefeEnMuerte) {
            jefeEnMuerte = true;
            soundManager.reproducirSonido(audioMuerteJefe());
            puntaje += 100;
            Historial.guardar(nombreJugador, puntaje);
            cambiarEstado(EstadoJuego.VICTORIA);
        }
    }

    /**
     * Controla eventos basados en el tiempo
     */
    public void controlarTiempo() {
        if (estado == EstadoJuego.NIVEL1) {
            if (tiempoTranscurrido >= 90000 && jefe == null) {
                enemigos.clear();
                jefe = new Arlong(mapWidth / 2, 50);
                jefe.cargarSprites(spriteManager.getSpritesBoss("arlong"));
                jefeEnMuerte = false;
                soundManager.reproducirSonido("resources/audio/Arlong/arlongllegada.wav");
            }
        }
    }

    /**
     * Reproduce sonidos aleatorios
     */
    private void verificarSonidoAleatorio() {
        long ahora = System.currentTimeMillis();
        if (ahora - tiempoUltimoSonidoAleatorio < INTERVALO_SONIDO_ALEATORIO) return;
        tiempoUltimoSonidoAleatorio = ahora;

        if (soundManager.hayAudioActivo()) return;

        if (jefe != null && jefe.estaVivo()) {
            soundManager.reproducirSonidoAleatorio(audiosRandomJefe(), 0);
            return;
        }

        soundManager.reproducirSonidoAleatorio(audiosRandomJugador(), 0);
    }

    /**
     * Verifica sonido de reaccion del jefe
     */
    private void verificarAudioReaccionJefe() {
        if (jefe == null || !jefe.estaVivo()) return;
        if (!jefe.getEstadoAnimacion().equals("reaccion")) return;
        if (soundManager.hayAudioActivo()) return;

        if (jefe instanceof Arlong) {
            soundManager.reproducirSonido("resources/audio/Arlong/arlongreaccion.wav");
        } else if (jefe instanceof Crocodile) {
            soundManager.reproducirSonido("resources/audio/Crocodile/crocodilereaccion.wav");
        }
    }

    /**
     * Obtiene audio de ataque del jugador
     * @return ruta del audio
     */
    public String audioAtaqueJugador() {
        if (player == null) return "";
        return switch (player.getTipo()) {
            case LUFFY -> "resources/audio/Luffy/luffyataque.wav";
            case ZORO -> "resources/audio/Zoro/zoroataque.wav";
            case SANJI -> "resources/audio/Sanji/sanjiataque.wav";
        };
    }

    /**
     * Obtiene audio especial del jugador
     * @return ruta del audio
     */
    public String audioEspecialJugador() {
        if (player == null) return "";
        return switch (player.getTipo()) {
            case LUFFY -> "resources/audio/Luffy/luffyespecial.wav";
            case ZORO -> "resources/audio/Zoro/zoroespecial.wav";
            case SANJI -> "resources/audio/Sanji/sanjiespecial.wav";
        };
    }

    /**
     * Obtiene audio de muerte del jugador
     * @return ruta del audio
     */
    private String audioMuerteJugador() {
        if (player == null) return "resources/audio/Otros/derrota.wav";
        return switch (player.getTipo()) {
            case LUFFY -> "resources/audio/Luffy/luffymuerte.wav";
            case ZORO -> "resources/audio/Zoro/zoromuerte.wav";
            case SANJI -> "resources/audio/Sanji/sanjimuerte.wav";
        };
    }

    /**
     * Obtiene audio de muerte del jefe
     * @return ruta del audio
     */
    private String audioMuerteJefe() {
        if (jefe instanceof Arlong) return "resources/audio/Arlong/arlongderrota.wav";
        if (jefe instanceof Crocodile) return "resources/audio/Crocodile/crocodilederrota.wav";
        return "resources/audio/Otros/victoria.wav";
    }

    /**
     * Obtiene audio de ataque de enemigo
     * @param e enemigo
     * @return ruta del audio
     */
    private String audioAtaqueEnemigo(Enemy e) {
        return switch (e.getTipo()) {
            case PIRATA1 -> "resources/audio/Enemigos/enemigomuerte1.wav";
            case PIRATA2 -> "resources/audio/Enemigos/enemigomuerte2.wav";
            case PIRATA3 -> "resources/audio/Enemigos/enemigomuerte3.wav";
            case MARINE1 -> "resources/audio/Enemigos/enemigomuerte1.wav";
            case MARINE2 -> "resources/audio/Enemigos/enemigomuerte2.wav";
            default -> "";
        };
    }

    /**
     * Obtiene audio de muerte de enemigo
     * @param e enemigo
     * @return ruta del audio
     */
    private String audioMuerteEnemigo(Enemy e) {
        return "";
    }

    /**
     * Obtiene lista de audios aleatorios del jugador
     * @return lista de audios
     */
    private List<String> audiosRandomJugador() {
        if (player == null) return List.of();
        return switch (player.getTipo()) {
            case LUFFY -> List.of(
                    "resources/audio/Luffy/luffyrandom1.wav",
                    "resources/audio/Luffy/luffyrandom2.wav",
                    "resources/audio/Luffy/luffyrandom3.wav"
            );
            case ZORO -> List.of(
                    "resources/audio/Zoro/zororandom1.wav",
                    "resources/audio/Zoro/zororandom2.wav",
                    "resources/audio/Zoro/zororandom3.wav"
            );
            case SANJI -> List.of(
                    "resources/audio/Sanji/sanjireaction1.wav",
                    "resources/audio/Sanji/sanjireaction2.wav",
                    "resources/audio/Sanji/sanjireaction3.wav"
            );
        };
    }

    /**
     * Obtiene lista de audios aleatorios del jefe
     * @return lista de audios
     */
    private List<String> audiosRandomJefe() {
        if (jefe instanceof Arlong) return List.of(
                "resources/audio/Arlong/arlongreaccion.wav",
                "resources/audio/Arlong/arlongreaccion.wav"
        );
        if (jefe instanceof Crocodile) return List.of(
                "resources/audio/Crocodile/crocodilereaccion.wav",
                "resources/audio/Crocodile/crocodilereaccion.wav"
        );
        return List.of();
    }

    /**
     * Reproduce sonido de ataque del jugador
     */
    public void reproducirSonidoAtaqueJugador() {
        soundManager.reproducirSonido(audioAtaqueJugador());
    }

    /**
     * Reproduce sonido especial del jugador
     */
    public void reproducirSonidoEspecialJugador() {
        soundManager.reproducirSonido(audioEspecialJugador());
    }

    /**
     * Reproduce sonidos de ataque
     * @param quien entidad que ataca
     */
    public void reproducirSonidoAtaque(String quien) {
        switch (quien) {
            case "jugador" -> reproducirSonidoAtaqueJugador();
            case "enemigo", "boss" -> {
            }
        }
    }

    /**
     * Renderiza todos los elementos del juego
     * @param g objeto graphics
     */
    public void renderizar(Graphics g) {
        if (player != null) player.draw(g);
        for (Enemy e : enemigos) e.draw(g);
        for (GameObject obj : objetos) obj.draw(g);
        for (Projectile p : proyectiles) p.draw(g);
        if (jefe != null) jefe.draw(g);
    }

    /**
     * Inicia el nivel 2
     */
    public void iniciarNivel2() {
        enemigos.clear();
        objetos.clear();
        proyectiles.clear();
        jefe = new Crocodile(mapWidth, mapHeight);
        jefe.cargarSprites(spriteManager.getSpritesBoss("crocodile"));
        jefeEnMuerte = false;
        nivelActual = 2;
        tiempoInicioNivel = System.currentTimeMillis();
        cambiarEstado(EstadoJuego.NIVEL2);
        soundManager.reproducirSonido("resources/audio/Crocodile/crocodileentrada.wav");
    }

    /**
     * Cambia el estado actual del juego
     * @param nuevoEstado nuevo estado
     */
    public void cambiarEstado(EstadoJuego nuevoEstado) {
        this.estado = nuevoEstado;
    }
    // Getters
    public EstadoJuego getEstado() {
        return estado;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemigos() {
        return enemigos;
    }

    public Boss getJefe() {
        return jefe;
    }

    public List<GameObject> getObjetos() {
        return objetos;
    }

    public long getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }


}
