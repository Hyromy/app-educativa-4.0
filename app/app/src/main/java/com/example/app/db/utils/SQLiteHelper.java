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
        db.execSQL(EstudianteModel.createTable);
        db.execSQL(AdministradorModel.createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioModel.dropTable);
        db.execSQL(EstudianteModel.dropTable);
        db.execSQL(AdministradorModel.dropTable);
        onCreate(db);
    }
}