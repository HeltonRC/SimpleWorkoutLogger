package com.hrcosta.simpleworkoutlogger.data.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.hrcosta.simpleworkoutlogger.data.DAO.RoutineDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.RoutineExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.WorkoutDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Routine;
import com.hrcosta.simpleworkoutlogger.data.Entity.RoutineExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class RoutineRepository {

    private RoutineDao routineDao;
    private RoutineExerciseJoinDao routineExerciseJoinDao;


    public RoutineRepository(Application application) {
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getInstance(application);
        routineDao = workoutDatabase.routineDao();
        routineExerciseJoinDao = workoutDatabase.routineExerciseJoinDao();
    }


    public List<Routine> getAllRoutines(){
//        return new RoutineRepository.LoadAllRoutinesAsyncTask(routineDao).execute().get();

        return routineDao.loadAllRoutines();
    }

    public LiveData<List<Exercise>> getExercisesForRoutine(int routine) {
        return routineExerciseJoinDao.getExercisesForRoutine(routine);
    }

    public LiveData<List<RoutineExerciseJoin>> getRoutinesExeJoins() {
        return routineExerciseJoinDao.getAllREJoin();
    }

    public void insertRoutine(Routine routine) {
        new RoutineRepository.InsertRoutineAsyncTask(routineDao).execute(routine);
    }

    public void updateRoutine(Routine routine) {
        new RoutineRepository.UpdateRoutineAsyncTask(routineDao).execute(routine);
    }

    public void deleteRoutine(Routine routine) {
        new RoutineRepository.DeleteRoutineAsyncTask(routineDao).execute(routine);
    }

    private static class InsertRoutineAsyncTask extends AsyncTask<Routine, Void, Void> {
        private RoutineDao routineDao;

        public InsertRoutineAsyncTask(RoutineDao routineDao) {
            this.routineDao = routineDao;
        }

        @Override
        protected Void doInBackground(Routine... routines) {
            routineDao.insert(routines[0]);
            return null;
        }
    }

    private static class UpdateRoutineAsyncTask extends AsyncTask<Routine, Void, Void> {
        private RoutineDao routineDao;

        public UpdateRoutineAsyncTask(RoutineDao routineDao) {
            this.routineDao = routineDao;
        }

        @Override
        protected Void doInBackground(Routine... routines) {
            routineDao.update(routines[0]);
            return null;
        }
    }

    private static class DeleteRoutineAsyncTask extends AsyncTask<Routine, Void, Void> {
        private RoutineDao routineDao;

        public DeleteRoutineAsyncTask(RoutineDao routineDao) {
            this.routineDao = routineDao;
        }

        @Override
        protected Void doInBackground(Routine... routines) {
            routineDao.delete(routines[0]);
            return null;
        }
    }

    }




