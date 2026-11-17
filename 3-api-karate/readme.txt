=====================================================================
 EJERCICIO 2 - PRUEBAS DE API: SIGNUP Y LOGIN DE DEMOBLAZE
 Karate DSL (Java / Maven)
=====================================================================

1. SERVICIOS PROBADOS
---------------------
  Signup: POST https://api.demoblaze.com/signup
  Login : POST https://api.demoblaze.com/login
  Body (ambos): { "username": "<usuario>", "password": "<password en Base64>" }
  Nota: la web de Demoblaze codifica el password con Base64 (btoa) antes de enviarlo;
  las pruebas replican ese comportamiento con la función aBase64() de karate-config.js.

2. CASOS AUTOMATIZADOS
----------------------
  signup.feature
   - Crear un nuevo usuario (Scenario Outline con 3 filas leídas de data/usuarios-nuevos.json)
   - Intentar crear un usuario ya existente
  login.feature
   - Usuario y password correctos (valida que se obtiene el Auth_token)
   - Usuario y password incorrectos (Scenario Outline con filas de data/login-incorrecto.csv:
     password incorrecto / usuario inexistente)

  Entradas y salidas observadas:
   | Caso                          | Status | Respuesta                                  |
   |-------------------------------|--------|--------------------------------------------|
   | Signup usuario nuevo          | 200    | ""  (cuerpo vacío)                         |
   | Signup usuario existente      | 200    | {"errorMessage":"This user already exist."}|
   | Login correcto                | 200    | "Auth_token: <token>"                      |
   | Login password incorrecto     | 200    | {"errorMessage":"Wrong password."}         |
   | Login usuario inexistente     | 200    | {"errorMessage":"User does not exist."}    |

3. REQUISITOS (versiones usadas)
--------------------------------
  - Java JDK 17 o superior     (java -version)
  - Apache Maven 3.9.x         (mvn -v)
  - Conexión a internet
  Dependencias (las descarga Maven):
  - Karate 1.5.0 (io.karatelabs:karate-junit5)
  - cucumber-reporting 5.8.1 (net.masterthought)

4. EJECUCIÓN PASO A PASO
------------------------
  1) Abrir una terminal en la carpeta "3-api-karate"
  2) Ejecutar todas las pruebas:
        mvn clean test
  3) (Opcional) Ejecutar solo un grupo por tag:
        mvn clean test -Dkarate.options="--tags @signup"
        mvn clean test -Dkarate.options="--tags @login"

5. REPORTES GENERADOS
---------------------
  - Karate (karate-report):  target/karate-reports/karate-summary.html
  - Cucumber HTML:           target/cucumber-html-reports/overview-features.html
  - Log de ejecución:        target/karate.log
  Una copia de los reportes de la última ejecución se encuentra en la carpeta "reportes/".

6. ESTRUCTURA
-------------
src/test/java
  ├── karate-config.js                 -> baseUrl y funciones reutilizables (usuarioUnico, aBase64)
  ├── logback-test.xml                 -> Configuración de logs
  └── demoblaze
      ├── DemoblazeApiTest.java        -> Runner JUnit 5 + generación de reporte Cucumber
      ├── signup/signup.feature
      ├── login/login.feature
      ├── common/crear-usuario.feature -> Helper reutilizable (@ignore) para crear usuarios
      └── data
          ├── usuarios-nuevos.json     -> Datos del outline de signup
          └── login-incorrecto.csv     -> Datos del outline de login incorrecto
