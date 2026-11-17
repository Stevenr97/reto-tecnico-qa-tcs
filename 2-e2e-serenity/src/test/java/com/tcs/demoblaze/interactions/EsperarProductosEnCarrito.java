package com.tcs.demoblaze.interactions;

import com.tcs.demoblaze.userinterfaces.CarritoPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * El carrito de Demoblaze se carga de forma asíncrona (una petición por producto) y el total
 * se calcula al final. Esta espera explícita evita validar el carrito a medio cargar.
 */
public class EsperarProductosEnCarrito implements Interaction {

    private final int cantidad;

    public EsperarProductosEnCarrito(int cantidad) {
        this.cantidad = cantidad;
    }

    public static EsperarProductosEnCarrito cantidad(int cantidad) {
        return instrumented(EsperarProductosEnCarrito.class, cantidad);
    }

    @Override
    @Step("{0} espera a que el carrito cargue #cantidad producto(s) y el total")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(d ->
                d.findElements(By.xpath(CarritoPage.XPATH_FILAS)).size() == cantidad
                        && !d.findElement(By.id("totalp")).getText().isBlank());
    }
}
