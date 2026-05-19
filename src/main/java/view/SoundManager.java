package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Gestiona la carga, reproducción y control de todos los efectos de sonido del juego.
 * <p>
 * Esta clase se encarga de:
 * - Precargar todos los archivos de audio al iniciar el juego.
 * - Reproducir sonidos específicos.
 * - Reproducir sonidos aleatorios.
 * - Detener todos los sonidos en reproducción.
 * - Verificar si hay algún sonido activo.
 * <p>
 * Los archivos de audio se encuentran organizados en la carpeta:
 * resources/audio/
 * <p>
 * Cada personaje, enemigo y jefe posee su propia carpeta de sonidos.
 *
 */
public class SoundManager {

    /**
     * Mapa que almacena cada ruta de audio junto con su respectivo Clip.
     */
    private final Map<String, Clip> sonidos = new HashMap<>();

    /**
     * Generador de números aleatorios para seleccionar sonidos al azar.
     */
    private final Random random = new Random();

    /**
     * Tiempo en milisegundos en el que se reprodujo el último audio.
     * Se utiliza para controlar el tiempo de espera entre sonidos aleatorios.
     */
    private long tiempoUltimoAudio = 0;

    /**
     * Constructor de la clase SoundManager.
     * <p>
     * Al crear una instancia de esta clase, se cargan automáticamente
     * todos los sonidos definidos en el método precargarSonidos().
     */
    public SoundManager() {
        precargarSonidos();
    }

    /**
     * Precarga todos los archivos de audio del juego.
     * <p>
     * Este método recorre un arreglo con las rutas de todos los archivos
     * de sonido y los carga en memoria para que puedan reproducirse
     * inmediatamente durante la ejecución del juego.
     * <p>
     * DOCUMENTACIÓN DE LOS AUDIOS DE LUFFY
     * resources/audio/Luffy/luffyataque.wav
     * Sonido del ataque básico de Luffy.
     * <p>
     * resources/audio/Luffy/luffyespecial.wav
     * Sonido del ataque especial de Luffy.
     * <p>
     * resources/audio/Luffy/luffymuerte.wav
     * Sonido cuando Luffy es derrotado.
     * <p>
     * resources/audio/Luffy/luffyrandom1.wav
     * resources/audio/Luffy/luffyrandom2.wav
     * resources/audio/Luffy/luffyrandom3.wav
     * Frases aleatorias de Luffy.
     * <p>
     * DOCUMENTACIÓN DE LOS AUDIOS DE ZORO
     * resources/audio/Zoro/zoroataque.wav
     * Sonido del ataque básico de Zoro.
     * <p>
     * resources/audio/Zoro/zoroespecial.wav
     * Sonido del ataque especial de Zoro.
     * <p>
     * resources/audio/Zoro/zoromuerte.wav
     * Sonido cuando Zoro es derrotado.
     * <p>
     * resources/audio/Zoro/zororandom1.wav
     * resources/audio/Zoro/zororandom2.wav
     * resources/audio/Zoro/zororandom3.wav
     * Frases aleatorias de Zoro.
     * <p>
     * DOCUMENTACIÓN DE LOS AUDIOS DE SANJI
     * resources/audio/Sanji/sanjiataque.wav
     * Sonido del ataque básico de Sanji.
     * <p>
     * resources/audio/Sanji/sanjiespecial.wav
     * Sonido del ataque especial de Sanji.
     * <p>
     * resources/audio/Sanji/sanjimuerte.wav
     * Sonido cuando Sanji es derrotado.
     * <p>
     * resources/audio/Sanji/sanjireaction1.wav
     * resources/audio/Sanji/sanjireaction2.wav
     * resources/audio/Sanji/sanjireaction3.wav
     * Frases aleatorias de Sanji.
     * <p>
     * DOCUMENTACIÓN DE LOS AUDIOS DE ENEMIGOS
     * resources/audio/Enemigos/enemigoataque.wav
     * Sonido de ataque de enemigos normales.
     * <p>
     * resources/audio/Enemigos/enemigomuerte1.wav
     * resources/audio/Enemigos/enemigomuerte2.wav
     * resources/audio/Enemigos/enemigomuerte3.wav
     * Sonidos de muerte de enemigos normales.
     * <p>
     * DOCUMENTACIÓN DE LOS AUDIOS DE ARLONG
     * resources/audio/Arlong/arlongataque.wav
     * Sonido de ataque de Arlong.
     * <p>
     * resources/audio/Arlong/arlongreaccion.wav
     * Sonido de reacción de Arlong.
     * <p>
     * resources/audio/Arlong/arlongderrota.wav
     * Sonido de derrota de Arlong.
     * <p>
     * resources/audio/Arlong/arlongllegada.wav
     * Sonido de entrada de Arlong.
     * <p>
     * DOCUMENTACIÓN DE LOS AUDIOS DE CROCODILE
     * resources/audio/Crocodile/crocodileataque.wav
     * Sonido de ataque de Crocodile.
     * <p>
     * resources/audio/Crocodile/crocodilereaccion.wav
     * Sonido de reacción de Crocodile.
     * <p>
     * resources/audio/Crocodile/crocodilederrota.wav
     * Sonido de derrota de Crocodile.
     * <p>
     * resources/audio/Crocodile/crocodileentrada.wav
     * Sonido de entrada de Crocodile.
     * <p>
     * DOCUMENTACIÓN DE LOS AUDIOS GENERALES
     * resources/audio/Otros/victoria.wav
     * Sonido de victoria del juego.
     * <p>
     * resources/audio/Otros/derrota.wav
     * Sonido de derrota del juego.
     */
    private void precargarSonidos() {
        String[] rutas = {
                // Luffy
                "resources/audio/Luffy/luffyataque.wav",
                "resources/audio/Luffy/luffyespecial.wav",
                "resources/audio/Luffy/luffymuerte.wav",
                "resources/audio/Luffy/luffyrandom1.wav",
                "resources/audio/Luffy/luffyrandom2.wav",
                "resources/audio/Luffy/luffyrandom3.wav",

                // Zoro
                "resources/audio/Zoro/zoroataque.wav",
                "resources/audio/Zoro/zoroespecial.wav",
                "resources/audio/Zoro/zoromuerte.wav",
                "resources/audio/Zoro/zororandom1.wav",
                "resources/audio/Zoro/zororandom2.wav",
                "resources/audio/Zoro/zororandom3.wav",

                // Sanji
                "resources/audio/Sanji/sanjiataque.wav",
                "resources/audio/Sanji/sanjiespecial.wav",
                "resources/audio/Sanji/sanjimuerte.wav",
                "resources/audio/Sanji/sanjireaction1.wav",
                "resources/audio/Sanji/sanjireaction2.wav",
                "resources/audio/Sanji/sanjireaction3.wav",

                // Enemigos
                "resources/audio/Enemigos/enemigoataque.wav",
                "resources/audio/Enemigos/enemigomuerte1.wav",
                "resources/audio/Enemigos/enemigomuerte2.wav",
                "resources/audio/Enemigos/enemigomuerte3.wav",

                // Arlong
                "resources/audio/Arlong/arlongataque.wav",
                "resources/audio/Arlong/arlongreaccion.wav",
                "resources/audio/Arlong/arlongderrota.wav",
                "resources/audio/Arlong/arlongllegada.wav",

                // Crocodile
                "resources/audio/Crocodile/crocodileataque.wav",
                "resources/audio/Crocodile/crocodilereaccion.wav",
                "resources/audio/Crocodile/crocodilederrota.wav",
                "resources/audio/Crocodile/crocodileentrada.wav",

                // Otros
                "resources/audio/Otros/victoria.wav",
                "resources/audio/Otros/derrota.wav"
        };


        for (String ruta : rutas) {
            cargarSonido(ruta);
        }
    }

    /**
     * Carga un archivo de audio y lo almacena en el mapa de sonidos.
     * <p>
     * Este método verifica si el archivo existe en la ruta especificada.
     * Si el archivo es válido y puede abrirse, se crea un objeto Clip
     * y se almacena en el mapa de sonidos usando la ruta como clave.
     * <p>
     * Si el archivo no existe o no puede cargarse, se muestra un
     * mensaje de error en la consola.
     *
     * @param ruta Ruta relativa del archivo de audio que se desea cargar.
     */
    private void cargarSonido(String ruta) {
        try {
            File archivo = new File(ruta);
            if (!archivo.exists()) {
                System.err.println("[SoundManager] No existe: " + ruta);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(archivo);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            sonidos.put(ruta, clip);

            System.out.println("[SoundManager] Cargado: " + ruta);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("[SoundManager] No se pudo cargar: " + ruta);
        }
    }

    /**
     * Reproduce un sonido previamente cargado.
     * <p>
     * Si el sonido ya se encuentra en reproducción, se detiene y se
     * reinicia desde el comienzo.
     * <p>
     * Si la ruta no existe en el mapa de sonidos, el método no realiza
     * ninguna acción.
     * <p>
     * Además, actualiza el tiempo del último audio reproducido.
     *
     * @param ruta Ruta del archivo de audio que se desea reproducir.
     */
    public void reproducirSonido(String ruta) {
        Clip clip = sonidos.get(ruta);

        if (clip == null) {
            return;
        }

        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);
        clip.start();

        tiempoUltimoAudio = System.currentTimeMillis();
    }

    /**
     * Reproduce un sonido aleatorio de una lista de rutas de audio.
     * <p>
     * El sonido solo se reproduce si ha transcurrido el tiempo de espera
     * indicado por el parámetro cooldownMs desde la última reproducción.
     * <p>
     * Este método es útil para reproducir frases aleatorias de personajes
     * o jefes cuando no se está ejecutando otra acción importante.
     *
     * @param lista      Lista de rutas de archivos de audio.
     * @param cooldownMs Tiempo mínimo de espera entre reproducciones,
     *                   expresado en milisegundos.
     */
    public void reproducirSonidoAleatorio(List<String> lista, long cooldownMs) {
        long ahora = System.currentTimeMillis();

        if (ahora - tiempoUltimoAudio < cooldownMs) {
            return;
        }

        if (lista.isEmpty()) {
            return;
        }

        String ruta = lista.get(random.nextInt(lista.size()));
        reproducirSonido(ruta);
    }

    /**
     * Reproduce un sonido aleatorio utilizando un tiempo de espera
     * predeterminado de 2000 milisegundos (2 segundos).
     * <p>
     * Este método existe para facilitar la llamada sin necesidad de
     * especificar manualmente el tiempo de cooldown.
     *
     * @param lista Lista de rutas de archivos de audio.
     */
    public void reproducirSonidoAleatorio(List<String> lista) {
        reproducirSonidoAleatorio(lista, 2000);
    }

    /**
     * Detiene todos los sonidos que se encuentren reproduciéndose.
     * <p>
     * Recorre todos los objetos Clip almacenados en el mapa de sonidos
     * y detiene aquellos que estén activos.
     */
    public void detenerTodo() {
        for (Clip c : sonidos.values()) {
            if (c.isRunning()) {
                c.stop();
            }
        }
    }

    /**
     * Verifica si existe al menos un sonido reproduciéndose actualmente.
     *
     * @return true si algún Clip está en reproducción;
     * false en caso contrario.
     */
    public boolean hayAudioActivo() {
        return sonidos.values().stream().anyMatch(Clip::isRunning);
    }
}
