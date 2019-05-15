package com.hrcosta.simpleworkoutlogger.data.ViewModel;

import android.app.Application;

import com.hrcosta.simpleworkoutlogger.data.DataRepository;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/*
    View Model class that will load the data required in the calendar view.
*/

public class CalendarActivityViewModel extends AndroidViewModel {

    private DataRepository repository;
    private LiveData<List<Workout>> allWorkouts;


    public CalendarActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
       // allWorkouts = repository.getAllWorkouts();

    }


    public void Insert(Workout workout) {
        repository.insertWorkout(workout);
    }

    public void Update(Workout workout) {
        repository.updateWorkout(workout);
    }

    public void Delete(Workout workout) {
        repository.deleteWorkout(workout);
    }

    public void DeleteAllWorkouts() {
        repository.deleteAllWorkouts();
    }

    public LiveData<List<Workout>> getAllWorkouts() {

        return repository.getAllWorkouts();
    }

    public LiveData<List<Exercise>> getExercisesOfWorkout(Workout workout){

        return repository.getExercisesForWorkout(workout);
    }

    public LiveData<List<Workout>> getWorkoutByDate(Date date) {
        LiveData<List<Workout>> result = repository.getWorkoutsByDate(date);


        return result;
    }



    public List<WorkExerciseJoin> getAllWEJoin(){
        return repository.getAllWEJoin();
    }



}
