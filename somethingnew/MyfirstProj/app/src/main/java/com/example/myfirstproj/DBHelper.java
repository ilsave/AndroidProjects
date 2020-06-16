package com.example.myfirstproj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //версия БД, начиная с 1
    public static final int DATABASE_VERSION = 1;
    //имя БД
    public static final String DATABASE_NAME = "MyDB";
    //имя таблицы в БД
    public static final String TABLE_CONTACTS = "Registration";

    //атрибуты в таблице
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";



    //конструктор контекст, имя БД, курсом, версия БД
    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    //вызывается при первом создании БД
    @Override
    public void onCreate(SQLiteDatabase db) {
        //execSQL - параметр выполнения кода на языке SQL
        // create table Registration (_id integer primary key, name varchar(40), phone varchar(20));
        db.execSQL("create table " + TABLE_CONTACTS + "(" + KEY_ID + " integer primary key," + KEY_NAME +
                " varchar(40)," + KEY_PHONE + " varchar(20)" + ")");
    }
    //вызывается при изменении БД
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + TABLE_CONTACTS);
        onCreate(db);

    }
}
