@Sumar
Feature: Sumar numeros

@High 
  Scenario Outline: Sumar dos numeros  del 0 al 9 en la calculadora
   Given Dos numeros "<numero1>" mas "<numero2>"
   When Presionas igual
   Then Se muestra "<resultado>" 
   
   Examples:
   | numero1 | numero2 | resultado |
   | 8 			 | 5       | 13        |
   | 1  		 | 6       | 7         |
   