function fn() {
  var env = karate.env || 'qa';
  karate.log('Ambiente de ejecución (karate.env):', env);

  var config = {
    env: env,
    baseUrl: 'https://api.demoblaze.com'
  };

  // Genera un username único (evita colisiones al ejecutar varias veces)
  config.usuarioUnico = function (prefijo) {
    var System = Java.type('java.lang.System');
    return prefijo + System.currentTimeMillis() + Math.floor(Math.random() * 1000);
  };

  // La web de Demoblaze envía el password codificado en Base64 (btoa). Se replica ese comportamiento.
  config.aBase64 = function (texto) {
    var Base64 = Java.type('java.util.Base64');
    var JString = Java.type('java.lang.String');
    return Base64.getEncoder().encodeToString(new JString(texto).getBytes('UTF-8'));
  };

  karate.configure('connectTimeout', 15000);
  karate.configure('readTimeout', 30000);
  karate.configure('logPrettyRequest', true);
  karate.configure('logPrettyResponse', true);

  return config;
}
