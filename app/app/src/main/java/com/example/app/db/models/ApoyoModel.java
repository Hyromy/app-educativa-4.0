package com.example.app.db.models;

import java.io.Serializable;

public class ApoyoModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "apoyo";

    public static final String id = "id_apoyo";
    public static final String idContenido = "id_contenido";
    public static final String idRecurso = "id_recurso";
    public static final String titulo = "titulo";

    // ---- valores ----
    public int idValue;
    public int idContenidoValue;
    public int idRecursoValue;
    public String tituloValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idContenido + " integer not null, " +
            idRecurso + " integer, " +
            titulo + " varchar(100) not null, " +
            "foreign key (" + idContenido + ") references " + ContenidoModel.tbName + " (" + ContenidoModel.id + ") on delete cascade on update cascade, " +
            "foreign key (" + idRecurso + ") references " + RecursoModel.tbName + " (" + RecursoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public ApoyoModel() {
        this.idValue = 0;
        this.idContenidoValue = 0;
        this.idRecursoValue = 0;
        this.tituloValue = null;
    }

    public ApoyoModel(
            int id,
            int idContenido,
            int idRecurso,
            String titulo
    ) {
        this.idValue = id;
        this.idContenidoValue = idContenido;
        this.idRecursoValue = idRecurso;
        this.tituloValue = titulo;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idContenido + "=" + idContenidoValue + ", " +
                idRecurso + "=" + idRecursoValue + ", " +
                titulo + "='" + tituloValue + "'}";
    }
}