package com.example.app.db.models;

import java.io.Serializable;

public class ResultadoExamenModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "resultado_examen";

    public static final String id = "id_resultado_examen";
    public static final String idUsuario = "id_usuario";
    public static final String idExamen = "id_examen_diagostico";
    public static final String puntajeObtenido = "puntaje_obtenido";
    public static final String nivelObtenido = "nivel_obtenido";

    // ---- valores ----
    public int idValue;
    public int idUsuarioValue;
    public int idExamenValue;
    public int puntajeObtenidoValue;
    public int nivelObtenidoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            idUsuario + " integer not null, " +
            idExamen + " integer not null, " +
            puntajeObtenido + " integer, " +
            nivelObtenido + " integer, " +
            "foreign key (" + idUsuario + ") references " + UsuarioModel.tbName + " (" + UsuarioModel.id + ") on delete cascade on update cascade, " +
            "foreign key (" + idExamen + ") references " + ExamenDiagnosticoModel.tbName + " (" + ExamenDiagnosticoModel.id + ") on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public ResultadoExamenModel() {
        this.idValue = 0;
        this.idUsuarioValue = 0;
        this.idExamenValue = 0;
        this.puntajeObtenidoValue = 0;
        this.nivelObtenidoValue = 0;
    }

    public ResultadoExamenModel(
            int idUsuario,
            int idExamen,
            int puntajeObtenido,
            int nivelObtenido
    ) {
        this.idValue = 0;
        this.idUsuarioValue = idUsuario;
        this.idExamenValue = idExamen;
        this.puntajeObtenidoValue = puntajeObtenido;
        this.nivelObtenidoValue = nivelObtenido;
    }

    public ResultadoExamenModel(
            int id,
            int idUsuario,
            int idExamen,
            int puntajeObtenido,
            int nivelObtenido
    ) {
        this.idValue = id;
        this.idUsuarioValue = idUsuario;
        this.idExamenValue = idExamen;
        this.puntajeObtenidoValue = puntajeObtenido;
        this.nivelObtenidoValue = nivelObtenido;
    }

    // ---- metodos ----
    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + "=" + idValue + ", " +
                idUsuario + "=" + idUsuarioValue + ", " +
                idExamen + "=" + idExamenValue + ", " +
                puntajeObtenido + "=" + puntajeObtenidoValue + ", " +
                nivelObtenido + "=" + nivelObtenidoValue + "}";
    }
}