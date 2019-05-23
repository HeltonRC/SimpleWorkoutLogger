package com.hrcosta.simpleworkoutlogger.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Repository.ExercisesRepository;
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

import static android.content.ContentValues.TAG;


public class CalendarActivityViewModel extends AndroidViewModel {

    private WorkoutRepository workoutRepository;
    private ExercisesRepository exercisesRepository;
    private LiveData<List<WorkExerciseJoin>> exerciseJoinLiveData;
    private LiveData<Workout> workoutLiveData;
    private MutableLiveData<Date> calendarDate = new MutableLiveData<Date>();

    public CalendarActivityViewModel(@NonNull Application application) {
        super(application);
        workoutRepository = new WorkoutRepository(application);
        exercisesRepository = new ExercisesRepository(application);

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

    public void removeExerciseFromWorkout(WorkExerciseJoin workExerciseJoin) {
        workoutRepository.removeExerciseFromWorktout(workExerciseJoin);
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

    public LiveData<List<Date>> getDatesOfEvents() {
        return workoutRepository.getDatesOfEvents();
    }

    public void addExerciseToWorkout(int exerciseId, Date date) {
        workoutRepository.addExerciseToWorkout(exerciseId,date);
    }

    public Exercise getExerciseDetailFromJoin(WorkExerciseJoin workExerciseJoin) {
        return exercisesRepository.getExerciseWithId(workExerciseJoin.getExerciseId());
    }


    public void updateRepsInWorkoutExercise(WorkExerciseJoin workExerciseJoin, int reps) {
        workoutRepository.updateRepsInWorkoutExercise(workExerciseJoin,reps);
    }
}
