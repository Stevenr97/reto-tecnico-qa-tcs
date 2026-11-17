package com.tcs.demoblaze.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.tcs.demoblaze.userinterfaces.ConfirmacionCompraModal.TITULO;
import static com.tcs.demoblaze.userinterfaces.FormularioCompraModal.BOTON_PURCHASE;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class FinalizarCompra implements Task {

    public static FinalizarCompra confirmandoLaOrden() {
        return instrumented(FinalizarCompra.class);
    }

    @Override
    @Step("{0} finaliza la compra con el botón Purchase")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BOTON_PURCHASE),
                WaitUntil.the(TITULO, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
