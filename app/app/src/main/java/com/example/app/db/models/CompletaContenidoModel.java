package com.example.app.db.models;

import java.io.Serializable;

public class CompletaContenidoModel  implements Serializable {
    // ---- atributos ----
    public static final String tbName = "completa_contenido";

    public static final String id = "id_completa_contenido";
    public static final String idUsuario = "id_usuario";
    public static final String idContenido = "id_contenido";

    // ---- valores ----
    public int idValue;
    public int idUsuarioValue;
    public int idContenidoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idUsuario + " integer not null, " +
            idContenido + " integer not null, " +
            "foreign key (" + idUsuario + ") references " + UsuarioModel.tbName + " (" + UsuarioModel.id + ") on delete cascade on update cascade, " +
            "foreign key (" + idContenido + ") references " + ContenidoModel.tbName + " (" + ContenidoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public CompletaContenidoModel() {
        this.idValue = 0;
        this.idUsuarioValue = 0;
        this.idContenidoValue = 0;
    }

    public CompletaContenidoModel(
            int id,
            int idUsuario,
            int idContenido
    ) {
        this.idValue = id;
        this.idUsuarioValue = idUsuario;
        this.idContenidoValue = idContenido;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idUsuario + "=" + idUsuarioValue + ", " +
                idContenido + "=" + idContenidoValue + "}";
    }
}