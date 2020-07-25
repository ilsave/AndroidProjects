package com.example.javaroomdatabasesimpleexample;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Person.class}, version = 1)
public abstract class PersonRoomDatabase extends RoomDatabase {

    public abstract PersonDao personDao();

    private static volatile PersonRoomDatabase INSTANCE;

    static PersonRoomDatabase getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (PersonRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        PersonRoomDatabase.class,
                        "Person_Database"
                ).build();
            }
        }
        return INSTANCE;
    }

}
