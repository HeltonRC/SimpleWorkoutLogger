package com.hrcosta.simpleworkoutlogger.ViewModel;

import android.app.Application;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Routine;
import com.hrcosta.simpleworkoutlogger.data.Repository.RoutineRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RoutinesViewModel extends AndroidViewModel {

    private RoutineRepository routineRepository;

    public RoutinesViewModel(@NonNull Application application) {
        super(application);
        routineRepository = new RoutineRepository(application);
    }

    public int Insert(Routine routine) {
        return routineRepository.insertRoutine(routine);
    }

    public void Update(Routine routine) {
        routineRepository.updateRoutine(routine);
    }

    public void Delete(Routine routine) {
        routineRepository.deleteRoutine(routine);
    }


    public List<Routine> getRoutines() {
        return routineRepository.getAllRoutines();
    }

    public LiveData<List<Exercise>> getExercisesOfRoutine (int routineId){
        return routineRepository.getExercisesForRoutine(routineId);
    }

    public void addExerciseToRoutine(int exerciseId, int routineId){
         routineRepository.addExerciseToRoutine(exerciseId,routineId);
    }


}
