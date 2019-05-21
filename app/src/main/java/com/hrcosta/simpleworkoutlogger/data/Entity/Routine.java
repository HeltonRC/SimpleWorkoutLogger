package com.hrcosta.simpleworkoutlogger.data.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "routine_table")
public class Routine implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String routine_name;


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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.routine_name);
    }

    @Ignore
    protected Routine(Parcel in) {
        this.id = in.readInt();
        this.routine_name = in.readString();
    }

    public static final Parcelable.Creator<Routine> CREATOR = new Parcelable.Creator<Routine>() {
        @Override
        public Routine createFromParcel(Parcel source) {
            return new Routine(source);
        }

        @Override
        public Routine[] newArray(int size) {
            return new Routine[size];
        }
    };
}
