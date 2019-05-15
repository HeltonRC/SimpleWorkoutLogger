package com.hrcosta.simpleworkoutlogger.data.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo (name = "ex_name")
    private String exName;
    @ColumnInfo (name = "ex_description")
    private String exDescription;
    @ColumnInfo (name = "ex_category")
    private String exCategory;

    @Ignore
    public Exercise(String exName, String exDescription, String exCategory) {
        this.exName = exName;
        this.exDescription = exDescription;
        this.exCategory = exCategory;
    }


    public Exercise(int id, String exName, String exDescription, String exCategory) {
        this.id = id;
        this.exName = exName;
        this.exDescription = exDescription;
        this.exCategory = exCategory;
    }

    public int getId() {
        return id;
    }

    public String getExName() {
        return exName;
    }

    public String getExDescription() {
        return exDescription;
    }

    public String getExCategory() {
        return exCategory;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public void setExDescription(String exDescription) {
        this.exDescription = exDescription;
    }

    public void setExCategory(String exCategory) {
        this.exCategory = exCategory;
    }
}
