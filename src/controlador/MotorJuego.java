package controlador;

import modelo.Entidad;
import modelo.Jugador;
import modelo.Enemigo;
import modelo.EnemigoVeloz;
import modelo.EnemigoTanque;
import modelo.Proyectil;
import vista.PanelJuego;

import java.util.ArrayList;
import java.util.Random;

public class MotorJuego implements Runnable {
    // Atributos del Modelo
    private Jugador jugadorActual;
    private ArrayList<Enemigo> listaEnemigos;
    private ArrayList<Proyectil> listaProyectiles;
    
    // Atributos de control de estado
    private PanelJuego panelJuego;
    private boolean juegoEnCurso;
    private int puntuacion;
    private int anchoPantalla;
    private int altoPantalla;
    
    // Herramientas internas del motor
    private int contadorFrames;
    private Random random;

    public MotorJuego(PanelJuego panelJuego, int anchoPantalla, int altoPantalla) {
        this.panelJuego = panelJuego;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        
        this.listaEnemigos = new ArrayList<>();
        this.listaProyectiles = new ArrayList<>();
        
        // El jugador nace centrado en la parte inferior de la pantalla (Rail Shooter)
        this.jugadorActual = new Jugador("Piloto 1", anchoPantalla / 2.0, altoPantalla - 100);
        
        this.juegoEnCurso = true;
        this.puntuacion = 0;
        this.contadorFrames = 0;
        this.random = new Random();
        this.contadorFrames = 0;
        this.random = new java.util.Random();

        // 1. Sensor para detectar los clics del ratón (Disparar)
        this.panelJuego.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                // Al hacer clic, le pedimos al modelo que genere una bala
                Proyectil nuevaBala = jugadorActual.disparar();
                if (nuevaBala != null) {
                    listaProyectiles.add(nuevaBala);
                }
                
                
            }
        });

        // 2. Sensor para detectar el movimiento del ratón (Apuntar)
        this.panelJuego.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                // Le pasamos las coordenadas exactas del puntero al tanque para la trigonometría
                jugadorActual.actualizarAngulo(e.getX(), e.getY());
            }
            
            // También actualizamos el ángulo si el jugador mueve el ratón mientras mantiene el clic
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                jugadorActual.actualizarAngulo(e.getX(), e.getY());
            }
        });
    }

    @Override
    public void run() {
        // Bucle Principal del Juego (Game Loop)
        while (juegoEnCurso) {
            // 1. Actualizar lógica matemática (Modelo)
            jugadorActual.actualizar();
            actualizarProyectiles();
            actualizarEnemigos();
            
            // 2. Generación aleatoria y detección de impactos
            generarEnemigos();
            detectarColisiones();
            
            // 3. Limpiar memoria (eliminar basura)
            limpiarEntidadesInactivas();
            
            // 4. Comprobar condición de derrota
            if (jugadorActual.getVida() <= 0) {
                juegoEnCurso = false;
                System.out.println("Fin de la partida. Puntuación final: " + puntuacion);
                
            }
            
            // 5. Renderizar gráficos (Vista)
            if (panelJuego != null) {
                panelJuego.actualizarDatos(jugadorActual, listaEnemigos, listaProyectiles, puntuacion);
            }
            
            // 6. Controlar la tasa de refresco a ~60 FPS
            try {
                Thread.sleep(16); // Pausa de 16 milisegundos por frame
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            contadorFrames++;
        }
    }

    // --- MÉTODOS PRIVADOS DEL MOTOR ---

    private void actualizarProyectiles() {
        for (Proyectil p : listaProyectiles) {
            if (p.isActivo()) {
                p.actualizar();
            }
        }
    }

    private void actualizarEnemigos() {
        for (Enemigo e : listaEnemigos) {
            if (e.isActivo()) {
                e.actualizar();
            }
        }
    }

    private void generarEnemigos() {
        // Cada 60 frames (1 segundo) aparece un enemigo nuevo
        if (contadorFrames % 60 == 0) {
            // Aparece en una coordenada X aleatoria dentro de la ventana
            double xAleatorio = random.nextInt(anchoPantalla - 50) + 25; 
            double yInicial = -50; // Nace oculto justo arriba del borde superior
            
            // 30% de probabilidad de ser un Tanque resistente, 70% de ser Veloz
            if (random.nextInt(100) < 30) {
                listaEnemigos.add(new EnemigoTanque(xAleatorio, yInicial));
            } else {
                listaEnemigos.add(new EnemigoVeloz(xAleatorio, yInicial));
            }
        }
    }

    private void detectarColisiones() {
        // 1. Validar Balas vs Enemigos
        for (Proyectil p : listaProyectiles) {
            if (!p.isActivo()) continue;
            
            for (Enemigo e : listaEnemigos) {
                if (!e.isActivo()) continue;
                
                if (hayColisionCircular(p, e)) {
                    e.recibirDanio(p.getDanio());
                    p.setActivo(false); // La bala se destruye al tocar al enemigo
                    
                    if (e.getVida() <= 0) {
                        puntuacion += e.getRecompensaPuntos(); // Sumar puntos si lo destruye
                    }
                }
            }
        }
        
        // 2. Validar Enemigos vs Jugador
        for (Enemigo e : listaEnemigos) {
            if (!e.isActivo()) continue;
            
            if (hayColisionCircular(e, jugadorActual)) {
                jugadorActual.recibirDanio(e.getDanio()); // El escudo valida internamente si bloquea o no
                e.setActivo(false); // El enemigo se destruye al impactar estilo kamikaze
            }
        }
    }

    private boolean hayColisionCircular(Entidad a, Entidad b) {
        // Teorema de Pitágoras para calcular la distancia entre los centros de ambas entidades
        double distanciaX = a.getX() - b.getX();
        double distanciaY = a.getY() - b.getY();
        double distanciaTotal = Math.sqrt((distanciaX * distanciaX) + (distanciaY * distanciaY));
        
        // Si la distancia es menor o igual a la suma de sus radios, están chocando
        return distanciaTotal <= (a.getRadio() + b.getRadio());
    }

    private void limpiarEntidadesInactivas() {
        // Usamos expresiones lambda de Java 8+ para borrar elementos de forma segura
        // Se borran si están marcados como "inactivos" o si salieron de los límites de la pantalla
        listaProyectiles.removeIf(p -> !p.isActivo() || p.getX() < 0 || p.getX() > anchoPantalla || p.getY() < 0 || p.getY() > altoPantalla);
        listaEnemigos.removeIf(e -> !e.isActivo() || e.getY() > altoPantalla + 100);
    }
    
    // --- GETTERS ÚTILES ---
    public Jugador getJugadorActual() {
        return jugadorActual;
    }
    
    public void agregarProyectil(Proyectil p) {
        if (p != null) {
            listaProyectiles.add(p);
        }
    }
}