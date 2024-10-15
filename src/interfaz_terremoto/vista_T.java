package interfaz_terremoto;

import java.awt.event.*;
import javax.swing.*;

public class vista_T {
    // Componentes de la interfaz
    private JFrame ventana;  // JFrame para la ventana principal
    private JComboBox<String> comboPeriodoTiempo;  // Selector de período de tiempo
    private JTextArea areaRespuesta;  // Área para mostrar la respuesta de la API
    private JButton botonObtenerDatos;  // Botón para obtener los datos de la API
    private conexion conexionT;  // Instancia de la clase de conexión a la API

    /**
     * Constructor de la interfaz gráfica. Configura el marco (JFrame) y los componentes visuales.
     */
    public vista_T() {
        // Configura el JFrame
        ventana = new JFrame("TERREMOTOS");
        ventana.setSize(600, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        conexionT = new conexion();  // Instancia para manejar la conexión a la API

        // Configura el selector de período de tiempo
        String[] periodo_Tiempo = { "Última Hora", "Último Día", "Última Semana" };
        comboPeriodoTiempo = new JComboBox<>(periodo_Tiempo);

        // Configura el área de texto para mostrar los datos de respuesta
        areaRespuesta = new JTextArea();
        areaRespuesta.setLineWrap(true);  // Habilita el ajuste de líneas
        areaRespuesta.setWrapStyleWord(true);  // Ajuste de palabras completas
        areaRespuesta.setEditable(false);  // No permite la edición

        // Configura el botón y su acción
        botonObtenerDatos = new JButton("Obtener datos de Terremotos");
        botonObtenerDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerDatosTerremotos();  // Llama a la función para obtener los datos
            }
        });

        // Configura el panel superior con el selector y el botón
        JPanel panel = new JPanel();
        panel.add(new JLabel("Período de tiempo:"));
        panel.add(comboPeriodoTiempo);
        panel.add(botonObtenerDatos);

        // Configura un panel con barra de desplazamiento para el área de respuesta
        JScrollPane panelDesplazable = new JScrollPane(areaRespuesta);
        panelDesplazable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Añade los componentes al JFrame
        ventana.add(panel, "North");
        ventana.add(panelDesplazable, "Center");
    }

    /**
     * Muestra la ventana configurada (JFrame).
     */
    public void mostrarVentana() {
        ventana.setVisible(true);  // Hace visible la ventana
    }

    /**
     * Obtiene los datos de terremotos llamando a la clase `ConexionTerremotos`
     * y los muestra en el área de texto.
     */
    private void obtenerDatosTerremotos() {
        // Obtiene el período seleccionado del JComboBox
        String periodoSeleccionado = (String) comboPeriodoTiempo.getSelectedItem();
        try {
            // Llama a la conexión para obtener los datos y los procesa
            String datos = conexionT.obtenerDatos(periodoSeleccionado);
            conexionT.analizarYMostrarDatosTerremotos(datos, areaRespuesta);
        } catch (Exception e) {
            // Muestra un mensaje de error si ocurre alguna excepción
            areaRespuesta.setText("Error: " + e.getMessage());
        }
    }
}
