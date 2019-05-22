package com.hrcosta.simpleworkoutlogger.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

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


    public void addExerciseToWorkout(int exerciseId, int workoutId, Date date) {
        //Async task required to get the exercise details before creating the join object.
        new AddExerciseToWorkoutAsyncTask(exercisesRepository,workoutRepository,workoutId,exerciseId,date).execute();
    }


    private static class AddExerciseToWorkoutAsyncTask extends AsyncTask<Void, Void, Exercise> {
        private ExercisesRepository exercisesRepository;
        private WorkoutRepository workoutRepository;
        private int workoutId;
        private int exerciseId;
        private Date date;

        AddExerciseToWorkoutAsyncTask(ExercisesRepository exRepository, WorkoutRepository workRepository,
                                      int workoutId, int exerciseId, Date date) {
            this.exercisesRepository = exRepository;
            this.workoutRepository = workRepository;
            this.workoutId = workoutId;
            this.exerciseId = exerciseId;
            this.date = date;
        }

        @Override
        protected Exercise doInBackground(Void... voids) {
            return exercisesRepository.getExerciseWithId(exerciseId);
        }

        @Override
        protected void onPostExecute(Exercise exercise) {
            super.onPostExecute(exercise);
            WorkExerciseJoin workExerciseJoin = new WorkExerciseJoin(workoutId,exercise.getId(),date,exercise.getExName(),0);
            workoutRepository.insertWorkExerciseJoin(workExerciseJoin);
        }
    }

}
