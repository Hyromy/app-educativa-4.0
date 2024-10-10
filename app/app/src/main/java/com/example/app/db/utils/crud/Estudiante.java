package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.EstudianteModel;

public class Estudiante extends AbstractCRUD<EstudianteModel> {
    public Estudiante(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
                EstudianteModel.tbName,
                colums(),
                arg + " = ?",
                new String[] {value},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(0);
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return id;
    }

    @Override
    public long insert(EstudianteModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EstudianteModel.id, obj.idValue);
        values.put(EstudianteModel.idUsuario, obj.idUsuarioValue);
        values.put(EstudianteModel.cuatrimestre, obj.cuatrimestreValue);

        return db.insert(EstudianteModel.tbName, null, values);
    }

    @Override
    public EstudianteModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        EstudianteModel estudiante = null;

        Cursor cursor = db.query(
                EstudianteModel.tbName,
                colums(),
                EstudianteModel.id + " = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            estudiante = new EstudianteModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return estudiante;
    }

    @Override
    public EstudianteModel[] readAll() {
        return new EstudianteModel[0];
    }

    @Override
    public int update(EstudianteModel obj) {
        return 0;
    }

    @Override
    public int delete(EstudianteModel obj) {
        return 0;
    }

    public int nextId() {
        return super.nextId(EstudianteModel.tbName, EstudianteModel.id);
    }

    public String[] colums() {
        return super.colums(EstudianteModel.tbName);
    }
}