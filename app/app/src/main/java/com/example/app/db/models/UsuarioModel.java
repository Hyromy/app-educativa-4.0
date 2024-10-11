package com.example.app.db.models;

import java.io.Serializable;

public class UsuarioModel implements Serializable {
    // ---- atributos ----
    public static final String tbName = "usuario";

    public static final String id = "id_usuario";
    public static final String matricula = "matricula";
    public static final String hashContrasena = "hash_contrasena";
    public static final String nombre = "nombre";
    public static final String aPaterno = "a_paterno";
    public static final String aMaterno = "a_materno";
    public static final String tipoAdministrador = "tipo_administrador";

    // ---- valores ----
    public int idValue;
    public String matriculaValue;
    public String contrasenaValue;
    public String nombreValue;
    public String aPaternoValue;
    public String aMaternoValue;
    public boolean tipoAdministradorValue;

    // ---- sentencias ----
    public static final String createTable =
            "create table if not exists " + tbName + " (" +
            id + " integer primary key autoincrement, " +
            matricula + " varchar(10) unique, " +
            hashContrasena + " varchar(32), " +
            nombre + " varchar(50), " +
            aPaterno + " varchar(50), " +
            aMaterno + " varchar(50), " +
            tipoAdministrador + " boolean default false)";

    public static final String dropTable = "drop table if exists " + tbName;

    // ---- constructores ----
    public UsuarioModel() {
        this.idValue = 0;
        this.matriculaValue = null;
        this.contrasenaValue = null;
        this.nombreValue = null;
        this.aPaternoValue = null;
        this.aMaternoValue = null;
        this.tipoAdministradorValue = false;
    }

    public UsuarioModel(
            int id,
            String matricula,
            String contrasena,
            String nombre,
            String aPaterno,
            String aMaterno,
            boolean tipoAdministrador
    ) {
        this.idValue = id;
        this.matriculaValue = matricula;
        this.contrasenaValue = contrasena;
        this.nombreValue = nombre;
        this.aPaternoValue = aPaterno;
        this.aMaternoValue = aMaterno;
        this.tipoAdministradorValue = tipoAdministrador;
    }

    public String getData() {
        return getClass().getSimpleName() + "{" +
                id + "=" + idValue + ", " +
                matricula + "='" + matriculaValue + "', " +
                hashContrasena + "='" + contrasenaValue + "', " +
                nombre + "='" + nombreValue + "', " +
                aPaterno + "='" + aPaternoValue + "', " +
                aMaterno + "='" + aMaternoValue + "'}";
    }
}