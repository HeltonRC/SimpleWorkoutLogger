package com.hrcosta.simpleworkoutlogger.data.Entity;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "routine_table")
public class Routine {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String routine_name;

    @Ignore
    private List<Exercise> exercisesList;


    public Routine(String routine_name) {
        this.routine_name = routine_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoutine_name() {
        return routine_name;
    }

    public void setRoutine_name(String routine_name) {
        this.routine_name = routine_name;
    }

    public List<Exercise> getExercisesList() {
        return exercisesList;
    }

    public void setExercisesList(List<Exercise> exercisesList) {
        this.exercisesList = exercisesList;
    }
}
