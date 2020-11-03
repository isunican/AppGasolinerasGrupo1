<h1 style="text-align:center">Plan de pruebas - Añadir tarjetas de descuento, #399738</h1>



## 0. Introducción 

Lo niveles de prueba que se van a aplicar son los siguientes:

* Pruebas de aceptación.
* Pruebas de interfaz. 
* Pruebas de integración.
* Pruebas unitarias.



## 1. Pruebas de aceptación

Pruebas de aceptación acordadas con el *Product Owner*.

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

* Caso de uso UC 1:  Buscar gasolinera por marca

UC.1.a: Éxito

UC.1.b: No se ha introducido nombre de marca

UC.1.c: Marca inválida 

Los casos de prueba definidos para cada uno de los escenarios son los que se muestran en la tabla 1:



| Identificador | Entrada       | Resultado            |
| :------------ | ------------- | -------------------- |
| UC.1.a        | "CEPSA"       | Lista de gasolineras |
| UC.1.b        | "Campo vacío" | "Campo vacío"        |
| UC.1.c        | "Cep"         | "Marca inválida"     |



## 2. Pruebas de interfaz 

Las pruebas de interfaz tendrán como objetivo comprobar el buen funcionamiento de la interfaz. Para realizar estas pruebas se hará uso de la librería de pruebas *"Espresso"*. Los casos de prueba definidos serán los mismos que los de las pruebas de aceptación (renombrados como IVF.X) 

| Identificador | Entrada       | Resultados           |
| ------------- | ------------- | -------------------- |
| IVF.1.a       | "CEPSA"       | Lista de gasolineras |
| IVF1.b        | "Campo vacío" | "Campo vacío"        |
| IVF.1.c       | "Cep"         | "Marca inválida"     |



## 3. Pruebas de integración 



## 4. Pruebas unitarias 



Método filtraGasolineras()

| Identificador | Entrada  | Resultados      |
| ------------- | -------- | --------------- |
| UGID.1.a      | "REPSOL" | Datos correctos |
| UGID.1.b      | "Ersolp" | Marca no existe |





