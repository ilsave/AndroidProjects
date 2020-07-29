package ru.gushchin.politexmark.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SubjectDao {

    @Insert
    void insertSubject(Subject subject);

    @Query("SELECT * FROM subject_table WHERE subject_id")
    List<Subject> getAllSubject();

    @Query("SELECT * FROM subject_table WHERE subject_id LIKE :id")
    Subject findSubjectById(int id);

    @Delete
    void deleteSubject(Subject subject);

    @Update
    void updateSubject(Subject subject);

    @Insert
    void insertMultipleSubjects(List<Subject> personList);

    @Query("SELECT * FROM subject_table")
    LiveData<List<Subject>> findPersonsUsingLiveDataOnly();

    @Query("SELECT * FROM subject_table")
    LiveData<List<Subject>> findAllPersons();

    @Query("DELETE FROM subject_table")
    void deleteAllSubjects();

}
