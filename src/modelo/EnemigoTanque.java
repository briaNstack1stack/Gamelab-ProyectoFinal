package modelo;


public class EnemigoTanque extends Enemigo {
    
    public EnemigoTanque(double x, double y) {
        // Llama al constructor de Enemigo: 
        // (x, y, radio=35, velocidad=1.0, vida=150, daño=40, puntos=150)
        // Es un enemigo muy grande (radio 35) y muy resistente, pero muy lento (1.0)
        super(x, y, 35, 1.0, 150, 40, 150);
    }
}
