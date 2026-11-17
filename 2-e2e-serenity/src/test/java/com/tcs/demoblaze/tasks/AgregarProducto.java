package com.tcs.demoblaze.tasks;

import com.tcs.demoblaze.interactions.ClicYAceptarAlerta;
import com.tcs.demoblaze.userinterfaces.ProductoPage;
import com.tcs.demoblaze.userinterfaces.TiendaHomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.Map;

import static com.tcs.demoblaze.models.Memoria.PRECIOS_PRODUCTOS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Selecciona un producto del catálogo, guarda su precio (para validar luego el total del carrito),
 * lo agrega al carrito aceptando la alerta "Product added." y regresa al catálogo.
 */
public class AgregarProducto implements Task {

    private final String producto;

    public AgregarProducto(String producto) {
        this.producto = producto;
    }

    public static AgregarProducto alCarrito(String producto) {
        return instrumented(AgregarProducto.class, producto);
    }

    @Override
    @Step("{0} agrega el producto #producto al carrito")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(TiendaHomePage.PRODUCTO.of(producto), isClickable()).forNoMoreThan(20).seconds(),
                Click.on(TiendaHomePage.PRODUCTO.of(producto)),
                WaitUntil.the(ProductoPage.PRECIO, isVisible()).forNoMoreThan(20).seconds()
        );

        Map<String, Integer> precios = actor.recall(PRECIOS_PRODUCTOS);
        precios.put(producto, precioDesdeTexto(ProductoPage.PRECIO.resolveFor(actor).getText()));

        actor.attemptsTo(
                ClicYAceptarAlerta.en(ProductoPage.BOTON_AGREGAR_AL_CARRITO),
                Click.on(TiendaHomePage.MENU_HOME),
                WaitUntil.the(TiendaHomePage.CATALOGO, isVisible()).forNoMoreThan(20).seconds()
        );
    }

    /** "$360 *includes tax" -> 360 */
    private static int precioDesdeTexto(String texto) {
        String numero = texto.replaceAll("^\\D*(\\d+).*$", "$1");
        return Integer.parseInt(numero);
    }
}
