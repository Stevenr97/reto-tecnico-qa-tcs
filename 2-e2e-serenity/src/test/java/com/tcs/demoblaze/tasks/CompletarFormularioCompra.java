package com.tcs.demoblaze.tasks;

import com.tcs.demoblaze.models.DatosCompra;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static com.tcs.demoblaze.userinterfaces.CarritoPage.BOTON_PLACE_ORDER;
import static com.tcs.demoblaze.userinterfaces.FormularioCompraModal.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class CompletarFormularioCompra implements Task {

    private final DatosCompra datos;

    public CompletarFormularioCompra(DatosCompra datos) {
        this.datos = datos;
    }

    public static CompletarFormularioCompra con(DatosCompra datos) {
        return instrumented(CompletarFormularioCompra.class, datos);
    }

    @Override
    @Step("{0} completa el formulario de compra con los datos de #datos")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BOTON_PLACE_ORDER),
                WaitUntil.the(CAMPO_NOMBRE, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(datos.getNombre()).into(CAMPO_NOMBRE),
                Enter.theValue(datos.getPais()).into(CAMPO_PAIS),
                Enter.theValue(datos.getCiudad()).into(CAMPO_CIUDAD),
                Enter.theValue(datos.getTarjeta()).into(CAMPO_TARJETA),
                Enter.theValue(datos.getMes()).into(CAMPO_MES),
                Enter.theValue(datos.getAnio()).into(CAMPO_ANIO)
        );
    }
}
