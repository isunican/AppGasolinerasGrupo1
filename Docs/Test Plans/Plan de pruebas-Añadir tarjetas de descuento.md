<h1 style="text-align:center">Plan de pruebas - Añadir tarjetas de descuento, #399359</h1>



## 0. Introducción

Introducción

## 1. Pruebas de aceptación

> ​	En esta sección se definen las pruebas de aceptación extraídas de la entrevista con el _Product Owner_. En este caso solo se derivó un caso de uso asociado a la historia de usuario, el caso de uso _"Añadir tarjeta de descuento"_  

Prueba 0: Éxito

​	1.- El usuario selecciona la opción de añadir tarjetas de descuento.

​	2.- El sistema abre una nueva ventana que muestra al usuario el formulario  en el que puede introducir la 	información de la tarjeta de descuento. 

​	3.- El usuario rellena el formulario de la tarjeta de descuento y confirma el formulario.

​	4.- Se verifica que el sistema introduce correctamente la tarjeta de descuento en el sistema.

​	5.- Se verifica que el sistema muestra un mensaje de éxito.

​	6.- Se verifica que el sistema aplica los descuentos a las gasolineras apropiadamente.



Prueba 1: Campo del formulario sin completar

​	1.- El usuario selecciona la opción de añadir tarjetas de descuento.

​	2.- El sistema abre una nueva ventana que muestra al usuario el formulario  en el que puede introducir la información de la tarjeta de descuento. 

​	3.- El usuario confirma el formulario sin rellenar algún campo.

​	4.- Se verifica que el sistema muestra un mensaje de error informando al usuario del error cometido.



- Caso de uso UC.1:  Añadir tarjeta de descuento por porcentaje

  - UC.1.a: Éxito

  - UC.1.b: Tarjeta no válida(Campo del formulario sin rellenar)

  

- Caso de uso UC.2: Añadir tarjeta de descuento por céntimos por litro

  - UC.2.a: Éxito

  - UC.2.b: Tarjeta no válida (Campo del formulario sin rellenar)

    

    | Identificador | Procedimiento                                                | Valor esperado                |
    | ------------- | ------------------------------------------------------------ | ----------------------------- |
    | UC.1.a        | Nombre="Tarjeta de prueba porcentual", Marca="CAMPSA", Tipo Descuento= "Porcentual" Descuento="1.25" Comentarios="Esto es una prueba" | "Tarjeta añadida con éxito"   |
    | UC.1.b        | Nombre="Tarjeta de prueba porcentual vacía", Marca="CAMPSA", Tipo Descuento= "Porcentual" Descuento="" Comentarios="" | "Falta un campo por rellenar" |
    | UC.2.a        | Nombre="Tarjeta de prueba por litro", Marca="CAMPSA", Tipo Descuento= "cts/L" Descuento="7" Comentarios="Esto es una prueba" | "Tarjeta añadida con éxito"   |
    | UC.2.b        | Nombre="Tarjeta de prueba por litro vacía", Marca="CAMPSA", Tipo Descuento= "cts/L" Descuento="" Comentarios="" | "Falta un campo por rellenar" |

  

## 2. Pruebas de interfaz

​		En las pruebas de interfaz, que se realizarán con la ayuda de la librería de pruebas _"Espresso"_, se comprobará que las interfaces se comportan acorde a lo descrito en los casos de prueba de aceptación. Para ello se identifican los mismos casos de prueba que los casos de uso identificados en la sección 1.

## 3. Pruebas de integración

​		En las pruebas de integración se comprobará que las clases _presenter_ añadidas al proyecto, en este caso _PresenterTarjetasDescuento_, funcionan bien cuando se les proporciona acceso al modelo de datos real, y no datos de juguete. Para esto se utilizarán los mismos casos de  prueba que los casos de uso identificados en la sección 1.



## 4.  Pruebas unitarias

- ### Presenter de tarjetas de descuento

  - anhadirNuevaTarjeta
  
    | Identificador | Proceso                                                      | Valor esperado |
    | ------------- | ------------------------------------------------------------ | -------------- |
    | UT.1.a        | nombre="TestPorcOk" descripcion="Test" marca="Cepsa" tipoTarjeta="Porcentual" descuento="33.3" | true           |
    | UT.1.b        | nombre="TestPorcFail1" descripcion="Test" marca="Cepsa" tipoTarjeta="Porcentual" descuento="456" | false          |
    | UT.1.d        | nombre="TestPorcFail2" descripcion="Test" marca="Cepsa" tipoTarjeta="Porcentual" descuento="cincuenta" | false          |
    | UT.1.d        | nombre="TestCtsOK"descripcion="Test" marca="Cepsa" tipoTarjeta="ctsLitro" descuento="5" | true           |
    | UT.1.e        | nombre="TestCtsFail"descripcion="Test" marca="Cepsa" tipoTarjeta="ctsLitro" descuento="cuatro" | false          |
    | UT.1.f        | nombre="TestTipoFail"descripcion="Test" marca="Cepsa" tipoTarjeta="test" descuento="3" | false          |
  
    
  
  - actualizarListaDePrecios
  
    | Identificador | Proceso                                                      | Valor esperado                     |
    | ------------- | ------------------------------------------------------------ | ---------------------------------- |
    | UT.2.a        | gasolineras=lista con gasolineras compatibles descuento="5"  | lista con los descuentos aplicados |
    | UT.2.b        | gasolineras=lista con gasolineras incompatibles descuento="5" | lista sin descuentos aplicados     |
  | UT.2.c        | gasolineras=lista vacia descuento="5"                        | lista vacia                        |
    
    