package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.RoutineExerciseJoin;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RoutineExerciseJoinDao {

    @Insert
    void insert(RoutineExerciseJoin routineExerciseJoin);

    @Query("SELECT * " +
            "FROM exercise_table t1 INNER JOIN routine_exe_join t2 ON" +
            " t1.id = t2.exerciseId WHERE " +
            " t2.routineId=:routineId " +
            "ORDER BY t2.id")
    LiveData<List<Exercise>> getExercisesForRoutine (final int routineId);

    @Query("SELECT * FROM routine_exe_join")
    LiveData<List<RoutineExerciseJoin>> getAllREJoin();
}
