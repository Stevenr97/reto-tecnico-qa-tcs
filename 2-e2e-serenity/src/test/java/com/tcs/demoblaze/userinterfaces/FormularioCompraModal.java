package com.tcs.demoblaze.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/** Modal "Place order" con el formulario de compra. */
public final class FormularioCompraModal {

    public static final Target CAMPO_NOMBRE = Target.the("campo Name").located(By.id("name"));
    public static final Target CAMPO_PAIS = Target.the("campo Country").located(By.id("country"));
    public static final Target CAMPO_CIUDAD = Target.the("campo City").located(By.id("city"));
    public static final Target CAMPO_TARJETA = Target.the("campo Credit card").located(By.id("card"));
    public static final Target CAMPO_MES = Target.the("campo Month").located(By.id("month"));
    public static final Target CAMPO_ANIO = Target.the("campo Year").located(By.id("year"));

    public static final Target BOTON_PURCHASE = Target.the("botón Purchase")
            .locatedBy("//div[@id='orderModal']//button[normalize-space(text())='Purchase']");

    private FormularioCompraModal() {
    }
}
