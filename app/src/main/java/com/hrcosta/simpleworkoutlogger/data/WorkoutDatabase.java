package com.hrcosta.simpleworkoutlogger.data;

import android.content.Context;
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

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Exercise.class, User.class, Workout.class, WorkExerciseJoin.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class WorkoutDatabase extends RoomDatabase {

    private static WorkoutDatabase instance;

    private static final String LOG_TAG = WorkoutDatabase.class.getSimpleName();

    public abstract ExerciseDao exerciseDao();
    public abstract UserDao userDao();
    public abstract WorkoutDao workoutDao();
    public abstract WorkExerciseJoinDao workExerciseJoinDao();

    public static synchronized WorkoutDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(LOG_TAG, "Creating new database instance");

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WorkoutDatabase.class, "workout_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(databaseCallback)
                    .build();
        }

        return instance;
    }

    private static WorkoutDatabase.Callback databaseCallback = new WorkoutDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private UserDao userDao;
        private ExerciseDao exerciseDao;
        private WorkoutDao workoutDao;
        private WorkExerciseJoinDao workExerciseJoinDao;

        public PopulateDbAsyncTask(WorkoutDatabase db) {
            this.userDao = db.userDao();
            this.exerciseDao = db.exerciseDao();
            this.workoutDao = db.workoutDao();
            this.workExerciseJoinDao = db.workExerciseJoinDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.insert(new User(1,"user1","user1@email.com"));

            exerciseDao.insert(new Exercise(1,"exName1","exDescription1","category1"));
            exerciseDao.insert(new Exercise(2,"exName2","exDescription2","category1"));
            exerciseDao.insert(new Exercise(3,"exName3","exDescription3","category1"));
            exerciseDao.insert(new Exercise(4,"exName4","exDescription4","category1"));
            exerciseDao.insert(new Exercise(5,"exName5","exDescription5","category1"));

            workoutDao.insert(new Workout(1,"notes from workout 1"));
            workoutDao.insert(new Workout(2,"notes from workout 2"));
            workoutDao.insert(new Workout(3,"notes from workout 3"));
            workoutDao.insert(new Workout(4,"notes from workout 4"));
            workoutDao.insert(new Workout(5,"notes from workout 5"));

            workExerciseJoinDao.insert(new WorkExerciseJoin(1,1, new Date(),10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(2,2, new Date(),10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(3,3, new Date(),10));

            return null;
        }
    }




}
