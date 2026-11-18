package com.tcs.demoblaze.questions;

import com.tcs.demoblaze.userinterfaces.CarritoPage;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

@Subject("el total mostrado en el carrito")
public class TotalDelCarrito implements Question<Integer> {

    public static TotalDelCarrito mostrado() {
        return new TotalDelCarrito();
    }

    @Override
    public Integer answeredBy(Actor actor) {
        return Integer.parseInt(CarritoPage.TOTAL.resolveFor(actor).getText().trim());
    }
}
