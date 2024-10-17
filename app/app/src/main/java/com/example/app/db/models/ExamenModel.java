package com.example.app.db.models;

import java.io.Serializable;

public class ExamenModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "examen_diagnostico";

    public static final String id = "id_examen_diagnostico";
    public static final String titulo = "titulo";
    public static final String nPreguntas = "n_preguntas";

    // ---- valores ----
    public int idValue;
    public String tituloValue;
    public int nPreguntasValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            titulo + " varchar(50), " +
            nPreguntas + " integer not null)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public ExamenModel() {
        this.idValue = 0;
        this.tituloValue = null;
        this.nPreguntasValue = 0;
    }

    public ExamenModel(
            int id,
            String titulo,
            int nPreguntas
    ) {
        this.idValue = id;
        this.tituloValue = titulo;
        this.nPreguntasValue = nPreguntas;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + ": " + idValue + ", " +
                titulo + ": " + tituloValue + ", " +
                nPreguntas + ": " + nPreguntasValue + "}";
    }
}