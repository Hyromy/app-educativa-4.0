package com.example.app.db.models;

import java.io.Serializable;

public class ParrafoModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "parrafo";

    public static final String id = "id_parrafo";
    public static final String idApoyo = "id_apoyo";
    public static final String texto = "texto";

    // ---- valores ----
    public int idValue;
    public int idApoyoValue;
    public String textoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idApoyo + " integer not null, " +
            texto + " varchar(250) not null, " +
            "foreign key (" + idApoyo + ") references " + ApoyoModel.tbName + " (" + ApoyoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public ParrafoModel() {
        this.idValue = 0;
        this.idApoyoValue = 0;
        this.textoValue = null;
    }

    public ParrafoModel(
            int id,
            int idApoyo,
            String texto
    ) {
        this.idValue = id;
        this.idApoyoValue = idApoyo;
        this.textoValue = texto;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idApoyo + "=" + idApoyoValue + ", " +
                texto + "='" + textoValue + "'}";
    }
}