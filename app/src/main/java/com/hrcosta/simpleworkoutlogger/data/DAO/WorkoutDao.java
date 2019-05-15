package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.DateConverter;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters(DateConverter.class)
public interface WorkoutDao {

    @Insert
    long insert(Workout workout);

    @Insert(onConflict = REPLACE)
    void saveExercises(List<Exercise> exercises);

    @Update(onConflict = REPLACE)
    void update(Workout workout);

    @Delete
    void delete(Workout workout);


    @Query("DELETE FROM workout_table")
    void deleteAll();

    @Query("SELECT *" +
            "FROM workout_table " +
            "ORDER BY id")
    LiveData<List<Workout>> loadAllWorkouts();

    @Query("SELECT *" +
            "FROM workout_table " +
            "WHERE id=:id")
    LiveData<Workout> loadWorkoutById(int id);


    @Query("SELECT * " +
            "FROM work_exercises_join " +
            "WHERE workoutId=:id")
    List<WorkExerciseJoin> loadJoinsByWorkoutId(int id);

}
