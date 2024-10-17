package com.example.app.db.utils.crud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

import com.example.app.db.models.ExamenModel;

public class Examen extends AbstractCRUD<ExamenModel> {
    public Examen(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
                ExamenModel.tbName,
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
    public long insert(ExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ExamenModel.titulo, obj.tituloValue);
        values.put(ExamenModel.nPreguntas, obj.nPreguntasValue);

        return db.insert(ExamenModel.tbName, null, values);
    }

    @Override
    public ExamenModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ExamenModel examen = null;

        Cursor cursor = db.query(
                ExamenModel.tbName,
                colums(),
                ExamenModel.id + " = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            examen = new ExamenModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2)
            );
            cursor.close();

        } else if (cursor != null) {
            cursor.close();
        }

        return examen;
    }

    // no probado
    @Override
    public ExamenModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                ExamenModel.tbName,
                colums(),
                null,
                null,
                null,
                null,
                null
        );
        ExamenModel[] examenes = new ExamenModel[cursor.getCount()];

        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                examenes[i] = new ExamenModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
                );
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return examenes;
    }

    // no probado
    @Override
    public int update(ExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ExamenModel.titulo, obj.tituloValue);
        values.put(ExamenModel.nPreguntas, obj.nPreguntasValue);

        return db.update(
                ExamenModel.tbName,
                values,
                ExamenModel.id + " = ?",
                new String[] {String.valueOf(obj.idValue)}
        );
    }

    // no probado
    @Override
    public int delete(ExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(
                ExamenModel.tbName,
                ExamenModel.id + " = ?",
                new String[] {String.valueOf(obj.idValue)}
        );
    }

    public String[] colums() {
        return super.colums(ExamenModel.tbName);
    }

    public int nextId() {
        return super.nextId(ExamenModel.tbName, ExamenModel.id);
    }
}