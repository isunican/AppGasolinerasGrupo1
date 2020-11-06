<h1 style="text-align:center">Plan de pruebas - Filtrar gasolinera por marca, #399738</h1>

## 0. Introducción 

​	Este documento contiene la descripción de las pruebas a realizar sobre el código implementado para la historia de usuario "***Filtrar gasolinera por marca***" con el objeto de verificar su comportamiento.

Las pruebas ha realizar son:

* Pruebas de aceptación.
* Pruebas de interfaz. 
* Pruebas unitarias.

## 1. Pruebas de aceptación

En esta sección se definen las pruebas de aceptación acordadas con el *Product Owner*.

Prueba 00: Éxito

1.- El usuario abre el panel lateral de la aplicación.

2.- El sistema muestra el panel lateral con las distintas opciones.

3.- El usuario selecciona la opción de filtrar.

4.- El sistema abre una ventana flotante con la información de los filtros .

5.- El usuario introduce el nombre de la marca o una parte de este.

6.- El sistema muestra en una lista las marcas que coincidan con lo introducido.

7.- El usuario selecciona la marca que desea de la lista.

8.- El usuario confirma la selección realizada.

9.- El sistema cierra la ventana de selección de filtro.

10.- El sistema muestra la información de las gasolineras ya filtradas.

11.- Se verifica que el sistema muestra las gasolineras en función del filtro de marca de gasolina aplicado por el usuario.



Prueba 01: No se ha introducido nombre de marca

1.- El usuario abre el panel lateral de la aplicación.

2.- El sistema muestra el panel lateral con las distintas opciones.

3.- El usuario selecciona la opción de filtrar.

4.- El sistema abre una ventana flotante con la información de los filtros.

5.- El usuario no introduce el nombre de la marca.

6.- Se verifica que el sistema muestra un mensaje informativo indicando al usuario que no se pueden aplicar los filtros debido a que no ha introducido ninguna marca.



Prueba 02: Marca inválida

1.- El usuario abre el panel lateral de la aplicación.

2.- El sistema muestra el panel lateral con las distintas opciones.

3.- El usuario selecciona la opción de filtrar.

4.- El sistema abre una ventana flotante con la información de los filtros.

5.- El usuario introduce un nombre de marca erróneo.

6.- Se verifica que el sistema muestra un mensaje informativo indicando al usuario que no se pueden aplicar los filtros debido a que ha introducido un nombre incorrecto.

**1.1 Casos de uso** 

El único caso para esta historia de usuario es "*Buscar gasolinera por marca*"

* Caso de uso UC.1:  Buscar gasolinera por marca

UC.1.a: Éxito

UC.1.b: No se ha introducido nombre de marca

UC.1.c: Marca inválida 

Los casos de prueba definidos para cada uno de los escenarios son los que se muestran en la *tabla 1*:

| Identificador | Entrada       | Resultado            |
| :------------ | ------------- | -------------------- |
| UC.1.a        | "CEPSA"       | Lista de gasolineras |
| UC.1.b        | "Campo vacío" | "Campo vacío"        |
| UC.1.c        | "Cep"         | "Marca inválida"     |

###### Tabla 1: Casos de prueba de aceptación



## 2. Pruebas de interfaz 

Las pruebas de interfaz tendrán como objetivo comprobar su buen funcionamiento. Para realizar estas pruebas se hará uso de la librería de pruebas *"Espresso"*. Los casos de prueba definidos serán los mismos que los de pruebas de aceptación (renombrados como UIT.x.y) 

| Identificador | Entrada       | Valor esperado                   |
| ------------- | ------------- | -------------------------------- |
| UIT.1.a       | "CEPSA"       | Lista de gasolineras             |
| UIT.1.b       | "Campo vacío" | Mensaje de error: Campo vacío    |
| UIT.1.c       | "Cep"         | Mensaje de error: Marca inválida |

###### Tabla 2: Casos de interfaz

## 3. Pruebas unitarias 

Las pruebas unitarias tendrán como objetivo comprobar el funcionamiento de los diferentes módulos que componen las clases creadas. En este caso se implementarán pruebas unitarias para el *Utilities* creado.

**3.1 *Utilitie* de Filtro gasolinera por marca**

Se han diseñado dos casos de prueba. Estos casos de prueba son identificables por UT.x.y 

* Caso de prueba UT.1: extractBrands

| Identificador | Entrada           | Valor esperado   |
| ------------- | ----------------- | ---------------- |
| UT1.a         | Lista gasolineras | Lista con marcas |
| UT1.b         | Lista vacía       | Lista vacía      |
| UT1.c         | Lista nula        | Lista vacía      |

###### Tabla 3: Casos de prueba para el método extractBrands()



* Caso de prueba UT.2: applyFilter

| Identificador | Entrada                     | Valor esperado                |
| ------------- | --------------------------- | ----------------------------- |
| UT2.a         | Lista gasolineras, "CAMPSA" | Lista gasolineras de "CAMPSA" |
| UT2.b         | Lista gasolineras, "REPSOL" | Lista vacía                   |
| UT2.c         | Lista vacía, "AVIA"         | Lista vacía                   |
| UT2.d         | Lista nula, "AVIA"          | Lista vacía                   |

###### Tabla 4: Casos de prueba para el método applyFilter()



