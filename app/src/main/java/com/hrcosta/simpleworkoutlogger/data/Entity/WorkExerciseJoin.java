package com.hrcosta.simpleworkoutlogger.data.Entity;

/*
    This class will be used for the database relationship of many to many for
    exercises and workout.
*/


import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "work_exercises_join",
        primaryKeys = { "workoutId", "exerciseId" },
        foreignKeys = {
                @ForeignKey(entity = Workout.class,
                        parentColumns = "id",
                        childColumns = "workoutId"),
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "exerciseId")
        })

public class WorkExerciseJoin {
    private int workoutId;
    private int exerciseId;

    @ColumnInfo(name = "log_date")
    private Date logDate;
    @ColumnInfo(name = "ex_name")
    private String exerciseName;
    private int repetitions;

    public WorkExerciseJoin(int workoutId, int exerciseId, Date logDate, String exerciseName, int repetitions) {
        this.workoutId = workoutId;
        this.exerciseId = exerciseId;
        this.logDate = logDate;
        this.exerciseName = exerciseName;
        this.repetitions = repetitions;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setWorkoutId(int workoutId) {
        this.workoutId = workoutId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }
}
