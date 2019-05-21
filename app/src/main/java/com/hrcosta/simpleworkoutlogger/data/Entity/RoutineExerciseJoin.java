package com.hrcosta.simpleworkoutlogger.data.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "routine_exe_join",
//        indices = {@Index(value = {"routineId"},unique = true)},
        foreignKeys = {
                @ForeignKey(entity = Routine.class,
                        parentColumns = "id",
                        childColumns = "routineId"),
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "exerciseId")
        })
public class RoutineExerciseJoin {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int routineId;
    private int exerciseId;

    public RoutineExerciseJoin(int routineId, int exerciseId) {
        this.routineId = routineId;
        this.exerciseId = exerciseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
}
