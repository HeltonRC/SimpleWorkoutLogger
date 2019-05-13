package com.hrcosta.simpleworkoutlogger.data.DAO;

import com.hrcosta.simpleworkoutlogger.data.DateConverter;
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

@Dao
@TypeConverters(DateConverter.class)
public interface WorkoutDao {

    @Insert
    void insert(Workout workout);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

    @Query("DELETE FROM workout_table")
    void deleteAll();

    @Query("SELECT *" +
            "FROM workout_table " +
            "ORDER BY id")
        LiveData<List<Workout>> loadAllWorkouts();


}
