package com.example.app.db.models;

import java.io.Serializable;

public class TemaModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "tema";

    public static final String id = "id_tema";
    public static final String titulo = "titulo";
    public static final String descripcion = "descripcion";

    // ---- valores ----
    public int idValue;
    public String tituloValue;
    public String descripcionValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            titulo + " varchar(100) unique not null, " +
            descripcion + " varchar(250) not null)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public TemaModel() {
        this.idValue = 0;
        this.tituloValue = null;
        this.descripcionValue = null;
    }

    public TemaModel(
            String titulo,
            String descripcion
    ) {
        this.idValue = 0;
        this.tituloValue = titulo;
        this.descripcionValue = descripcion;
    }

    public TemaModel(
            int id,
            String titulo,
            String descripcion
    ) {
        this.idValue = id;
        this.tituloValue = titulo;
        this.descripcionValue = descripcion;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "= " + idValue + ", " +
                titulo + "='" + tituloValue + "', " +
                descripcion + "='" + descripcionValue + "'}";
    }
}