# calcular_salario.exs

defmodule CalcularSalario do
  @moduledoc """
  Módulo que recibe nombre, horas trabajadas y valor por hora,
  y calcula el salario neto considerando horas extra al 125%.
  """

  def main(args) do
    case args do
      [nombre, horas_str, valor_hora_str | _] ->
        with {horas_trabajadas, ""} <- Integer.parse(horas_str),
             {valor_por_hora, ""} <- Integer.parse(valor_hora_str) do

          # Calcular salario neto
          salario_neto = calcular_salario_neto(horas_trabajadas, valor_por_hora)

          # Mostrar el resultado
          IO.puts("#{nombre}:#{salario_neto}")
        else
          :error ->
            IO.puts("ERROR: Las horas y el valor por hora deben ser números válidos")
        end

      _ ->
        IO.puts("ERROR: Se requieren tres argumentos: nombre, horas_trabajadas, valor_por_hora")
    end
  end

  @doc """
  Calcula el salario neto considerando horas extra al 125%
  """
  def calcular_salario_neto(horas_trabajadas, valor_por_hora) when horas_trabajadas <= 160 do
    # Si trabaja 160 horas o menos, no hay horas extra
    horas_trabajadas * valor_por_hora
  end

  def calcular_salario_neto(horas_trabajadas, valor_por_hora) when horas_trabajadas > 160 do
    # Si trabaja más de 160 horas, hay horas extra al 125%
    horas_normales = 160
    horas_extra = horas_trabajadas - 160

    # Salario por horas normales
    salario_normal = horas_normales * valor_por_hora

    # Pago por horas extra (125% = valor_por_hora * 1.25)
    pago_horas_extra = horas_extra * valor_por_hora * 1.25

    # Salario neto total
    salario_normal + pago_horas_extra
  end
end

# Ejecutar la función main con los argumentos de línea de comandos
CalcularSalario.main(System.argv())
