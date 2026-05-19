package controller;

import model.Enemy;
import model.Entity;
import model.GameObject;
import model.Player;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class CollisionManager {

    // Flags de ataque del jugador — se resetean cuando cambia el estado de animación
    private boolean yaCausoDanoNormal = false;
    private boolean yaCausoDanoEspecial = false;
    private boolean yaCausoDanoBossNormal = false;
    private boolean yaCausoDanoBossEspecial = false;
    private String estadoAnterior = "";

    // Flags de ataque de Crocodile — se resetean cuando comienza nueva animación
    private boolean yaCausoDanoCrocodile = false;
    private boolean crocoAtacandoAnterior = false;

    public boolean hayColision(Entity a, Entity b) {
        return a.getBounds().intersects(b.getBounds());
    }

    public boolean colisionRect(Rectangle a, Rectangle b) {
        return a.intersects(b);
    }

    /**
     * Resetea todas las flags de "ya causé daño este swing" cuando el estado del
     * jugador cambia (nuevo ataque). Se llama primero en cada verificación.
     */
    private void actualizarEstadoJugador(String estadoActual) {
        if (!estadoActual.equals(estadoAnterior)) {
            yaCausoDanoNormal = false;
            yaCausoDanoEspecial = false;
            yaCausoDanoBossNormal = false;
            yaCausoDanoBossEspecial = false;
            estadoAnterior = estadoActual;
        }
    }

    /**
     * Verifica colisiones entre el jugador y los enemigos cuerpo a cuerpo.
     */
    public void verificarColisionesEnemigos(Player player, List<Enemy> enemigos, GameController gc) {
        String estadoActual = player.getEstadoAnimacion();
        actualizarEstadoJugador(estadoActual);

        Rectangle rangoEspecial = getRangoEspecial(player);

        for (Enemy e : enemigos) {
            if (!e.estaVivo()) continue;

            // Enemigo golpea al jugador (cuerpo a cuerpo)
            if (!e.isEsDistancia() && hayColision(player, e)) {
                if (e.puedeAtacar()) {
                    e.atacar();
                    player.recibirDanoEspada(e.getDano());
                    gc.reproducirSonidoAtaque("enemigo");
                }
            }

            // Jugador golpea enemigo — solo una vez por swing
            if (estadoActual.equals("atacar") && !yaCausoDanoNormal) {
                Rectangle rangoAtaque = getRangoAtaque(player);
                if (colisionRect(rangoAtaque, e.getBounds())) {
                    e.recibirDano(player.getDanoBasico());
                    gc.reproducirSonidoAtaque("jugador");
                    yaCausoDanoNormal = true;
                }
            }

            // Especial — daña a todos en el área, marcado después del loop
            if (estadoActual.equals("especial") && !yaCausoDanoEspecial) {
                if (colisionRect(rangoEspecial, e.getBounds())) {
                    e.recibirDano(player.getDanoEspecial());
                    gc.reproducirSonidoAtaque("jugador");
                }
            }
        }

        if (estadoActual.equals("especial")) {
            yaCausoDanoEspecial = true;
        }
    }

    /**
     * Verifica colisiones de proyectiles con el jugador.
     */
    public void verificarColisionesProyectiles(Player player, List<Projectile> proyectiles, GameController gc) {
        Iterator<Projectile> it = proyectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            if (p.verificarColision(player)) {
                player.recibirDanoBala(p.getDano());
                it.remove();
            } else if (p.haExpirado()) {
                it.remove();
            }
        }
    }

    /**
     * Verifica colisiones del jugador con Arlong.
     * El jugador solo puede golpear a Arlong UNA VEZ por swing de ataque.
     */
    public void verificarColisionArlong(Player player, Arlong arlong, GameController gc) {
        if (arlong == null || !arlong.estaVivo()) return;

        String estadoActual = player.getEstadoAnimacion();
        // Nota: actualizarEstadoJugador ya fue llamado en verificarColisionesEnemigos;
        // si se llama sin enemigos, resetear aquí también.
        actualizarEstadoJugador(estadoActual);

        // Arlong golpea al jugador solo durante su animación de ataque activa
        if (hayColision(player, arlong) && arlong.estaAtacando()) {
            player.recibirDanoEspada(arlong.getDanoAtaque());
            gc.reproducirSonidoAtaque("boss");
        }

        // Jugador golpea a Arlong — UNA sola vez por swing
        if (estadoActual.equals("atacar") && !yaCausoDanoBossNormal) {
            Rectangle rangoAtaque = getRangoAtaque(player);
            if (colisionRect(rangoAtaque, arlong.getBounds())) {
                arlong.recibirDano(player.getDanoBasico());
                gc.reproducirSonidoAtaque("jugador");
                yaCausoDanoBossNormal = true;
            }
        }

        // Especial — UNA sola vez por activación
        if (estadoActual.equals("especial") && !yaCausoDanoBossEspecial) {
            arlong.recibirDano(player.getDanoEspecial());
            yaCausoDanoBossEspecial = true;
        }
    }

    /**
     * Verifica colisiones del jugador con Crocodile.
     * Crocodile solo puede ser golpeado acercándose a él.
     */
    public void verificarColisionCrocodile(Player player, Crocodile croc, GameController gc) {
        if (croc == null || !croc.estaVivo()) return;

        String estadoActual = player.getEstadoAnimacion();
        actualizarEstadoJugador(estadoActual);

        // Detectar inicio de nueva animación de ataque (flanco de subida)
        boolean atacandoAhora = croc.estaAtacando();
        if (atacandoAhora && !crocoAtacandoAnterior) {
            yaCausoDanoCrocodile = false;
        }
        crocoAtacandoAnterior = atacandoAhora;

        // Crocodile aplica daño global UNA SOLA VEZ por animación de ataque
        if (atacandoAhora && !yaCausoDanoCrocodile) {
            player.recibirDano(croc.getDanoAtaqueGlobal());
            gc.reproducirSonidoAtaque("boss");
            yaCausoDanoCrocodile = true;
        }

        // Jugador golpea a Crocodile solo si está cerca — UNA vez por swing
        if (croc.jugadorPuedeGolpear(player)) {
            if (estadoActual.equals("atacar") && !yaCausoDanoBossNormal) {
                croc.recibirDano(player.getDanoBasico());
                gc.reproducirSonidoAtaque("jugador");
                yaCausoDanoBossNormal = true;
            }
            if (estadoActual.equals("especial") && !yaCausoDanoBossEspecial) {
                croc.recibirDano(player.getDanoEspecial());
                yaCausoDanoBossEspecial = true;
            }
        }
    }

    /**
     * Verifica colisiones del jugador con items en el mapa.
     */
    public void verificarColisionesObjetos(Player player, List<GameObject> objetos,
                                           boolean quiereRecoger) {
        if (!quiereRecoger) return;

        Iterator<GameObject> it = objetos.iterator();
        while (it.hasNext()) {
            GameObject obj = it.next();
            if (colisionRect(player.getBounds(), obj.getBounds())) {
                if (obj instanceof Carne c && c.sePuedeRecoger(player)) {
                    c.aplicarEfecto(player);
                    it.remove();
                } else if (obj instanceof Especial esp && esp.sePuedeRecoger(player)) {
                    esp.aplicarEfecto(player);
                    it.remove();
                }
            }
        }
    }

    private Rectangle getRangoAtaque(Player player) {
        int alcance = switch (player.getTipo()) {
            case ZORO -> 110;
            case LUFFY -> 100;
            case SANJI -> 85;
            default -> 90;
        };
        return switch (player.getDirVisual()) {
            case DERECHA ->
                    new Rectangle(player.getX() + player.getWidth(), player.getY(), alcance, player.getHeight());
            case IZQUIERDA -> new Rectangle(player.getX() - alcance, player.getY(), alcance, player.getHeight());
        };
    }

    private Rectangle getRangoEspecial(Player player) {
        int alcance = 250;
        int alturaExtra = 100;
        int py = player.getY() - alturaExtra / 2;
        int ph = player.getHeight() + alturaExtra;
        return switch (player.getDirVisual()) {
            case DERECHA -> new Rectangle(player.getX(), py, alcance, ph);
            case IZQUIERDA -> new Rectangle(player.getX() + player.getWidth() - alcance, py, alcance, ph);
        };
    }
}
