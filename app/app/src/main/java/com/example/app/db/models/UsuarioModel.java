package com.example.app.db.models;

public class UsuarioModel {
    // ---- atributos ----
    public static final String tbName = "usuario";

    public static final String id = "id";
    public static final String matricula = "matricula";
    public static final String contrasena = "contrasena";
    public static final String nombre = "nombre";
    public static final String aPaterno = "a_paterno";
    public static final String aMaterno = "a_materno";

    // ---- valores ----
    public int idValue;
    public String matriculaValue;
    public String contrasenaValue;
    public String nombreValue;
    public String aPaternoValue;
    public String aMaternoValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            matricula + " varchar(10), " +
            contrasena + "varchar(32)" +
            nombre + " varchar(50), " +
            aPaterno + " varchar(50), " +
            aMaterno + " varchar(50))";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public UsuarioModel() {}

    public UsuarioModel(
            int id,
            String matricula,
            String contrasena,
            String nombre,
            String aPaterno,
            String aMaterno
    ) {
        this.idValue = id;
        this.matriculaValue = matricula;
        this.contrasenaValue = contrasena;
        this.nombreValue = nombre;
        this.aPaternoValue = aPaterno;
        this.aMaternoValue = aMaterno;
    }
}