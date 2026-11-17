package com.tcs.demoblaze.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/** Ventana de confirmación (sweet-alert) que aparece al finalizar la compra. */
public final class ConfirmacionCompraModal {

    public static final Target TITULO = Target.the("título de la confirmación de compra")
            .locatedBy("//div[contains(@class,'sweet-alert')]/h2");

    public static final Target DETALLE = Target.the("detalle del comprobante de compra")
            .locatedBy("//div[contains(@class,'sweet-alert')]/p[contains(@class,'lead')]");

    public static final Target BOTON_OK = Target.the("botón OK de la confirmación")
            .locatedBy("//div[contains(@class,'sweet-alert')]//button[contains(@class,'confirm')]");

    private ConfirmacionCompraModal() {
    }
}
