package com.tcs.demoblaze.questions;

import com.tcs.demoblaze.userinterfaces.CarritoPage;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.List;
import java.util.stream.Collectors;

@Subject("los productos listados en el carrito")
public class ProductosEnCarrito implements Question<List<String>> {

    public static ProductosEnCarrito listados() {
        return new ProductosEnCarrito();
    }

    @Override
    public List<String> answeredBy(Actor actor) {
        return CarritoPage.NOMBRES_PRODUCTOS.resolveAllFor(actor).stream()
                .map(WebElementFacade::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
