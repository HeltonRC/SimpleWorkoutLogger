package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WorkExerciseJoinDao {

    @Insert
    void insert(WorkExerciseJoin workExerciseJoin);

//    @Query("SELECT * FROM WORKOUT_TABLE INNER JOIN work_exercises_join ON " +
//            " workoutId = work_exercises_join.workoutId " +
//            "WHERE work_exercises_join.exerciseId=:exerciseId")
//            List<Workout> getWorkoutsForExercise(final int exerciseId);


    @Query("SELECT * " +
            "FROM exercise_table INNER JOIN work_exercises_join ON" +
            " exerciseId = work_exercises_join.exerciseId WHERE " +
            " work_exercises_join.workoutId=:workoutId")
    LiveData<List<Exercise>> getExercisesForWorkout (final int workoutId);



    @Query("SELECT * " +
            "FROM   work_exercises_join t1 " +
            "INNER JOIN exercise_table t2 " +
            "ON t1.exerciseId = t2.id " +
            "INNER JOIN workout_table t3 " +
            "ON t1.workoutId = t3.id " +
            "WHERE t1.log_date = :date")
    List<WorkExerciseJoin> getWorkoutsOnDate(final Date date);


}
