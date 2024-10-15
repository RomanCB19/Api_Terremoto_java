package interfaz_terremoto;

import org.json.*;
import java.io.*;
import java.net.*;

public class conexion {
    /**
     * Obtiene los datos de terremotos en formato JSON desde la API
     * del USGS según el período de tiempo seleccionado.
     *
     * @param tiempoSeleccionado El período de tiempo (Última Hora, Último Día, Última Semana).
     * @return Una cadena JSON con los datos de terremotos.
     * @throws Exception Si ocurre un error al realizar la conexión o procesar los datos.
     */
    public String obtenerDatos(String tiempoSeleccionado) throws Exception {
        // Variable para almacenar la URL de la API según el período seleccionado
        String urlString = "";

        // Selecciona la URL adecuada según el período de tiempo
        switch (tiempoSeleccionado) {
            case "Última Hora":
                urlString = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
                break;
            case "Último Día":
                urlString = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_day.geojson";
                break;
            case "Última Semana":
                urlString = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson";
                break;
            default:
                throw new IllegalArgumentException("Período de tiempo no válido: " + tiempoSeleccionado);
        }

        // Asegúrate de que la URL no esté vacía antes de abrir la conexión
        if (urlString.isEmpty()) {
            throw new IllegalArgumentException("La URL está vacía. Verifica el período de tiempo seleccionado.");
        }

        // Crea la conexión HTTP y obtiene los datos de la API
        URL url = new URL(urlString);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");

        // Lee la respuesta de la API
        BufferedReader entrada = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String lineaEntrada;
        StringBuilder contenido = new StringBuilder();

        // Construye una cadena con el contenido de la respuesta
        while ((lineaEntrada = entrada.readLine()) != null) {
            contenido.append(lineaEntrada);
        }
        entrada.close();  // Cierra el lector

        // Devuelve los datos JSON como una cadena
        return contenido.toString();
    }

    /**
     * Analiza los datos JSON obtenidos de la API y muestra la información relevante
     * en el área de texto proporcionada.
     *
     * @param datosJson      Los datos en formato JSON que contienen información sobre terremotos.
     * @param areaRespuesta  El área de texto donde se mostrarán los resultados.
     */
    public void analizarYMostrarDatosTerremotos(String datosJson, javax.swing.JTextArea areaRespuesta) {
        // Crea un objeto JSON a partir de la cadena de datos
        JSONObject jsonObject = new JSONObject(datosJson);
        JSONArray features = jsonObject.getJSONArray("features");  // Obtiene el arreglo de características

        // Limpia el área de texto antes de mostrar nueva información
        areaRespuesta.setText("");

        // Itera sobre los resultados (características) de los terremotos
        for (int i = 0; i < features.length(); i++) {
            // Obtiene los detalles de cada terremoto
            JSONObject feature = features.getJSONObject(i);
            JSONObject properties = feature.getJSONObject("properties");
            double magnitud = properties.getDouble("mag");  // Obtiene la magnitud del terremoto
            String lugar = properties.getString("place");   // Obtiene la ubicación del terremoto
            long tiempo = properties.getLong("time");       // Obtiene el tiempo del evento

            // Muestra la información en el área de texto
            areaRespuesta.append("Magnitud: " + magnitud + "\n");
            areaRespuesta.append("Ubicación: " + lugar + "\n");
            areaRespuesta.append("Hora: " + new java.util.Date(tiempo) + "\n\n");
        }
    }
}
