package modelo;


public class Jugador extends Entidad{
    private String nombre;
    private int vida;
    private double anguloApuntado;
    
    private int cooldownDisparo;
    private int tiempoEscudo;
    private boolean escudoActivo;
    private int cooldownBomba;

    public Jugador(String nombre, double x, double y) {
        super(x, y, 20, 0, true);
        this.nombre = nombre;
        this.vida = 100;
        this.anguloApuntado = 0.0;
        
        this.cooldownDisparo = 0;
        this.tiempoEscudo = 0;
        this.escudoActivo = false;
        this.cooldownBomba = 0;
    }

    @Override
    public void actualizar() {
        //Es la recarga del arma
        if(cooldownDisparo > 0){
            cooldownDisparo--;
        }
        
        //Es el desgaste del escudo temporal
        if(tiempoEscudo > 0){
            tiempoEscudo--;
            if(tiempoEscudo == 0){
                escudoActivo = false;
            }
        }
        
        //Recarga de la bomba de area
        if(cooldownBomba > 0){
            cooldownBomba--;
        }
    }
    
    public Proyectil disparar(){
        //solo dispara cuando el canion se "enfrio"
        if(cooldownDisparo == 0){
            cooldownDisparo = 15; //Bloquea el cañón por 15 frames
            return new Proyectil(this.x, this.y, this.anguloApuntado);
        }
        return null;//si se esta cargando, no dispara
    }
    
    public void actualizarAngulo(int mouseX, int mouseY){
        // Fórmula trigonométrica para calcular el ángulo de rotación hacia el ratón
        double deltaX = mouseX - this.x;
        double deltaY = mouseY - this.y;
        //
        this.anguloApuntado = Math.atan2(deltaY, deltaX);
    }
    
    public void activarEscudo() {
        // Si el escudo no está activo, lo activa por 3 segundos
        if (!escudoActivo) {
            this.escudoActivo = true;
            this.tiempoEscudo = 180; 
        }
    }
    
    public void recibirDanio(int cantidad){
        if(escudoActivo){
            return;
        }
        this.vida -= cantidad;
        if(this.vida < 0){
            this.vida = 0;//Es para que la vida no baje de 0
        }
    }
    
    // --- GETTERS Y SETTERS ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int nuevaVida) {
        if (nuevaVida <= 0) {
            this.vida = 0; 
        } else if (nuevaVida > 100) {
            this.vida = 100;
        } else {
            this.vida = nuevaVida;
        }
    }

    public double getAnguloApuntado() {
        return anguloApuntado;
    }

    public void setAnguloApuntado(double anguloApuntado) {
        this.anguloApuntado = anguloApuntado;
    }

    public boolean isEscudoActivo() {
        return escudoActivo;
    }

    public void setEscudoActivo(boolean escudoActivo) {
        this.escudoActivo = escudoActivo;
    }
    
    
}
