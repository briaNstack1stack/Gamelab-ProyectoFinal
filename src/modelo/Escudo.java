package modelo;


public class Escudo extends Entidad {
    private int duracion; // Tiempo de vida del escudo en frames

    public Escudo(double x, double y) {
        // Llama al constructor de Entidad: (x, y, radio, velocidad, activo)
        // Velocidad 0 porque se quedará fijo sobre el tanque
        super(x, y, 30, 0, true);
        this.duracion = 180; 
    }

    @Override
    public void actualizar() {
        // Descontamos el tiempo de vida en cada frame
        if (this.duracion > 0) {
            this.duracion--;
        }
        
        // Si el tiempo se acaba, el escudo se apaga
        if (this.duracion <= 0) {
            this.activo = false;
        }
    }

    // Método solicitado en tu diagrama UML para cuando el escudo recibe un impacto
    public void desgastar(int cantidad) {
        if (this.activo) {
            // El daño de los enemigos le resta tiempo de vida al escudo
            this.duracion -= cantidad;
            
            // Validamos que la duración no quede en negativo
            if (this.duracion <= 0) {
                this.duracion = 0;
                this.activo = false; // El escudo se rompe por el daño
            }
        }
    }

    // --- GETTERS Y SETTERS ---

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int nuevaDuracion) {
        if (nuevaDuracion < 0) {
            this.duracion = 0;
            this.activo = false;
        } else {
            this.duracion = nuevaDuracion;
        }
    }
}
