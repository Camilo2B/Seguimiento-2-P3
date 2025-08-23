# eco_mensaje.exs
defmodule EcoMensaje do
  @moduledoc """
  Módulo que recibe un mensaje como argumento y lo devuelve en mayúsculas.
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
