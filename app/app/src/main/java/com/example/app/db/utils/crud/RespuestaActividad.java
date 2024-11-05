package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.RespuestaActividadModel;

public class RespuestaActividad extends AbstractCRUD<RespuestaActividadModel> {
    public RespuestaActividad(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            RespuestaActividadModel.tbName,
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
    public long insert(RespuestaActividadModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RespuestaActividadModel.idPreguntaActividad, obj.idPreguntaActividadValue);
        values.put(RespuestaActividadModel.idRecurso, obj.idRecursoValue);
        values.put(RespuestaActividadModel.texto, obj.textoValue);
        values.put(RespuestaActividadModel.esCorrecto, obj.esCorrectoValue);

        return db.insert(RespuestaActividadModel.tbName, null, values);
    }

    @Override
    public RespuestaActividadModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        RespuestaActividadModel respuestaActividad = null;

        Cursor cursor = db.query(
            RespuestaActividadModel.tbName,
            colums(),
            RespuestaActividadModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            respuestaActividad = new RespuestaActividadModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getInt(4) == 1
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return respuestaActividad;
    }

    @Override
    public RespuestaActividadModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        RespuestaActividadModel[] respuestaActividades = null;

        Cursor cursor = db.query(
            RespuestaActividadModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );
        int size = cursor.getCount();
        respuestaActividades = new RespuestaActividadModel[size];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                respuestaActividades[i] = new RespuestaActividadModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1
                );
                cursor.moveToNext();
            }
        }

        return respuestaActividades;
    }

    @Override
    public int update(RespuestaActividadModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RespuestaActividadModel.idPreguntaActividad, obj.idPreguntaActividadValue);
        values.put(RespuestaActividadModel.idRecurso, obj.idRecursoValue);
        values.put(RespuestaActividadModel.texto, obj.textoValue);
        values.put(RespuestaActividadModel.esCorrecto, obj.esCorrectoValue);

        return db.update(
            RespuestaActividadModel.tbName,
            values,
            RespuestaActividadModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(RespuestaActividadModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            RespuestaActividadModel.tbName,
            RespuestaActividadModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(RespuestaActividadModel.tbName, RespuestaActividadModel.id);
    }

    public String[] colums() {
        return super.colums(RespuestaActividadModel.tbName);
    }
}