package com.example.app.db.utils.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.utils.SQLiteHelper;

public abstract class AbstractCRUD<Table> {
    private SQLiteHelper dbHelper;
    private Context context;

    protected AbstractCRUD(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new SQLiteHelper(this.context);
    }

    public void close() {
        dbHelper.close();
    }

    protected SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    protected int nextId(String tableName, String idColumn) {
        SQLiteDatabase db = getReadableDatabase();
        int id = 0;

        Cursor cursor = db.rawQuery("SELECT MAX(" + idColumn + ") FROM " + tableName, null);

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(0) + 1;
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }

        return id;
    }

    protected String[] colums(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        String[] colums = new String[cursor.getCount()];

        if (cursor.moveToFirst()) {
            int i = 0;
            int nameIndex = cursor.getColumnIndex("name");
            do {
                colums[i] = cursor.getString(nameIndex);
                i++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return colums;
    }

    protected abstract int getIdBy(String arg, String value);

    protected abstract long insert(Table obj);

    protected abstract Table read(int id);

    protected abstract Table[] readAll();

    protected abstract int update(Table obj);

    protected abstract int delete(Table obj);
}