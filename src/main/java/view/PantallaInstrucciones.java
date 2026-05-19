package view;

import java.awt.*;

public class PantallaInstrucciones {
    /**
     * Dibuja la pantalla de instrucciones
     *
     * @param g objeto Graphics usado para dibujar
     * @param w ancho de la ventana
     * @param h alto de la ventana
     */
    public void draw(Graphics g, int w, int h) {

        // Fondo
        g.setColor(new Color(10, 20, 60));

        g.fillRect(0, 0, w, h);

        // Titulo
        g.setColor(new Color(255, 200, 0));

        g.setFont(new Font("Impact", Font.BOLD, 36));

        g.drawString("INSTRUCCIONES",
                w / 2 - 130,
                60);

        // Texto principal
        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 16));

        String[] lineas = {

                "MOVIMIENTO   W / A / S / D",

                "ATACAR       J",

                "ESPECIAL     K  necesitas tener el item especial",

                "RECOGER ITEM E  parate encima del item",

                "PAUSA        ESC",

                "",

                "PERSONAJES",

                "LUFFY  Velocidad media inmune a balas recibe doble dano con espada",

                "ZORO   Mas lento hace mas dano",

                "SANJI  Mas rapido hace menos dano",

                "",

                "NIVEL 1",

                "Sobrevive 3 minutos eliminando enemigos Luego aparece ARLONG",

                "Derrota a Arlong para volver al menu y elegir Nivel 2",

                "",

                "NIVEL 2",

                "Derrota a CROCODILE Sus ataques son globales acercate para golpearlo",

                "",

                "ITEMS",

                "Carne    Recupera vida",

                "Especial Activa ataque de area",

                "",

                "Haz clic en cualquier lugar para volver al menu"
        };

        int startY = 100;

        for (String linea : lineas) {

            if (linea.startsWith("PERSONAJES")
                    || linea.startsWith("NIVEL")
                    || linea.startsWith("ITEMS")
                    || linea.startsWith("MOVIMIENTO")) {

                g.setColor(new Color(255, 200, 0));

                g.setFont(new Font("Arial", Font.BOLD, 16));

            } else {

                g.setColor(Color.WHITE);

                g.setFont(new Font("Arial", Font.PLAIN, 15));
            }

            g.drawString(linea, 40, startY);

            startY += 20;
        }
    }

}
