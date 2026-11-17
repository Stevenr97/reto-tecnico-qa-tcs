@login
Feature: Servicio de autenticación (login) - Demoblaze
  POST https://api.demoblaze.com/login
  Entradas: username, password (codificado en Base64, igual que la web)

  Background:
    * url baseUrl
    # Se registra UNA sola vez un usuario para todos los escenarios de este feature
    * def registrado = callonce read('classpath:demoblaze/common/crear-usuario.feature') { prefijo: 'tcsqa_login_', password: 'Login2026*' }

  @login_correcto
  Scenario: Login con usuario y password correctos
    Given path 'login'
    And request { username: '#(registrado.username)', password: '#(registrado.passwordCifrado)' }
    When method post
    Then status 200
    * string respuesta = response
    And match respuesta contains 'Auth_token: '
    * def token = respuesta.replace('Auth_token: ', '').replace(/"/g, '').trim()
    And assert token.length > 10
    * karate.log('Token obtenido:', token)

  @login_incorrecto
  Scenario Outline: Login con credenciales incorrectas - <caso>
    # En el CSV, USUARIO_REGISTRADO se reemplaza por el usuario creado en el Background
    * def usernameLogin = usuario == 'USUARIO_REGISTRADO' ? registrado.username : usuario
    * def passwordLogin = aBase64(password)
    Given path 'login'
    And request { username: '#(usernameLogin)', password: '#(passwordLogin)' }
    When method post
    Then status 200
    And match response == { errorMessage: '#(mensajeEsperado)' }

    Examples:
      | read('classpath:demoblaze/data/login-incorrecto.csv') |
