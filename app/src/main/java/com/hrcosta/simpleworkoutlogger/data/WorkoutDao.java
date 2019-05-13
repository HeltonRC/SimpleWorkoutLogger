package com.hrcosta.simpleworkoutlogger.data;

import java.util.Date;
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
    void insertTask(Workout workout);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Workout workout);

    @Delete
    void deleteTask(Workout workout);


    @Query("SELECT *" +
            "FROM workout_table " +
            "ORDER BY id")
        LiveData<List<Workout>> loadAllWorkouts();


}
