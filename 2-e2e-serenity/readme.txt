=====================================================================
 EJERCICIO 1 - PRUEBA E2E: FLUJO DE COMPRA EN https://www.demoblaze.com
 Serenity BDD + Screenplay + Cucumber (Java / Maven)
=====================================================================

1. QUÉ AUTOMATIZA
-----------------
Flujo completo de compra:
  - Agregar dos productos al carrito
  - Visualizar el carrito (valida productos y total = suma de precios)
  - Completar el formulario de compra (Place Order)
  - Finalizar la compra (valida mensaje "Thank you for your purchase!",
    monto, tarjeta y nombre en el comprobante)
Adicional: escenario negativo que intenta comprar con el formulario vacío.

2. REQUISITOS (versiones usadas)
--------------------------------
  - Java JDK 17 o superior        (probado con Temurin 21.0.13)   -> java -version
  - Apache Maven 3.9 o superior   (probado con 3.10.1)            -> mvn -v
  - Google Chrome (última versión estable)
  - Conexión a internet (descarga de dependencias y del driver de Chrome)
  - Probado en Windows 11
  Dependencias principales (las descarga Maven automáticamente):
  - Serenity BDD 4.2.1 (core, screenplay, screenplay-webdriver, cucumber)
  - Cucumber 7.20.1 / JUnit Platform 1.11.3
  - Selenium 4 (incluido en Serenity; Selenium Manager descarga chromedriver solo)

3. EJECUCIÓN PASO A PASO
------------------------
  1) Abrir una terminal en la carpeta "2-e2e-serenity"
  2) Ejecutar todos los escenarios:
        mvn clean verify
  3) (Opcional) Ejecutar sin abrir el navegador (modo headless):
        mvn clean verify -Dheadless.mode=true
  4) (Opcional) Ejecutar por tags:
        mvn clean verify -Dcucumber.filter.tags="@compra_exitosa"
        mvn clean verify -Dcucumber.filter.tags="@negativo"

4. REPORTES GENERADOS
---------------------
  - Serenity (reporte completo con capturas por paso):
        target/site/serenity/index.html
  - Serenity resumen de una página:
        target/site/serenity/serenity-summary.html
  - Cucumber HTML / JSON:
        target/cucumber-reports/cucumber.html
        target/cucumber-reports/cucumber.json
  Una copia de los reportes de la última ejecución se encuentra en la carpeta "reportes/".

5. ESTRUCTURA DEL PROYECTO (patrón Screenplay)
----------------------------------------------
src/test/java/com/tcs/demoblaze
  ├── userinterfaces   -> Targets/localizadores de cada página (Home, Producto, Carrito, modales)
  ├── tasks            -> Tareas de negocio (AbrirTienda, AgregarProducto, VerCarrito,
  │                       CompletarFormularioCompra, FinalizarCompra, IntentarCompraSinDatos)
  ├── interactions     -> Interacciones técnicas reutilizables (ClicYAceptarAlerta,
  │                       EsperarProductosEnCarrito)
  ├── questions        -> Preguntas para validar (ProductosEnCarrito, TotalDelCarrito,
  │                       MensajeDeConfirmacion, DetalleDelComprobante, TextoDeLaAlerta)
  ├── models           -> DatosCompra (modelo de datos) y Memoria (claves de memoria del actor)
  ├── utils            -> LectorCsv (lectura de datos externos)
  ├── stepdefinitions  -> Glue de Cucumber + Hooks
  └── runners          -> CucumberTestSuite (runner JUnit 5)
src/test/resources
  ├── features/compra/compra_demoblaze.feature  -> Escenarios en Gherkin (español)
  ├── data/compras.csv                          -> Datos de prueba del Scenario Outline
  └── serenity.conf                             -> Configuración del navegador / Serenity

6. DATOS DE PRUEBA
------------------
El "Esquema del escenario" usa la columna idCompra de "Ejemplos" para leer, desde
data/compras.csv, los dos productos y los datos del cliente. Para agregar un nuevo caso
basta con añadir una fila al CSV y su idCompra en la tabla de Ejemplos.
