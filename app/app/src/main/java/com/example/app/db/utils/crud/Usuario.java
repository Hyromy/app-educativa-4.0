package com.example.app.db.utils.crud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

import com.example.app.db.models.UsuarioModel;

public class Usuario extends AbstractCRUD<UsuarioModel> {
    public Usuario(Context context) {
        super(context);
    }

    @Override
    public int getIdBy(String arg, String value) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.query(
            UsuarioModel.tbName,
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
    public long insert(UsuarioModel obj) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(UsuarioModel.matricula, obj.matriculaValue);
        values.put(UsuarioModel.contrasena, obj.contrasenaValue);
        values.put(UsuarioModel.nombre, obj.nombreValue);
        values.put(UsuarioModel.aPaterno, obj.aPaternoValue);
        values.put(UsuarioModel.aMaterno, obj.aMaternoValue);

        return db.insert(UsuarioModel.tbName, null, values);
    }

    @Override
    public UsuarioModel read(int id) {
        SQLiteDatabase db = getReadableDatabase();
        UsuarioModel usuario = null;

        Cursor cursor = db.query(
            UsuarioModel.tbName,
            colums(),
                UsuarioModel.id + " = ?",
                new String[] {String.valueOf(id)},
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            usuario = new UsuarioModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            );
            cursor.close();

        } else if (cursor != null) {
            cursor.close();
        }

        return usuario;
    }

    @Override
    public UsuarioModel[] readAll() {
        return new UsuarioModel[0];
    }

    @Override
    public int delete(UsuarioModel obj) {
        return 0;
    }

    @Override
    public int update(UsuarioModel obj) {
        return 0;
    }

    public int nextId() {
        return super.nextId(UsuarioModel.tbName, UsuarioModel.id);
    }

    public String[] colums() {
        return super.colums(UsuarioModel.tbName);
    }

    public UsuarioModel login(String matricula, String password) {
        SQLiteDatabase db = getReadableDatabase();
        UsuarioModel usuario = null;
        String[] colums = colums();

        String selection = UsuarioModel.matricula + " = ? AND " + UsuarioModel.contrasena + " = ?";
        String[] selectionArgs = {matricula, password};

        Cursor cursor = db.query(
            UsuarioModel.tbName,
            colums,
            selection,
            selectionArgs,
            null,
            null,
            null
        );

        if (cursor != null && cursor.moveToFirst()) {
            usuario = new UsuarioModel(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5)
            );
            cursor.close();

        } else if (cursor != null) {
            cursor.close();
        }

        return usuario;
    }
}