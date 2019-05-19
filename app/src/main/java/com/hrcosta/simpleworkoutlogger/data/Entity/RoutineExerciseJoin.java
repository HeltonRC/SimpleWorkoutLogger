package com.hrcosta.simpleworkoutlogger.data.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(
        tableName = "routine_exe_join",
        primaryKeys = { "routineId", "exerciseId" },
        foreignKeys = {
                @ForeignKey(entity = Routine.class,
                        parentColumns = "id",
                        childColumns = "routineId"),
                @ForeignKey(entity = Exercise.class,
                        parentColumns = "id",
                        childColumns = "exerciseId")
        })
public class RoutineExerciseJoin {
    private int routineId;
    private int exerciseId;

    public RoutineExerciseJoin(int routineId, int exerciseId) {
        this.routineId = routineId;
        this.exerciseId = exerciseId;
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
