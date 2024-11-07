package com.example.app.preload_data.info;

import android.content.Context;

import com.example.app.db.models.RespuestaActividadModel;
import com.example.app.db.utils.crud.RespuestaActividad;

public class RespuestasActividad {
    private Context context;
    private RespuestaActividad crud;
    private RespuestaActividadModel[] respuestas;

    public RespuestasActividad(Context context) {
        this.context = context;
        this.crud = new RespuestaActividad(this.context);

        run();
    }

    public void run() {
        System.out.println("Creando respuestas de actividad...");
        crud.open();

        String p = "[Pendiente]";
        respuestas = new RespuestaActividadModel[] {
                new RespuestaActividadModel(1, 1, 0, p, true),
                new RespuestaActividadModel(2, 1, 0, p, false),
                new RespuestaActividadModel(3, 2, 0, p, true),
                new RespuestaActividadModel(4, 2, 0, p, false),
                new RespuestaActividadModel(5, 3, 0, p, true),
                new RespuestaActividadModel(6, 3, 0, p, false),
                new RespuestaActividadModel(7, 4, 0, p, true),
                new RespuestaActividadModel(8, 4, 0, p, false),
                new RespuestaActividadModel(9, 5, 0, p, true),
                new RespuestaActividadModel(10, 5, 0, p, false),
                new RespuestaActividadModel(11, 6, 0, p, true),
                new RespuestaActividadModel(12, 6, 0, p, false),
                new RespuestaActividadModel(13, 7, 0, p, true),
                new RespuestaActividadModel(14, 7, 0, p, false),
                new RespuestaActividadModel(15, 8, 0, p, true),
                new RespuestaActividadModel(16, 8, 0, p, false),
                new RespuestaActividadModel(17, 9, 0, p, true),
                new RespuestaActividadModel(18, 9, 0, p, false),
                new RespuestaActividadModel(19, 10, 0, p, true),
                new RespuestaActividadModel(20, 10, 0, p, false),
        };

        createRespuestas(respuestas);

        crud.close();
        System.out.println("Respuestas de actividad creadas.");
    }

    private void createRespuestas(RespuestaActividadModel[] respuestas) {
        RespuestaActividadModel[] respuestasExistentes = crud.readAll();
        if (respuestasExistentes.length > 0) {
            return;
        }

        for (RespuestaActividadModel respuesta : respuestas) {
            for (RespuestaActividadModel respuestaExistente : respuestasExistentes) {
                if (respuesta.equals(respuestaExistente)) {
                    break;
                }
            }

            System.out.println("Insertando respuesta de actividad...");
            try {
                crud.insert(respuesta);
            } catch (Exception e) {
                System.out.println("Error al insertar respuesta de actividad: " + e.getMessage());
            }
        }
    }
}