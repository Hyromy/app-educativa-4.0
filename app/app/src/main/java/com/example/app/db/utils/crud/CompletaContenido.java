package com.example.app.db.utils.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.models.CompletaContenidoModel;
import com.example.app.db.models.ContenidoModel;
import com.example.app.db.models.ExamenDiagnosticoModel;
import com.example.app.db.models.PreguntaExamenModel;
import com.example.app.db.models.TemaModel;
import com.example.app.db.models.UsuarioModel;

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

    public boolean existLogFrom(int idUsuario, int idContenido) {
        SQLiteDatabase db = getReadableDatabase();
        boolean exist = false;

        Cursor cursor = db.query(
            CompletaContenidoModel.tbName,
            new String[] {CompletaContenidoModel.id},
            CompletaContenidoModel.idUsuario + " = ? AND " + CompletaContenidoModel.idContenido + " = ?",
            new String[] {String.valueOf(idUsuario), String.valueOf(idContenido)},
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

    public int[] getLevelsContentFromTestAnswer(PreguntaExamenModel pregunta) {
        String sql = "select " + ContenidoModel.tbName + "." + ContenidoModel.id + " from " + ContenidoModel.tbName +
            " inner join " + TemaModel.tbName + " on " + ContenidoModel.tbName + "." + ContenidoModel.idTema + " = " + TemaModel.tbName + "." + TemaModel.id +
            " inner join " + ExamenDiagnosticoModel.tbName + " on " + TemaModel.tbName + "." + TemaModel.id + " = " + ExamenDiagnosticoModel.tbName + "." + ExamenDiagnosticoModel.idTema +
            " inner join " + PreguntaExamenModel.tbName + " on " + ExamenDiagnosticoModel.tbName + "." + ExamenDiagnosticoModel.id + " = " + PreguntaExamenModel.tbName + "." + PreguntaExamenModel.idExamen +
            " where " + PreguntaExamenModel.tbName + "." + PreguntaExamenModel.id + " = ?" +
            " order by " + ContenidoModel.tbName + "." + ContenidoModel.nivel + " asc";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, new String[] {String.valueOf(pregunta.idValue)});
        int size = cursor.getCount();
        int[] ids = new int[size];

        for (int i = 0; i < size; i++) {
            cursor.moveToPosition(i);
            ids[i] = cursor.getInt(0);
        }

        return ids;
    }

    public int[] getIdContentsCompletedByUser(UsuarioModel usuario) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
            CompletaContenidoModel.tbName,
            new String[] {CompletaContenidoModel.id},
            CompletaContenidoModel.idUsuario + " = ?",
            new String[] {String.valueOf(usuario.idValue)},
            null,
            null,
            null
        );
        int size = cursor.getCount();
        int[] ids = new int[size];

        for(int i = 0; i < size; i++) {
            cursor.moveToPosition(i);
            ids[i] = cursor.getInt(0);
        }

        return ids;
    }
}