# longitud_palabra.exs
defmodule LongitudPalabra do
  @moduledoc """
  Módulo que recibe una palabra como argumento y devuelve su longitud (cantidad de letras).
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
