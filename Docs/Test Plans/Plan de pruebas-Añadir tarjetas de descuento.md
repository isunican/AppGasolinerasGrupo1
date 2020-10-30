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



## 4.  pruebas unitarias

> - Tarjetas de descuento por litro
> - Tarjetas de descuento por porcentaje



- Presenter de tarjetas de descuento

