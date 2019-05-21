package com.hrcosta.simpleworkoutlogger.data.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.hrcosta.simpleworkoutlogger.data.DAO.RoutineDao;
import com.hrcosta.simpleworkoutlogger.data.DAO.RoutineExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Routine;
import com.hrcosta.simpleworkoutlogger.data.Entity.RoutineExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Transaction;

public class RoutineRepository {

    private RoutineDao routineDao;
    private RoutineExerciseJoinDao routineExerciseJoinDao;
    private WorkoutDatabase mDatabase;

    public RoutineRepository(Application application) {
        mDatabase = WorkoutDatabase.getInstance(application);
        routineDao = mDatabase.routineDao();
        routineExerciseJoinDao = mDatabase.routineExerciseJoinDao();
    }


    public List<Routine> getAllRoutines(){
        return routineDao.loadAllRoutines();
    }

    public LiveData<List<Exercise>> getExercisesForRoutine(int routine) {
        return routineExerciseJoinDao.getExercisesForRoutine(routine);
    }

    public LiveData<List<RoutineExerciseJoin>> getRoutinesExeJoins() {
        return routineExerciseJoinDao.getAllREJoin();
    }

    public void addExerciseToRoutine(int exerciseId, int routineId){
        RoutineExerciseJoin routineExerciseJoin = new RoutineExerciseJoin(routineId,exerciseId);
        new RoutineRepository.AddExerciseToRoutineAsyncTask(routineExerciseJoinDao).execute(routineExerciseJoin);
    }



    public int insertRoutine(Routine routine) {
        int result = 0;
        try {
            InsertRoutineAsyncTask task = new InsertRoutineAsyncTask(routineDao);
            result = task.execute(routine).get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public void updateRoutine(Routine routine) {
        new RoutineRepository.UpdateRoutineAsyncTask(routineDao).execute(routine);
    }

    public void deleteRoutine(Routine routine) {
        //TODO run it in a asynctask
        mDatabase.runInTransaction(() -> {
            try {
                routineExerciseJoinDao.deleteFromRoutine(routine.getId());
                routineDao.delete(routine);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }


    //---------------Async Tasks---------------
    private static class InsertRoutineAsyncTask extends AsyncTask<Routine, Void, Integer> {
        private RoutineDao routineDao;
        private int routineId;
//        private ResultListener listener;
//
//        //listener interface
//        public interface ResultListener {
//            void ResultListener(int result);
//        }

        public InsertRoutineAsyncTask(RoutineDao routineDao) {
            this.routineDao = routineDao;
        }

        @Override
        protected Integer doInBackground(Routine... routines) {
            routineId = (int) routineDao.insert(routines[0]);
            return routineId;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
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

//    private static class DeleteRoutineAsyncTask extends AsyncTask<Routine, Void, Void> {
//        private RoutineDao routineDao;
//        private RoutineExerciseJoinDao joinDao;
//
//        public DeleteRoutineAsyncTask(RoutineDao routineDao, RoutineExerciseJoinDao joinDao) {
//            this.routineDao = routineDao;
//            this.joinDao = joinDao;
//        }
//
//        @Override
//        protected Void doInBackground(Routine... routines) {
//            try {
//                workoutDatabase.runInTransaction(new Runnable(){
//                    @Override
//                    public void run(){
//                        Access all your daos here
//                    }
//                });
//
//
//                routineDao.delete(routines[0]);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            return null;
//        }
//    }


    private static class AddExerciseToRoutineAsyncTask extends AsyncTask<RoutineExerciseJoin, Void, Void> {
        private RoutineExerciseJoinDao joinDao;

        public AddExerciseToRoutineAsyncTask(RoutineExerciseJoinDao joinDao) {
            this.joinDao = joinDao;
        }

        @Override
        protected Void doInBackground(RoutineExerciseJoin... routineExerciseJoins) {
            joinDao.insert(routineExerciseJoins[0]);
            return null;
        }
    }


    }




