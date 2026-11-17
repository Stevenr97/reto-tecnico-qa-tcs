package com.tcs.demoblaze.questions;

import com.tcs.demoblaze.models.Memoria;
import net.serenitybdd.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

@Subject("el texto de la última alerta del navegador")
public class TextoDeLaAlerta implements Question<String> {

    public static TextoDeLaAlerta mostrada() {
        return new TextoDeLaAlerta();
    }

    @Override
    public String answeredBy(Actor actor) {
        return actor.recall(Memoria.TEXTO_ALERTA);
    }
}
