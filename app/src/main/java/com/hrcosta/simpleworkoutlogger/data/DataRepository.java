package com.hrcosta.simpleworkoutlogger.data;

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
    private ExerciseDao exerciseDao;
    private UserDao userDao;
    private WorkoutDao workoutDao;
    private WorkExerciseJoinDao workExerciseJoinDao;

    private LiveData<List<User>> allUsers;


    public DataRepository(Application application) {
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getInstance(application);
        exerciseDao = workoutDatabase.exerciseDao();
        userDao = workoutDatabase.userDao();
        workoutDao = workoutDatabase.workoutDao();
        workExerciseJoinDao = workoutDatabase.workExerciseJoinDao();

        allUsers = userDao.loadAllUsers();

    }

    /*
     ============================================================
        Exercise operations :
    */
    public LiveData<List<Exercise>> getAllExercises(){
        return exerciseDao.loadAllExercises();
    }

    public void insertExercise(Exercise exercise) {
        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void updateExercise(Exercise exercise) {
        new UpdateExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void deleteExercise(Exercise exercise) {
        new DeleteExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void deleteAllExercises() {
        new DeleteAllExercisesAsyncTask(exerciseDao).execute();
    }


    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao;

        public InsertExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.insert(exercises[0]);
            return null;
        }
    }

    private static class UpdateExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao;

        public UpdateExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.update(exercises[0]);
            return null;
        }
    }

    private static class DeleteExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {
        private ExerciseDao exerciseDao;

        public DeleteExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercises) {
            exerciseDao.delete(exercises[0]);
            return null;
        }
    }

    private static class DeleteAllExercisesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao;

        public DeleteAllExercisesAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            exerciseDao.deleteAll();
            return null;
        }
    }

    /*
     ============================================================
        Workout operations :
    */
    public LiveData<List<Workout>> getAllWorkouts(){
        return workoutDao.loadAllWorkouts();
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

    public LiveData<Workout> getWorkoutExercises(int id) {
        LiveData<Workout> workoutLiveData = workoutDao.loadWorkoutById(id);
        workoutLiveData = Transformations.switchMap(workoutLiveData, new Function<Workout, LiveData<Workout>>() {
            @Override
            public LiveData<Workout> apply(Workout inputWorkout) {
                LiveData<List<Exercise>> exercisesLiveData = workExerciseJoinDao.getExercisesForWorkout(inputWorkout.getId());
                LiveData<Workout> outputLiveData = Transformations.map(exercisesLiveData, new Function<List<Exercise>, Workout>() {
                    @Override
                    public Workout apply(List<Exercise> inputExercises) {
                        inputWorkout.setExercises(inputExercises);
                        return inputWorkout;
                    }
                });
                return outputLiveData;
            }
        });
        return workoutLiveData;
    }

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

    public LiveData<List<Workout>> getWorkoutsByDate(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        LiveData<List<Workout>> workoutsLiveData = workExerciseJoinDao.getWorkoutOnDateInt(day,month,year);

        workoutsLiveData = Transformations.map(workoutsLiveData, new Function<List<Workout>, List<Workout>>() {
            @Override
            public List<Workout> apply(final List<Workout> inputWorkouts) {
                for (Workout workout : inputWorkouts) {
                    //Unable to use LiveData in this request
                    //ref.  https://proandroiddev.com/android-room-handling-relations-using-livedata-2d892e40bd53

                    int workoutId = workout.getId();
                    Log.d("TAG", "workoutId: " + String.valueOf(workoutId));
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            workout.setExercises(workExerciseJoinDao.getExercisesListForWorkout(workout.getId()));
                        }
                    });
                }
                return inputWorkouts;
            }
        });

        return workoutsLiveData;
    }


    public List<WorkExerciseJoin> getAllWEJoin(){
        return workExerciseJoinDao.getAllWEJoin();
    }

    public void insertWorkExerciseJoin(WorkExerciseJoin workExerciseJoin) {
        new InsertJoinAsyncTask(workExerciseJoinDao).execute(workExerciseJoin);
    }

//    private static class getWorkoutsByDateAsyncTask extends AsyncTask<Date,Void,Void> {
//        private WorkExerciseJoinDao joinDao;
//        Calendar cal = Calendar.getInstance();
//
//        public getWorkoutsByDateAsyncTask(WorkExerciseJoinDao joinDao) {
//            this.joinDao = joinDao;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Date... dates) {
//
//            cal.setTime(dates[0]);
//            int day = cal.get(Calendar.DAY_OF_MONTH);
//            int month = cal.get(Calendar.MONTH) + 1;
//            int year = cal.get(Calendar.YEAR);
//            LiveData<List<Workout>> workoutsLiveData = joinDao.getWorkoutOnDateInt(day,month,year);
//
//            workoutsLiveData = Transformations.map(workoutsLiveData, new Function<List<Workout>, List<Workout>>() {
//                @Override
//                public List<Workout> apply(final List<Workout> inputWorkouts) {
//                    for (Workout workout : inputWorkouts) {
//                        //Unable to use LiveData in this request
//                        //ref.  https://proandroiddev.com/android-room-handling-relations-using-livedata-2d892e40bd53
//                        workout.setExercises(joinDao.getExercisesListForWorkout(workout.getId()));
//                    }
//                    return inputWorkouts;
//                }
//            });
//            return null;
//        }
//    }
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
