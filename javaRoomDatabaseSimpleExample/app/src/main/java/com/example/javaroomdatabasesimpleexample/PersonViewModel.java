package com.example.javaroomdatabasesimpleexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {

    PersonRoomDatabase personRoomDatabase;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        personRoomDatabase = PersonRoomDatabase.getINSTANCE(application.getApplicationContext());
    }

    public LiveData<List<Person>> getAllPersons(){
        return personRoomDatabase.personDao().findAllPersons();
    }

}
