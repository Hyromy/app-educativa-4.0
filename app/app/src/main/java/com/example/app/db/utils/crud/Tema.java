package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.TemaModel;

public class Tema extends AbstractCRUD<TemaModel> {
    public Tema(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            TemaModel.tbName,
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
    public long insert(TemaModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TemaModel.titulo, obj.tituloValue);
        values.put(TemaModel.descripcion, obj.descripcionValue);

        return db.insert(TemaModel.tbName, null, values);
    }

    @Override
    public TemaModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        TemaModel tema = null;

        Cursor cursor = db.query(
            TemaModel.tbName,
            colums(),
            TemaModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            tema = new TemaModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return tema;
    }

    @Override
    public TemaModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TemaModel.tbName,
                colums(),
                null,
                null,
                null,
                null,
                null
        );
        int size = cursor.getCount();
        TemaModel[] temas = new TemaModel[size];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                temas[i] = new TemaModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
                );
                cursor.moveToNext();
            }
        }
        cursor.close();

        return temas;
    }

    @Override
    public int update(TemaModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TemaModel.titulo, obj.tituloValue);
        values.put(TemaModel.descripcion, obj.descripcionValue);

        return db.update(
            TemaModel.tbName,
            values,
            TemaModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(TemaModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            TemaModel.tbName,
            TemaModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(TemaModel.tbName, TemaModel.id);
    }

    public String[] colums() {
        return super.colums(TemaModel.tbName);
    }

    public int[] readAllIds() {
        return super.readAllIds(TemaModel.tbName, TemaModel.id);
    }
}