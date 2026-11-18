# Reto Técnico – QA Automatizador – TCS Ecuador

Repositorio con la resolución de las pruebas técnicas.

| Carpeta | Ejercicio | Herramientas |
|---|---|---|
| `1-prueba-teorica/` | Prueba teórica (Excel con respuestas) | — |
| `2-e2e-serenity/` | E2E: flujo de compra en demoblaze.com | Serenity BDD 4 + Screenplay + Cucumber 7, Java 17, Maven |
| `3-api-karate/` | APIs: signup y login de api.demoblaze.com | Karate 1.5, Java 17, Maven |
| `4-performance-k6/` | Performance: carga del login (20 TPS) + análisis de resultados (`ejercicio2-analisis/InformeResultados.doc`) | k6 (probado con v2.4.0) |

Cada carpeta contiene:
- `readme.txt` – instrucciones paso a paso y versiones
- `conclusiones.txt` – hallazgos, opiniones y consideraciones
- `reportes/` o `reports/` – reportes de la última ejecución

## Reportes en línea (GitHub Pages)
| Reporte | Enlace |
|---|---|
| E2E – Serenity | https://stevenr97.github.io/reto-tecnico-qa-tcs/2-e2e-serenity/reportes/serenity/index.html |
| E2E – Cucumber | https://stevenr97.github.io/reto-tecnico-qa-tcs/2-e2e-serenity/reportes/cucumber-reports/cucumber.html |
| API – Karate | https://stevenr97.github.io/reto-tecnico-qa-tcs/3-api-karate/reportes/karate-reports/karate-summary.html |
| API – Cucumber | https://stevenr97.github.io/reto-tecnico-qa-tcs/3-api-karate/reportes/cucumber-html-reports/overview-features.html |
| Performance – k6 | https://stevenr97.github.io/reto-tecnico-qa-tcs/4-performance-k6/reports/summary.html |

## Ejecución rápida

```bash
# E2E (abre Chrome)
cd 2-e2e-serenity && mvn clean verify
# Reporte: target/site/serenity/index.html  |  target/cucumber-reports/cucumber.html

# APIs
cd 3-api-karate && mvn clean test
# Reporte: target/karate-reports/karate-summary.html

# Performance
cd 4-performance-k6 && k6 run login-load-test.js
# Reporte: reports/summary.html
```

## Requisitos (versiones con las que se probó)
- Java JDK 17+ (probado con Temurin 21.0.13)
- Maven 3.9+ (probado con 3.10.1)
- Google Chrome (versión estable actual)
- k6 (probado con v2.4.0)
- Windows 11

## Resultados de la última ejecución
| Ejercicio | Resultado |
|---|---|
| E2E Serenity | 4/4 escenarios OK |
| API Karate | 7/7 escenarios OK (+3 escenarios `@defecto` que documentan defectos de la API) |
| Performance k6 | 20 TPS, 0 % errores, p(95) 439 ms: todos los thresholds aprobados |

## Defectos encontrados
- **API signup**: acepta usuarios con password vacío y responde 500 con username vacío (la validación solo existe en la web).
- **Comprobante de compra**: muestra el mes con un mes de atraso (18/10/2026 en una compra del 18/11/2026).
- **API login**: mensajes distintos para "usuario no existe" y "password incorrecto" (permite enumerar usuarios).

Detalle en el `conclusiones.txt` de cada carpeta.
