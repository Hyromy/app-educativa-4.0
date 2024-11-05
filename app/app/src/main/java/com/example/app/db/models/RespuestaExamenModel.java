package com.example.app.db.models;

import java.io.Serializable;

public class RespuestaExamenModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "respuesta_examen";

    public static final String id = "id_respuesta_examen";
    public static final String idPreguntaExamen = "id_pregunta_examen";
    public static final String idRecurso = "id_recurso";
    public static final String texto = "texto";
    public static final String puntaje = "puntaje";

    // ---- valores ----
    public int idValue;
    public int idPreguntaExamenValue;
    public int idRecursoValue;
    public String textoValue;
    public int puntajeValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idPreguntaExamen + " integer not null, " +
            idRecurso + " integer, " +
            texto + " varchar(50), " +
            puntaje + " integer not null, " +
            "foreign key (" + idPreguntaExamen + ") references " + PreguntaExamenModel.tbName + " (" + PreguntaExamenModel.id + ") on delete cascade on update cascade, " +
            "foreign key (" + idRecurso + ") references " + RecursoModel.tbName + " (" + RecursoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public RespuestaExamenModel() {
        this.idValue = 0;
        this.idPreguntaExamenValue = 0;
        this.idRecursoValue = 0;
        this.textoValue = null;
        this.puntajeValue = 0;
    }

    public RespuestaExamenModel(
            int idPreguntaExamen,
            int idRecurso,
            String texto,
            int puntaje
    ) {
        this.idValue = 0;
        this.idPreguntaExamenValue = idPreguntaExamen;
        this.idRecursoValue = idRecurso;
        this.textoValue = texto;
        this.puntajeValue = puntaje;
    }

    public RespuestaExamenModel(
            int id,
            int idPreguntaExamen,
            int idRecurso,
            String texto,
            int puntaje
    ) {
        this.idValue = id;
        this.idPreguntaExamenValue = idPreguntaExamen;
        this.idRecursoValue = idRecurso;
        this.textoValue = texto;
        this.puntajeValue = puntaje;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idPreguntaExamen + "=" + idPreguntaExamenValue + ", " +
                idRecurso + "=" + idRecursoValue + ", " +
                texto + "='" + textoValue + "', " +
                puntaje + "=" + puntajeValue + "}";
    }
}