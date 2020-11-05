

# Plan de pruebas - Añadir tarjetas de descuento, #399359



## 0. Introducción

​	En el siguiente plan de pruebas se describirán las pruebas a realizar sobre el código implementado para la historia de usuario "**_Añadir Tarjeta de descuento_**" con objeto de verificar su comportamiento.

​	Las pruebas que se realizarán son:

- Pruebas de aceptación

- Pruebas de interfaz

- Pruebas unitarias

  

## 1. Pruebas de aceptación

 ​	En esta sección se definen las pruebas de aceptación extraídas de la entrevista con el _Product Owner_. 

- Prueba 0: Éxito

​	1.- El usuario selecciona la opción de añadir tarjetas de descuento.

​	2.- El sistema abre una nueva ventana que muestra al usuario el formulario  en el que puede introducir la información de la tarjeta de descuento. 

​	3.- El usuario rellena el formulario de la tarjeta de descuento y confirma el formulario.

​	4.- Se verifica que el sistema introduce correctamente la tarjeta de descuento en el sistema.

​	5.- Se verifica que el sistema muestra un mensaje de éxito.

​	6.- Se verifica que el sistema aplica los descuentos a las gasolineras apropiadamente.



- Prueba 1: Campo del formulario sin completar

​	1.- El usuario selecciona la opción de añadir tarjetas de descuento.

​	2.- El sistema abre una nueva ventana que muestra al usuario el formulario  en el que puede introducir la información de la tarjeta de descuento. 

​	3.- El usuario confirma el formulario sin rellenar algún campo.

​	4.- Se verifica que el sistema muestra un mensaje de error informando al usuario del error cometido.



#### 1.1 Casos de uso

​	En este caso, se derivaron dos casos de uso asociados a la historia de usuario: el caso de uso _"Añadir tarjeta de descuento por porcentaje"_ y el caso de uso _"Añadir tarjeta de descuento por céntimos por litro"_:  

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

  ###### Tabla 1: Casos de prueba de aceptación
  
  <div style="page-break-after: always;"></div>

## 2. Pruebas de interfaz

​	En las pruebas de interfaz, que se realizarán con la ayuda de la librería de pruebas _"Espresso"_, se comprobará que las interfaces se comportan acorde a lo descrito en los casos de prueba de aceptación. Para ello se identifican los mismos casos de prueba que los casos de uso identificados en la sección 1, esta vez renombrados a **UIT.x.y**.

| Identificador | Procedimiento                                                | Valor esperado                |
| ------------- | ------------------------------------------------------------ | ----------------------------- |
| UIT.1.a       | Nombre="Tarjeta de prueba porcentual", Marca="CAMPSA", Tipo Descuento= "Porcentual" Descuento="1.25" Comentarios="Esto es una prueba" | "Tarjeta añadida con éxito"   |
| UIT.1.b       | Nombre="Tarjeta de prueba porcentual vacía", Marca="CAMPSA", Tipo Descuento= "Porcentual" Descuento="" Comentarios="" | "Falta un campo por rellenar" |
| UIT.2.a       | Nombre="Tarjeta de prueba por litro", Marca="CAMPSA", Tipo Descuento= "cts/L" Descuento="7" Comentarios="Esto es una prueba" | "Tarjeta añadida con éxito"   |
| UIT.2.b       | Nombre="Tarjeta de prueba por litro vacía", Marca="CAMPSA", Tipo Descuento= "cts/L" Descuento="" Comentarios="" | "Falta un campo por rellenar" |

###### Tabla 2: Casos de prueba de la interfaz

<div style="page-break-after: always;"></div>

## 3.  Pruebas unitarias

​	Además de comprobar el correcto funcionamiento de la interfaz, se deberá comprobar el comportamiento de los componentes que administran los datos que se le pasan a la interfaz. En este caso se implementarán pruebas unitarias para los _presenters_ creados.

#### 3.1 _Presenter_ de tarjetas de descuento

​	Para este _presenter_ se han diseñado dos casos de prueba, uno por cada método público (excluyendo _setters_ y _getters_). Estos casos de prueba son identificables por **UT.x.y**.

- Caso de prueba UT.1: anhadirNuevaTarjeta

  | Identificador | Proceso                                                      | Valor esperado |
  | ------------- | ------------------------------------------------------------ | -------------- |
  | UT.1.a        | nombre="TestPorcOk" descripcion="Test" marca="Cepsa" tipoTarjeta="Porcentual" descuento="33.3" | true           |
  | UT.1.b        | nombre="TestPorcFail1" descripcion="Test" marca="Cepsa" tipoTarjeta="Porcentual" descuento="456" | false          |
  | UT.1.d        | nombre="TestPorcFail2" descripcion="Test" marca="Cepsa" tipoTarjeta="Porcentual" descuento="cincuenta" | false          |
  | UT.1.d        | nombre="TestCtsOK"descripcion="Test" marca="Cepsa" tipoTarjeta="cts/Litro" descuento="5" | true           |
  | UT.1.e        | nombre="TestCtsFail"descripcion="Test" marca="Cepsa" tipoTarjeta="cts/Litro" descuento="cuatro" | false          |
  | UT.1.f        | nombre="TestTipoFail"descripcion="Test" marca="Cepsa" tipoTarjeta="test" descuento="3" | false          |

  ###### Tabla 3: Casos de prueba para el método _anhadirNuevaTarjeta()_

  <div style="page-break-after: always;"></div>

- Caso de prueba UT.2: actualizarListaDePrecios

  | Identificador | Proceso                                                      | Valor esperado                                               |
  | ------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | UT.2.a        | gasolineras = mezcla de gasolineras compatibles e incompatibles descuento="0%+0" | lista de gasolineras sin descuentos aplicados                |
  | UT.2.b        | gasolineras=1 gasolinera compatible descuento="5% + 2cts/Litro" | gasolinera con el descuento porcentual aplicado              |
  | UT.2.c        | gasolineras=1 gasolinera incompatible descuento="5% + 2/ctsLitro" | gasolinera con el descuento sin aplicar                      |
  | UT.2.d        | gasolineras= mezcla de gasolineras compatibles e incompatibles descuento="5% + 2cts/Litro" | lista de gasolineras con los descuentos aplicados cuando corresponde |
  | UT.2.e        | gasolineras= lista vacía descuento="5% + 2cts/Litro"         | lista vacía                                                  |
  | UT.2.f        | gasolineras=null descuento="5% + 2cts/Litro"                 | lista vacía                                                  |

  ###### Tabla 4: Casos de prueba para el método _actualizarListaDePrecios()_



<div style="page-break-after: always;"></div>

# Informe de pruebas



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