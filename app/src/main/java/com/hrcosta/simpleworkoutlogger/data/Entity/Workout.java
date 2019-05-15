package com.hrcosta.simpleworkoutlogger.data.Entity;

import java.util.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "workout_table")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String notes;

//    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
//    @ColumnInfo (name = "user_id")
//    private int userId;

    @Ignore
    private List<Exercise> exercises;

    public Workout(String notes) {
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

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
