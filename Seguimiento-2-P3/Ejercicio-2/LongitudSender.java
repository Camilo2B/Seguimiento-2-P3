import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class LongitudSender {
    public static void main(String[] args) {
        try {
            // 1. Solicitar al usuario que digite una palabra
            String palabraIngresada = JOptionPane.showInputDialog(
                null,
                "Digite una palabra:",
                "Contador de letras",
                JOptionPane.QUESTION_MESSAGE
            );
            
            // Verificar que el usuario no canceló o ingresó texto vacío
            if (palabraIngresada == null || palabraIngresada.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se ingresó ninguna palabra.");
                return;
            }
            
            // Mostrar la palabra ingresada
            System.out.println("Palabra ingresada: " + palabraIngresada);
            
            // 2. Ejecutar el script de Elixir pasándole la palabra
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "elixir", "longitud_palabra.exs", palabraIngresada);
            pb.directory(new java.io.File("."));
            pb.redirectErrorStream(true);
            Process proceso = pb.start();
            
            // 3. Leer la respuesta de Elixir (cantidad de letras)
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            StringBuilder output = new StringBuilder();
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                output.append(linea);
            }
            
            // Esperar a que termine el proceso
            int exitCode = proceso.waitFor();
            String longitudRespuesta = output.toString().trim();
            
            // 4. Mostrar el resultado
            System.out.println("Código de salida: " + exitCode);
            if (!longitudRespuesta.isEmpty() && exitCode == 0) {
                System.out.println("Cantidad de letras: " + longitudRespuesta);
                
                // Mostrar en ventana de diálogo
                JOptionPane.showMessageDialog(
                    null,
                    "Palabra: " + palabraIngresada + "\n" +
                    "Cantidad de letras: " + longitudRespuesta,
                    "Resultado - Longitud de palabra",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                System.out.println("Error: No se pudo calcular la longitud de la palabra");
                JOptionPane.showMessageDialog(null, "Error: No se pudo procesar la palabra");
            }
            
        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando el script de Elixir: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}