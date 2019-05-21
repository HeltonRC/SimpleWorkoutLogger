package com.hrcosta.simpleworkoutlogger.ViewModel;

import android.app.Application;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Repository.ExercisesRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ExerciseListViewModel extends AndroidViewModel {

    private ExercisesRepository repository;
    private LiveData<List<Exercise>> allExercises;

    public ExerciseListViewModel(@NonNull Application application) {
        super(application);
        repository = new ExercisesRepository(application);
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

    public LiveData<List<Exercise>> getAllExercises (){
        return repository.getAllExercises();
    }

}
