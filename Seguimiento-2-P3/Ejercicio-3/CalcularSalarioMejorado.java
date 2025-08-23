import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * @moduledoc"""
 * ## Módulo CalcularSalarioMejorado
 * 
 * Aplicación Java que integra con Elixir para calcular salarios de empleados
 * considerando horas trabajadas, valor por hora y horas extra con recargo del 125%.
 * 
 * ### Funcionalidades principales:
 * - Interfaz gráfica amigable para entrada de datos con JOptionPane
 * - Validación robusta de entradas del usuario
 * - Cálculo de horas extra (125% después de 160 horas mensuales)
 * - Integración con script de Elixir mediante ProcessBuilder
 * - Presentación detallada de resultados con formato HTML
 * - Manejo completo de errores y excepciones
 * 
 * ### Reglas de negocio:
 * - Máximo 160 horas normales por mes
 * - Horas extra se pagan al 125% del valor base
 * - Todos los campos son obligatorios y deben ser mayores a 0
 * - Integración con script "calcular_salario.exs"
 * 
 * ### Uso desde línea de comandos:
 * bash
 * java CalcularSalarioMejorado
 * # Se abrirán ventanas de diálogo para ingresar:
 * # Nombre: "Juan Pérez"
 * # Horas: 180
 * # Valor por hora: 15000
 * # Resultado: Ventana con desglose detallado del salario
 * 
 * 
 * ### Dependencias externas:
 * - Script "calcular_salario.exs" debe estar en el directorio actual
 * - Elixir debe estar instalado y disponible en el PATH del sistema
 * - Java Swing para la interfaz gráfica
 * 
 * ### Notas importantes:
 * - La comunicación con Elixir espera formato "Nombre:SalarioNeto"
 * - Utiliza ProcessBuilder para ejecutar comandos del sistema
 * - Maneja múltiples tipos de excepciones de forma específica
 * - La aplicación se ejecuta automáticamente al invocar la clase
 * """
 */
public class CalcularSalarioMejorado {
    
    /**
     * @doc"""
     * ## Método principal de la aplicación
     * 
     * Coordina todo el flujo de la calculadora de salarios desde la entrada
     * de datos hasta la presentación de resultados, incluyendo la integración
     * con el script de Elixir para el cálculo.
     * 
     * ### Flujo de ejecución:
     * 1. Solicita datos del empleado mediante ventanas de diálogo
     * 2. Valida que todos los campos sean correctos y numéricos
     * 3. Muestra información preliminar en consola
     * 4. Ejecuta el script de Elixir con los parámetros proporcionados
     * 5. Procesa la respuesta y calcula detalles adicionales
     * 6. Presenta el resultado final en una ventana HTML formateada
     * 
     * ### Parámetros:
     * - args - Argumentos de línea de comandos (no utilizados en esta implementación)
     * 
     * ### Validaciones implementadas:
     * - Campos no pueden estar vacíos, nulos o contener solo espacios
     * - Horas y valor deben ser números enteros válidos
     * - Ambos valores deben ser mayores a 0
     * - Respuesta de Elixir debe seguir formato "Nombre:Salario"
     * 
     * ### Proceso de integración con Elixir:
     * java
     * ProcessBuilder pb = new ProcessBuilder(
     *     "cmd", "/c", "elixir", "calcular_salario.exs", 
     *     nombre, horasTrabajadas, valorHora
     * );
     * 
     * 
     * ### Ejemplos de entrada y salida:
     * 
     * Entrada:
     * - Nombre: "María González"
     * - Horas: 170
     * - Valor por hora: 12000
     * 
     * Salida esperada de Elixir: "María González:2070000"
     * 
     * Resultado mostrado:
     * - Salario base (160h): $1,920,000
     * - Horas extra (10h al 125%): $150,000
     * - SALARIO NETO TOTAL: $2,070,000
     * 
     * 
     * ### Manejo de errores específicos:
     * - NumberFormatException - Entradas no numéricas del usuario
     * - IOException - Errores de comunicación con el proceso de Elixir
     * - InterruptedException - Interrupciones del proceso externo
     * - Validación de formato de respuesta inválida de Elixir
     * - Campos vacíos o nulos en la entrada del usuario
 
     */
    public static void main(String[] args) {
        try {
            // 1. Solicitar datos del empleado
            String nombre = JOptionPane.showInputDialog(
                null, 
                "💼 Ingrese el nombre del empleado:", 
                "Calculadora de Salario", 
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (nombre == null || nombre.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nombre requerido.");
                return;
            }
            
            String horasStr = JOptionPane.showInputDialog(
                null, 
                "⏰ Ingrese las horas trabajadas en el mes:", 
                "Horas Trabajadas", 
                JOptionPane.QUESTION_MESSAGE
            );
            
            String valorStr = JOptionPane.showInputDialog(
                null, 
                "💰 Ingrese el valor por hora:", 
                "Valor por Hora", 
                JOptionPane.QUESTION_MESSAGE
            );

            // Validar entradas
            if (horasStr == null || valorStr == null) {
                JOptionPane.showMessageDialog(null, "Todos los campos son requeridos.");
                return;
            }

            int horasTrabajadas, valorHora;
            try {
                horasTrabajadas = Integer.parseInt(horasStr.trim());
                valorHora = Integer.parseInt(valorStr.trim());
                
                if (horasTrabajadas <= 0 || valorHora <= 0) {
                    JOptionPane.showMessageDialog(null, "Las horas y el valor por hora deben ser mayores a 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese números válidos.");
                return;
            }

            // Mostrar datos ingresados
            System.out.println("=== DATOS DEL EMPLEADO ===");
            System.out.println("Nombre: " + nombre);
            System.out.println("Horas trabajadas: " + horasTrabajadas);
            System.out.println("Valor por hora: $" + valorHora);
            
            // Mostrar si hay horas extra
            if (horasTrabajadas > 160) {
                System.out.println("Horas extra: " + (horasTrabajadas - 160) + " (se pagan al 125%)");
            }

            // 2. Ejecutar el script de Elixir
            ProcessBuilder pb = new ProcessBuilder(
                "cmd", "/c", "elixir", "calcular_salario.exs", 
                nombre, String.valueOf(horasTrabajadas), String.valueOf(valorHora)
            );
            pb.directory(new java.io.File("."));
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            // 3. Leer la respuesta de Elixir
            BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            StringBuilder output = new StringBuilder();
            String linea;

            while ((linea = reader.readLine()) != null) {
                output.append(linea).append("\n");
            }

            int exitCode = proceso.waitFor();
            String respuesta = output.toString().trim();

            // 4. Procesar y mostrar el resultado
            System.out.println("\n=== RESPUESTA DE ELIXIR ===");
            System.out.println("Código de salida: " + exitCode);
            System.out.println("Respuesta completa: " + respuesta);

            if (!respuesta.isEmpty() && exitCode == 0) {
                // La respuesta viene en formato "Nombre:SalarioNeto"
                String[] partes = respuesta.split(":");
                if (partes.length == 2) {
                    String nombreRespuesta = partes[0];
                    String salarioNeto = partes[1];
                    
                    // Convertir a double para formatear
                    double salario = Double.parseDouble(salarioNeto);
                    
                    // Calcular salario base y horas extra para mostrar detalle
                    double salarioBase = Math.min(horasTrabajadas, 160) * valorHora;
                    double pagoHorasExtra = 0;
                    
                    if (horasTrabajadas > 160) {
                        int horasExtra = horasTrabajadas - 160;
                        pagoHorasExtra = horasExtra * valorHora * 1.25;
                    }

                    System.out.println("Salario calculado: $" + salario);

                    // Mostrar resultado detallado
                    String mensaje = String.format(
                        "<html>" +
                        "<div style='padding: 20px;'>" +
                        "<h2 style='color: #27AE60;'>💵 Cálculo de Salario Completado</h2>" +
                        "<hr>" +
                        "<h3 style='color: #2C3E50;'>👤 Empleado: %s</h3>" +
                        "<div style='background: #F8F9FA; padding: 15px; margin: 10px 0;'>" +
                        "<p><b>⏰ Horas trabajadas:</b> %d horas</p>" +
                        "<p><b>💰 Valor por hora:</b> $%,d</p>" +
                        "%s" +
                        "</div>" +
                        "<div style='background: #E8F5E8; padding: 15px; margin: 10px 0;'>" +
                        "<p><b>📊 Salario base (160h máx):</b> $%,.0f</p>" +
                        "%s" +
                        "<hr style='margin: 10px 0;'>" +
                        "<h3 style='color: #E74C3C;'>💸 SALARIO NETO TOTAL: $%,.0f</h3>" +
                        "</div>" +
                        "<p style='color: #7F8C8D; font-size: 11px;'>✨ Calculado con Java + Elixir</p>" +
                        "</div>" +
                        "</html>",
                        nombreRespuesta,
                        horasTrabajadas,
                        valorHora,
                        horasTrabajadas > 160 ? "<p style='color: #E67E22;'><b>⚡ Horas extra:</b> " + (horasTrabajadas - 160) + " horas (125%)</p>" : "",
                        salarioBase,
                        horasTrabajadas > 160 ? String.format("<p><b>⚡ Pago horas extra:</b> $%,.0f</p>", pagoHorasExtra) : "<p style='color: #7F8C8D;'>Sin horas extra</p>",
                        salario
                    );

                    JOptionPane.showMessageDialog(
                        null,
                        mensaje,
                        "🎉 Resultado del Cálculo Salarial",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                } else {
                    JOptionPane.showMessageDialog(null, "Error: Formato de respuesta inválido");
                }
            } else {
                System.out.println("Error: " + respuesta);
                JOptionPane.showMessageDialog(null, "Error: No se pudo calcular el salario\n" + respuesta);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error ejecutando el script de Elixir: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error del sistema: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numérico: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error: Respuesta numérica inválida de Elixir");
        }
    }
}