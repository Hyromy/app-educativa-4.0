package com.example.app.db.utils.crud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.app.db.utils.SQLiteHelper;

public abstract class AbstractCRUD<Table> {
    private SQLiteHelper dbHelper;

    protected AbstractCRUD(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    protected SQLiteDatabase getWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getReadableDatabase() {
        return dbHelper.getReadableDatabase();
    }

    protected abstract long insert(Table obj);

    protected abstract Table read(int id);

    protected abstract Table[] readAll();

    protected abstract int delete(Table obj);

    protected abstract int update(Table obj);
}