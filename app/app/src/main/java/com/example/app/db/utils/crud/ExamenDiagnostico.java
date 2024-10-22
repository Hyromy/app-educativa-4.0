package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.TemaModel;

public class ExamenDiagnostico extends AbstractCRUD<ExamenDiagnosticoModel> {
    private Context context;

    public ExamenDiagnostico(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            ExamenDiagnosticoModel.tbName,
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
    public long insert(ExamenDiagnosticoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ExamenDiagnosticoModel.idTema, obj.idTemaValue);
        values.put(ExamenDiagnosticoModel.titulo, obj.tituloValue);
        values.put(ExamenDiagnosticoModel.nPreguntas, obj.nPreguntasValue);
        values.put(ExamenDiagnosticoModel.nivelMaximo, obj.nivelMaximoValue);

        return db.insert(ExamenDiagnosticoModel.tbName, null, values);
    }

    @Override
    public ExamenDiagnosticoModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        ExamenDiagnosticoModel examenDiagnostico = null;

        Cursor cursor = db.query(
            ExamenDiagnosticoModel.tbName,
            colums(),
            ExamenDiagnosticoModel.id + " = ?",
            new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            examenDiagnostico = new ExamenDiagnosticoModel(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getInt(4)
            );
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return examenDiagnostico;
    }

    @Override
    public ExamenDiagnosticoModel[] readAll() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
            ExamenDiagnosticoModel.tbName,
            colums(),
            null,
            null,
            null,
            null,
            null
        );

        int size = cursor.getCount();
        ExamenDiagnosticoModel[] examenesDiagnostico = new ExamenDiagnosticoModel[size];

        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                examenesDiagnostico[i] = new ExamenDiagnosticoModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4)
                );
                cursor.moveToNext();
            }
        }
        cursor.close();

        return examenesDiagnostico;
    }

    @Override
    public int update(ExamenDiagnosticoModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ExamenDiagnosticoModel.idTema, obj.idTemaValue);
        values.put(ExamenDiagnosticoModel.titulo, obj.tituloValue);
        values.put(ExamenDiagnosticoModel.nPreguntas, obj.nPreguntasValue);
        values.put(ExamenDiagnosticoModel.nivelMaximo, obj.nivelMaximoValue);

        return db.update(
            ExamenDiagnosticoModel.tbName,
            values,
            ExamenDiagnosticoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    @Override
    public int delete(ExamenDiagnosticoModel obj) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete(
            ExamenDiagnosticoModel.tbName,
            ExamenDiagnosticoModel.id + " = ?",
            new String[] {String.valueOf(obj.idValue)}
        );
    }

    public int nextId() {
        return super.nextId(ExamenDiagnosticoModel.tbName, ExamenDiagnosticoModel.id);
    }

    public String[] colums() {
        return super.colums(ExamenDiagnosticoModel.tbName);
    }

    public TemaModel getTema(ExamenDiagnosticoModel examenDiagnostico) {
        return new Tema(this.context).read(examenDiagnostico.idTemaValue);
    }
}