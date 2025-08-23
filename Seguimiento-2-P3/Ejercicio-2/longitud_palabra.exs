# longitud_palabra.exs
defmodule LongitudPalabra do
  @moduledoc """
  ## Módulo LongitudPalabra

  Módulo que procesa argumentos de línea de comandos para calcular y mostrar
  la longitud de una palabra proporcionada como parámetro.

  ### Funcionalidades principales:
  - Recibe argumentos de línea de comandos
  - Calcula la longitud de la primera palabra proporcionada
  - Muestra el resultado en la consola
  - Maneja errores cuando no se proporcionan argumentos

  ### Notas importantes:
  - Solo procesa la primera palabra si se proporcionan múltiples argumentos
  - Cuenta todos los caracteres de la cadena, incluyendo espacios si los hay
  - El módulo se ejecuta automáticamente al cargar el archivo
  """

  @doc """
  ## Función principal del programa

  Procesa los argumentos de línea de comandos y calcula la longitud de la primera palabra.
  Esta función maneja tanto el caso exitoso como el error cuando no se proporcionan argumentos.

  ### Parámetros:
  - args - Lista de argumentos de línea de comandos (generalmente de System.argv())

  ### Comportamiento:
  - Si se proporciona al menos una palabra, calcula y muestra su longitud
  - Si no se proporcionan argumentos, muestra un mensaje de error
  - Solo considera el primer argumento, ignora los demás


  """
  def main(args) do
    case args do
      [palabra | _] ->
        # Contar la cantidad de letras de la palabra
        longitud = String.length(palabra)
        IO.puts(longitud)

      [] ->
        IO.puts("ERROR: No se proporcionó ninguna palabra")
    end
  end
end

# Ejecutar la función main con los argumentos de línea de comandos
LongitudPalabra.main(System.argv())
