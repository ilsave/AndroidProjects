package com.example.notes;

import android.provider.BaseColumns;
//тут хранится инфа о бд, столько вложенных классов сколько всего у нас таблиц
public class NotesContract {
    public static final class NotesEntry implements BaseColumns{
        //базеколум показываем что таблица в базе данных, колонка id создается автоматически baseco;umn
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_DAY_OF_WEEK = "day_of_week";

        public static final String TYPE_TEXT = "TEXT";
        public static final String TYPE_INTEGER = "INTEGER";
        //создание типов

        public static final String CREATE_COLUMN = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "("+_ID+" "+TYPE_INTEGER + " PRIMARY KEY AUTOINCREMENT, " + COLUMN_TITLE +
                " " + TYPE_TEXT + ", " + COLUMN_DESCRIPTION + " " + TYPE_TEXT + ", " + COLUMN_DAY_OF_WEEK +
                " " + TYPE_INTEGER + ", " + COLUMN_PRIORITY + " " + TYPE_INTEGER + ")";

            public static final String DROP_COMMAND = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
