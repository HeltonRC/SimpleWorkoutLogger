package com.hrcosta.simpleworkoutlogger.data;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WorkoutExerciseJoinDao {

    @Insert
    void insert(WorkoutExerciseJoin workoutExerciseJoin);

    // may not be required
    @Query("SELECT * FROM WORKOUT_TABLE INNER JOIN workout_exercises_join ON " +
            " workoutId = workout_exercises_join.workoutId " +
            "WHERE workout_exercises_join.exerciseId=:exerciseId")
            List<Workout> getWorkoutsForExercise(final int exerciseId);


    @Query("SELECT * FROM exercise_table INNER JOIN workout_exercises_join ON" +
            " exerciseId = workout_exercises_join.exerciseId WHERE " +
            " workout_exercises_join.workoutId=:workoutId")
            List<Exercise> getExercisesForWorkout (final int workoutId);



    @Query("SELECT *" +
            "FROM   workout_exercises_join t1 " +
            "INNER JOIN exercise_table t2 " +
            "ON t1.exerciseId = t2.id " +
            "INNER JOIN workout_table t3 " +
            "ON t1.workoutId = t3.id " +
            "WHERE t1.log_date = :date")
    List<WorkoutExerciseJoin> getWorkoutsOnDate(final Date date);


}
