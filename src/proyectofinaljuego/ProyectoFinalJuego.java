
package proyectofinaljuego;

import vista.PantallaJuego;
import javax.swing.SwingUtilities;/*Garantiza que la pantalla se cree
dentro de un "hilo seguro" para interfaces graficas*/
public class ProyectoFinalJuego {
    /*Version jugable
    falta cambiar la velocidad de los Enemigos(los veloces son muy veloces) 
    y que los enemigos se dirijan
    hacia el Jugador e implementar el escudo al rededor del Jugador*/
    
    public static void main(String[] args) {
        //Ejecuta la ventana dentro del hilo de eventos de swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Arranca el menú de inicio
                vista.PantallaInicio menu = new vista.PantallaInicio();
                menu.setVisible(true); 
            }
        });
    }
    
}
