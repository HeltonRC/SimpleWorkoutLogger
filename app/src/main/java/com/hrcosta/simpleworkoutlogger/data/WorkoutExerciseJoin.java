package com.hrcosta.simpleworkoutlogger.data;

/*
    This class will be used for the database relationship of many to many for
    exercises and workout.
*/


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "workout_exercises_join",
        primaryKeys = { "workoutId", "exerciseId" },
        foreignKeys = {
                @ForeignKey(entity = Workout.class,
                        parentColumns = "id",
                        childColumns = "workoutId"),
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "exerciseId")
        })
public class WorkoutExerciseJoin {
    private int workoutId;
    private int exerciseId;

    @ColumnInfo(name = "log_date")
    private Date logDate;
    private int repetitions;

    public WorkoutExerciseJoin(int workoutId, int exerciseId, Date date, int repetitions) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.logDate = date;
        this.repetitions = repetitions;
    }
}
