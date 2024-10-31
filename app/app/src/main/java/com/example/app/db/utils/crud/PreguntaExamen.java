package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.PreguntaExamenModel;

public class PreguntaExamen extends AbstractCRUD<PreguntaExamenModel> {
    public PreguntaExamen(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            PreguntaExamenModel.tbName,
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
    public long insert(PreguntaExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PreguntaExamenModel.idExamen, obj.idExamenValue);
        values.put(PreguntaExamenModel.idRecurso, obj.idRecursoValue);
        values.put(PreguntaExamenModel.texto, obj.textoValue);

        return db.insert(PreguntaExamenModel.tbName, null, values);
    }

    @Override
    public PreguntaExamenModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        PreguntaExamenModel preguntaExamen = null;

        Cursor cursor = db.query(
            PreguntaExamenModel.tbName,
            colums(),
            PreguntaExamenModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            preguntaExamen = new PreguntaExamenModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return preguntaExamen;
    }

    @Override
    public PreguntaExamenModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
            PreguntaExamenModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );
        int size = cursor.getCount();
        PreguntaExamenModel[] preguntaExamenes = new PreguntaExamenModel[size];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                preguntaExamenes[i] = new PreguntaExamenModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                );
                cursor.moveToNext();
            }
        }

        return preguntaExamenes;
    }

    @Override
    public int update(PreguntaExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PreguntaExamenModel.idExamen, obj.idExamenValue);
        values.put(PreguntaExamenModel.idRecurso, obj.idRecursoValue);
        values.put(PreguntaExamenModel.texto, obj.textoValue);

        return db.update(
            PreguntaExamenModel.tbName,
            values,
            PreguntaExamenModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(PreguntaExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            PreguntaExamenModel.tbName,
            PreguntaExamenModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(PreguntaExamenModel.tbName, PreguntaExamenModel.idExamen);
    }

    public String[] colums() {
        return super.colums(PreguntaExamenModel.tbName);
    }
}