package com.example.javaroomdatabasesimpleexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void insertSinglePerson(View view) {
        Person person = new Person("Vladimir", "Gushchin", false);
        new InsertAsyncTask().execute(person);
        Toast.makeText(this, person.toString(), Toast.LENGTH_SHORT).show();
        Log.d("TaskDb", person.toString());
    }

    public void getAllPersons(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Person> personList= PersonRoomDatabase.getINSTANCE(getApplicationContext())
                        .personDao()
                        .getAllPersons();
                
                Log.d(TAG, "run: " + personList.toString());
            }
        });
        thread.start();
    }


    public void deletePerson(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Person person = PersonRoomDatabase.getINSTANCE(getApplicationContext())
                        .personDao()
                        .findPersonById(1);

                Log.d(TAG, "run: " + person.toString());

                PersonRoomDatabase.getINSTANCE(getApplicationContext())
                        .personDao()
                        .deletePerson(person);

                Log.d(TAG, "person has been deleted:  " + person.toString());
            }
        }).start();


    }

    public void updatePerson(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Person person = PersonRoomDatabase.getINSTANCE(getApplicationContext())
                        .personDao()
                        .findPersonById(12);

                if (person != null){
                    person.setCompiled(true);
                    PersonRoomDatabase.getINSTANCE(getApplicationContext())
                            .personDao()
                            .updatePersons(person);

                    Log.d(TAG, "person has been updated: " + person.toString());
                }


            }
        }).start();
    }

    public void insertMultiPersons(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {

            List<Person> personList = new ArrayList<>();
            personList.add(new Person("Michail", "Ivanov", false));
            personList.add(new Person("Nick", "Petrenko", false));

            PersonRoomDatabase.getINSTANCE(getApplicationContext())
                    .personDao()
                    .insertMultiplePersons(personList);
            Log.d(TAG, "run: persons has been inserted");

            }
        }).start();
    }

    public void findCompeledPersons(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Person> personList = PersonRoomDatabase.getINSTANCE(getApplicationContext())
                        .personDao()
                        .getAllCompiledPersons();
                Log.d(TAG, "run: persons"+ personList.toString());
            }
        }).start();

    }

    public void getAllLiveDataPersons(View view) {

        LiveData<List<Person>> personList = PersonRoomDatabase.getINSTANCE(getApplicationContext())
                .personDao()
                .findPersonsUsingLiveDataOnly();

        personList.observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(List<Person> personList) {
                Log.d(TAG,"onChanged: " + personList.toString());
                Log.d(TAG,"onChanged: " + personList.size());
            }
        });

       // personList.removeObservers(this);

    }


    class InsertAsyncTask extends AsyncTask<Person, Void, Void> {

        @Override
        protected Void doInBackground(Person... persons) {

            PersonRoomDatabase.getINSTANCE(getApplicationContext())
                    .personDao()
                    .insertPerson(persons[0]);

            return null;
        }
    }
}