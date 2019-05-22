package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;

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
    long insert(Exercise exercise);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exercise_table")
    void deleteAll();

    @Query("SELECT * FROM exercise_table ORDER BY ex_name")
    LiveData<List<Exercise>> loadAllExercises();

    @Query("SELECT * FROM exercise_table WHERE id = :id")
    Exercise loadExerciseById(int id);
}
