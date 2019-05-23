package com.hrcosta.simpleworkoutlogger.data.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.hrcosta.simpleworkoutlogger.data.DAO.ExerciseDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkoutDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private ExerciseDao exerciseDao;
    private WorkExerciseJoinDao workExerciseJoinDao;


    public WorkoutRepository(Application application) {
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getInstance(application);
        workoutDao = workoutDatabase.workoutDao();
        exerciseDao = workoutDatabase.exerciseDao();
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

    public LiveData<List<Date>> getDatesOfEvents() {
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
        LiveData<Workout> workoutLiveData = workExerciseJoinDao.getWLiveDataOnDate(day,month,year);

        return workoutLiveData;
    }

    public void addExerciseToWorkout(int exerciseId, Date date) {
        new AddExerciseToWorkoutAsyncTask(exerciseDao,workoutDao,workExerciseJoinDao,exerciseId,date).execute();
    }

    public void removeExerciseFromWorktout(WorkExerciseJoin workExerciseJoin) {
        new DeleteWorkoutExerciseJoinAsyncTask(workExerciseJoinDao).execute(workExerciseJoin);
    }

    public void updateRepsInWorkoutExercise(WorkExerciseJoin workExerciseJoin, int reps) {
        new UpdateWEJoinAsyncTask(workExerciseJoinDao,workExerciseJoin,reps).execute();
    }


    //---------------Async Tasks---------------
    private static class AddExerciseToWorkoutAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao;
        private WorkoutDao workoutDao;
        private WorkExerciseJoinDao workExerciseJoinDao;
        private int workoutId = -1;
        private int exerciseId;
        private Date date;

        public AddExerciseToWorkoutAsyncTask(ExerciseDao exerciseDao, WorkoutDao workoutDao,
                                             WorkExerciseJoinDao workExerciseJoinDao, int exerciseId, Date date) {
            this.exerciseDao = exerciseDao;
            this.workoutDao = workoutDao;
            this.workExerciseJoinDao = workExerciseJoinDao;
            this.exerciseId = exerciseId;
            this.date = date;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Exercise exercise = exerciseDao.loadExerciseById(exerciseId);
            //check if there is a workout on the date selected
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);

            Workout workout = workExerciseJoinDao.getWorkoutIdOnDate(day,month,year);
            if (workout==null){
                workoutId = (int) workoutDao.insert(new Workout("Workout Notes."));
            } else {
                workoutId = workout.getId();
            }

            WorkExerciseJoin workExerciseJoin = new WorkExerciseJoin(workoutId,exercise.getId(),date,exercise.getExName(),0);
            workExerciseJoinDao.insert(workExerciseJoin);

            return null;
        }


    }

    private static class InsertJoinAsyncTask extends AsyncTask<WorkExerciseJoin, Void, Void> {
        private WorkExerciseJoinDao workExerciseJoinDao;

        public InsertJoinAsyncTask(WorkExerciseJoinDao joinDao) {
            this.workExerciseJoinDao = joinDao;
        }

        @Override
        protected Void doInBackground(WorkExerciseJoin... workExerciseJoins) {
            workExerciseJoinDao.insert(workExerciseJoins[0]);
            return null;
        }
    }


    private static class InsertWorkoutAsyncTask extends AsyncTask<Workout, Void, Long> {
        private WorkoutDao workoutDao;

        public InsertWorkoutAsyncTask(WorkoutDao workoutDao) {
            this.workoutDao = workoutDao;
        }

        @Override
        protected Long doInBackground(Workout... workouts) {
            return workoutDao.insert(workouts[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
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

    private static class DeleteWorkoutExerciseJoinAsyncTask extends AsyncTask<WorkExerciseJoin, Void, Void> {
        private WorkExerciseJoinDao workExerciseJoinDao;

        public DeleteWorkoutExerciseJoinAsyncTask(WorkExerciseJoinDao workExerciseJoinDao) {
            this.workExerciseJoinDao = workExerciseJoinDao;
        }

        @Override
        protected Void doInBackground(WorkExerciseJoin... joins) {
            workExerciseJoinDao.delete(joins[0]);
            return null;
        }
    }

    private static class UpdateWEJoinAsyncTask extends AsyncTask<Void, Void, Void> {
        private WorkExerciseJoinDao workExerciseJoinDao;
        private WorkExerciseJoin workExerciseJoin;
        private int reps;

        public UpdateWEJoinAsyncTask(WorkExerciseJoinDao workExerciseJoinDao, WorkExerciseJoin workExerciseJoin, int reps) {
            this.workExerciseJoinDao = workExerciseJoinDao;
            this.workExerciseJoin = workExerciseJoin;
            this.reps = reps;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            workExerciseJoinDao.updateWEJoin(workExerciseJoin.getId(),reps);
            return null;
        }
    }

}
