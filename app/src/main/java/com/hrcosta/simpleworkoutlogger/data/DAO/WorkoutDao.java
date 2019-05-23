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

    @Query("SELECT id " +
            "FROM workout_table " +
            "WHERE id=:id")
    Integer loadWorkoutIdById(int id);

    @Query("SELECT * " +
            "FROM work_exercises_join " +
            "WHERE workoutId=:id")
    List<WorkExerciseJoin> loadJoinsByWorkoutId(int id);

    @Query("UPDATE workout_table SET notes=:note WHERE id = :id")
    void saveNoteToWorkout(int id, String note);
}
