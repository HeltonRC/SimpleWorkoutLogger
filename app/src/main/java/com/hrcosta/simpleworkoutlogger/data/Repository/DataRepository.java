package com.hrcosta.simpleworkoutlogger.data.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.hrcosta.simpleworkoutlogger.data.DAO.ExerciseDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.UserDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkoutDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.User;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

/*
This class is an abstraction layer between the data sources and the View Model component of the app.
*/

public class DataRepository {
    private UserDao userDao;
    private WorkExerciseJoinDao workExerciseJoinDao;
    private LiveData<List<User>> allUsers;


    public DataRepository(Application application) {
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getInstance(application);
        userDao = workoutDatabase.userDao();
        workExerciseJoinDao = workoutDatabase.workExerciseJoinDao();

        allUsers = userDao.loadAllUsers();
    }

    /*
     ============================================================
        Workout operations :
    */


//    public LiveData<Workout> getWorkoutExercises(int id) {
//        LiveData<Workout> workoutLiveData = workoutDao.loadWorkoutById(id);
//        workoutLiveData = Transformations.switchMap(workoutLiveData, new Function<Workout, LiveData<Workout>>() {
//            @Override
//            public LiveData<Workout> apply(Workout inputWorkout) {
//                LiveData<List<Exercise>> exercisesLiveData = workExerciseJoinDao.getExercisesForWorkout(inputWorkout.getId());
//                LiveData<Workout> outputLiveData = Transformations.map(exercisesLiveData, new Function<List<Exercise>, Workout>() {
//                    @Override
//                    public Workout apply(List<Exercise> inputExercises) {
//                        inputWorkout.setExercisesDone(inputExercises);
//                        return inputWorkout;
//                    }
//                });
//                return outputLiveData;
//            }
//        });
//        return workoutLiveData;
//    }

    /*
     ============================================================
        User operations :
    */

    public LiveData<List<User>> getAllUsers(){
        return userDao.loadAllUsers();
    }

    public void insertUser(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void deleteUser(User user) {
        new DeleteUserAsyncTask(userDao).execute(user);
    }

    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public DeleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }


    /*
     ============================================================
        Workout Exercise Join operations :
    */

    public LiveData<List<Exercise>> getExercisesForWorkout(Workout workout){
        return workExerciseJoinDao.getExercisesForWorkout(workout.getId());
    }


    public LiveData<List<WorkExerciseJoin>> getWorkExerciseJoinByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) +1;
        int year = cal.get(Calendar.YEAR);

        return workExerciseJoinDao.getWorkExeJoinOnDateInt(day,month,year);
    }

    public LiveData<Workout> getWorkoutByDate(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        LiveData<Workout> workoutLiveData = workExerciseJoinDao.getWorkoutOnDateInt(day,month,year);

        // ------ TO BE RUN IF IS REQUIRED TO FILL THE LIST OF EXERCISES INSIDE THE WORKOUT ENTITY
//        workoutLiveData = Transformations.map(workoutLiveData, new Function<List<Workout>, List<Workout>>() {
//            @Override
//            public List<Workout> apply(final List<Workout> inputWorkouts) {
//                for (Workout workout : inputWorkouts) {
//                    //Unable to use LiveData in this request
//                    //ref.  https://proandroiddev.com/android-room-handling-relations-using-livedata-2d892e40bd53
//
//                    int workoutId = workout.getId();
//                    Log.d("TAG", "workoutId: " + String.valueOf(workoutId));
//                    AsyncTask.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            workout.setExercisesDone(workExerciseJoinDao.getExercisesListForWorkout(workout.getId()));
//                        }
//                    });
//                }
//                return inputWorkouts;
//            }
//        });

        return workoutLiveData;
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

}
