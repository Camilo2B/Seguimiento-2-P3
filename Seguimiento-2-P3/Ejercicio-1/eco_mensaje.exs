# eco_mensaje.exs
defmodule EcoMensaje do
  @moduledoc """
  ## Módulo EcoMensaje

  Módulo que procesa argumentos de línea de comandos para recibir un mensaje
  y devolverlo convertido completamente a mayúsculas.

  ### Funcionalidades principales:
  - Recibe argumentos de línea de comandos
  - Convierte la primera cadena proporcionada a mayúsculas
  - Muestra el resultado transformado en la consola
  - Maneja errores cuando no se proporcionan argumentos

  ### Notas importantes:
  - Solo procesa el primer argumento si se proporcionan múltiples
  - Preserva caracteres especiales y espacios en la conversión
  - Funciona correctamente con caracteres Unicode (acentos, ñ, etc.)
  - El módulo se ejecuta automáticamente al cargar el archivo
  """

  @doc """
  ## Función principal del programa

  Procesa los argumentos de línea de comandos, toma el primer mensaje y lo convierte
  a mayúsculas usando la función String.upcase/1. Maneja tanto el caso exitoso
  como el error cuando no se proporcionan argumentos.

  ### Parámetros:
  - args - Lista de argumentos de línea de comandos (generalmente de System.argv())

  ### Comportamiento:
  - Si se proporciona al menos un mensaje, lo convierte a mayúsculas y lo muestra
  - Si no se proporcionan argumentos, muestra un mensaje de error descriptivo
  - Solo considera el primer argumento, ignora argumentos adicionales
  - Preserva la estructura original del texto (espacios, puntuación, etc.)

  ### Ejemplos:
  ```elixir
  iex> EcoMensaje.main(["hello world"])
  # Imprime: HELLO WORLD
  :ok

  iex> EcoMensaje.main(["Hola", "Mundo"])
  # Imprime: HOLA (solo procesa el primer argumento)
  :ok

  iex> EcoMensaje.main([])
  # Imprime: ERROR: No se proporcionó ningún mensaje
  :ok


  """
  def main(args) do
    case args do
      [mensaje | _] ->
        # Convertir el mensaje a mayúsculas y devolverlo
        mensaje_mayusculas = String.upcase(mensaje)
        IO.puts(mensaje_mayusculas)

      [] ->
        IO.puts("ERROR: No se proporcionó ningún mensaje")
    end
  end
end

# Ejecutar la función main con los argumentos de línea de comandos
EcoMensaje.main(System.argv())
