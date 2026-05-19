package view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestiona la carga y organización de todos los sprites del juego.
 * <p>
 * Esta clase se encarga de:
 * - Precargar en memoria todas las animaciones de personajes, enemigos y jefes.
 * - Cargar imágenes individuales de los ítems del juego.
 * - Proporcionar métodos que retornan mapas con las animaciones
 * correspondientes a cada entidad.
 * - Evitar recargas innecesarias mediante un sistema de caché.
 * <p>
 * Estructura general de recursos:
 * resources/sprites/
 * <p>
 * Dentro de esa carpeta, cada personaje posee subcarpetas separadas
 * por tipo de animación, por ejemplo:
 * <p>
 * resources/sprites/Luffy/CaminarDerecha/
 * resources/sprites/Luffy/Ataque/
 * resources/sprites/Luffy/Muerte/
 *
 */
public class SpriteManager {

    /**
     * Caché principal de animaciones.
     * <p>
     * Clave de ejemplo:
     * - "luffy_caminar_derecha"
     * - "zoro_atacar"
     * - "arlong_atacar3"
     * <p>
     * Valor:
     * Arreglo de BufferedImage que contiene todos los frames
     * de la animación.
     */
    private final Map<String, BufferedImage[]> cache = new HashMap<>();

    /**
     * Almacena imágenes individuales de ítems.
     * <p>
     * Claves:
     * - "carne"
     * - "especial"
     * - "bala"
     */
    private final Map<String, BufferedImage> items = new HashMap<>();

    /**
     * Constructor de SpriteManager.
     * <p>
     * Al crear una instancia se precargan automáticamente
     * todos los sprites y los ítems del juego.
     */
    public SpriteManager() {
        precargar();
    }

    /**
     * Precarga todos los sprites e ítems del juego.
    */

    private void precargar() {
        // LUFFY

        // Caminar derecha
        cache.put("luffy_caminar_derecha", cargar(
                "resources/sprites/Luffy/CaminarDerecha/LUCD1.png",
                "resources/sprites/Luffy/CaminarDerecha/LUCD2.png",
                "resources/sprites/Luffy/CaminarDerecha/LUCD3.png",
                "resources/sprites/Luffy/CaminarDerecha/LUCD4.png",
                "resources/sprites/Luffy/CaminarDerecha/LUCD5.png",
                "resources/sprites/Luffy/CaminarDerecha/LUCD6.png",
                "resources/sprites/Luffy/CaminarDerecha/LUCD7.png",
                "resources/sprites/Luffy/CaminarDerecha/LUCD8.png"
        ));

        // Caminar izquierda
        cache.put("luffy_caminar_izquierda", cargar(
                "resources/sprites/Luffy/CaminarIzquierda/LUCI1.png",
                "resources/sprites/Luffy/CaminarIzquierda/LUCI2.png",
                "resources/sprites/Luffy/CaminarIzquierda/LUCI3.png",
                "resources/sprites/Luffy/CaminarIzquierda/LUCI4.png",
                "resources/sprites/Luffy/CaminarIzquierda/LUCI5.png",
                "resources/sprites/Luffy/CaminarIzquierda/LUCI6.png",
                "resources/sprites/Luffy/CaminarIzquierda/LUCI7.png",
                "resources/sprites/Luffy/CaminarIzquierda/LUCI8.png"
        ));

        // Quieto (siempre derecha, se repite en ciclo)
        cache.put("luffy_quieto", cargar(
                "resources/sprites/Luffy/De Pie/LUS1.png",
                "resources/sprites/Luffy/De Pie/LUS2.png",
                "resources/sprites/Luffy/De Pie/LUS3.png",
                "resources/sprites/Luffy/De Pie/LUS4.png"
        ));

        // Reacción AFK (siempre derecha, se repite en ciclo)
        cache.put("luffy_reaccion", cargar(
                "resources/sprites/Luffy/Reacción/LUR1.png",
                "resources/sprites/Luffy/Reacción/LUR2.png",
                "resources/sprites/Luffy/Reacción/LUR3.png",
                "resources/sprites/Luffy/Reacción/LUR4.png",
                "resources/sprites/Luffy/Reacción/LUR5.png",
                "resources/sprites/Luffy/Reacción/LUR6.png",
                "resources/sprites/Luffy/Reacción/LUR7.png"
        ));

        // Ataque normal (siempre derecha, una sola vez)
        cache.put("luffy_atacar", cargar(
                "resources/sprites/Luffy/Ataque/LUA1.png",
                "resources/sprites/Luffy/Ataque/LUA2.png",
                "resources/sprites/Luffy/Ataque/LUA3.png",
                "resources/sprites/Luffy/Ataque/LUA4.png",
                "resources/sprites/Luffy/Ataque/LUA5.png",
                "resources/sprites/Luffy/Ataque/LUA6.png",
                "resources/sprites/Luffy/Ataque/LUA7.png"
        ));

        // Ataque especial (siempre derecha, una sola vez)
        cache.put("luffy_especial", cargar(
                "resources/sprites/Luffy/Especial/LUE1.png",
                "resources/sprites/Luffy/Especial/LUE2.png",
                "resources/sprites/Luffy/Especial/LUE3.png",
                "resources/sprites/Luffy/Especial/LUE4.png",
                "resources/sprites/Luffy/Especial/LUE5.png"
        ));

        // Muerte (siempre derecha, una sola vez)
        cache.put("luffy_morir", cargar(
                "resources/sprites/Luffy/Muerte/LUD1.png",
                "resources/sprites/Luffy/Muerte/LUD2.png",
                "resources/sprites/Luffy/Muerte/LUD3.png",
                "resources/sprites/Luffy/Muerte/LUD4.png",
                "resources/sprites/Luffy/Muerte/LUD5.png",
                "resources/sprites/Luffy/Muerte/LUD6.png",
                "resources/sprites/Luffy/Muerte/LUD7.png",
                "resources/sprites/Luffy/Muerte/LUD8.png",
                "resources/sprites/Luffy/Muerte/LUD9.png"
        ));


        // ZORO

        // Caminar derecha
        cache.put("zoro_caminar_derecha", cargar(
                "resources/sprites/Zoro/CaminarDerecha/ZOCD1.png",
                "resources/sprites/Zoro/CaminarDerecha/ZOCD2.png",
                "resources/sprites/Zoro/CaminarDerecha/ZOCD3.png",
                "resources/sprites/Zoro/CaminarDerecha/ZOCD4.png",
                "resources/sprites/Zoro/CaminarDerecha/ZOCD5.png",
                "resources/sprites/Zoro/CaminarDerecha/ZOCD6.png",
                "resources/sprites/Zoro/CaminarDerecha/ZOCD7.png",
                "resources/sprites/Zoro/CaminarDerecha/ZOCD8.png"
        ));

        // Caminar izquierda
        cache.put("zoro_caminar_izquierda", cargar(
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI1.png",
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI2.png",
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI3.png",
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI4.png",
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI5.png",
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI6.png",
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI7.png",
                "resources/sprites/Zoro/CaminarIzquierda/ZOCI8.png"
        ));

        // Quieto (siempre derecha, se repite en ciclo)
        cache.put("zoro_quieto", cargar(
                "resources/sprites/Zoro/De Pie/ZOS1.png",
                "resources/sprites/Zoro/De Pie/ZOS2.png",
                "resources/sprites/Zoro/De Pie/ZOS3.png",
                "resources/sprites/Zoro/De Pie/ZOS4.png"
        ));

        // Reacción AFK (siempre derecha, se repite en ciclo)
        cache.put("zoro_reaccion", cargar(
                "resources/sprites/Zoro/Reacción/ZOR1.png",
                "resources/sprites/Zoro/Reacción/ZOR2.png",
                "resources/sprites/Zoro/Reacción/ZOR3.png",
                "resources/sprites/Zoro/Reacción/ZOR4.png",
                "resources/sprites/Zoro/Reacción/ZOR5.png",
                "resources/sprites/Zoro/Reacción/ZOR6.png",
                "resources/sprites/Zoro/Reacción/ZOR7.png",
                "resources/sprites/Zoro/Reacción/ZOR8.png",
                "resources/sprites/Zoro/Reacción/ZOR9.png",
                "resources/sprites/Zoro/Reacción/ZOR10.png"
        ));

        // Ataque normal (siempre derecha, una sola vez)
        cache.put("zoro_atacar", cargar(
                "resources/sprites/Zoro/Ataque/ZOA1.png",
                "resources/sprites/Zoro/Ataque/ZOA2.png",
                "resources/sprites/Zoro/Ataque/ZOA3.png",
                "resources/sprites/Zoro/Ataque/ZOA4.png",
                "resources/sprites/Zoro/Ataque/ZOA5.png",
                "resources/sprites/Zoro/Ataque/ZOA6.png",
                "resources/sprites/Zoro/Ataque/ZOA7.png",
                "resources/sprites/Zoro/Ataque/ZOA8.png",
                "resources/sprites/Zoro/Ataque/ZOA9.png",
                "resources/sprites/Zoro/Ataque/ZOA10.png"
        ));

        // Ataque especial (siempre derecha, una sola vez)
        cache.put("zoro_especial", cargar(
                "resources/sprites/Zoro/Especial/ZOE1.png",
                "resources/sprites/Zoro/Especial/ZOE2.png",
                "resources/sprites/Zoro/Especial/ZOE3.png",
                "resources/sprites/Zoro/Especial/ZOE4.png",
                "resources/sprites/Zoro/Especial/ZOE5.png",
                "resources/sprites/Zoro/Especial/ZOE6.png",
                "resources/sprites/Zoro/Especial/ZOE7.png",
                "resources/sprites/Zoro/Especial/ZOE8.png",
                "resources/sprites/Zoro/Especial/ZOE9.png",
                "resources/sprites/Zoro/Especial/ZOE10.png",
                "resources/sprites/Zoro/Especial/ZOE11.png"
        ));

        // Muerte (siempre derecha, una sola vez)
        cache.put("zoro_morir", cargar(
                "resources/sprites/Zoro/Muerte/ZOD1.png",
                "resources/sprites/Zoro/Muerte/ZOD2.png",
                "resources/sprites/Zoro/Muerte/ZOD3.png",
                "resources/sprites/Zoro/Muerte/ZOD4.png",
                "resources/sprites/Zoro/Muerte/ZOD5.png",
                "resources/sprites/Zoro/Muerte/ZOD6.png",
                "resources/sprites/Zoro/Muerte/ZOD7.png"
        ));


        // SANJI

        // Caminar derecha
        cache.put("sanji_caminar_derecha", cargar(
                "resources/sprites/Sanji/CaminarDerecha/SACD1.png",
                "resources/sprites/Sanji/CaminarDerecha/SACD2.png",
                "resources/sprites/Sanji/CaminarDerecha/SACD3.png",
                "resources/sprites/Sanji/CaminarDerecha/SACD4.png",
                "resources/sprites/Sanji/CaminarDerecha/SACD5.png",
                "resources/sprites/Sanji/CaminarDerecha/SACD6.png",
                "resources/sprites/Sanji/CaminarDerecha/SACD7.png",
                "resources/sprites/Sanji/CaminarDerecha/SACD8.png"
        ));

        // Caminar izquierda
        cache.put("sanji_caminar_izquierda", cargar(
                "resources/sprites/Sanji/CaminarIzquierda/SACI1.png",
                "resources/sprites/Sanji/CaminarIzquierda/SACI2.png",
                "resources/sprites/Sanji/CaminarIzquierda/SACI3.png",
                "resources/sprites/Sanji/CaminarIzquierda/SACI4.png",
                "resources/sprites/Sanji/CaminarIzquierda/SACI5.png",
                "resources/sprites/Sanji/CaminarIzquierda/SACI6.png",
                "resources/sprites/Sanji/CaminarIzquierda/SACI7.png",
                "resources/sprites/Sanji/CaminarIzquierda/SACI8.png"
        ));

        // Quieto (siempre derecha, se repite en ciclo)
        cache.put("sanji_quieto", cargar(
                "resources/sprites/Sanji/De Pie/SAS1.png",
                "resources/sprites/Sanji/De Pie/SAS2.png",
                "resources/sprites/Sanji/De Pie/SAS3.png",
                "resources/sprites/Sanji/De Pie/SAS4.png"
        ));

        // Reacción AFK (siempre derecha, se repite en ciclo)
        cache.put("sanji_reaccion", cargar(
                "resources/sprites/Sanji/Reacción/SAR1.png",
                "resources/sprites/Sanji/Reacción/SAR2.png",
                "resources/sprites/Sanji/Reacción/SAR3.png",
                "resources/sprites/Sanji/Reacción/SAR4.png"
        ));

        // Ataque normal (siempre derecha, una sola vez)
        cache.put("sanji_atacar", cargar(
                "resources/sprites/Sanji/Ataque/SAA1.png",
                "resources/sprites/Sanji/Ataque/SAA2.png",
                "resources/sprites/Sanji/Ataque/SAA3.png",
                "resources/sprites/Sanji/Ataque/SAA4.png",
                "resources/sprites/Sanji/Ataque/SAA5.png",
                "resources/sprites/Sanji/Ataque/SAA6.png",
                "resources/sprites/Sanji/Ataque/SAA7.png",
                "resources/sprites/Sanji/Ataque/SAA8.png",
                "resources/sprites/Sanji/Ataque/SAA9.png",
                "resources/sprites/Sanji/Ataque/SAA10.png"
        ));

        // Ataque especial (siempre derecha, una sola vez)
        cache.put("sanji_especial", cargar(
                "resources/sprites/Sanji/Especial/SAE1.png",
                "resources/sprites/Sanji/Especial/SAE2.png",
                "resources/sprites/Sanji/Especial/SAE3.png",
                "resources/sprites/Sanji/Especial/SAE4.png",
                "resources/sprites/Sanji/Especial/SAE5.png",
                "resources/sprites/Sanji/Especial/SAE6.png",
                "resources/sprites/Sanji/Especial/SAE7.png",
                "resources/sprites/Sanji/Especial/SAE8.png",
                "resources/sprites/Sanji/Especial/SAE9.png",
                "resources/sprites/Sanji/Especial/SAE10.png",
                "resources/sprites/Sanji/Especial/SAE11.png"
        ));

        // Muerte (siempre derecha, una sola vez)
        cache.put("sanji_morir", cargar(
                "resources/sprites/Sanji/Muerte/SAD1.png",
                "resources/sprites/Sanji/Muerte/SAD2.png",
                "resources/sprites/Sanji/Muerte/SAD3.png",
                "resources/sprites/Sanji/Muerte/SAD4.png",
                "resources/sprites/Sanji/Muerte/SAD5.png",
                "resources/sprites/Sanji/Muerte/SAD6.png"
        ));


        // PIRATA 1

        // Caminar derecha
        cache.put("pirata1_caminar_derecha", cargar(
                "resources/sprites/Pirata 1/CaminarDerecha/P1CD1.png",
                "resources/sprites/Pirata 1/CaminarDerecha/P1CD2.png",
                "resources/sprites/Pirata 1/CaminarDerecha/P1CD3.png",
                "resources/sprites/Pirata 1/CaminarDerecha/P1CD4.png",
                "resources/sprites/Pirata 1/CaminarDerecha/P1CD5.png",
                "resources/sprites/Pirata 1/CaminarDerecha/P1CD6.png"
        ));

        // Caminar izquierda
        cache.put("pirata1_caminar_izquierda", cargar(
                "resources/sprites/Pirata 1/CaminarIzquierda/P1CI1.png",
                "resources/sprites/Pirata 1/CaminarIzquierda/P1CI2.png",
                "resources/sprites/Pirata 1/CaminarIzquierda/P1CI3.png",
                "resources/sprites/Pirata 1/CaminarIzquierda/P1CI4.png",
                "resources/sprites/Pirata 1/CaminarIzquierda/P1CI5.png",
                "resources/sprites/Pirata 1/CaminarIzquierda/P1CI6.png"
        ));

        // Ataque (siempre izquierda, una sola vez)
        cache.put("pirata1_atacar", cargar(
                "resources/sprites/Pirata 1/Atacar/P1A1.png",
                "resources/sprites/Pirata 1/Atacar/P1A2.png",
                "resources/sprites/Pirata 1/Atacar/P1A3.png",
                "resources/sprites/Pirata 1/Atacar/P1A4.png",
                "resources/sprites/Pirata 1/Atacar/P1A5.png"
        ));

        // Muerte (siempre izquierda, una sola vez)
        cache.put("pirata1_morir", cargar(
                "resources/sprites/Pirata 1/Muerte/P1M1.png",
                "resources/sprites/Pirata 1/Muerte/P1M2.png",
                "resources/sprites/Pirata 1/Muerte/P1M3.png"
        ));


        // PIRATA 2


        // Caminar derecha
        cache.put("pirata2_caminar_derecha", cargar(
                "resources/sprites/Pirata 2/CaminarDerecha/P2CD1.png",
                "resources/sprites/Pirata 2/CaminarDerecha/P2CD2.png",
                "resources/sprites/Pirata 2/CaminarDerecha/P2CD3.png",
                "resources/sprites/Pirata 2/CaminarDerecha/P2CD4.png",
                "resources/sprites/Pirata 2/CaminarDerecha/P2CD5.png",
                "resources/sprites/Pirata 2/CaminarDerecha/P2CD6.png"
        ));

        // Caminar izquierda
        cache.put("pirata2_caminar_izquierda", cargar(
                "resources/sprites/Pirata 2/CaminarIzquierda/P2CI1.png",
                "resources/sprites/Pirata 2/CaminarIzquierda/P2CI2.png",
                "resources/sprites/Pirata 2/CaminarIzquierda/P2CI3.png",
                "resources/sprites/Pirata 2/CaminarIzquierda/P2CI4.png",
                "resources/sprites/Pirata 2/CaminarIzquierda/P2CI5.png",
                "resources/sprites/Pirata 2/CaminarIzquierda/P2CI6.png"
        ));

        // Ataque (siempre izquierda, una sola vez)
        cache.put("pirata2_atacar", cargar(
                "resources/sprites/Pirata 2/Atacar/P2A1.png",
                "resources/sprites/Pirata 2/Atacar/P2A2.png",
                "resources/sprites/Pirata 2/Atacar/P2A3.png"
        ));

        // Muerte (siempre izquierda, una sola vez)
        cache.put("pirata2_morir", cargar(
                "resources/sprites/Pirata 2/Muerte/P2M1.png",
                "resources/sprites/Pirata 2/Muerte/P2M2.png",
                "resources/sprites/Pirata 2/Muerte/P2M3.png"
        ));


        // PIRATA 3

        // Caminar derecha
        cache.put("pirata3_caminar_derecha", cargar(
                "resources/sprites/Pirata 3/CaminarDerecha/P3CD1.png",
                "resources/sprites/Pirata 3/CaminarDerecha/P3CD2.png"
        ));

        // Caminar izquierda
        cache.put("pirata3_caminar_izquierda", cargar(
                "resources/sprites/Pirata 3/CaminarIzquierda/P3CI1.png",
                "resources/sprites/Pirata 3/CaminarIzquierda/P3CI2.png"
        ));

        // Ataque (siempre izquierda, una sola vez)
        cache.put("pirata3_atacar", cargar(
                "resources/sprites/Pirata 3/Atacar/P3A1.png",
                "resources/sprites/Pirata 3/Atacar/P3A2.png",
                "resources/sprites/Pirata 3/Atacar/P3A3.png",
                "resources/sprites/Pirata 3/Atacar/P3A4.png"
        ));

        // Muerte (siempre izquierda, una sola vez)
        cache.put("pirata3_morir", cargar(
                "resources/sprites/Pirata 3/Muerte/P3M1.png",
                "resources/sprites/Pirata 3/Muerte/P3M2.png",
                "resources/sprites/Pirata 3/Muerte/P3M3.png",
                "resources/sprites/Pirata 3/Muerte/P3M4.png"
        ));


        // MARINO 1

        // Caminar derecha
        cache.put("marino1_caminar_derecha", cargar(
                "resources/sprites/Marino 1/CaminarDerecha/M1CD1.png",
                "resources/sprites/Marino 1/CaminarDerecha/M1CD2.png",
                "resources/sprites/Marino 1/CaminarDerecha/M1CD3.png",
                "resources/sprites/Marino 1/CaminarDerecha/M1CD4.png",
                "resources/sprites/Marino 1/CaminarDerecha/M1CD5.png"
        ));

        // Caminar izquierda
        cache.put("marino1_caminar_izquierda", cargar(
                "resources/sprites/Marino 1/CaminarIzquierda/M1CI1.png",
                "resources/sprites/Marino 1/CaminarIzquierda/M1CI2.png",
                "resources/sprites/Marino 1/CaminarIzquierda/M1CI3.png",
                "resources/sprites/Marino 1/CaminarIzquierda/M1CI4.png",
                "resources/sprites/Marino 1/CaminarIzquierda/M1CI5.png"
        ));

        // Ataque (siempre izquierda, una sola vez)
        cache.put("marino1_atacar", cargar(
                "resources/sprites/Marino 1/Atacar/M1A1.png",
                "resources/sprites/Marino 1/Atacar/M1A2.png",
                "resources/sprites/Marino 1/Atacar/M1A3.png",
                "resources/sprites/Marino 1/Atacar/M1A4.png"
        ));

        // Muerte (siempre izquierda, una sola vez)
        cache.put("marino1_morir", cargar(
                "resources/sprites/Marino 1/Muerte/M1M1.png",
                "resources/sprites/Marino 1/Muerte/M1M2.png",
                "resources/sprites/Marino 1/Muerte/M1M3.png"
        ));


        // MARINO 2

        // Caminar derecha
        cache.put("marino2_caminar_derecha", cargar(
                "resources/sprites/Marino 2/CaminarDerecha/M2CD1.png",
                "resources/sprites/Marino 2/CaminarDerecha/M2CD2.png",
                "resources/sprites/Marino 2/CaminarDerecha/M2CD3.png",
                "resources/sprites/Marino 2/CaminarDerecha/M2CD4.png",
                "resources/sprites/Marino 2/CaminarDerecha/M2CD5.png",
                "resources/sprites/Marino 2/CaminarDerecha/M2CD6.png"
        ));

        // Caminar izquierda
        cache.put("marino2_caminar_izquierda", cargar(
                "resources/sprites/Marino 2/CaminarIzquierda/M2CI1.png",
                "resources/sprites/Marino 2/CaminarIzquierda/M2CI2.png",
                "resources/sprites/Marino 2/CaminarIzquierda/M2CI3.png",
                "resources/sprites/Marino 2/CaminarIzquierda/M2CI4.png",
                "resources/sprites/Marino 2/CaminarIzquierda/M2CI5.png",
                "resources/sprites/Marino 2/CaminarIzquierda/M2CI6.png"
        ));

        // Ataque (siempre izquierda, una sola vez)
        cache.put("marino2_atacar", cargar(
                "resources/sprites/Marino 2/Atacar/M2A1.png",
                "resources/sprites/Marino 2/Atacar/M2A2.png"
        ));

        // Muerte (siempre izquierda, una sola vez)
        cache.put("marino2_morir", cargar(
                "resources/sprites/Marino 2/Muerte/M2M1.png",
                "resources/sprites/Marino 2/Muerte/M2M2.png",
                "resources/sprites/Marino 2/Muerte/M2M3.png"
        ));


        // ARLONG

        // Caminar Derecha
        cache.put("arlong_caminar_derecha", cargar(
                "resources/sprites/Arlong/CaminarDerecha/ARCD1.png",
                "resources/sprites/Arlong/CaminarDerecha/ARCD2.png"
        ));


        // Caminar Izquierda
        cache.put("arlong_caminar_izquierda", cargar(
                "resources/sprites/Arlong/CaminarIzquierda/ARCI1.png",
                "resources/sprites/Arlong/CaminarIzquierda/ARCI2.png"
        ));

        // Reacción (se repite en ciclo entre ataques, siempre izquierda)
        cache.put("arlong_reaccion", cargar(
                "resources/sprites/Arlong/Reacción/ARR1.png",
                "resources/sprites/Arlong/Reacción/ARR2.png",
                "resources/sprites/Arlong/Reacción/ARR3.png",
                "resources/sprites/Arlong/Reacción/ARR4.png"
        ));

        // Ataque fase 1 (siempre izquierda, una sola vez)
        cache.put("arlong_atacar1", cargar(
                "resources/sprites/Arlong/Ataque1/ARA1-1.png",
                "resources/sprites/Arlong/Ataque1/ARA1-2.png",
                "resources/sprites/Arlong/Ataque1/ARA1-3.png",
                "resources/sprites/Arlong/Ataque1/ARA1-4.png"
        ));

        // Ataque fase 2 (siempre izquierda, una sola vez)
        cache.put("arlong_atacar2", cargar(
                "resources/sprites/Arlong/Ataque2/ARA2-1.png",
                "resources/sprites/Arlong/Ataque2/ARA2-2.png",
                "resources/sprites/Arlong/Ataque2/ARA2-3.png",
                "resources/sprites/Arlong/Ataque2/ARA2-4.png",
                "resources/sprites/Arlong/Ataque2/ARA2-5.png"
        ));

        // Ataque fase 3 (siempre izquierda, una sola vez)
        cache.put("arlong_atacar3", cargar(
                "resources/sprites/Arlong/Ataque3/ARA3-1.png",
                "resources/sprites/Arlong/Ataque3/ARA3-2.png",
                "resources/sprites/Arlong/Ataque3/ARA3-3.png",
                "resources/sprites/Arlong/Ataque3/ARA3-4.png",
                "resources/sprites/Arlong/Ataque3/ARA3-5.png",
                "resources/sprites/Arlong/Ataque3/ARA3-6.png",
                "resources/sprites/Arlong/Ataque3/ARA3-7.png",
                "resources/sprites/Arlong/Ataque3/ARA3-8.png",
                "resources/sprites/Arlong/Ataque3/ARA3-9.png",
                "resources/sprites/Arlong/Ataque3/ARA3-10.png",
                "resources/sprites/Arlong/Ataque3/ARA3-11.png",
                "resources/sprites/Arlong/Ataque3/ARA3-12.png",
                "resources/sprites/Arlong/Ataque3/ARA3-13.png",
                "resources/sprites/Arlong/Ataque3/ARA3-14.png",
                "resources/sprites/Arlong/Ataque3/ARA3-15.png",
                "resources/sprites/Arlong/Ataque3/ARA3-16.png",
                "resources/sprites/Arlong/Ataque3/ARA3-17.png",
                "resources/sprites/Arlong/Ataque3/ARA3-18.png"
        ));

        // Muerte (siempre izquierda, una sola vez)
        cache.put("arlong_morir", cargar(
                "resources/sprites/Arlong/Muerte/ARM1.png",
                "resources/sprites/Arlong/Muerte/ARM2.png",
                "resources/sprites/Arlong/Muerte/ARM3.png",
                "resources/sprites/Arlong/Muerte/ARM4.png"
        ));


        // CROCODILE


        // Reacción (Siempre a la izquierda que se usa para cambiar de dirección arriba-abajo)
        cache.put("crocodile_reaccion", cargar(
                "resources/sprites/Crocodile/Reacción/CRR1.png",
                "resources/sprites/Crocodile/Reacción/CRR2.png",
                "resources/sprites/Crocodile/Reacción/CRR3.png",
                "resources/sprites/Crocodile/Reacción/CRR4.png",
                "resources/sprites/Crocodile/Reacción/CRR5.png",
                "resources/sprites/Crocodile/Reacción/CRR6.png",
                "resources/sprites/Crocodile/Reacción/CRR7.png",
                "resources/sprites/Crocodile/Reacción/CRR8.png"
        ));


        // Ataque de la fase 1
        cache.put("crocodile_atacar1", cargar(
                "resources/sprites/Crocodile/Ataque 1/CRA1-1.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-2.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-3.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-4.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-5.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-6.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-7.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-8.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-9.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-10.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-11.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-12.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-13.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-14.png",
                "resources/sprites/Crocodile/Ataque 1/CRA1-15.png"
        ));

        // Ataque de la fase 2
        cache.put("crocodile_atacar2", cargar(
                "resources/sprites/Crocodile/Ataque 2/CRA2-1.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-2.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-3.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-4.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-5.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-6.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-7.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-8.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-9.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-10.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-11.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-12.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-13.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-14.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-15.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-16.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-17.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-18.png",
                "resources/sprites/Crocodile/Ataque 2/CRA2-19.png"
        ));

        // Ataque de la fase 3
        cache.put("crocodile_atacar3", cargar(
                "resources/sprites/Crocodile/Ataque 3/CRA3-1.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-2.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-3.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-4.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-5.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-6.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-7.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-8.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-9.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-10.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-11.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-12.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-13.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-14.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-15.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-16.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-17.png",
                "resources/sprites/Crocodile/Ataque 3/CRA3-18.png"
        ));

        // Muerte de Crocodile
        cache.put("crocodile_morir", cargar(
                "resources/sprites/Crocodile/Muerte/CRD1.png",
                "resources/sprites/Crocodile/Muerte/CRD2.png",
                "resources/sprites/Crocodile/Muerte/CRD3.png",
                "resources/sprites/Crocodile/Muerte/CRD4.png",
                "resources/sprites/Crocodile/Muerte/CRD5.png",
                "resources/sprites/Crocodile/Muerte/CRD6.png",
                "resources/sprites/Crocodile/Muerte/CRD7.png",
                "resources/sprites/Crocodile/Muerte/CRD8.png",
                "resources/sprites/Crocodile/Muerte/CRD9.png",
                "resources/sprites/Crocodile/Muerte/CRD10.png",
                "resources/sprites/Crocodile/Muerte/CRD11.png",
                "resources/sprites/Crocodile/Muerte/CRD12.png"


        ));

        cargarItem("carne", "resources/sprites/Carne y Comida/Carne.png");
        cargarItem("especial", "resources/sprites/Carne y Comida/Comida.png");

        System.out.println("[SpriteManager] Directorio actual: " + new File(".").getAbsolutePath());

        // ── DIAGNÓSTICO — borrar después de confirmar que funciona ────────────────
        int cargados = 0;
        for (Map.Entry<String, BufferedImage[]> entry : cache.entrySet()) {
            if (entry.getValue() != null) cargados++;
            else System.out.println("[SpriteManager] VACIO: " + entry.getKey());
        }
        System.out.println("[SpriteManager] Total cargados: " + cargados + "/" + cache.size());
        System.out.println("[SpriteManager] Directorio actual: " + new File(".").getAbsolutePath());
    }

    // ── Carga una lista de rutas y devuelve el array de frames ────────────────
    private BufferedImage[] cargar(String... rutas) {
        List<BufferedImage> frames = new ArrayList<>();
        for (String ruta : rutas) {
            try {
                File f = new File(ruta);
                if (f.exists()) frames.add(ImageIO.read(f));
                else System.err.println("[SpriteManager] No encontrado: " + ruta);
            } catch (IOException e) {
                System.err.println("[SpriteManager] Error leyendo: " + ruta);
            }
        }
        return frames.isEmpty() ? null : frames.toArray(new BufferedImage[0]);
    }

    private void cargarItem(String clave, String ruta) {
        try {
            File f = new File(ruta);
            if (f.exists()) items.put(clave, ImageIO.read(f));
        } catch (IOException e) {
            System.err.println("[SpriteManager] Error cargando item: " + ruta);
        }
    }

    // Getters de items

    public BufferedImage getItemCarne() {
        return items.get("carne");
    }

    public BufferedImage getItemEspecial() {
        return items.get("especial");
    }

    public BufferedImage getItemBala() {
        return items.get("bala");
    }


    // Mapas de sprites por personaje

    public Map<String, BufferedImage[]> getSpritesLuffy() {
        return mapJugador("luffy");
    }

    public Map<String, BufferedImage[]> getSpritesZoro() {
        return mapJugador("zoro");
    }

    public Map<String, BufferedImage[]> getSpritesSanji() {
        return mapJugador("sanji");
    }

    public Map<String, BufferedImage[]> getSpritesEnemigo(String prefijo) {
        return mapEnemigo(prefijo);
    }

    public Map<String, BufferedImage[]> getSpritesBoss(String prefijo) {
        return mapBoss(prefijo);
    }

    private Map<String, BufferedImage[]> mapJugador(String p) {
        Map<String, BufferedImage[]> m = new HashMap<>();
        putSi(m, "caminar_derecha", cache.get(p + "_caminar_derecha"));
        putSi(m, "caminar_izquierda", cache.get(p + "_caminar_izquierda"));
        putSi(m, "quieto", cache.get(p + "_quieto"));
        putSi(m, "reaccion", cache.get(p + "_reaccion"));
        putSi(m, "atacar", cache.get(p + "_atacar"));
        putSi(m, "especial", cache.get(p + "_especial"));
        putSi(m, "morir", cache.get(p + "_morir"));
        return m;
    }

    private Map<String, BufferedImage[]> mapEnemigo(String p) {
        Map<String, BufferedImage[]> m = new HashMap<>();
        putSi(m, "caminar_derecha", cache.get(p + "_caminar_derecha"));
        putSi(m, "caminar_izquierda", cache.get(p + "_caminar_izquierda"));
        putSi(m, "atacar", cache.get(p + "_atacar"));
        putSi(m, "morir", cache.get(p + "_morir"));
        return m;
    }

    private Map<String, BufferedImage[]> mapBoss(String p) {
        Map<String, BufferedImage[]> m = new HashMap<>();
        putSi(m, "caminar_derecha", cache.get(p + "_caminar_derecha"));
        putSi(m, "caminar_izquierda", cache.get(p + "_caminar_izquierda"));
        putSi(m, "reaccion", cache.get(p + "_reaccion"));
        putSi(m, "atacar1", cache.get(p + "_atacar1"));
        putSi(m, "atacar2", cache.get(p + "_atacar2"));
        putSi(m, "atacar3", cache.get(p + "_atacar3"));
        putSi(m, "morir", cache.get(p + "_morir"));
        return m;
    }

    private void putSi(Map<String, BufferedImage[]> m, String clave, BufferedImage[] frames) {
        if (frames != null) m.put(clave, frames);
    }
}

