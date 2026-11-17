package com.tcs.demoblaze.interactions;

import com.tcs.demoblaze.models.Memoria;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Da clic en un elemento que dispara una alerta nativa de JavaScript (window.alert),
 * espera a que aparezca, guarda su texto en la memoria del actor y la acepta.
 *
 * Se hace en una sola interacción a propósito: si Serenity tomara una captura de pantalla
 * mientras la alerta está abierta, el navegador la cerraría (o fallaría la captura).
 */
public class ClicYAceptarAlerta implements Interaction {

    private final Target elemento;

    public ClicYAceptarAlerta(Target elemento) {
        this.elemento = elemento;
    }

    public static ClicYAceptarAlerta en(Target elemento) {
        return instrumented(ClicYAceptarAlerta.class, elemento);
    }

    @Override
    @Step("{0} da clic en #elemento y acepta la alerta del navegador")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        elemento.resolveFor(actor).waitUntilClickable().click();
        Alert alerta = new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.alertIsPresent());
        actor.remember(Memoria.TEXTO_ALERTA, alerta.getText());
        alerta.accept();
    }
}
