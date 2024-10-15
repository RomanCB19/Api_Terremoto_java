package interfaz_terremoto;
import javax.swing.SwingUtilities;

public class main {
     public static void main(String[] args) {
        // Utiliza SwingUtilities para asegurarse de que la creación de la interfaz gráfica ocurra en el hilo de eventos de Swing.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Crea una instancia de la interfaz gráfica de usuario y la muestra.
                vista_T interfaz = new vista_T();
                interfaz.mostrarVentana();  // Muestra la ventana principal de la aplicación.
            }
        });
    }
}
