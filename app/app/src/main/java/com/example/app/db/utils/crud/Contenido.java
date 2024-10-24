package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.ContenidoModel;

public class Contenido extends AbstractCRUD<ContenidoModel> {
    public Contenido(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cusor = db.query(
            ContenidoModel.tbName,
            colums(),
            arg + " = ?",
            new String[] {value},
            null,
            null,
            null
        );

        if (cusor != null && cusor.moveToFirst()) {
            id = cusor.getInt(0);
            cusor.close();
        } else if (cusor != null) {
            cusor.close();
        }

        return id;
    }

    @Override
    public long insert(ContenidoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ContenidoModel.idTema, obj.idTemaValue);
        values.put(ContenidoModel.titulo, obj.tituloValue);
        values.put(ContenidoModel.descripcion, obj.descripcionValue);
        values.put(ContenidoModel.nivel, obj.nivelValue);
        values.put(ContenidoModel.nPreguntas, obj.nPreguntasValue);

        return db.insert(ContenidoModel.tbName, null, values);
    }

    @Override
    public ContenidoModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ContenidoModel contenido = null;

        Cursor cursor = db.query(
            ContenidoModel.tbName,
            colums(),
            ContenidoModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            contenido = new ContenidoModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return contenido;
    }

    @Override
    public ContenidoModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
            ContenidoModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );
        int size = cursor.getCount();
        ContenidoModel[] contenidos = new ContenidoModel[size];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                contenidos[i] = new ContenidoModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
                );
                cursor.moveToNext();
            }
        }

        return contenidos;
    }

    @Override
    public int update(ContenidoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ContenidoModel.idTema, obj.idTemaValue);
        values.put(ContenidoModel.titulo, obj.tituloValue);
        values.put(ContenidoModel.nivel, obj.nivelValue);
        values.put(ContenidoModel.descripcion, obj.descripcionValue);
        values.put(ContenidoModel.nPreguntas, obj.nPreguntasValue);

        return db.update(
            ContenidoModel.tbName,
            values,
            ContenidoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(ContenidoModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            ContenidoModel.tbName,
            ContenidoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(ContenidoModel.tbName, ContenidoModel.id);
    }

    public String[] colums() {
        return super.colums(ContenidoModel.tbName);
    }
}