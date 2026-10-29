package modelo;


public class Bomba extends Entidad {
    private int radioExplosion;
    private int danio;
    
    // Variable extra recomendada para que la explosión dure un momento en pantalla
    private int tiempoExplosion; 

    public Bomba(double x, double y) {
        // Llama al constructor de Entidad: (x, y, radio, velocidad, activo)
        // Nace como un objeto pequeño (radio 10) y estático (velocidad 0)
        super(x, y, 10, 0, true);
        
        this.radioExplosion = 300; // Un radio gigante para despejar gran parte de la pantalla
        this.danio = 150;          // Daño masivo para destruir a la mayoría de los enemigos
        this.tiempoExplosion = 30; // La explosión durará medio segundo (30 frames a 60 FPS)
    }

    @Override
    public void actualizar() {
        // Descontamos el tiempo de vida de la explosión
        if (this.tiempoExplosion > 0) {
            this.tiempoExplosion--;
        }
        
        // Cuando el temporizador llega a 0, la bomba se desactiva y desaparece
        if (this.tiempoExplosion <= 0) {
            this.activo = false;
        }
    }

    // Método solicitado en tu diagrama UML
    public void detonar() {
        if (this.activo) {
            // Al detonar, el radio base de la entidad se vuelve gigante
            // Esto permitirá que el MotorJuego detecte colisiones con casi todos los enemigos
            this.setRadio(this.radioExplosion);
        }
    }

    // --- GETTERS Y SETTERS ---

    public int getRadioExplosion() {
        return radioExplosion;
    }

    public void setRadioExplosion(int radioExplosion) {
        if (radioExplosion <= 0) {
            this.radioExplosion = 100; // Tamaño mínimo de seguridad
        } else {
            this.radioExplosion = radioExplosion;
        }
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int nuevoDanio) {
        if (nuevoDanio < 0) {
            this.danio = 0; 
        } else {
            this.danio = nuevoDanio;
        }
    }
}
