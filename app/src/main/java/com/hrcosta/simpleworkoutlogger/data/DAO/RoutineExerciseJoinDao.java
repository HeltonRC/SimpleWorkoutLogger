package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.RoutineExerciseJoin;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RoutineExerciseJoinDao {

    @Insert
    void insert(RoutineExerciseJoin routineExerciseJoin);

    @Delete
    void delete(RoutineExerciseJoin routineExerciseJoin);

    @Query("SELECT t1.id, t1.ex_name, t1.ex_description, t1.ex_category " +
            "FROM exercise_table t1 INNER JOIN routine_exe_join t2 ON" +
            " t1.id = t2.exerciseId WHERE " +
            " t2.routineId=:routineId " +
            "ORDER BY t2.id")
    LiveData<List<Exercise>> getExercisesForRoutine (final int routineId);

    @Query("SELECT * FROM routine_exe_join")
    LiveData<List<RoutineExerciseJoin>> getAllREJoin();

    @Query("DELETE FROM routine_exe_join WHERE routineId =:routineId")
    void deleteFromRoutine(int routineId);

}
