

# Plan de pruebas - Filtrar por tipo de gasolina, #399353



## 0. Introducción

​	En el siguiente plan de pruebas se describirán las pruebas a realizar sobre el código implementado para la historia de usuario "**_Filtrar por tipo de gasolina_**" con objeto de verificar su comportamiento.

​	Las pruebas que se realizarán son:

- Pruebas de aceptación

- Pruebas de interfaz

- Pruebas unitarias

  

## 1. Pruebas de aceptación

 ​	En esta sección se definen las pruebas de aceptación extraídas de la entrevista con el _Product Owner_. 

- Prueba 0: Éxito

  1.- El usuario abre el panel lateral de la aplicación.

  2.- El sistema muestra el panel lateral con las distintas opciones.

  3.- El usuario selecciona la opción de filtrar.

  4.- El sistema abre una ventana flotante con la información de los filtros.

  5.- El sistema muestra la lista de tipos de combustible.

  6.- El usuario selecciona el tipo de combustible por el que desea filtrar las gasolineras y confirma la selección.

  7.- El sistema cierra la ventana de selección de filtro y muestra las gasolineras filtradas por el tipo de gasolina seleccionado.

  8.- Se verifica que el sistema muestra las gasolineras en función del filtro de tipos de gasolinas aplicado por el usuario.



- Prueba 1: No se ha seleccionado tipo de combustible.

  1.- El usuario abre el panel lateral de la aplicación.

  2.- El sistema muestra el panel lateral con las distintas opciones.

  3.- El usuario selecciona la opción de filtrar.

  4.- El sistema abre una ventana flotante con la información de los filtros.

  5.- El sistema muestra la lista de tipos de combustible.

  6.- El usuario no selecciona el tipo de combustible por el que desea filtrar las gasolineras y confirma la selección.

  7.-Se verifica que el sistema muestra un mensaje informativo indicando al usuario que no se pueden aplicar los filtros debido a que no ha introducido ningún tipo de combustible.



#### 1.1 Casos de uso

​	En este caso, se derivaron dos casos de uso asociados a la historia de usuario: el caso de uso _"Añadir tarjeta de descuento por porcentaje"_ y el caso de uso _"Añadir tarjeta de descuento por céntimos por litro"_:  

- Caso de uso UC.1:  Filtrar por tipo de gasolina

  a.   Filtrado correcto (tipo de gasolina correcto)

  b.   Filtrado incorrecto (tipo de gasolina no válido)

  

  - | Identificador | Procedimiento            | Valor esperado                  |
    | ------------- | ------------------------ | ------------------------------- |
    | UC.1.a        | TipoDeGasolina="Diesel"  | "Lista de gasolineras"          |
    | UC.1.b        | TipoDeGasolina="Cerveza" | "No existe el tipo de gasolina" |

  ###### Tabla 1: Casos de prueba de aceptaci

  <div style="page-break-after: always;"></div>

## 2. Pruebas de interfaz

​	En las pruebas de interfaz, que se realizarán con la ayuda de la librería de pruebas _"Espresso"_, se comprobará que las interfaces se comportan acorde a lo descrito en los casos de prueba de aceptación. Para ello se identifican los mismos casos de prueba que los casos de uso identificados en la sección 1, esta vez renombrados a **UIT.x.y**.

| Identificador | Procedimiento           | Valor esperado                                    |
| ------------- | ----------------------- | ------------------------------------------------- |
| UIT.1.a       | TipoDeGasolina="Diesel" | "Lista de gasolineras que tengan gasolina Diesel" |

###### Tabla 2: Casos de prueba de la interfaz

<div style="page-break-after: always;"></div>

## 3.  Pruebas unitarias

​	Además de comprobar el correcto funcionamiento de la interfaz, se deberá comprobar el comportamiento de los componentes que administran los datos que se le pasan a la interfaz. En este caso se implementarán pruebas unitarias para los _presenters_ creados.

#### 3.1 _Presenter_Gasolinera

​	Para este _presenter_ se han diseñado dos casos de prueba, uno por cada método público (excluyendo _setters_ y _getters_). Estos casos de prueba son identificables por **UT.x.y**.

- Caso de prueba UT.1: filtraGasolinerasTipoCombustible

  | Identificador | Proceso                                                      | Valor esperado                  |
  | ------------- | ------------------------------------------------------------ | ------------------------------- |
  | UT.1.a        | TipoDeGasolina="Diesel", Lista vacia                         | Lista nula                      |
  | UT.1.b        | TipoDeGasolina="Diesel", Lista de gasolineras sin Diesel     | Lista vacia                     |
  | UT.1.d        | TipoDeGasolina="Diesel", Lista con una gasolinera con Diesel | Lista con la gasolinera         |
  | UT.1.d        | TipoDeGasolina="Diesel", Lista con solo gasolineras con Diesel | Lista con todas las gasolineras |

  ###### Tabla 3: Casos de prueba para el método _filtraGasolinerasTipoCombustible()_

  <div style="page-break-after: always;"></div>

- Caso de prueba UT.2: tiposGasolina

  | Identificador | Proceso                                | Valor esperado    |
  | ------------- | -------------------------------------- | ----------------- |
  | UT.2.a        | Gasolinera con Diesel y Gasolina       | Diesel Gasolina95 |
  | UT.2.b        | Gasolinera solo con tipo Diesel        | Diesel            |
  | UT.2.c        | Gasolinera solo con tipo Gasolina 95   | Gasolina95        |
  | UT.2.d        | Gasolinera sin ningún tipo de gasolina | ""                |

  ###### Tabla 4: Casos de prueba para el método tiposGasolina()_



<div style="page-break-after: always;"></div>
