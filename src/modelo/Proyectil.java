package modelo;


public class Proyectil extends Entidad {
    private double anguloViaje;
    private int danio;

    public Proyectil(double x, double y, double anguloViaje) {
        // Llama al constructor de Entidad: (x, y, radio, velocidad, activo)
        // Radio pequeño (ej. 5) y velocidad rápida constante (ej. 15.0)
        super(x, y, 5, 15.0, true); 
        this.anguloViaje = anguloViaje;
        this.danio = 20; // El daño base por defecto de cada proyectil
    }

    @Override
    public void actualizar() {
        // Trigonometría para avanzar en la dirección del ángulo apuntado
        // Math.cos calcula el avance horizontal y Math.sin el vertical
        this.x += this.velocidad * Math.cos(this.anguloViaje);
        this.y += this.velocidad * Math.sin(this.anguloViaje);
    }

    // --- GETTERS Y SETTERS ---

    public double getAnguloViaje() {
        return anguloViaje;
    }

    public void setAnguloViaje(double anguloViaje) {
        this.anguloViaje = anguloViaje;
    }

    public int getDanio() {
        return danio;
    }

    public void setDanio(int nuevoDanio) {
        if (nuevoDanio < 0) {
            this.danio = 0; // Evita que una bala cure al enemigo por tener daño negativo
        } else {
            this.danio = nuevoDanio;
        }
    }
}
