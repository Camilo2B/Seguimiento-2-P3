defmodule Secuencia do #Modculo -> UpperCamelCase
  def mostrar_mensaje() do
    "Mensaje desde una funcion"
    |> IO.puts()
  end

  def mostrar_mensaje_unalinea(), do: IO.puts("Hola a todos") #Funcion


#Función provada -> Snake_case
  defp mostrar_mensaje_privado(mensaje) do
    mensaje
    |> IO.puts()
  end

  def invocacion_privada() do
    "Mensaje privado desde una función"
    |> mostrar_mensaje_privado()
  end
end

Secuencia.invocacion_privada()
#Secuencia.mostrar_mensaje_unalinea()
#Secuencia.mostrar_mensaje_privado("Mensaje privado desde una función")
