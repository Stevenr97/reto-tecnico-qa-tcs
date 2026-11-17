# language: es
@flujo_compra
Característica: Flujo de compra en la tienda online Demoblaze
  Como cliente de la tienda online Demoblaze
  Quiero agregar productos al carrito y completar la orden de compra
  Para adquirir los productos que necesito

  Antecedentes:
    Dado que el cliente abre la tienda Demoblaze

  @e2e @compra_exitosa
  Esquema del escenario: Compra exitosa de dos productos - <descripcion>
    Cuando agrega al carrito los productos de la compra "<idCompra>"
    Y visualiza el carrito de compras
    Entonces el carrito muestra los productos agregados con el total correcto
    Cuando completa el formulario de compra con sus datos
    Y finaliza la compra
    Entonces visualiza el mensaje de confirmación "<mensaje>"
    Y el comprobante muestra el monto total, la tarjeta y el nombre del cliente

    # Los datos de cada compra (productos y datos del cliente) se leen de src/test/resources/data/compras.csv
    Ejemplos:
      | idCompra | descripcion             | mensaje                      |
      | 1        | dos celulares           | Thank you for your purchase! |
      | 2        | dos laptops             | Thank you for your purchase! |
      | 3        | dos celulares gama alta | Thank you for your purchase! |

  @negativo
  Escenario: No se permite finalizar la compra sin nombre ni tarjeta de crédito
    Cuando agrega al carrito el producto "Nexus 6"
    Y visualiza el carrito de compras
    Y intenta finalizar la compra sin llenar el formulario
    Entonces el sistema muestra la alerta "Please fill out Name and Creditcard."
