package com.hrcosta.simpleworkoutlogger.data;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DataRepository {
    private ExerciseDao exerciseDao;
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private WorkoutExerciseJoinDao workoutExerciseJoinDao;

    private LiveData<List<Exercise>> listExercises;
    private LiveData<List<Workout>> listWorkouts;
    private LiveData<List<User>> listUsers;


    public DataRepository(Application application) {
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getInstance(application);
        exerciseDao = workoutDatabase.exerciseDao();
        userDao = workoutDatabase.userDao();
        workoutDao = workoutDatabase.workoutDao();
        workoutExerciseJoinDao = workoutDatabase.workoutExerciseJoinDao();

        listExercises = exerciseDao.loadAllExercises();
        listUsers = userDao.loadAllUsers();
        listWorkouts = workoutDao.loadAllWorkouts();

        //TODO finish this repository class creating all the CRUD commands required for the app.

    }
}
