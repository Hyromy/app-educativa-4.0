package com.example.app.db.utils.crud;

import android.app.ApplicationErrorReport;
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
    public long insert(UsuarioModel obj) {
        // conexion y valores
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        // valores
        values.put(UsuarioModel.matricula, obj.matriculaValue);
        values.put(UsuarioModel.contrasena, obj.contrasenaValue);
        values.put(UsuarioModel.nombre, obj.nombreValue);
        values.put(UsuarioModel.aPaterno, obj.aPaternoValue);
        values.put(UsuarioModel.aMaterno, obj.aMaternoValue);

        // insertar
        return db.insert(UsuarioModel.tbName, null, values);
    }

    @Override
    protected UsuarioModel read(int id) {
        // conexion y modelo
        SQLiteDatabase db = getReadableDatabase();
        UsuarioModel usuario = null;

        // columnas
        String[] colums = {
            UsuarioModel.id,
            UsuarioModel.matricula,
            UsuarioModel.contrasena,
            UsuarioModel.nombre,
            UsuarioModel.aPaterno,
            UsuarioModel.aMaterno
        };
        String selection = UsuarioModel.id + " = ?"; // WHERE id = ?
        String[] selectionArgs = {String.valueOf(id)}; // WHERE id = id

        // consulta
        Cursor cursor = db.query(
            UsuarioModel.tbName,
            colums,
            selection,
            selectionArgs,
            null,
            null,
            null
        );

        // si hay resultados
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
    protected UsuarioModel[] readAll() {
        return new UsuarioModel[0];
    }

    @Override
    protected int delete(UsuarioModel obj) {
        return 0;
    }

    @Override
    protected int update(UsuarioModel obj) {
        return 0;
    }
}