package com.hrcosta.simpleworkoutlogger.ViewModel;

import android.app.Application;

import com.hrcosta.simpleworkoutlogger.data.Repository.UserRepository;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;
import com.hrcosta.simpleworkoutlogger.data.Repository.WorkoutRepository;

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

    private WorkoutRepository workoutRepository;
    private LiveData<List<WorkExerciseJoin>> exerciseJoinLiveData;
    private LiveData<Workout> workoutLiveData;
    private MutableLiveData<Date> calendarDate = new MutableLiveData<Date>();

    public CalendarActivityViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);

        exerciseJoinLiveData = Transformations.switchMap(calendarDate,
                v -> workoutRepository.getWorkExerciseJoinByDate(v));

        workoutLiveData = Transformations.switchMap(calendarDate,
                v -> workoutRepository.getWorkoutByDate(v));

    }

    public void Insert(Workout workout) {
        workoutRepository.insertWorkout(workout);
    }

    public void Update(Workout workout) {
        workoutRepository.updateWorkout(workout);
    }

    public void Delete(Workout workout) {
        workoutRepository.deleteWorkout(workout);
    }

    public void DeleteAllWorkouts() {
        workoutRepository.deleteAllWorkouts();
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
        return workoutRepository.getAllWEJoin();
    }

    public List<Date> getDatesOfEvents() {
        return workoutRepository.getDatesOfEvents();
    }


}
