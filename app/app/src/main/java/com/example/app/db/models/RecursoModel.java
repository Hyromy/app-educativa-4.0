package com.example.app.db.models;

import java.io.Serializable;

public class RecursoModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "recurso";

    public static final String id = "id_recurso";
    public static final String nombre = "nombre";
    public static final String extension = "extension";
    public static final String tipo = "tipo";

    // ---- valores ----
    public int idValue;
    public String nombreValue;
    public String extensionValue;
    public String tipoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            nombre + " varchar(32), " +
            extension + " varchar(5), " +
            tipo + " varchar(24)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public RecursoModel() {
        this.idValue = 0;
        this.nombreValue = null;
        this.extensionValue = null;
        this.tipoValue = null;
    }

    public RecursoModel(
            int id,
            String nombre,
            String extension,
            String tipo
    ) {
        this.idValue = id;
        this.nombreValue = nombre;
        this.extensionValue = extension;
        this.tipoValue = tipo;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "= " + idValue + ", " +
                nombre + "='" + nombreValue + "', " +
                extension + "='" + extensionValue + "', " +
                tipo + "='" + tipoValue + "'}";
    }
}