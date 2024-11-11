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

    public int nextId(String tableName, String idColumn) {
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

    public String[] colums(String tableName) {
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

    public int[] readAllIds(String table, String id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                table,
                new String[] {id},
                null,
                null,
                null,
                null,
                null
        );

        int size = cursor.getCount();
        int[] ids = new int[size];
        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                ids[i] = cursor.getInt(0);
                cursor.moveToNext();
            }
        }

        return ids;
    }

    public String[] getStringLogFromId(String table, String id, int idValue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                table,
                colums(table),
                id + " = ?",
                new String[] {String.valueOf(idValue)},
                null,
                null,
                null
        );

        String log[] = new String[cursor.getColumnCount()];
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                log[i] = cursor.getString(i);
            }
        }
        cursor.close();

        return log;
    }

    public String[][] getStringLogs(String table) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                table,
                colums(table),
                null,
                null,
                null,
                null,
                null
        );

        int size = cursor.getCount();
        String[][] logs = new String[size][cursor.getColumnCount()];
        if (cursor.moveToFirst()) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    logs[i][j] = cursor.getString(j);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();

        return logs;
    }

    public int count(String table) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + table, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    protected abstract int getIdBy(String arg, String value);

    protected abstract long insert(Table obj);

    protected abstract Table read(int id);

    protected abstract Table[] readAll();

    protected abstract int update(Table obj);

    protected abstract int delete(Table obj);
}