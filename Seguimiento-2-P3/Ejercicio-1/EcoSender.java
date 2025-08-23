/**
 * @moduledoc """
 * ## Módulo: EcoSender
 *
 * Este módulo permite enviar un mensaje desde Java a un script de Elixir (eco_mensaje.exs)
 * y recibir la respuesta procesada de vuelta. 
 *
 * ### Funcionalidades principales:
 * - Define un mensaje original en Java.
 * - Ejecuta un script de Elixir, pasándole el mensaje como argumento.
 * - Captura la respuesta de Elixir desde la salida estándar.
 * - Muestra los resultados tanto en consola como en un cuadro de diálogo gráfico.
 *
 * ### Entradas:
 * - Un mensaje de texto definido en Java (ejemplo: "Hola mundo").
 *
 * ### Salidas:
 * - La respuesta generada por el script de Elixir.
 *
 * ### Excepciones:
 * - IOException: si ocurre un error al ejecutar el script de Elixir.
 * - InterruptedException: si el proceso externo es interrumpido durante su ejecución.
 * """
 */

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class EcoSender {

    /**
     * @doc """
     * ## Función: main/1
     *
     * Función principal que gestiona la comunicación entre Java y Elixir.
     *
     * ### Pasos que realiza:
     * 1. Define un mensaje en Java (mensajeOriginal).
     * 2. Ejecuta el script de Elixir (eco_mensaje.exs), enviando ese mensaje como argumento.
     * 3. Lee la respuesta que Elixir devuelve por consola.
     * 4. Muestra el resultado en consola y en un cuadro de diálogo gráfico.
     *
     * ### Parámetros:
     * - args: Argumentos recibidos desde la línea de comandos (no se usan en este caso).
     *
     * ### Retorno:
     * - Ninguno (imprime en consola y muestra resultados en ventana emergente).
     * """
     */
    public static void main(String[] args) {
        try {
            // 1. Mensaje que Java enviará a Elixir
            String mensajeOriginal = "Hola mundo";

            // Mostrar el mensaje original
            System.out.println("Mensaje original desde Java: " + mensajeOriginal);

            // 2. Ejecutar el script de Elixir pasándole el mensaje
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "elixir", "eco_mensaje.exs", mensajeOriginal);
            pb.directory(new java.io.File("."));  // Establecer directorio de trabajo
            pb.redirectErrorStream(true); // Redirigir errores al stream principal
            Process proceso = pb.start();

            // 3. Leer la respuesta de Elixir
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            StringBuilder output = new StringBuilder();
            String linea;

            // Leer todas las líneas de salida
            while ((linea = reader.readLine()) != null) {
                output.append(linea);
            }

            // Esperar a que termine el proceso
            int exitCode = proceso.waitFor();
            String mensajeRespuesta = output.toString().trim();

            // 4. Mostrar en pantalla lo que Elixir devolvió
            System.out.println("Código de salida: " + exitCode);
            if (!mensajeRespuesta.isEmpty()) {
                System.out.println("Respuesta de Elixir: " + mensajeRespuesta);

                // Mostrar en diálogo gráfico
                JOptionPane.showMessageDialog(
                    null,
                    "Mensaje original: " + mensajeOriginal + "\n" +
                    "Respuesta de Elixir: " + mensajeRespuesta,
                    "Comunicacion Java-Elixir",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                System.out.println("Error: No se recibió respuesta de Elixir o respuesta vacía");
                System.out.println("Código de salida: " + exitCode);
                JOptionPane.showMessageDialog(null, "Error: No se recibió respuesta de Elixir");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando el script de Elixir: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}