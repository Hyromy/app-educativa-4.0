package com.example.app.db.models;

import java.io.Serializable;

public class ContenidoModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "contenido";

    public static final String id = "id_contenido";
    public static final String idTema = "id_tema";
    public static final String nivel = "nivel_contenido";
    public static final String nPreguntas = "n_preguntas";

    // ---- valores ----
    public int idValue;
    public int idTemaValue;
    public int nivelValue;
    public int nPreguntasValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idTema + " integer not null, " +
            nivel + " integer, " +
            nPreguntas + " integer, " +
            "foreign key (" + idTema + ") references " + TemaModel.tbName + " (" + TemaModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public ContenidoModel() {
        this.idValue = 0;
        this.idTemaValue = 0;
        this.nivelValue = 0;
        this.nPreguntasValue = 0;
    }

    public ContenidoModel(
            int idTema,
            int nivel,
            int nPreguntas
    ) {
        this.idValue = 0;
        this.idTemaValue = idTema;
        this.nivelValue = nivel;
        this.nPreguntasValue = nPreguntas;
    }

    public ContenidoModel(
            int id,
            int idTema,
            int nivel,
            int nPreguntas
    ) {
        this.idValue = id;
        this.idTemaValue = idTema;
        this.nivelValue = nivel;
        this.nPreguntasValue = nPreguntas;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idTema + "=" + idTemaValue + ", " +
                nivel + "=" + nivelValue + ", " +
                nPreguntas + "=" + nPreguntasValue + "}";
    }
}