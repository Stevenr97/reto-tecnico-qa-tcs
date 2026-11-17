package com.tcs.demoblaze.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Página del carrito de compras (cart.html). */
public final class CarritoPage {

    public static final String XPATH_FILAS = "//tbody[@id='tbodyid']/tr";

    public static final Target FILAS_PRODUCTOS = Target.the("filas de productos del carrito")
            .locatedBy(XPATH_FILAS);

    public static final Target NOMBRES_PRODUCTOS = Target.the("nombres de los productos del carrito")
            .locatedBy(XPATH_FILAS + "/td[2]");

    public static final Target TOTAL = Target.the("total del carrito")
            .located(By.id("totalp"));

    public static final Target BOTON_PLACE_ORDER = Target.the("botón Place Order")
            .locatedBy("//button[normalize-space(text())='Place Order']");

    private CarritoPage() {
    }
}
