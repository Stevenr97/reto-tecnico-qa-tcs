=====================================================================
 PERFORMANCE QA - EJERCICIO 1: PRUEBA DE CARGA DEL SERVICIO DE LOGIN
 Herramienta: k6
=====================================================================

1. SERVICIO PROBADO
-------------------
  POST https://fakestoreapi.com/auth/login
  Header: Content-Type: application/json
  Body:   { "username": "<user>", "password": "<passwd>" }
  Datos parametrizados desde: data/users.csv (columnas user,passwd)

2. ESCENARIO Y CRITERIOS DE ACEPTACIÓN
--------------------------------------
  - Ejecutor constant-arrival-rate (modelo abierto): 20 iteraciones/segundo durante
    2 minutos; cada iteración = 1 login  => 20 TPS sostenidos (~2.400 peticiones).
  - Thresholds configurados en el script (la prueba FALLA si no se cumplen):
      http_req_duration  p(95) < 1500 ms y p(99) < 1500 ms   (tiempo máx. 1,5 s)
      http_req_failed    rate  < 3 %                           (tasa de error)
      checks             rate  > 97 %
      http_reqs          rate >= 19 req/s                      (se alcanzan ~20 TPS)
  - Checks por petición: status 200/201, respuesta con token, tiempo <= 1,5 s.

3. REQUISITOS (versiones)
-------------------------
  - k6 v2.4.0 (probado; compatible con k6 v1.x)   -> k6 version
    Instalación:
      Windows : winget install k6 --source winget   (o choco install k6)
      macOS   : brew install k6
      Linux   : https://grafana.com/docs/k6/latest/set-up/install-k6/
  - Conexión a internet (el script importa papaparse, k6-summary y k6-reporter desde
    jslib.k6.io / GitHub en tiempo de ejecución).

4. EJECUCIÓN PASO A PASO
------------------------
  1) Abrir una terminal en la carpeta "4-performance-k6"
  2) Ejecutar la prueba:
        k6 run login-load-test.js
  3) (Opcional) Cambiar TPS o duración:
        k6 run -e TPS=30 -e DURATION=5m login-load-test.js
  4) (Opcional) Generar además el dashboard web de k6 en HTML:
        Windows (PowerShell):
          $env:K6_WEB_DASHBOARD="true"; $env:K6_WEB_DASHBOARD_EXPORT="reports/dashboard.html"; k6 run login-load-test.js
        macOS / Linux:
          K6_WEB_DASHBOARD=true K6_WEB_DASHBOARD_EXPORT=reports/dashboard.html k6 run login-load-test.js

5. REPORTES
-----------
  reports/summary.html   -> Reporte HTML (k6-reporter v3)
  reports/summary.txt    -> Resumen de consola en texto
  reports/summary.json   -> Datos completos en JSON
  reports/dashboard.html -> Dashboard web de k6 (si se usó el paso 4)

6. EJERCICIO 2 - ANÁLISIS DE RESULTADOS
---------------------------------------
  El informe solicitado está en: ejercicio2-analisis/InformeResultados.doc
  (también en formato .docx).
