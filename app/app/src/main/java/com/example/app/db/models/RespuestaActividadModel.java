package com.example.app.db.models;

import java.io.Serializable;

public class RespuestaActividadModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "respuesta_actividad";

    public static final String id = "id_respuesta_actividad";
    public static final String idPreguntaActividad = "id_pregunta_actividad";
    public static final String idRecurso = "id_recurso";
    public static final String texto = "texto";
    public static final String esCorrecto = "es_correcto";

    // ---- valores ----
    public int idValue;
    public int idPreguntaActividadValue;
    public int idRecursoValue;
    public String textoValue;
    public boolean esCorrectoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idPreguntaActividad + " integer not null, " +
            idRecurso + " integer, " +
            texto + " varchar(50), " +
            esCorrecto + " boolean not null, " +
            "foreign key (" + idPreguntaActividad + ") references " + PreguntaActividadModel.tbName + " (" + PreguntaActividadModel.id + ") on delete cascade on update cascade, " +
            "foreign key (" + idRecurso + ") references " + RecursoModel.tbName + " (" + RecursoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public RespuestaActividadModel() {
        this.idValue = 0;
        this.idPreguntaActividadValue = 0;
        this.idRecursoValue = 0;
        this.textoValue = null;
        this.esCorrectoValue = false;
    }

    public RespuestaActividadModel(
            int idPreguntaActividad,
            int idRecurso,
            String texto,
            boolean esCorrecto
    ) {
        this.idValue = 0;
        this.idPreguntaActividadValue = idPreguntaActividad;
        this.idRecursoValue = idRecurso;
        this.textoValue = texto;
        this.esCorrectoValue = esCorrecto;
    }

    public RespuestaActividadModel(
            int id,
            int idPreguntaActividad,
            int idRecurso,
            String texto,
            boolean esCorrecto
    ) {
        this.idValue = id;
        this.idPreguntaActividadValue = idPreguntaActividad;
        this.idRecursoValue = idRecurso;
        this.textoValue = texto;
        this.esCorrectoValue = esCorrecto;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idPreguntaActividad + "=" + idPreguntaActividadValue + ", " +
                idRecurso + "=" + idRecursoValue + ", " +
                texto + "='" + textoValue + "', " +
                esCorrecto + "=" + esCorrectoValue + "}";
    }
}