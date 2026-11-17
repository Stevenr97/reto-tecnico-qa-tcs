# Reto Técnico – QA Automatizador – TCS Ecuador

Repositorio con la resolución de las pruebas técnicas.

| Carpeta | Ejercicio | Herramientas |
|---|---|---|
| `1-prueba-teorica/` | Prueba teórica (Excel con respuestas) | — |
| `2-e2e-serenity/` | E2E: flujo de compra en demoblaze.com | Serenity BDD 4 + Screenplay + Cucumber 7, Java 17, Maven |
| `3-api-karate/` | APIs: signup y login de api.demoblaze.com | Karate 1.5, Java 17, Maven |
| `4-performance-k6/` | Performance: carga del login (20 TPS) + análisis de resultados (`ejercicio2-analisis/InformeResultados.doc`) | k6 v1.x |

Cada carpeta contiene:
- `readme.txt` – instrucciones paso a paso y versiones
- `conclusiones.txt` – hallazgos, opiniones y consideraciones
- `reportes/` o `reports/` – reportes de la última ejecución

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

## Requisitos
- Java JDK 17+
- Maven 3.9+
- Google Chrome
- k6 v1.0+
