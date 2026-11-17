package com.tcs.demoblaze.tasks;

import com.tcs.demoblaze.interactions.ClicYAceptarAlerta;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.tcs.demoblaze.userinterfaces.CarritoPage.BOTON_PLACE_ORDER;
import static com.tcs.demoblaze.userinterfaces.FormularioCompraModal.BOTON_PURCHASE;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

public class IntentarCompraSinDatos implements Task {

    public static IntentarCompraSinDatos enElFormulario() {
        return instrumented(IntentarCompraSinDatos.class);
    }

    @Override
    @Step("{0} intenta finalizar la compra con el formulario vacío")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BOTON_PLACE_ORDER),
                WaitUntil.the(BOTON_PURCHASE, isClickable()).forNoMoreThan(10).seconds(),
                ClicYAceptarAlerta.en(BOTON_PURCHASE)
        );
    }
}
