package model;

import model.enums.TipoEnemigo;

/**
 * Fábrica de enemigos normales del juego.
 * <p>
 * Esta clase implementa el patrón de diseño Factory, cuyo propósito es
 * centralizar la creación de objetos de tipo {@code Enemy}.
 * <p>
 * Su función principal es:
 * - Crear automáticamente la subclase correcta de enemigo según el valor
 * del enum {@code TipoEnemigo}.
 * - Generar un tipo de enemigo aleatorio entre los enemigos básicos.
 * <p>
 * Los enemigos que puede crear esta fábrica son:
 * - Pirata1
 * - Pirata2
 * - Pirata3
 * - Marino1
 * - Marino2
 * <p>
 * Nota:
 * Los jefes como Arlong y Crocodile no se crean con esta clase,
 * sino con una fábrica distinta.
 *
 */
public class EnemyFactory {

    /**
     * Crea una instancia del enemigo indicado por el tipo recibido.
     * <p>
     * Dependiendo del valor del enum {@code TipoEnemigo}, este método
     * instancia la clase concreta correspondiente y la posiciona en las
     * coordenadas dadas.
     * <p>
     * Ejemplos:
     * - PIRATA1  → new Pirata1(x, y)
     * - MARINE2  → new Marino2(x, y)
     * <p>
     * Si se intenta crear un jefe con este método, se lanza una excepción
     * indicando que debe utilizarse la fábrica de jefes.
     *
     * @param tipo Tipo de enemigo que se desea crear.
     * @param x    Coordenada horizontal inicial del enemigo.
     * @param y    Coordenada vertical inicial del enemigo.
     * @return Instancia del enemigo solicitado.
     * @throws IllegalArgumentException Si el tipo corresponde a un jefe
     *                                  y no a un enemigo normal.
     */
    public static Enemy crearEnemigo(TipoEnemigo tipo, int x, int y) {
        return switch (tipo) {
            case PIRATA1 -> new Pirata1(x, y);
            case PIRATA2 -> new Pirata2(x, y);
            case PIRATA3 -> new Pirata3(x, y);
            case MARINE1 -> new Marino1(x, y);
            case MARINE2 -> new Marino2(x, y);
            default -> throw new IllegalArgumentException(
                    "Usar Boss para: " + tipo
            );
        };
    }

    /**
     * Selecciona aleatoriamente un tipo de enemigo básico.
     * <p>
     * Los tipos posibles son:
     * - PIRATA1
     * - PIRATA2
     * - PIRATA3
     * - MARINE1
     * - MARINE2
     * <p>
     * Este método es útil para generar enemigos de forma automática
     * durante el juego.
     *
     * @return Un valor aleatorio del enum {@code TipoEnemigo}.
     */
    public static TipoEnemigo tipoAleatorio() {
        TipoEnemigo[] basicos = {
                TipoEnemigo.PIRATA1,
                TipoEnemigo.PIRATA2,
                TipoEnemigo.PIRATA3,
                TipoEnemigo.MARINE1,
                TipoEnemigo.MARINE2
        };

        return basicos[(int) (Math.random() * basicos.length)];
    }
}
