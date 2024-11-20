package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.RecursoModel;

public class Recurso extends AbstractCRUD<RecursoModel> {
    private Context context;

    public Recurso(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            RecursoModel.tbName,
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
    public long insert(RecursoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RecursoModel.nombre, obj.nombreValue);
        values.put(RecursoModel.extension, obj.extensionValue);
        values.put(RecursoModel.tipo, obj.tipoValue);

        return db.insert(RecursoModel.tbName, null, values);
    }

    @Override
    public RecursoModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        RecursoModel recurso = null;

        Cursor cursor = db.query(
            RecursoModel.tbName,
            colums(),
            RecursoModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            recurso = new RecursoModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return recurso;
    }

    @Override
    public RecursoModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
            RecursoModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );
        int size = cursor.getCount();
        RecursoModel[] recursos = new RecursoModel[size];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                recursos[i] = new RecursoModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                );
                cursor.moveToNext();
            }
        }
        cursor.close();

        return recursos;
    }

    @Override
    public int update(RecursoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RecursoModel.nombre, obj.nombreValue);
        values.put(RecursoModel.extension, obj.extensionValue);
        values.put(RecursoModel.tipo, obj.tipoValue);

        return db.update(
            RecursoModel.tbName,
            values,
            RecursoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(RecursoModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            RecursoModel.tbName,
            RecursoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(RecursoModel.tbName, RecursoModel.id);
    }

    public String[] colums() {
        return super.colums(RecursoModel.tbName);
    }

    public String getFileNameById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String name = null;
        Cursor cursor = db.query(
            RecursoModel.tbName,
            new String[] {RecursoModel.nombre, RecursoModel.extension},
            RecursoModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(0) + "." + cursor.getString(1);
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return name;
    }
}