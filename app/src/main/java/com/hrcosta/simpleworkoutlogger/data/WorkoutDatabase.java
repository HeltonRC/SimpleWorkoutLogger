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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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
            //userDao.insert(new User(1,"user1","user1@email.com"));

            Date date1 = new GregorianCalendar(2019,05,01).getTime();
            Date date2 = new GregorianCalendar(2019,05,02).getTime();
            Date date3 = new GregorianCalendar(2019,05,03).getTime();


            long exerciseId1 = exerciseDao.insert(new Exercise("exName1","exDescription1","category1"));
            long exerciseId2 = exerciseDao.insert(new Exercise("exName3","exDescription3","category1"));
            long exerciseId3 = exerciseDao.insert(new Exercise("exName4","exDescription4","category1"));
            long exerciseId4 = exerciseDao.insert(new Exercise("exName5","exDescription5","category1"));

            int intExeId1 = (int) (long) exerciseId1;
            int intExeId2 = (int) (long) exerciseId2;
            int intExeId3 = (int) (long) exerciseId3;
            int intExeId4 = (int) (long) exerciseId4;

            long workoutId = workoutDao.insert(new Workout("notes from workout 1"));
            int intWorkId = (int) (long) workoutId;

            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId1,date1,"exName1",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId2,date1,"exName2",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId3,date1,"exName3",13));

            workoutId = workoutDao.insert(new Workout("notes from workout 2"));
            intWorkId = (int) (long) workoutId;
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId2,date2,"exName2",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId3,date2,"exName3",14));
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId4,date2,"exName4",10));

            workoutId = workoutDao.insert(new Workout("notes from workout 3"));
            intWorkId = (int) (long) workoutId;
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId3,date2,"exName3",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId4,date2,"exName4",11));
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId2,date2,"exName2",12));


            workoutId = workoutDao.insert(new Workout("notes from workout 4"));
            intWorkId = (int) (long) workoutId;
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId1,date3,"exName1",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(intWorkId,intExeId3,date3,"exName3",13));

            return null;
        }
    }




}
