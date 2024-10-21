package com.example.app.db.models;

import java.io.Serializable;

public class PreguntaExamenModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "pregunta_examen";

    public static final String id = "id_pregunta_examen";
    public static final String idExamen = "id_examen_diagostico";
    public static final String idRecurso = "id_recurso";
    public static final String texto = "texto";

    // ---- valores ----
    public int idValue;
    public int idExamenValue;
    public int idRecursoValue;
    public String textoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idExamen + " integer not null, " +
            idRecurso + " integer, " +
            texto + " varchar(250), " +
            "foreign key (" + idExamen + ") references " + ExamenDiagnosticoModel.tbName + " (" + ExamenDiagnosticoModel.id + ") on delete cascade on update cascade, " +
            "foreign key (" + idRecurso + ") references " + RecursoModel.tbName + " (" + RecursoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public PreguntaExamenModel() {
        this.idValue = 0;
        this.idExamenValue = 0;
        this.idRecursoValue = 0;
        this.textoValue = null;
    }

    public PreguntaExamenModel(
            int id,
            int idExamen,
            int idRecurso,
            String texto
    ) {
        this.idValue = id;
        this.idExamenValue = idExamen;
        this.idRecursoValue = idRecurso;
        this.textoValue = texto;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idExamen + "=" + idExamenValue + ", " +
                idRecurso + "=" + idRecursoValue + ", " +
                texto + "='" + textoValue + "'}";
    }
}