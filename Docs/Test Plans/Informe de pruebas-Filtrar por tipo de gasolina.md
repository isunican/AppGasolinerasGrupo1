# Informe de pruebas - Filtrar por tipo de gasolina, #399353



## 0. Introducción

​	Tras la realización del plan de pruebas, se procedió a implementar los tests especificados para comprobar el correcto funcionamiento de los diversos componentes implementados.

​	Para la interfaz se implementó tan solo el test correspondiente al caso de prueba UIT.1.a.

​	Para los métodos, se crearon todos los casos de prueba especificados.



## 1. Distribución de trabajo

​	La distribución de trabajo fue la siguiente:

- El plan de pruebas (y por consiguiente el informe de pruebas) fue elaborado por Miguel Carbayo.
- Las pruebas de interfaz fueron implementadas por Miguel Carbayo.
- Las pruebas unitarias fueron implementadas por Jaime López-Agudo.
- La revisión de este documento fue realizada por Miguel Carbayo.



## 2. Fallos detectados

​	Tras la ejecución de las pruebas se detectaron algunos errores en el código. En el caso de filtraGasolinerasTipoCombustible, si se introducía una lista nula la aplicación se detenía ya que no se había implementado el control de la excepción que sacaba el programa.

​	Además de esto,  en la interfaz se detectaron varios fallos. El primero de ellos fue la imposibilidad de accionar el panel lateral usando _Espresso_ (ante la imposibilidad de resolver este fallo se decidió usar una estrategia alternativa). Asimismo, se detectó un fallo al intentar invocar la interfaz desde el menú auxiliar (accesible desde el botón de 3 puntos) en el que el menú se cerraba, pero la nueva interfaz no aparecía.

