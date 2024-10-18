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

        db.execSQL(ExamenDiagnosticoModel.createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioModel.dropTable);
        db.execSQL(RecursoModel.dropTable);
        db.execSQL(TemaModel.dropTable);

        db.execSQL(ExamenDiagnosticoModel.dropTable);

        onCreate(db);
    }
}