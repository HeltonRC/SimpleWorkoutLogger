package com.hrcosta.simpleworkoutlogger.data;

import java.util.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "workout_table")
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String notes;



//    @Ignore
//    private List<Exercise> exercises;

//    @Ignore
//    private int UserId;

    //username / list of exercises

}
