package com.example.myfinalproject.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class,FavouriteMovie.class}, version = 5, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase database;
    private static final String DB_NAME = "movies.db";

    private static final Object LOCK = new Object();

    public static MovieDatabase getInstance(Context context){
        synchronized (LOCK) {//чтобы в одном потоке
            if (database == null) {
                database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
                //  fallbackToDescription - автоматически добавляются новые данные (удалем все и добавляем старое и новое)
            }
        }
       return database;
    }

    public abstract MovieDao movieDao();
}
