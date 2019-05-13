package com.hrcosta.simpleworkoutlogger.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ExerciseDao {

    @Insert
    void insertExercise(Exercise exercise);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateExercise(Exercise exercise);

    @Delete
    void deleteExercise(Exercise exercise);

    @Query("SELECT * FROM exercise_table ORDER BY ex_name")
    LiveData<List<Exercise>> loadAllExercises();

    @Query("SELECT * FROM exercise_table WHERE id = :id")
    LiveData<Exercise> loadExerciseById(int id);



}
