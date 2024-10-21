package com.example.app.db.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app.db.models.*;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DBName = "DAE4.0.db";
    private static final int DBVersion = 1;

    public SQLiteHelper(Context context) {
        super(context, DBName, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UsuarioModel.createTable);
        db.execSQL(RecursoModel.createTable);
        db.execSQL(TemaModel.createTable);

        db.execSQL(ContenidoModel.createTable);
        db.execSQL(ExamenDiagnosticoModel.createTable);

        db.execSQL(ResultadoExamenModel.createTable);
        db.execSQL(CompletaContenidoModel.createTable);
        db.execSQL(PreguntaExamenModel.createTable);
        db.execSQL(PreguntaActividadModel.createTable);
        db.execSQL(ApoyoModel.createTable);

        db.execSQL(RespuestaExamenModel.createTable);
        db.execSQL(RespuestaActividadModel.createTable);
        db.execSQL(ParrafoModel.createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioModel.dropTable);
        db.execSQL(RecursoModel.dropTable);
        db.execSQL(TemaModel.dropTable);

        db.execSQL(ContenidoModel.dropTable);
        db.execSQL(ExamenDiagnosticoModel.dropTable);

        db.execSQL(ResultadoExamenModel.dropTable);
        db.execSQL(CompletaContenidoModel.dropTable);
        db.execSQL(PreguntaExamenModel.dropTable);
        db.execSQL(PreguntaActividadModel.dropTable);
        db.execSQL(ApoyoModel.dropTable);

        db.execSQL(RespuestaExamenModel.dropTable);
        db.execSQL(RespuestaActividadModel.dropTable);
        db.execSQL(ParrafoModel.dropTable);

        onCreate(db);
    }
}