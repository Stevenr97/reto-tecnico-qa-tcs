package com.tcs.demoblaze.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

/** Página de detalle de un producto (prod.html). */
public final class ProductoPage {

    public static final Target NOMBRE = Target.the("nombre del producto")
            .locatedBy("//div[@id='tbodyid']//h2[@class='name']");

    public static final Target PRECIO = Target.the("precio del producto")
            .locatedBy("//div[@id='tbodyid']//h3[@class='price-container']");

    public static final Target BOTON_AGREGAR_AL_CARRITO = Target.the("botón Add to cart")
            .locatedBy("//a[normalize-space(text())='Add to cart']");

    private ProductoPage() {
    }
}
