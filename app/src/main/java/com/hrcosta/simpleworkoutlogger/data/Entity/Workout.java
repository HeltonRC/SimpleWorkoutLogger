package com.hrcosta.simpleworkoutlogger.data.Entity;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "workout_table")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String notes;

    @Ignore
    private List<WorkExerciseJoin> exercisesDone;

    //    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id")
    //    @ColumnInfo (name = "user_id")
    //    private int userId;


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

    public List<WorkExerciseJoin> getExercisesDone() {
        return exercisesDone;
    }

    public void setExercisesDone(List<WorkExerciseJoin> exercisesDone) {
        this.exercisesDone = exercisesDone;
    }
}
