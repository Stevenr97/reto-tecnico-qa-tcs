package com.tcs.demoblaze.questions;

import com.tcs.demoblaze.userinterfaces.ConfirmacionCompraModal;
import net.serenitybdd.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

@Subject("el mensaje de confirmación de la compra")
public class MensajeDeConfirmacion implements Question<String> {

    public static MensajeDeConfirmacion mostrado() {
        return new MensajeDeConfirmacion();
    }

    @Override
    public String answeredBy(Actor actor) {
        return ConfirmacionCompraModal.TITULO.resolveFor(actor).getText().trim();
    }
}
