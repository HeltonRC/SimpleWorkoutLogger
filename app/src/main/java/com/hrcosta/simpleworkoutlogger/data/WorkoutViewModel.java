package com.hrcosta.simpleworkoutlogger.data;

import android.app.Application;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WorkoutViewModel extends AndroidViewModel {

    private DataRepository repository;
    private LiveData<List<Workout>> allWorkouts;


    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allWorkouts = repository.getAllWorkouts();

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

}
