package com.tcs.demoblaze.stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class Hooks {

    /** Prepara el "escenario" de Screenplay: cada actor recibe la habilidad de navegar con el WebDriver de Serenity. */
    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }
}
