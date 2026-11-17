@ignore
Feature: Helper reutilizable - crea un usuario nuevo en signup y devuelve sus credenciales
  # Recibe: prefijo, password
  # Devuelve: username, passwordCifrado

  Scenario: Crear usuario de apoyo
    * def username = usuarioUnico(prefijo)
    * def passwordCifrado = aBase64(password)
    Given url baseUrl
    And path 'signup'
    And request { username: '#(username)', password: '#(passwordCifrado)' }
    When method post
    Then status 200
    * string respuesta = response
    * match respuesta !contains 'errorMessage'
