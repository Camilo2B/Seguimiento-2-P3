# calcular_salario.exs

defmodule CalcularSalario do
  @moduledoc """
  Módulo que recibe el salario base y el porcentaje de aumento como argumentos,
  y devuelve el salario final después de aplicar el aumento.
  """

  def main(args) do
    case args do
      [salario_base_str, porcentaje_aumento_str | _] ->
        with {salario_base, ""} <- Float.parse(salario_base_str),
             {porcentaje_aumento, ""} <- Float.parse(porcentaje_aumento_str) do
          # Calcular el aumento
          aumento = salario_base * (porcentaje_aumento / 100)
          # Calcular el salario final
          salario_final = salario_base + aumento
          IO.puts(salario_final)
        else
          :error ->
            IO.puts("ERROR: Los argumentos deben ser números válidos")
        end

      _ ->
        IO.puts("ERROR: Se requieren dos argumentos: salario_base y porcentaje_aumento")
    end
  end
end
