package com.example.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";//имя
    private static final int DB_VERSION = 3;//версия бд, если хотим потом обновить то добавлем 1

    public NotesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //вызывается при создании таблицы в базе данных
        db.execSQL(NotesContract.NotesEntry.CREATE_COLUMN);//чтобы исполнить запрос надо использовать execSQL
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //если наша база данных обновилась то нужно удалить старую таблицу и создать новую
        db.execSQL(NotesContract.NotesEntry.DROP_COMMAND);//удаление
        onCreate(db);

    }
}
