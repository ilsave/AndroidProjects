package com.example.javaroomdatabasesimpleexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert
    void insertPerson(Person person);

    @Query("SELECT * FROM person_table WHERE person_id")
    List<Person> getAllPersons();

    @Query("SELECT * FROM person_table WHERE person_id LIKE :id")
    Person findPersonById(int id);

    @Delete
    void deletePerson(Person person);

    @Update
    void updatePersons(Person person);

    @Insert
    void insertMultiplePersons(List<Person> personList);

    @Query("SELECT * FROM person_table WHERE person_compiled LIKE 1")
    List<Person> getAllCompiledPersons();

    @Query("SELECT * FROM person_table WHERE person_compiled LIKE 1")
    List<Person> getAllCompletedPersons();

    @Query("SELECT * FROM person_table")
    LiveData<List<Person>> findPersonsUsingLiveDataOnly();

    @Query("SELECT * FROM person_table")
    LiveData<List<Person>> findAllPersons();
}
