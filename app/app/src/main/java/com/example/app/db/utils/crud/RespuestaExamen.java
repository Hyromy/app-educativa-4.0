package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.RespuestaExamenModel;

public class RespuestaExamen extends AbstractCRUD<RespuestaExamenModel> {
    public RespuestaExamen(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            RespuestaExamenModel.tbName,
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
    public long insert(RespuestaExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RespuestaExamenModel.idPreguntaExamen, obj.idPreguntaExamenValue);
        values.put(RespuestaExamenModel.idRecurso, obj.idRecursoValue);
        values.put(RespuestaExamenModel.texto, obj.textoValue);
        values.put(RespuestaExamenModel.puntaje, obj.puntajeValue);

        return db.insert(RespuestaExamenModel.tbName, null, values);
    }

    @Override
    public RespuestaExamenModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        RespuestaExamenModel respuestaExamen = null;

        Cursor cursor = db.query(
            RespuestaExamenModel.tbName,
            colums(),
            RespuestaExamenModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            respuestaExamen = new RespuestaExamenModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getInt(4)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return respuestaExamen;
    }

    @Override
    public RespuestaExamenModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        RespuestaExamenModel[] respuestaExamenes = null;

        Cursor cursor = db.query(
            RespuestaExamenModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );
        int size = cursor.getCount();
        respuestaExamenes = new RespuestaExamenModel[size];
        for (int i = 0; i < size; i++) {
            cursor.moveToPosition(i);
            respuestaExamenes[i] = new RespuestaExamenModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getInt(4)
            );
        }
        cursor.close();

        return respuestaExamenes;
    }

    @Override
    public int update(RespuestaExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RespuestaExamenModel.idPreguntaExamen, obj.idPreguntaExamenValue);
        values.put(RespuestaExamenModel.idRecurso, obj.idRecursoValue);
        values.put(RespuestaExamenModel.texto, obj.textoValue);
        values.put(RespuestaExamenModel.puntaje, obj.puntajeValue);

        return db.update(
            RespuestaExamenModel.tbName,
            values,
            RespuestaExamenModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(RespuestaExamenModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            RespuestaExamenModel.tbName,
            RespuestaExamenModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(RespuestaExamenModel.tbName, RespuestaExamenModel.id);
    }

    public String[] colums() {
        return super.colums(RespuestaExamenModel.tbName);
    }

    public String getTextFromQuestion(RespuestaExamenModel obj) {
        SQLiteDatabase db = getReadableDatabase();
        String text = null;

        String query = "select " + PreguntaExamenModel.tbName + "." + PreguntaExamenModel.texto +
                " from " + PreguntaExamenModel.tbName +
                " inner join " + RespuestaExamenModel.tbName +
                " on " + PreguntaExamenModel.tbName + "." + PreguntaExamenModel.id + " = " + RespuestaExamenModel.tbName + "." + RespuestaExamenModel.idPreguntaExamen +
                " where " + RespuestaExamenModel.tbName + "." + RespuestaExamenModel.id + " = ?";

        Cursor cursor = db.rawQuery(
                query,
                new String[] {String.valueOf(obj.idValue)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            text = cursor.getString(0);
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return text;
    }
}