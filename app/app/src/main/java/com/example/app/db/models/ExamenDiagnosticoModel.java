package com.example.app.db.models;

import java.io.Serializable;

public class ExamenDiagnosticoModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "examen_diagnostico";

    public static final String id = "id_examen_diagnostico";
    public static final String idTema = "id_tema";
    public static final String titulo = "titulo";
    public static final String nPreguntas = "n_preguntas";
    public static final String nivelMaximo = "nivel_maximo";

    // ---- valores ----
    public int idValue;
    public int idTemaValue;
    public String tituloValue;
    public int nPreguntasValue;
    public int nivelMaximoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idTema + " integer, " +
            titulo + " varchar(100), " +
            nPreguntas + " integer, " +
            nivelMaximo + " integer, " +
            "foreign key (" + idTema + ") references " + TemaModel.tbName + "(" + TemaModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public ExamenDiagnosticoModel() {
        this.idValue = 0;
        this.idTemaValue = 0;
        this.tituloValue = null;
        this.nPreguntasValue = 0;
        this.nivelMaximoValue = 0;
    }

    public ExamenDiagnosticoModel(
            int id,
            int idTema,
            String titulo,
            int nPreguntas,
            int nivelMaximo
    ) {
        this.idValue = id;
        this.idTemaValue = idTema;
        this.tituloValue = titulo;
        this.nPreguntasValue = nPreguntas;
        this.nivelMaximoValue = nivelMaximo;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + "{" +
                id + "=" + idValue + ", " +
                idTema + "=" + idTemaValue + ", " +
                titulo + "='" + tituloValue + "', " +
                nPreguntas + "=" + nPreguntasValue + ", " +
                nivelMaximo + "=" + nivelMaximoValue + "}";
    }
}