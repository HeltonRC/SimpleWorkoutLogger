package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Routine;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface RoutineDao {

    @Insert
    long insert(Routine routine);

    @Insert(onConflict = REPLACE)
    void saveExercisesToRoutine(List<Exercise> exercises);

    @Update(onConflict = REPLACE)
    void update(Routine routine);

    @Delete
    void delete(Routine routine);

    @Query("DELETE * FROM routine_table")
    void deleteAll();

    @Query("SELECT * FROM routine_table")
    List<Routine> selectAllRoutines();


}