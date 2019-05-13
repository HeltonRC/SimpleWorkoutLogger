package com.hrcosta.simpleworkoutlogger.data;

import android.app.Application;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ExerciseViewModel extends AndroidViewModel {

    private DataRepository repository;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allExercises = repository.getAllExercises();

    }

    public void Insert(Exercise exercise) {
        repository.insertExercise(exercise);
    }

    public void Update(Exercise exercise) {
        repository.updateExercise(exercise);
    }

    public void Delete(Exercise exercise) {
        repository.deleteExercise(exercise);
    }

    public void DeleteAllExercises() {
        repository.deleteAllExercises();
    }

}
