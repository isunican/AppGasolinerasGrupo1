# Informe de pruebas-Filtrar gasolinera por marca, #399738

## 0. Introducción

En esta sección se describe el resultado de la ejecución de las pruebas para la historia de usuario "**Filtrar gasolinera por marca**", indicando los responsables (autores y/o ejecutores) de cada artefacto y el número de fallos encontrados por cada prueba.

## 1. Distribución de trabajo

La distribución del trabajo se realizó de la siguiente forma:

* El plan de pruebas e informe de pruebas fueron elaborados por Carolay Corales.
* Las pruebas de interfaz fueron implementadas por Carolay Corales.
* La pruebas unitarias fueron implementadas por Luis Cruz.
* La revisión de este documento fue realizada por Carolay Corales y Elena Romón.



## 2. Fallos detectados

Los fallos encontrados fueron los siguientes:

* En las pruebas unitarias se detectó un error en el caso de prueba UT.2.a. Al pasarle una lista de gasolineras y un filtro, devolvía la lista sin filtrar. Además, se detectó otro error en los casos de prueba UT.1.c y UT.2.d, al invocar el método con la lista nula produce un *NullPointerException*. Una vez solucionados los fallos, se exhibió el comportamiento esperado.
* En las pruebas de interfaz se detectaron varios fallos. En primer lugar, no se pudo activar la ventana lateral usando *Espresso*. Se utilizó de forma auxiliar un botón para activar el *alertDialog*. 