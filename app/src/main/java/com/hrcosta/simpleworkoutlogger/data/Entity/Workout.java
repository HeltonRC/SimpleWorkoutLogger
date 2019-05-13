package com.hrcosta.simpleworkoutlogger.data.Entity;

import java.util.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "workout_table")
public class Workout {

    @PrimaryKey
    private int id;
    private String notes;



//    @Ignore
//    private List<Exercise> exercises;

//    @Ignore
//    private int UserId;

    //username / list of exercises
    @Ignore
    public Workout(String notes) {
        this.notes = notes;
    }

    public Workout(int id, String notes) {
        this.id = id;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
