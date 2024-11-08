package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.ResultadoExamenModel;

public class ResultadoExamen extends AbstractCRUD<ResultadoExamenModel> {
    public ResultadoExamen(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            ResultadoExamenModel.tbName,
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
    public long insert(ResultadoExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ResultadoExamenModel.idUsuario, obj.idUsuarioValue);
        values.put(ResultadoExamenModel.idExamen, obj.idExamenValue);
        values.put(ResultadoExamenModel.puntajeObtenido, obj.puntajeObtenidoValue);
        values.put(ResultadoExamenModel.nivelObtenido, obj.nivelObtenidoValue);

        return db.insert(ResultadoExamenModel.tbName, null, values);
    }

    @Override
    public ResultadoExamenModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ResultadoExamenModel resultadoExamen = null;

        Cursor cursor = db.query(
            ResultadoExamenModel.tbName,
            colums(),
            ResultadoExamenModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            resultadoExamen = new ResultadoExamenModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return resultadoExamen;
    }

    @Override
    public ResultadoExamenModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
            ResultadoExamenModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );
        int size = cursor.getCount();
        ResultadoExamenModel[] resultadoExamenes = new ResultadoExamenModel[size];
        for (int i = 0; i < size; i++) {
            cursor.moveToPosition(i);
            resultadoExamenes[i] = new ResultadoExamenModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4)
            );
        }
        cursor.close();

        return resultadoExamenes;
    }

    @Override
    public int update(ResultadoExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ResultadoExamenModel.idUsuario, obj.idUsuarioValue);
        values.put(ResultadoExamenModel.idExamen, obj.idExamenValue);
        values.put(ResultadoExamenModel.puntajeObtenido, obj.puntajeObtenidoValue);
        values.put(ResultadoExamenModel.nivelObtenido, obj.nivelObtenidoValue);

        return db.update(
            ResultadoExamenModel.tbName,
            values,
            ResultadoExamenModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(ResultadoExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            ResultadoExamenModel.tbName,
            ResultadoExamenModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(ResultadoExamenModel.tbName, ResultadoExamenModel.id);
    }

    public String[] colums() {
        return super.colums(ResultadoExamenModel.tbName);
    }

    public boolean existLogFrom(int idUsuario, int idExamen) {
        SQLiteDatabase db = getReadableDatabase();
        boolean exist = false;

        Cursor cursor = db.query(
            ResultadoExamenModel.tbName,
            new String[] {ResultadoExamenModel.id},
            ResultadoExamenModel.idUsuario + " = ? AND " + ResultadoExamenModel.idExamen + " = ?",
            new String[] {String.valueOf(idUsuario), String.valueOf(idExamen)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            exist = cursor.getInt(0) > 0;
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return exist;
    }
}