/**
 * Prueba de carga - Servicio de login (https://fakestoreapi.com/auth/login)
 *
 * Objetivo: sostener al menos 20 TPS (transacciones por segundo) validando:
 *   - Tiempo de respuesta máximo permitido: 1,5 s
 *   - Tasa de error menor al 3 % del total de peticiones
 *
 * Ejecución:  k6 run login-load-test.js
 * Parámetros opcionales (variables de entorno):
 *   TPS       -> peticiones por segundo objetivo (por defecto 20)
 *   DURATION  -> duración de la carga sostenida (por defecto 2m)
 *   BASE_URL  -> URL base del servicio (por defecto https://fakestoreapi.com)
 */
import http from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';
import exec from 'k6/execution';
import { Trend, Rate } from 'k6/metrics';
import papaparse from 'https://jslib.k6.io/papaparse/5.1.1/index.js';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.2/index.js';
import { htmlReport } from 'https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js';

const BASE_URL = __ENV.BASE_URL || 'https://fakestoreapi.com';
const TPS = parseInt(__ENV.TPS || '20', 10);
const DURATION = __ENV.DURATION || '2m';

// Datos parametrizados desde CSV. SharedArray carga el archivo una sola vez y lo comparte entre VUs.
const usuarios = new SharedArray('usuarios', function () {
  return papaparse.parse(open('./data/users.csv'), { header: true, skipEmptyLines: true }).data;
});

// Métricas propias para el reporte
const tiempoLogin = new Trend('login_duration', true);
const loginsExitosos = new Rate('login_success_rate');

export const options = {
  scenarios: {
    login_carga_constante: {
      // Modelo abierto: k6 inicia TPS iteraciones por segundo sin importar cuánto tarde el servidor.
      // Cada iteración hace 1 petición, por lo que iteraciones/s = TPS.
      executor: 'constant-arrival-rate',
      rate: TPS,
      timeUnit: '1s',
      duration: DURATION,
      preAllocatedVUs: 30,
      maxVUs: 100,
    },
  },
  thresholds: {
    // Tiempo de respuesta: 95 % y 99 % de las peticiones por debajo de 1,5 s
    http_req_duration: ['p(95)<1500', 'p(99)<1500'],
    // Tasa de error < 3 %
    http_req_failed: ['rate<0.03'],
    // Al menos el 97 % de las validaciones (checks) deben pasar
    checks: ['rate>0.97'],
    // Throughput: se debe sostener al menos ~20 TPS (5 % de tolerancia por arranque/cierre)
    http_reqs: [`rate>=${TPS * 0.95}`],
  },
  summaryTrendStats: ['avg', 'min', 'med', 'max', 'p(90)', 'p(95)', 'p(99)'],
};

// El servicio responde 200/201 cuando el login es correcto
http.setResponseCallback(http.expectedStatuses(200, 201));

export default function () {
  // Se recorre el CSV de forma circular según el número global de iteración
  const usuario = usuarios[exec.scenario.iterationInTest % usuarios.length];

  const payload = JSON.stringify({ username: usuario.user, password: usuario.passwd });
  const params = {
    headers: { 'Content-Type': 'application/json' },
    timeout: '60s', // equivalente a --max-time 60 del cURL entregado
    tags: { name: 'POST /auth/login' },
  };

  const res = http.post(`${BASE_URL}/auth/login`, payload, params);
  tiempoLogin.add(res.timings.duration);

  const ok = check(res, {
    'status 200/201': (r) => r.status === 200 || r.status === 201,
    'respuesta contiene token': (r) => {
      try {
        return typeof r.json('token') === 'string' && r.json('token').length > 0;
      } catch (e) {
        return false;
      }
    },
    'tiempo de respuesta <= 1.5 s': (r) => r.timings.duration <= 1500,
  });
  loginsExitosos.add(ok);
}

// Genera los reportes al finalizar: consola, HTML y JSON
export function handleSummary(data) {
  return {
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
    'reports/summary.txt': textSummary(data, { indent: ' ', enableColors: false }),
    'reports/summary.html': htmlReport(data, { title: 'Reto TCS - Prueba de carga login' }),
    'reports/summary.json': JSON.stringify(data, null, 2),
  };
}
