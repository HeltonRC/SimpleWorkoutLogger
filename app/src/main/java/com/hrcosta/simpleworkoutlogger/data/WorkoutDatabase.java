package com.hrcosta.simpleworkoutlogger.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hrcosta.simpleworkoutlogger.data.DAO.ExerciseDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.RoutineDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.RoutineExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.UserDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkoutDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Routine;
import com.hrcosta.simpleworkoutlogger.data.Entity.RoutineExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.User;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Exercise.class, User.class, Workout.class, WorkExerciseJoin.class, Routine.class, RoutineExerciseJoin.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class WorkoutDatabase extends RoomDatabase {

    private static WorkoutDatabase instance;

    private static final String LOG_TAG = WorkoutDatabase.class.getSimpleName();

    public abstract ExerciseDao exerciseDao();
    public abstract RoutineDao routineDao();
    public abstract RoutineExerciseJoinDao routineExerciseJoinDao();
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
        private RoutineDao routineDao;
        private RoutineExerciseJoinDao routineExerciseJoinDao;
        private WorkoutDao workoutDao;
        private WorkExerciseJoinDao workExerciseJoinDao;

        public PopulateDbAsyncTask(WorkoutDatabase db) {
            this.userDao = db.userDao();
            this.exerciseDao = db.exerciseDao();
            this.routineDao = db.routineDao();
            this.routineExerciseJoinDao = db.routineExerciseJoinDao();
            this.workoutDao = db.workoutDao();
            this.workExerciseJoinDao = db.workExerciseJoinDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Date date1 = new GregorianCalendar(2019,04,01).getTime();
            Date date2 = new GregorianCalendar(2019,04,02).getTime();

            int exerciseId1 = (int) exerciseDao.insert(new Exercise("Leg extension","Leg exercise","Quadriceps"));
            int exerciseId2 = (int) exerciseDao.insert(new Exercise("Leg press","Leg exercise","Quadriceps"));
            int exerciseId3 = (int) exerciseDao.insert(new Exercise("Lunge","Leg exercise","Quadriceps"));
            int exerciseId4 = (int) exerciseDao.insert(new Exercise("Squat","Leg exercise","Quadriceps"));
            int exerciseId5 = (int) exerciseDao.insert(new Exercise("Good-morning","Leg exercise","Hamstrings"));
            int exerciseId6 = (int) exerciseDao.insert(new Exercise("Leg curl","Leg exercise","Hamstrings"));
            int exerciseId7 = (int) exerciseDao.insert(new Exercise("Deadlift","Leg exercise","Hamstrings"));
            int exerciseId8 = (int) exerciseDao.insert(new Exercise("Calf raise","Calves exercise","Calves"));
            int exerciseId9 = (int) exerciseDao.insert(new Exercise("Bench press","Chest exercise","Pectorals"));
            int exerciseId10= (int) exerciseDao.insert(new Exercise("Chest fly","Chest exercise","Pectorals"));
            int exerciseId11= (int) exerciseDao.insert(new Exercise("Dips","Chest exercise","Pectorals"));
            int exerciseId12= (int) exerciseDao.insert(new Exercise("Machine fly","Chest exercise","Pectorals"));


            int routineA = (int) routineDao.insert(new Routine("routine A"));
            int routineB = (int) routineDao.insert(new Routine("routine B"));

            routineExerciseJoinDao.insert(new RoutineExerciseJoin(routineA,exerciseId1));
            routineExerciseJoinDao.insert(new RoutineExerciseJoin(routineA,exerciseId2));
            routineExerciseJoinDao.insert(new RoutineExerciseJoin(routineA,exerciseId3));
            routineExerciseJoinDao.insert(new RoutineExerciseJoin(routineA,exerciseId4));

            routineExerciseJoinDao.insert(new RoutineExerciseJoin(routineB,exerciseId3));
            routineExerciseJoinDao.insert(new RoutineExerciseJoin(routineB,exerciseId2));
            routineExerciseJoinDao.insert(new RoutineExerciseJoin(routineB,exerciseId4));


            int workoutId = (int) workoutDao.insert(new Workout("notes from workout 1"));
            workExerciseJoinDao.insert(new WorkExerciseJoin(workoutId,exerciseId1,date1,"Leg extension",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(workoutId,exerciseId2,date1,"Leg press",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(workoutId,exerciseId3,date1,"Lunge",13));

            workoutId = (int) workoutDao.insert(new Workout("notes from workout 2"));
            workExerciseJoinDao.insert(new WorkExerciseJoin(workoutId,exerciseId2,date2,"Leg press",10));
            workExerciseJoinDao.insert(new WorkExerciseJoin(workoutId,exerciseId3,date2,"exName3",14));
            workExerciseJoinDao.insert(new WorkExerciseJoin(workoutId,exerciseId4,date2,"Squat",10));


            return null;
        }
    }




}
