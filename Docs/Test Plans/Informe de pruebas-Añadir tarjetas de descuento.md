# Informe de pruebas - Añadir tarjetas de descuento, #399359



## 0. Introducción

​	Tras la realización del plan de pruebas, se procedió a implementar los tests especificados para comprobar el correcto funcionamiento de los diversos componentes implementados.

​	Para la interfaz se implementó tan solo el test correspondiente al caso de prueba UIT.1.a.

​	Para el _presenter_ implementado, se crearon todos los casos de prueba especificados.



## 1. Distribución de trabajo

​	La distribución de trabajo fue la siguiente:

- El plan de pruebas (y por consiguiente el informe de pruebas) fue elaborado por Luis Cruz.
- Las pruebas de interfaz fueron implementadas por Elena Romón.
- Las pruebas unitarias fueron implementadas por Adrián Celis y Luis Cruz.
- La revisión de este documento fue realizada por Adrián Celis y Elena Romón.



## 2. Fallos detectados

​	Tras la ejecución de las pruebas se detectaron algunos errores en el código. En el apartado de pruebas unitarias, se detectó un error en el caso de prueba UT.2.f, donde el código del _presenter_ causaba un _NullPointerException_ al recibir un elemento `null` en vez de una lista vacía. Una vez solucionado esto, el _presenter_ exhibió el comportamiento esperado.



​	Además de esto,  en la interfaz se detectaron varios fallos. El primero de ellos fue la imposibilidad de accionar el panel lateral usando _Espresso_ (ante la imposibilidad de resolver este fallo se decidió usar una estrategia alternativa). Asimismo, se detectó un fallo al intentar invocar la interfaz desde el menú auxiliar (accesible desde el botón de 3 puntos) en el que el menú se cerraba, pero la nueva interfaz no aparecía.