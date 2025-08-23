import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class EcoSender {
    public static void main(String[] args) {
        try {
            // 1. Mensaje que Java enviará a Elixir
            String mensajeOriginal = "Hola mundo";
            
            // Mostrar el mensaje original
            System.out.println("Mensaje original desde Java: " + mensajeOriginal);
            
            // 2. Ejecutar el script de Elixir pasándole el mensaje
            // Diferentes formas de intentar ejecutar Elixir
            ProcessBuilder pb = null;
            
            // Intentar con cmd para usar el PATH de Windows
            pb = new ProcessBuilder("cmd", "/c", "elixir", "eco_mensaje.exs", mensajeOriginal);
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