package modelo;


public class EnemigoVeloz extends Enemigo{

    public EnemigoVeloz(double x, double y) {
        // Llama al constructor de Enemigo: 
        // (x, y, radio=15, velocidad=5.0, vida=30, daño=10, puntos=50)
        // Es un enemigo pequeño y con poca vida, pero avanza rápido (5.0)
        super(x, y, 15, 5.0, 30, 10, 50);
    }
    
}
