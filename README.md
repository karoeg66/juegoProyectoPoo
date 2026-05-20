# juegoProyectoPoo
Juego creado para proyecto, usando POO, con ayuda de sprites y graphics
ENEMIES IN SIGHT

Juego 2D desarrollado en Java inspirado en el universo de One Piece donde el jugador debe sobrevivir a oleadas de enemigos y derrotar poderosos jefes usando habilidades especiales y diferentes personajes.

Descripción

ENEMIES IN SIGHT es un juego desarrollado con Programación Orientada a Objetos en Java utilizando gráficos con Swing y AWT.

El jugador puede elegir entre Luffy, Zoro y Sanji, cada uno con habilidades y estadísticas diferentes.

El objetivo es sobrevivir a enemigos, recoger objetos y derrotar a los bosses finales de cada nivel.

Características
Animaciones con sprites
Ataques normales y especiales
Sistema de cooldowns
Sistema de vida y regeneración
Pantallas de menú
Pantalla de victoria y derrota
Selección de personajes
Selección de niveles
Bosses con múltiples fases
Sistema de objetos e inventario
Detección de teclado en tiempo real
Música y efectos de sonido
Arquitectura basada en POO



Personajes

Luffy
Velocidad media
Inmune a balas
Recibe doble daño con espada
Zoro


Más daño
Más lento


Sanji
Más rápido
Menos daño


Controles


Tecla	Acción
W A S D	Movimiento
J	Ataque
K	Ataque especial
E	Recoger item
ESC	Pausa


Niveles


Nivel 1

Sobrevive durante 3 minutos
Aparece Arlong como boss final
Debes derrotarlo para desbloquear el siguiente nivel
Nivel 2

Combate directo contra Crocodile
Utiliza ataques globales
Debes acercarte para atacarlo

Items

Carne

Recupera vida gradualmente

Especial

Permite usar un ataque especial de área


Mecánicas principales
Sistema de animaciones

Cada entidad utiliza animaciones basadas en frames cargados desde spritesheets o imágenes individuales.

Sistema de bosses

Los bosses cambian de fase dependiendo de la vida restante y modifican sus patrones de ataque.

Sistema AFK

El personaje cambia automáticamente a estados de quieto o reacción cuando el jugador deja de moverse.

Sistema de cooldown

Los ataques normales y especiales tienen tiempos de espera para evitar spam.

Cómo ejecutar
Clonar el repositorio
git clone URL_DEL_REPOSITORIO
Abrir el proyecto en IntelliJ IDEA o NetBeans
Ejecutar la clase principal
Main.java
Requisitos
Java JDK 17 o superior
IntelliJ IDEA recomendado

Autores
Lued Karolay Garcia Vasquez
Elkin alzate
Santiago Sanchez Sanchez

Proyecto desarrollado como práctica de Programación Orientada a Objetos
