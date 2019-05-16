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

    @Query("SELECT * " +
            "FROM exercise_table INNER JOIN work_exercises_join ON" +
            " exerciseId = work_exercises_join.exerciseId WHERE " +
            " work_exercises_join.workoutId=:workoutId")
    LiveData<List<Exercise>> getExercisesForWorkout (final int workoutId);


    @Query("SELECT * FROM exercise_table t1 " +
            "INNER JOIN work_exercises_join t2 " +
            "ON t1.Id = t2.exerciseId WHERE " +
            " t2.workoutId=:workoutId")
    List<WorkExerciseJoin> getExercisesListForWorkout(final int workoutId);


    @Query("SELECT * " +
            "FROM work_exercises_join " +
            "WHERE log_date = Date(:date)")
    LiveData<List<WorkExerciseJoin>> getWorkExeJoinOnDate(Date date);


    @Query("SELECT *, " +
            "CAST(strftime('%Y', datetime(log_date/1000, 'unixepoch')) AS int) AS year, " +
            "CAST(strftime('%m', datetime(log_date/1000, 'unixepoch')) AS int) AS month," +
            "CAST(strftime('%d', datetime(log_date/1000, 'unixepoch')) AS int) AS day " +
            "FROM work_exercises_join " +
            "WHERE day=:day AND month=:mon AND year=:year " +
            "ORDER BY log_date ASC ")
    LiveData<List<WorkExerciseJoin>> getWorkExeJoinOnDateInt(int day, int mon, int year);


    @Query("SELECT * " +
            "FROM workout_table t1 " +
            "INNER JOIN work_exercises_join t2 " +
            "ON t1.id = t2.workoutId " +
            "WHERE t2.log_date = Date(:date)")
    LiveData<List<Workout>> getWorkoutOnDate(Date date);


    @Query("SELECT *, " +
            "CAST(strftime('%Y', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS year, " +
            "CAST(strftime('%m', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS month, " +
            "CAST(strftime('%d', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS day " +
            "FROM workout_table t1 " +
            "INNER JOIN work_exercises_join t2 " +
            "ON t1.id = t2.workoutId " +
            "WHERE day=:day AND month=:mon AND year=:year")
    LiveData<Workout> getWorkoutOnDateInt(int day,int mon,int year);


    @Query("SELECT * FROM work_exercises_join ORDER BY log_date")
    List<WorkExerciseJoin> getAllWEJoin();


}
