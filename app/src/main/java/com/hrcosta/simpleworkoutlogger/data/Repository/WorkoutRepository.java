package com.hrcosta.simpleworkoutlogger.data.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.hrcosta.simpleworkoutlogger.data.DAO.WorkExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkoutDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.RoutineExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private WorkExerciseJoinDao workExerciseJoinDao;


    public WorkoutRepository(Application application) {
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getInstance(application);
        workoutDao = workoutDatabase.workoutDao();
        workExerciseJoinDao = workoutDatabase.workExerciseJoinDao();

    }

    public void insertWorkout(Workout workout) {
        new InsertWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public void updateWorkout(Workout workout) {
        new UpdateWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public void deleteWorkout(Workout workout) {
        new DeleteWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public void deleteAllWorkouts() {
        new DeleteAllWorkoutsAsyncTask(workoutDao).execute();
    }


    public LiveData<List<WorkExerciseJoin>> getWorkExerciseJoinByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) +1;
        int year = cal.get(Calendar.YEAR);

        return workExerciseJoinDao.getWorkExeJoinOnDateInt(day,month,year);
    }

    public List<Date> getDatesOfEvents() {
        return workExerciseJoinDao.getDatesOfEvents();
    }

    public List<WorkExerciseJoin> getAllWEJoin(){
        return workExerciseJoinDao.getAllWEJoin();
    }

    public void insertWorkExerciseJoin(WorkExerciseJoin workExerciseJoin) {
        new InsertJoinAsyncTask(workExerciseJoinDao).execute(workExerciseJoin);
    }

    public LiveData<Workout> getWorkoutByDate(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        LiveData<Workout> workoutLiveData = workExerciseJoinDao.getWorkoutOnDateInt(day,month,year);

        return workoutLiveData;
    }


    //---------------Async Tasks---------------

    private static class InsertJoinAsyncTask extends AsyncTask<WorkExerciseJoin, Void, Void> {
        private WorkExerciseJoinDao joinDao;

        public InsertJoinAsyncTask(WorkExerciseJoinDao joinDao) {
            this.joinDao = joinDao;
        }

        @Override
        protected Void doInBackground(WorkExerciseJoin... workExerciseJoins) {
            joinDao.insert(workExerciseJoins[0]);
            return null;
        }
    }


    private static class InsertWorkoutAsyncTask extends AsyncTask<Workout, Void, Void> {
        private WorkoutDao workoutDao;

        public InsertWorkoutAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.insert(workouts[0]);
            return null;
        }
    }

    private static class UpdateWorkoutAsyncTask extends AsyncTask<Workout, Void, Void> {
        private WorkoutDao workoutDao;

        public UpdateWorkoutAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.update(workouts[0]);
            return null;
        }
    }

    private static class DeleteWorkoutAsyncTask extends AsyncTask<Workout, Void, Void> {
        private WorkoutDao workoutDao;

        public DeleteWorkoutAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.delete(workouts[0]);
            return null;
        }
    }

    private static class DeleteAllWorkoutsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WorkoutDao workoutDao;

        public DeleteAllWorkoutsAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            workoutDao.deleteAll();
            return null;
        }
    }






}
