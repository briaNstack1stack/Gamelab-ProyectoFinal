package modelo;

public abstract class Entidad {
    protected double x;
    protected double y;
    protected int radio;
    protected double velocidad;
    protected boolean activo;

    public Entidad(double x, double y, int radio, double velocidad, boolean activo) {
        this.x = x;
        this.y = y;
        this.radio = radio;
        this.velocidad = velocidad;
        this.activo = true;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        /*-Double.isNaN(x) Significa "not a number" se genera cuando aparace una
        operacion matematicamente infita como (0.0 / 0.0)
        -Double.isInfinite(x) Verifica si el numero crecio hasta volverse infinito,
        basicamente es cuando se divide un numero por 0 */
        if(Double.isNaN(x) || Double.isInfinite(x)){
            this.x = 0;
        }else{
            this.x = x;
        }
        
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        if (Double.isNaN(y) || Double.isInfinite(y)) {
            this.y = 0; 
        } else {
            this.y = y;
        }
    }

    public int getRadio() {
        return radio;
    }

    public void setRadio(int radio) {
        if(radio <= 0){
            this.radio = 1;
        } else{
            this.radio = radio;
        }
        
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        if(velocidad < 0){
            this.velocidad = 0;
        } else{
            this.velocidad = velocidad;
        }
        
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public abstract void actualizar();
    
    public boolean colisionesCon(Entidad otra){
        return false;
    }
}
