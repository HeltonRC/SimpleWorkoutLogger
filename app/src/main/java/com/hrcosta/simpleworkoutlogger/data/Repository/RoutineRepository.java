package com.hrcosta.simpleworkoutlogger.data.Repository;

import android.app.Application;

import com.hrcosta.simpleworkoutlogger.data.DAO.RoutineDao;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

public class RoutineRepository {

    private RoutineDao routineDao;


    public RoutineRepository(Application application) {
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getInstance(application);
        routineDao = workoutDatabase.routineDao();


    }
}
