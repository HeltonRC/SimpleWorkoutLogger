package com.hrcosta.simpleworkoutlogger.data.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo (name = "user_name")
    private String userName;
    @ColumnInfo (name = "user_email")
    private String userEmail;


    public User(int id, String userName, String userEmail) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
