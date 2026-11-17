package com.tcs.demoblaze.tasks;

import com.tcs.demoblaze.interactions.EsperarProductosEnCarrito;
import com.tcs.demoblaze.userinterfaces.TiendaHomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import java.util.Map;

import static com.tcs.demoblaze.models.Memoria.PRECIOS_PRODUCTOS;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class VerCarrito implements Task {

    public static VerCarrito deCompras() {
        return instrumented(VerCarrito.class);
    }

    @Override
    @Step("{0} abre el carrito de compras")
    public <T extends Actor> void performAs(T actor) {
        Map<String, Integer> precios = actor.recall(PRECIOS_PRODUCTOS);
        actor.attemptsTo(
                Click.on(TiendaHomePage.MENU_CARRITO),
                EsperarProductosEnCarrito.cantidad(precios.size())
        );
    }
}
