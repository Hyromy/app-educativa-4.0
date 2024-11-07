package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.PreguntaActividadModel;
import com.example.app.db.utils.crud.PreguntaActividad;

public class PreguntasActividad {
    private Context context;
    private PreguntaActividad crud;
    private PreguntaActividadModel[] preguntas;

    public PreguntasActividad(Context context) {
        this.context = context;
        this.crud = new PreguntaActividad(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando preguntas de actividad...");
        crud.open();

        preguntas = new PreguntaActividadModel[] {
                new PreguntaActividadModel(1, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(2, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(3, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(4, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(5, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(6, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(7, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(8, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(9, 1, 0, "[pregunta pendiente]"),
                new PreguntaActividadModel(10, 1, 0, "[pregunta pendiente]"),
        };

        createPregunta(preguntas);

        crud.close();
        System.out.println("Preguntas de actividad creadas...");
    }

    private void createPregunta(PreguntaActividadModel[] preguntas) {
        PreguntaActividadModel[] preguntasExistentes = crud.readAll();
        if (preguntasExistentes.length > 0) {
            return;
        }

        for (PreguntaActividadModel pregunta : preguntas) {
            for (PreguntaActividadModel preguntaExistente : preguntasExistentes) {
                if (pregunta.idValue == preguntaExistente.idValue) {
                    break;
                }
            }

            System.out.println("Insertando pregunta: " + pregunta.getData());
            try {
                crud.insert(pregunta);
            } catch (Exception e) {
                System.out.println("Error al insertar pregunta: " + pregunta.getData());
            }
        }
    }
}