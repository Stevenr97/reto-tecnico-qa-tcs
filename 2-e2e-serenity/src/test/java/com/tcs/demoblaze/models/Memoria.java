package com.tcs.demoblaze.models;

/** Claves que usa el actor para recordar información entre pasos (actor.remember / actor.recall). */
public final class Memoria {

    public static final String COMPRA = "COMPRA";
    /** Map<String, Integer> con nombre de producto -> precio capturado en la página del producto */
    public static final String PRECIOS_PRODUCTOS = "PRECIOS_PRODUCTOS";
    public static final String TEXTO_ALERTA = "TEXTO_ALERTA";

    private Memoria() {
    }
}
