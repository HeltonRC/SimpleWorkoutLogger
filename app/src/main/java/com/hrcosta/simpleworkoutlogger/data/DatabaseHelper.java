package com.hrcosta.simpleworkoutlogger.data;

import com.hrcosta.simpleworkoutlogger.data.DAO.WorkExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkoutDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class DatabaseHelper {

    private WorkoutDao workoutDao;
    private WorkExerciseJoinDao workExerciseJoinDao;

    public DatabaseHelper(WorkoutDatabase workoutDatabase) {
        this.workoutDao = workoutDatabase.workoutDao();
        this.workExerciseJoinDao = workoutDatabase.workExerciseJoinDao();
    }

    public LiveData<Workout> getWorkoutExercises(int id) {
        LiveData<Workout> workoutLiveData = workoutDao.loadWorkoutById(id);
        workoutLiveData = Transformations.switchMap(workoutLiveData, new Function<Workout, LiveData<Workout>>() {
            @Override
            public LiveData<Workout> apply(Workout inputWorkout) {
                LiveData<List<Exercise>> exercisesLiveData = workExerciseJoinDao.getExercisesForWorkout(inputWorkout.getId());
                LiveData<Workout> outputLiveData = Transformations.map(exercisesLiveData, new Function<List<Exercise>, Workout>() {
                    @Override
                    public Workout apply(List<Exercise> inputExercises) {
                        inputWorkout.setExercises(inputExercises);
                        return inputWorkout;
                    }
                });
                return outputLiveData;
            }
        });
        return workoutLiveData;
    }


    public LiveData<List<Workout>> getWorkoutsWithExercises() {
        LiveData<List<Workout>> workoutsLiveData = workoutDao.loadAllWorkouts();
        workoutsLiveData = Transformations.map(workoutsLiveData, new Function<List<Workout>, List<Workout>>() {
            @Override
            public List<Workout> apply(final List<Workout> inputWorkouts) {
                for (Workout workout : inputWorkouts) {
                    //Unable to use LiveData in this request
                    //ref.  https://proandroiddev.com/android-room-handling-relations-using-livedata-2d892e40bd53
                    workout.setExercises(workExerciseJoinDao.getExercisesListForWorkout(workout.getId()));
                }
                return inputWorkouts;
            }
        });
        return workoutsLiveData;
    }


    public void saveWorkout(Workout workout) {
        workoutDao.insert(workout);
        workoutDao.saveExercises(workout.getExercises());
    }


}
