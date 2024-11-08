package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.utils.crud.PreguntaExamen;

public class PreguntasExamen {
    private Context context;
    private PreguntaExamen crud;
    private PreguntaExamenModel[] preguntas;

    public PreguntasExamen(Context context) {
        this.context = context;
        this.crud = new PreguntaExamen(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando preguntas de examen...");
        crud.open();

        String sigFigura = "Determina la figura que completa la sucesión";
        String sigAnalogia = "Determina la figura que completa la analogía";
        String pendiente = "[enunciado]";
        preguntas = new PreguntaExamenModel[] {
                new PreguntaExamenModel(1, 0, sigFigura),
                new PreguntaExamenModel(1, 0, sigFigura),
                new PreguntaExamenModel(1, 0, sigFigura),
                new PreguntaExamenModel(1, 0, sigFigura),
                new PreguntaExamenModel(1, 0, sigFigura),
                new PreguntaExamenModel(1, 0, sigFigura),
                new PreguntaExamenModel(1, 0, sigFigura),
                new PreguntaExamenModel(1, 0, "Determina las letras que completan la suseción.\nQ, A, Z, W, S, ?, E, ?, ?, R"),
                new PreguntaExamenModel(1, 0, "Determina la combinación de caracteres que puede completar la sucesión.\nVW, IXW, IXS, IXS, ?, IIA, IID"),
                new PreguntaExamenModel(1, 0, "Determina el número que completa la sucesión.\n1, 2, 5, 12, 30, 75, ?"),
                new PreguntaExamenModel(1, 0, "De acuerdo a la siguiente declaración ¿Cúal es la sentencia que cumple con el planteamiento\n3 = 60\n4 = 90\n5 = 108\n6 = 120\n7 = ?"),
                new PreguntaExamenModel(1, 0, "¿Cuál es la sentencia que completa correctamente la siguiente declaración?\n|    -|-    )_    ->    -)\n°|    .    S    ->    A\nb    }{    ‘/    ->    A )_\nB    °/.    |    ->    ?"),
                new PreguntaExamenModel(1, 0, "Determina el número que completa correctamente la sucesión.\n0, 1, 1, 2, 3, 5, 8, ?"),
                new PreguntaExamenModel(1, 0, "Determina el número que completa la sucesión.\n0, 2, 6, 14, 30, ?"),
                new PreguntaExamenModel(1, 0, "Determina la respuesta correcta al siguiente planteamiento.\n14892 = 3\n41762 = 1\n29408 = 4\n24715 = 0\n46298 = ?"),

                new PreguntaExamenModel(1, 0, sigAnalogia),
                new PreguntaExamenModel(1, 0, sigAnalogia),
                new PreguntaExamenModel(1, 0, sigAnalogia),
                new PreguntaExamenModel(1, 0, sigAnalogia),
                new PreguntaExamenModel(1, 0, sigAnalogia),
                new PreguntaExamenModel(1, 0, sigAnalogia),
                new PreguntaExamenModel(1, 0, sigAnalogia),
                new PreguntaExamenModel(1, 0, "Luz es a ver, como sonido es a..."),
                new PreguntaExamenModel(1, 0, "Pulsera es a accesorio, así como bota es a..."),
                new PreguntaExamenModel(1, 0, "Ovíparo es a huevo, así como vivíparo es a..."),
                new PreguntaExamenModel(1, 0, "Dos es a bilingüe, así como siete es a..."),
                new PreguntaExamenModel(1, 0, "Caudal es a flujo, así como ohm es a..."),
                new PreguntaExamenModel(1, 0, "Circulo es a pi, así como Pentágono es a..."),
                new PreguntaExamenModel(1, 0, "Bin es a 2, así Hex es a..."),
                new PreguntaExamenModel(1, 0, "Tiempo es a vida, así como Viento es a..."),

                new PreguntaExamenModel(1, 0, "Se tiene el siguiente plegable de un dado, ¿Cuál de las siguientes figuras corresponde al plano original?"),
                new PreguntaExamenModel(1, 0, "Una hormiga azul se encuentra en un laberinto y desea salir, la hormiga está mirando en dirección norte y solo puede ir a la izquierda o derecha solo cuando tiene una pared justo delante de ella, determine la ruta que debe de seguir para salir (interprétese “D” como derecha e “I” como izquierda)"),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente),
                new PreguntaExamenModel(1, 0, pendiente)
        };

        createPreguntas(preguntas);

        crud.close();
        System.out.println("Preguntas de examen creadas...");
    }

    private void createPreguntas(PreguntaExamenModel[] preguntas) {
        PreguntaExamenModel[] preguntasExistentes = crud.readAll();
        if (preguntasExistentes.length > 0) {
            return;
        }

        for (PreguntaExamenModel pregunta: preguntas) {
            for (PreguntaExamenModel preguntaExistente: preguntasExistentes) {
                if (preguntaExistente.idValue == pregunta.idValue) {
                    break;
                }
            }

            System.out.println("Insertando pregunta: " + pregunta.getData());
            try {
                crud.insert(pregunta);
            } catch (Exception e) {
                System.out.println("Error al insertar pregunta: " + e.getMessage());
            }
        }
    }
}