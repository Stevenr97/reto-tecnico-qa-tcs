@signup
Feature: Servicio de registro de usuarios (signup) - Demoblaze
  POST https://api.demoblaze.com/signup
  Entradas: username, password (codificado en Base64, igual que la web)

  Background:
    * url baseUrl

  @crear_usuario
  Scenario Outline: Crear un nuevo usuario en signup - <caso>
    * def username = usuarioUnico(prefijo)
    * def passwordCifrado = aBase64(password)
    Given path 'signup'
    And request { username: '#(username)', password: '#(passwordCifrado)' }
    When method post
    Then status 200
    # Un registro exitoso responde 200 con cuerpo vacío (""), sin errorMessage
    * string respuesta = response
    And match respuesta !contains 'errorMessage'
    * karate.log('Usuario creado:', username)

    Examples:
      | read('classpath:demoblaze/data/usuarios-nuevos.json') |

  @usuario_existente
  Scenario: Intentar crear un usuario ya existente
    # Precondición: se registra un usuario nuevo para garantizar que exista
    * def existente = call read('classpath:demoblaze/common/crear-usuario.feature') { prefijo: 'tcsqa_dup_', password: 'Clave2026*' }
    Given path 'signup'
    And request { username: '#(existente.username)', password: '#(existente.passwordCifrado)' }
    When method post
    Then status 200
    And match response == { errorMessage: 'This user already exist.' }
