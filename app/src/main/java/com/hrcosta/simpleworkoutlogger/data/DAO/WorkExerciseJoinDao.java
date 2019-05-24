package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface WorkExerciseJoinDao {

    @Insert
    void insert(WorkExerciseJoin workExerciseJoin);

    @Delete
    void delete(WorkExerciseJoin workExerciseJoin);

//    @Query("SELECT * " +
//            "FROM exercise_table t1 INNER JOIN work_exercises_join t2 ON" +
//            " t1.id = t2.exerciseId WHERE " +
//            " t2.workoutId=:workoutId " +
//            "ORDER BY t2.id")
//    LiveData<List<Exercise>> getExercisesForWorkout (final int workoutId);
//
//
//    @Query("SELECT * FROM exercise_table t1 " +
//            "INNER JOIN work_exercises_join t2 " +
//            "ON t1.Id = t2.exerciseId WHERE " +
//            " t2.workoutId=:workoutId " +
//            "ORDER BY t2.id")
//    List<WorkExerciseJoin> getExercisesListForWorkout(final int workoutId);


    @Query("SELECT *, " +
            "CAST(strftime('%Y', datetime(log_date/1000, 'unixepoch')) AS int) AS year, " +
            "CAST(strftime('%m', datetime(log_date/1000, 'unixepoch')) AS int) AS month," +
            "CAST(strftime('%d', datetime(log_date/1000, 'unixepoch')) AS int) AS day " +
            "FROM work_exercises_join " +
            "WHERE day=:day AND month=:mon AND year=:year " +
            "ORDER BY log_date ASC ")
    LiveData<List<WorkExerciseJoin>> getWorkExeJoinOnDateInt(int day, int mon, int year);


    @Query("SELECT t1.id, t1.notes, " +
            "CAST(strftime('%Y', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS year, " +
            "CAST(strftime('%m', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS month, " +
            "CAST(strftime('%d', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS day " +
            "FROM workout_table t1 " +
            "INNER JOIN work_exercises_join t2 " +
            "ON t1.id = t2.workoutId " +
            "WHERE day=:day AND month=:mon AND year=:year " +
            "ORDER BY t2.id")
    LiveData<Workout> getWLiveDataOnDate(int day, int mon, int year);

    @Query("SELECT t1.id, t1.notes, " +
            "CAST(strftime('%Y', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS year, " +
            "CAST(strftime('%m', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS month, " +
            "CAST(strftime('%d', datetime(t2.log_date/1000, 'unixepoch')) AS int) AS day " +
            "FROM workout_table t1 " +
            "INNER JOIN work_exercises_join t2 " +
            "ON t1.id = t2.workoutId " +
            "WHERE day=:day AND month=:mon AND year=:year " +
            "ORDER BY t2.id")
    Workout getWorkoutIdOnDate(int day,int mon,int year);


    @Query("SELECT * FROM work_exercises_join ORDER BY id")
    List<WorkExerciseJoin> getAllWEJoin();


    @Query("SELECT log_date FROM work_exercises_join ORDER by id")
    LiveData<List<Date>> getDatesOfEvents();

    @Query("UPDATE work_exercises_join SET repetitions=:reps WHERE id = :id")
    void updateWEJoin(int id, int reps);

    @Query("SELECT * FROM work_exercises_join WHERE log_date<date('now') ORDER BY log_date DESC LIMIT 1")
    WorkExerciseJoin getLastWorkoutDone();


    @Query("SELECT ex_name FROM work_exercises_join WHERE workoutId=:workoutId")
    List<String> getListOfExercisesForWorkout(int workoutId);
}
