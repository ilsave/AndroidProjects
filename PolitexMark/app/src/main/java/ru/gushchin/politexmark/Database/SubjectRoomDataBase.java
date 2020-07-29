package ru.gushchin.politexmark.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Subject.class}, version = 1)
public abstract class SubjectRoomDataBase extends RoomDatabase {

    public abstract SubjectDao personDao();

    private static volatile SubjectRoomDataBase INSTANCE;

    public static SubjectRoomDataBase getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (SubjectRoomDataBase.class){
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        SubjectRoomDataBase.class,
                        "Person_Database"
                ).build();
            }
        }
        return INSTANCE;
    }

}
