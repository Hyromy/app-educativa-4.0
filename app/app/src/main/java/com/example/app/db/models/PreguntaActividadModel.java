package com.example.app.db.models;

import java.io.Serializable;

public class PreguntaActividadModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "pregunta_actividad";

    public static final String id = "id_pregunta_actividad";
    public static final String idContenido = "id_contenido";
    public static final String idRecurso = "id_recurso";
    public static final String texto = "texto";

    // ---- valores ----
    public int idValue;
    public int idContenidoValue;
    public int idRecursoValue;
    public String textoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idContenido + " integer not null, " +
            idRecurso + " integer, " +
            texto + " varchar(250), " +
            "foreign key (" + idContenido + ") references " + ContenidoModel.tbName + " (" + ContenidoModel.id + ") on delete cascade on update cascade, " +
            "foreign key (" + idRecurso + ") references " + RecursoModel.tbName + " (" + RecursoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public PreguntaActividadModel() {
        this.idValue = 0;
        this.idContenidoValue = 0;
        this.idRecursoValue = 0;
        this.textoValue = null;
    }

    public PreguntaActividadModel(
            int id,
            int idContenido,
            int idRecurso,
            String texto
    ) {
        this.idValue = id;
        this.idContenidoValue = idContenido;
        this.idRecursoValue = idRecurso;
        this.textoValue = texto;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idContenido + "=" + idContenidoValue + ", " +
                idRecurso + "=" + idRecursoValue + ", " +
                texto + "='" + textoValue + "'}";
    }
}