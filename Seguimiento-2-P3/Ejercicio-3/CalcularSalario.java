import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class CalcularSalario {
    public static void main(String[] args) {
        try {
            // 1. Solicitar al empleado datos necesarios
            String nombre = JOptionPane.showInputDialog(null, "Ingrese su nombre: ", "Solicitud", JOptionPane.QUESTION_MESSAGE);
            int horasTrabajadas = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese las horas trabajadas en el mes: ", "Solicitud", JOptionPane.QUESTION_MESSAGE));
            int valorHora = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el valor por hora: ", "Solicitud", JOptionPane.QUESTION_MESSAGE));

            // Verificar que el usuario no canceló o ingresó texto vacio
            if (nombre == null || nombre.trim().isEmpty() || horasTrabajadas <= 0 || valorHora <= 0) {

                JOptionPane.showMessageDialog(null, "Datos inválidos ingresados.");
                return;
            }

            //Mostrar contenido ingresado
            System.out.println("Nombre: " + nombre + "\n, Horas trabajadas: " + horasTrabajadas + "\n, Valor por hora: " + valorHora);

            // 2. Ejecutar el script de elixir pasándole los datos
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "elixir", "calcular_salario.exs", nombre, String.valueOf(horasTrabajadas), String.valueOf(valorHora));
            pb.directory(new java.io.File("."));
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            // 3. Leer la respuesta de Elixir (salario mensual)
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            StringBuilder output = new StringBuilder();
            String linea;

            while ((linea = reader.readLine()) != null) {
                output.append(linea);
            }

            // Esperar a que termine el proceso

            int exitCode = proceso.waitFor();
            String salarioRespuesta = output.toString().trim();

            // 4. Mostrar el resultado
            System.out.println("Código de salida: " + exitCode);
            if (!salarioRespuesta.isEmpty() && exitCode == 0) {
                System.out.println("Salario neto: " + salarioRespuesta);

                // Mostrar en ventana de diálogo
                JOptionPane.showMessageDialog(
                    null,
                    "Empleado: " + nombre + "\n" +
                    "Horas trabajadas: " + horasTrabajadas + "\n" +
                    "Valor por hora: " + valorHora + "\n" +
                    "Salario neto: " + salarioRespuesta,
                    "Resultado - Cálculo de salario",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                System.out.println("Error: No se pudo calcular el salario");
                JOptionPane.showMessageDialog(null, "Error: No se pudo procesar el salario");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando el script de Elixir: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }

    }
}