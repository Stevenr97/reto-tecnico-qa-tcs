package com.tcs.demoblaze.userinterfaces;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Página principal (catálogo) de Demoblaze.
 * Patrón Screenplay: las clases de "user interface" solo describen DÓNDE están los elementos (Targets),
 * no qué se hace con ellos.
 */
@DefaultUrl("https://www.demoblaze.com/index.html")
public class TiendaHomePage extends PageObject {

    public static final Target CATALOGO = Target.the("catálogo de productos")
            .located(By.id("tbodyid"));

    /** Target dinámico: se usa como PRODUCTO.of("Samsung galaxy s6") */
    public static final Target PRODUCTO = Target.the("producto {0} del catálogo")
            .locatedBy("//a[@class='hrefch' and normalize-space(text())='{0}']");

    public static final Target MENU_HOME = Target.the("opción Home del menú")
            .locatedBy("//a[@class='nav-link' and contains(normalize-space(.),'Home')]");

    public static final Target MENU_CARRITO = Target.the("opción Cart del menú")
            .located(By.id("cartur"));
}
