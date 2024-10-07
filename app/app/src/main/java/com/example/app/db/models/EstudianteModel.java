package com.example.app.db.models;

public class EstudianteModel {
    public static final String tbName = "estudiante";

    public static final String id = "id";
    public static final String idUsuario = "id_usuario";
    public static final String cuatrimestre = "cuatrimestre";

    public static final String createTable = "create table if not exists " +
            tbName + " (" +
            id + " integer primary key autoincrement, " +
            idUsuario + " integer, " +
            cuatrimestre + " integer, " +
            "foreign key (" + idUsuario + ") references " + UsuarioModel.tbName + "(" + UsuarioModel.id + ") " +
            "on delete cascade on update cascade)";

    public static final String dropTable = "drop table if exists " + tbName;
}