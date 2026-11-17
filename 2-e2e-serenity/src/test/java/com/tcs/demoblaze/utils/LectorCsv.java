package com.tcs.demoblaze.utils;

import com.tcs.demoblaze.models.DatosCompra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Lee los datos de prueba desde archivos CSV ubicados en src/test/resources. */
public final class LectorCsv {

    private static final String ARCHIVO_COMPRAS = "data/compras.csv";

    private LectorCsv() {
    }

    public static DatosCompra compraConId(String idCompra) {
        return leer(ARCHIVO_COMPRAS).stream()
                .filter(fila -> idCompra.equals(fila.get("idCompra")))
                .findFirst()
                .map(DatosCompra::new)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe la compra con id " + idCompra + " en " + ARCHIVO_COMPRAS));
    }

    public static List<Map<String, String>> leer(String rutaClasspath) {
        InputStream is = LectorCsv.class.getClassLoader().getResourceAsStream(rutaClasspath);
        if (is == null) {
            throw new IllegalArgumentException("No se encontró el archivo " + rutaClasspath);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            List<Map<String, String>> filas = new ArrayList<>();
            String[] cabecera = br.readLine().split(",");
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) {
                    continue;
                }
                String[] valores = linea.split(",", -1);
                Map<String, String> fila = new LinkedHashMap<>();
                for (int i = 0; i < cabecera.length; i++) {
                    fila.put(cabecera[i].trim(), i < valores.length ? valores[i].trim() : "");
                }
                filas.add(fila);
            }
            return filas;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
