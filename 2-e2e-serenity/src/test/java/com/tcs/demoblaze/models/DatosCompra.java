package com.tcs.demoblaze.models;

import java.util.Map;

/** Modelo con los datos de una compra: productos a agregar y datos del cliente para el formulario. */
public class DatosCompra {

    private final String idCompra;
    private final String producto1;
    private final String producto2;
    private final String nombre;
    private final String pais;
    private final String ciudad;
    private final String tarjeta;
    private final String mes;
    private final String anio;

    public DatosCompra(Map<String, String> fila) {
        this.idCompra = fila.get("idCompra");
        this.producto1 = fila.get("producto1");
        this.producto2 = fila.get("producto2");
        this.nombre = fila.get("nombre");
        this.pais = fila.get("pais");
        this.ciudad = fila.get("ciudad");
        this.tarjeta = fila.get("tarjeta");
        this.mes = fila.get("mes");
        this.anio = fila.get("anio");
    }

    public String getIdCompra() { return idCompra; }
    public String getProducto1() { return producto1; }
    public String getProducto2() { return producto2; }
    public String getNombre() { return nombre; }
    public String getPais() { return pais; }
    public String getCiudad() { return ciudad; }
    public String getTarjeta() { return tarjeta; }
    public String getMes() { return mes; }
    public String getAnio() { return anio; }

    @Override
    public String toString() {
        return "compra " + idCompra + " (" + producto1 + " + " + producto2 + ") de " + nombre;
    }
}
