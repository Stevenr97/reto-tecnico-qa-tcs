package com.tcs.demoblaze.questions;

import com.tcs.demoblaze.userinterfaces.ConfirmacionCompraModal;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

/** Texto del comprobante, por ejemplo: "Id: 123\nAmount: 1180 USD\nCard Number: 4111...\nName: Juan\nDate: ..." */
@Subject("el detalle del comprobante de compra")
public class DetalleDelComprobante implements Question<String> {

    public static DetalleDelComprobante mostrado() {
        return new DetalleDelComprobante();
    }

    @Override
    public String answeredBy(Actor actor) {
        return ConfirmacionCompraModal.DETALLE.resolveFor(actor).getText();
    }
}
