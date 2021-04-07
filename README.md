# CHALLENGE - SPRING BOOT REACTIVE WEBFLUX SSE #

## Summary ##

En este reto deberiamos usar Spring Boot Reactive con Webflux, aplicando los conceptos de la programación funciona y reactiva, llevando a cabo cada principio de forma correcta, se pretende demostrar el uso de la herramientas aprendidas de la programación reactiva con el uso de la librería de reactor core y spring boot.

## Use Case/Problem ##

En esta aplicación se deberá utilizar la siguientes herramientas de forma correcta :

  


 *  Spring Boot
 *  WebFlux
 *  Gradle

  


Ejecute esto usando el envoltorio gradle incluido en el repositorio.

  


~~~~~~~~~~
./gradlew bootRun
~~~~~~~~~~

  


Esto iniciará la aplicación en el puerto 8080.

  


  


**PROBLEMA:**

  


Se tiene el test **src/test/java/com/example/ReactorTests.java** con algunos operadores funcionales lo que se busca es crear algunos servicios funcionales donde se puedan aplicar estos operadores dentro de un servicio web de Spring Boot, se deja a disposición del aprendiz el problema o ejemplo que debe aplicar los conceptos de reactividad, solo debe tener en cuenta las siguientes directrices:

  


 *  Aplicar los siguientes operadores en un ejemplo:
 *  zip
 *  map
 *  concatWith
 *  zipWith
 *  toIterable
 *  fromIterable
 *  just
 *  empty
 *  flatMap
 *  switchIfEmpty
 *  Crear minimo 5 servicios web reactivo con sus respectivos servicios
 *  Tener una cobertura al 100%
 *  El problema plateado por usted tiene coherencia y sentido practico, es decir aplicar un modelamiento del problema correcto para ello.

  


Recuerde que en el archivo de los tests va a encontrar ejemplo de algunos operadores basicos, esto les puede ayudar para aplicar el modelo diseñado por ustedes.

## Evaluation criteria ##

| Criteria                                                                            | Percentage |
| ----------------------------------------------------------------------------------- | ---------- |
| Aplica los operadores descritos en el caso/problema                                 | 30.0 %     |
| Tiene cobertura del 100%                                                            | 30.0 %     |
| Diseña un problema practico usando una arquitectura por capas y totalmente reactiva | 40.0 %     |