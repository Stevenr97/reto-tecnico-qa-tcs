package com.tcs.demoblaze.stepdefinitions;

import com.tcs.demoblaze.models.DatosCompra;
import com.tcs.demoblaze.questions.DetalleDelComprobante;
import com.tcs.demoblaze.questions.MensajeDeConfirmacion;
import com.tcs.demoblaze.questions.ProductosEnCarrito;
import com.tcs.demoblaze.questions.TextoDeLaAlerta;
import com.tcs.demoblaze.questions.TotalDelCarrito;
import com.tcs.demoblaze.tasks.AbrirTienda;
import com.tcs.demoblaze.tasks.AgregarProducto;
import com.tcs.demoblaze.tasks.CompletarFormularioCompra;
import com.tcs.demoblaze.tasks.FinalizarCompra;
import com.tcs.demoblaze.tasks.IntentarCompraSinDatos;
import com.tcs.demoblaze.tasks.VerCarrito;
import com.tcs.demoblaze.utils.LectorCsv;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;

import java.util.Map;

import static com.tcs.demoblaze.models.Memoria.COMPRA;
import static com.tcs.demoblaze.models.Memoria.PRECIOS_PRODUCTOS;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class CompraStepDefinitions {

    private static final String CLIENTE = "Cliente";

    @Dado("que el cliente abre la tienda Demoblaze")
    public void queElClienteAbreLaTiendaDemoblaze() {
        theActorCalled(CLIENTE).wasAbleTo(AbrirTienda.demoblaze());
    }

    @Cuando("agrega al carrito los productos de la compra {string}")
    public void agregaAlCarritoLosProductosDeLaCompra(String idCompra) {
        DatosCompra compra = LectorCsv.compraConId(idCompra);
        Actor cliente = theActorInTheSpotlight();
        cliente.remember(COMPRA, compra);
        cliente.attemptsTo(
                AgregarProducto.alCarrito(compra.getProducto1()),
                AgregarProducto.alCarrito(compra.getProducto2())
        );
    }

    @Cuando("agrega al carrito el producto {string}")
    public void agregaAlCarritoElProducto(String producto) {
        theActorInTheSpotlight().attemptsTo(AgregarProducto.alCarrito(producto));
    }

    @Cuando("visualiza el carrito de compras")
    public void visualizaElCarritoDeCompras() {
        theActorInTheSpotlight().attemptsTo(VerCarrito.deCompras());
    }

    @Entonces("el carrito muestra los productos agregados con el total correcto")
    public void elCarritoMuestraLosProductosAgregadosConElTotalCorrecto() {
        Actor cliente = theActorInTheSpotlight();
        Map<String, Integer> precios = cliente.recall(PRECIOS_PRODUCTOS);
        int totalEsperado = precios.values().stream().mapToInt(Integer::intValue).sum();

        cliente.should(
                seeThat(ProductosEnCarrito.listados(), containsInAnyOrder(precios.keySet().toArray(new String[0]))),
                seeThat(TotalDelCarrito.mostrado(), equalTo(totalEsperado))
        );
    }

    @Cuando("completa el formulario de compra con sus datos")
    public void completaElFormularioDeCompraConSusDatos() {
        Actor cliente = theActorInTheSpotlight();
        DatosCompra compra = cliente.recall(COMPRA);
        cliente.attemptsTo(CompletarFormularioCompra.con(compra));
    }

    @Cuando("finaliza la compra")
    public void finalizaLaCompra() {
        theActorInTheSpotlight().attemptsTo(FinalizarCompra.confirmandoLaOrden());
    }

    @Entonces("visualiza el mensaje de confirmación {string}")
    public void visualizaElMensajeDeConfirmacion(String mensajeEsperado) {
        theActorInTheSpotlight().should(seeThat(MensajeDeConfirmacion.mostrado(), equalTo(mensajeEsperado)));
    }

    @Entonces("el comprobante muestra el monto total, la tarjeta y el nombre del cliente")
    public void elComprobanteMuestraElMontoLaTarjetaYElNombre() {
        Actor cliente = theActorInTheSpotlight();
        DatosCompra compra = cliente.recall(COMPRA);
        Map<String, Integer> precios = cliente.recall(PRECIOS_PRODUCTOS);
        int total = precios.values().stream().mapToInt(Integer::intValue).sum();

        cliente.should(seeThat(DetalleDelComprobante.mostrado(), allOf(
                containsString("Amount: " + total + " USD"),
                containsString("Card Number: " + compra.getTarjeta()),
                containsString("Name: " + compra.getNombre())
        )));
    }

    @Cuando("intenta finalizar la compra sin llenar el formulario")
    public void intentaFinalizarLaCompraSinLlenarElFormulario() {
        theActorInTheSpotlight().attemptsTo(IntentarCompraSinDatos.enElFormulario());
    }

    @Entonces("el sistema muestra la alerta {string}")
    public void elSistemaMuestraLaAlerta(String mensajeEsperado) {
        theActorInTheSpotlight().should(seeThat(TextoDeLaAlerta.mostrada(), equalTo(mensajeEsperado)));
    }
}
