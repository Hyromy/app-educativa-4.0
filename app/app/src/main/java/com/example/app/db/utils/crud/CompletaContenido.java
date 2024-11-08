package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.CompletaContenidoModel;

public class CompletaContenido extends AbstractCRUD<CompletaContenidoModel> {
    public CompletaContenido(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cusor = db.query(
            CompletaContenidoModel.tbName,
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
    public long insert(CompletaContenidoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CompletaContenidoModel.idUsuario, obj.idUsuarioValue);
        values.put(CompletaContenidoModel.idContenido, obj.idContenidoValue);

        return db.insert(CompletaContenidoModel.tbName, null, values);
    }

    @Override
    public CompletaContenidoModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        CompletaContenidoModel contenidoCompleto = null;

        Cursor cursor = db.query(
            CompletaContenidoModel.tbName,
            colums(),
            CompletaContenidoModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            contenidoCompleto = new CompletaContenidoModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return contenidoCompleto;
    }

    @Override
    public CompletaContenidoModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
            CompletaContenidoModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );
        int size = cursor.getCount();
        CompletaContenidoModel[] contenidosCompletos = new CompletaContenidoModel[size];

        for (int i = 0; i < size; i++) {
            cursor.moveToPosition(i);
            contenidosCompletos[i] = new CompletaContenidoModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2)
            );
        }
        cursor.close();

        return contenidosCompletos;
    }

    @Override
    public int update(CompletaContenidoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CompletaContenidoModel.idUsuario, obj.idUsuarioValue);
        values.put(CompletaContenidoModel.idContenido, obj.idContenidoValue);

        return db.update(
            CompletaContenidoModel.tbName,
            values,
            CompletaContenidoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(CompletaContenidoModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            CompletaContenidoModel.tbName,
            CompletaContenidoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(CompletaContenidoModel.tbName, CompletaContenidoModel.id);
    }

    public String[] colums() {
        return super.colums(CompletaContenidoModel.tbName);
    }
}