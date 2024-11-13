package com.example.app.ui.debug.view_db.view_table;

import android.content.Context;

import com.example.app.db.utils.crud.*;

public class ViewTableModel {
    public AbstractCRUD getCRUD(String table, Context context) {
        switch (table) {
            case "usuario":
                return new Usuario(context);

            case "tema":
                return new Tema(context);

            case "recurso":
                return null;

            case "contenido":
                return new Contenido(context);

            case "examen_diagnostico":
                return new ExamenDiagnostico(context);

            case "resultado_examen":
                return new ResultadoExamen(context);

            case "completa_contenido":
                return new CompletaContenido(context);

            case "pregunta_examen":
                return new PreguntaExamen(context);

            case "pregunta_actividad":
                return new PreguntaActividad(context);

            case "apoyo":
                return null;

            case "respuesta_examen":
                return new RespuestaExamen(context);

            case "respuesta_actividad":
                return new RespuestaActividad(context);

            case "parrafo":
                return null;

            default:
                return null;
        }
    }
}