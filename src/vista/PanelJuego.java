package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JPanel;

import modelo.Enemigo;
import modelo.EnemigoTanque;
import modelo.Jugador;
import modelo.Proyectil;

public class PanelJuego extends javax.swing.JPanel {
    private Jugador jugador;
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private ArrayList<Proyectil> proyectiles = new ArrayList<>();
    private int puntuacion = 0;
    /**
     * Creates new form PanelJuego
     */
    public PanelJuego() {
        initComponents();
        this.setBackground(Color.BLACK);//Esto es para el fonde negro
        //En la ultima version podriamos hacer que el fondo tenga puntos blancos
        //como estrellas(nosecomosehace)
    }
    public void actualizarDatos(Jugador j, ArrayList<Enemigo> e, ArrayList<Proyectil> p, int puntuacion) {
        this.jugador = j;
        this.enemigos = e;
        this.proyectiles = p;
        this.puntuacion = puntuacion;
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Activa bordes suaves (Anti-Aliasing) para mejor acabado visual
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1. DIBUJAR JUGADOR, SU CAÑÓN Y SU ESCUDO
        if (jugador != null && jugador.getVida() > 0) {
            int jX = (int) jugador.getX();
            int jY = (int) jugador.getY();
            int jRadio = jugador.getRadio();

            // A) Escudo Activo (Aura azul brillante)
            if (jugador.isEscudoActivo()) {
                g2d.setColor(new Color(0, 191, 255, 100)); // Azul translúcido
                g2d.fillOval(jX - jRadio - 10, jY - jRadio - 10, (jRadio + 10) * 2, (jRadio + 10) * 2);
                g2d.setColor(Color.CYAN);
                g2d.drawOval(jX - jRadio - 10, jY - jRadio - 10, (jRadio + 10) * 2, (jRadio + 10) * 2);
            }

            // B) Cañón Giratorio (Gira en la dirección de anguloApuntado)
            double angulo = jugador.getAnguloApuntado();
            int largoCanon = jRadio + 15;
            int canonX = (int) (jX + Math.cos(angulo) * largoCanon);
            int canonY = (int) (jY + Math.sin(angulo) * largoCanon);

            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(7)); // Ancho del cañón
            g2d.drawLine(jX, jY, canonX, canonY);
            g2d.setStroke(new BasicStroke(1)); // Resetear grosor

            // C) Cuerpo de la Nave (Círculo Azul)
            g2d.setColor(new Color(30, 144, 255));
            g2d.fillOval(jX - jRadio, jY - jRadio, jRadio * 2, jRadio * 2);
            g2d.setColor(Color.WHITE);
            g2d.drawOval(jX - jRadio, jY - jRadio, jRadio * 2, jRadio * 2);

            // D) Barra de vida del Jugador (100 HP máx)
            dibujarBarraVida(g2d, jX, jY - jRadio - 15, jugador.getVida(), 100, 40, 6);
        }

        // 2. DIBUJAR PROYECTILES (Balas amarillas)
        g2d.setColor(Color.YELLOW);
        for (Proyectil p : proyectiles) {
            int pX = (int) p.getX();
            int pY = (int) p.getY();
            int pRadio = p.getRadio();
            g2d.fillOval(pX - pRadio, pY - pRadio, pRadio * 2, pRadio * 2);
        }

        // 3. DIBUJAR ENEMIGOS Y SUS BARRAS DE VIDA
        for (Enemigo e : enemigos) {
            int eX = (int) e.getX();
            int eY = (int) e.getY();
            int eRadio = e.getRadio();

            // Diferenciación de colores entre tipos de enemigo
            if (e instanceof EnemigoTanque) {
                g2d.setColor(new Color(178, 34, 34)); // Rojo oscuro
            } else {
                g2d.setColor(Color.RED); // Rojo encendido
            }

            g2d.fillOval(eX - eRadio, eY - eRadio, eRadio * 2, eRadio * 2);

            // Barra de vida según el tipo de enemigo
            int vidaMax = (e instanceof EnemigoTanque) ? 150 : 30;
            dibujarBarraVida(g2d, eX, eY - eRadio - 12, e.getVida(), vidaMax, 30, 5);
        }

        // 4. DIBUJAR PUNTUACIÓN (HUD en pantalla)
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("SCORE: " + puntuacion, 20, 30);
    }
    private void dibujarBarraVida(Graphics2D g2d, int centerX, int topY, int vidaActual, int vidaMax, int ancho, int alto) {
        int x = centerX - (ancho / 2);
        int porcentaje = Math.max(0, (vidaActual * ancho) / vidaMax);

        // Fondo rojo (vida faltante)
        g2d.setColor(Color.RED);
        g2d.fillRect(x, topY, ancho, alto);

        // Relleno verde (vida actual)
        g2d.setColor(Color.GREEN);
        g2d.fillRect(x, topY, porcentaje, alto);

        // Borde fino
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect(x, topY, ancho, alto);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
