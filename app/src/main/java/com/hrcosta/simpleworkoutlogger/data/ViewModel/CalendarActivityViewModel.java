package com.hrcosta.simpleworkoutlogger.data.ViewModel;

import android.app.Application;
import android.view.animation.Transformation;

import com.hrcosta.simpleworkoutlogger.data.DataRepository;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/*
    View Model class that will load the data required in the calendar view.
*/

public class CalendarActivityViewModel extends AndroidViewModel {

    private DataRepository repository;
    private LiveData<List<Workout>> allWorkouts;
    private LiveData<List<WorkExerciseJoin>> exerciseJoinLiveData;
    private LiveData<Workout> workoutLiveData;
    private MutableLiveData<Date> calendarDate = new MutableLiveData<Date>();

    public CalendarActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
       // allWorkouts = repository.getAllWorkouts();
        exerciseJoinLiveData = Transformations.switchMap(calendarDate,
                v -> repository.getWorkExerciseJoinByDate(v));

        workoutLiveData = Transformations.switchMap(calendarDate,
                v -> repository.getWorkoutByDate(v));

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

    public LiveData<List<WorkExerciseJoin>> getExercisesDoneOnDate(){
        return exerciseJoinLiveData;
    }

    public LiveData<Workout> getWorkoutByDate() {
        return workoutLiveData;
    }

    public void setDate(Date date){
        calendarDate.setValue(date);
    }

    public List<WorkExerciseJoin> getAllWEJoin(){
        return repository.getAllWEJoin();
    }



//    public LiveData<List<Workout>> getAllWorkouts() {
//        return repository.getAllWorkouts();
//    }
//
//    public LiveData<List<Exercise>> getExercisesOfWorkout(Workout workout){
//        return repository.getExercisesForWorkout(workout);
//    }





}
