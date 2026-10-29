package modelo;


public class Enemigo extends Entidad {
    protected int vida;
    protected int danio;
    protected int recompensaPuntos; // Cuántos puntos te da al destruirlo

    public Enemigo(double x, double y, int radio, double velocidad, int vida, int danio, int recompensaPuntos) {
        super(x, y, radio, velocidad, true);
        this.vida = vida;
        this.danio = danio;
        this.recompensaPuntos = recompensaPuntos;
    }

    @Override
    public void actualizar() {
        // En un rail shooter, el entorno avanza. Por lo tanto, los enemigos 
        // simplemente bajan por la pantalla sumando su velocidad a la coordenada Y.
        this.y += this.velocidad;
        
        // Si el enemigo sale completamente por abajo de la pantalla (ej. > 650),
        // podrías desactivarlo en el MotorJuego para liberar memoria.
    }

    public void recibirDanio(int cantidad) {
        this.vida -= cantidad;
        if (this.vida <= 0) {
            this.vida = 0;
            this.activo = false; // El enemigo "muere"
        }
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        if (vida <= 0) {
            this.vida = 0;
            this.activo = false; // Si su vida llega a 0 por error, se desactiva inmediatamente
        } else {
            this.vida = vida;
        }
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int nuevoDanio) {
        if (nuevoDanio < 0) {
            this.danio = 0; // Evita que el enemigo cure al jugador
        } else {
            this.danio = nuevoDanio;
        }
    }

    public int getRecompensaPuntos() {
        return recompensaPuntos;
    }

    public void setRecompensaPuntos(int nuevosPuntos) {
        if (nuevosPuntos < 0) {
            this.recompensaPuntos = 0;
        } else {
            this.recompensaPuntos = nuevosPuntos;
        }
    }
    
}
