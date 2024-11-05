package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.PreguntaActividadModel;
import com.example.app.db.models.TemaModel;

public class PreguntaActividad extends AbstractCRUD<PreguntaActividadModel> {
    public PreguntaActividad(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
                PreguntaActividadModel.tbName,
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
    public long insert(PreguntaActividadModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PreguntaActividadModel.idContenido, obj.idContenidoValue);
        values.put(PreguntaActividadModel.idRecurso, obj.idRecursoValue);
        values.put(PreguntaActividadModel.texto, obj.textoValue);

        return db.insert(PreguntaActividadModel.tbName, null, values);
    }

    @Override
    public PreguntaActividadModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        PreguntaActividadModel preguntaActividad = null;

        Cursor cursor = db.query(
                PreguntaActividadModel.tbName,
                colums(),
                PreguntaActividadModel.id + " = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            preguntaActividad = new PreguntaActividadModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getString(3)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return preguntaActividad;
    }

    @Override
    public PreguntaActividadModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                PreguntaActividadModel.tbName,
                colums(),
                null,
                null,
                null,
                null,
                null
        );
        int size = cursor.getCount();
        PreguntaActividadModel[] preguntaActividades = new PreguntaActividadModel[size];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                preguntaActividades[i] = new PreguntaActividadModel(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                );
                cursor.moveToNext();
            }
        }

        return preguntaActividades;
    }

    @Override
    public int update(PreguntaActividadModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PreguntaActividadModel.idContenido, obj.idContenidoValue);
        values.put(PreguntaActividadModel.idRecurso, obj.idRecursoValue);
        values.put(PreguntaActividadModel.texto, obj.textoValue);

        return db.update(
                PreguntaActividadModel.tbName,
                values,
                PreguntaActividadModel.id + " = ?",
                new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(PreguntaActividadModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
                PreguntaActividadModel.tbName,
                PreguntaActividadModel.id + " = ?",
                new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(PreguntaActividadModel.tbName, PreguntaActividadModel.id);
    }

    public String[] colums() {
        return super.colums(PreguntaActividadModel.tbName);
    }

    public int getLevelFromContent(PreguntaActividadModel obj) {
        SQLiteDatabase db = getReadableDatabase();
        int level = -1;

        String query = "select " + ContenidoModel.tbName + "." + ContenidoModel.nivel +
                " from " + PreguntaActividadModel.tbName +
                " inner join " + ContenidoModel.tbName +
                " on " + PreguntaActividadModel.tbName + "." + PreguntaActividadModel.idContenido + " = " + ContenidoModel.tbName + "." + ContenidoModel.id +
                " where " + PreguntaActividadModel.tbName + "." + PreguntaActividadModel.idContenido + " = ?";

        Cursor cursor = db.rawQuery(
                query,
                new String[] {String.valueOf(obj.idContenidoValue)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            level = cursor.getInt(0);
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return level;
    }

    public String getTitleFromTheme(PreguntaActividadModel obj) {
        SQLiteDatabase db = getReadableDatabase();
        String title = null;

        String query = "select " + TemaModel.tbName + "." + TemaModel.titulo +
                " from " + TemaModel.tbName +
                " inner join " + ContenidoModel.tbName +
                " on " + TemaModel.tbName + "." + TemaModel.id + " = " + ContenidoModel.tbName + "." + ContenidoModel.idTema +
                " inner join " + PreguntaActividadModel.tbName +
                " on " + ContenidoModel.tbName + "." + ContenidoModel.id + " = " + PreguntaActividadModel.tbName + "." + PreguntaActividadModel.idContenido +
                " where " + PreguntaActividadModel.tbName + "." + PreguntaActividadModel.id + " = ?";

        Cursor cursor = db.rawQuery(
                query,
                new String[] {String.valueOf(obj.idValue)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            title = cursor.getString(0);
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return title;
    }
}