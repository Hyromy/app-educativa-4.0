package com.example.app.db.models;

public class EstudianteModel {
    // ---- atributos ----
    public static final String tbName = "estudiante";

    public static final String id = "id";
    public static final String idUsuario = "id_usuario";
    public static final String cuatrimestre = "cuatrimestre";

    // ---- valores ----
    public int idValue;
    public int idUsuarioValue;
    public int cuatrimestreValue;

    // ---- sentencias ----
    public static final String createTable = "create table if not exists " +
            tbName + " (" +
            id + " integer primary key autoincrement, " +
            idUsuario + " integer, " +
            cuatrimestre + " integer, " +
            "foreign key (" + idUsuario + ") references " + UsuarioModel.tbName + "(" + UsuarioModel.id + ") " +
            "on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public EstudianteModel() {
        this.idValue = 0;
        this.idUsuarioValue = 0;
        this.cuatrimestreValue = 0;
    }

    public EstudianteModel(int id, int idUsuario, int cuatrimestre) {
        this.idValue = id;
        this.idUsuarioValue = idUsuario;
        this.cuatrimestreValue = cuatrimestre;
    }

    public String getData() {
        return getClass().getSimpleName() + " {" +
                id + " = " + idValue + ", " +
                idUsuario + " = " + idUsuarioValue + ", " +
                cuatrimestre + " = " + cuatrimestreValue + "}";
    }
}