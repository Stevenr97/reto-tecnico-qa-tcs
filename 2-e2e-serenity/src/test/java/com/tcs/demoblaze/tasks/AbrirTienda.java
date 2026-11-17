package com.tcs.demoblaze.tasks;

import com.tcs.demoblaze.userinterfaces.TiendaHomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.LinkedHashMap;

import static com.tcs.demoblaze.models.Memoria.PRECIOS_PRODUCTOS;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class AbrirTienda implements Task {

    public static AbrirTienda demoblaze() {
        return instrumented(AbrirTienda.class);
    }

    @Override
    @Step("{0} abre la tienda online Demoblaze")
    public <T extends Actor> void performAs(T actor) {
        actor.remember(PRECIOS_PRODUCTOS, new LinkedHashMap<String, Integer>());
        actor.attemptsTo(
                Open.browserOn().the(TiendaHomePage.class),
                WaitUntil.the(TiendaHomePage.CATALOGO, isVisible()).forNoMoreThan(20).seconds()
        );
    }
}
