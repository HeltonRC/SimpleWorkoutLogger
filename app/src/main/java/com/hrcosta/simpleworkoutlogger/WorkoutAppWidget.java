package com.hrcosta.simpleworkoutlogger;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.hrcosta.simpleworkoutlogger.data.DAO.WorkExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class WorkoutAppWidget extends AppWidgetProvider {

    private static SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    private List<String> exercisesListStr = null;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, WorkExerciseJoin workExerciseJoin, String exercisesStr) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.workout_app_widget);
        if (workExerciseJoin!=null) {
            views.setTextViewText(R.id.appwidget_date, dateFormatForDisplaying.format(workExerciseJoin.getLogDate()));
            views.setTextViewText(R.id.appwidget_details, exercisesStr);

            views.setOnClickPendingIntent(R.id.appwidget_layout, pendingIntent);

        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        new GetDetailsForWidgetAsyncTask(appWidgetManager, appWidgetIds,context).execute();
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private class GetDetailsForWidgetAsyncTask extends AsyncTask<Void,Void,String> {
        AppWidgetManager appWidgetManager;
        int[] appWidgetIds;
        WorkExerciseJoin workExerciseJoin;
        Context context;

        public GetDetailsForWidgetAsyncTask(AppWidgetManager appWidgetManager, int[] appWidgetIds, Context context) {
            this.appWidgetManager = appWidgetManager;
            this.appWidgetIds = appWidgetIds;
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... voids) {

            List<String> exercisesList = null;
            String formattedList = "";

            WorkoutDatabase db = WorkoutDatabase.getInstance(context);
            WorkExerciseJoinDao workExerciseJoinDao = db.workExerciseJoinDao();
            workExerciseJoin = workExerciseJoinDao.getLastWorkoutDone();
            exercisesList = workExerciseJoinDao.getListOfExercisesForWorkout(workExerciseJoin.getWorkoutId());

            for (String s: exercisesList) {
                formattedList = formattedList.concat(s + "\n");
            }

            return formattedList;
        }


        @Override
        protected void onPostExecute(String exercisesStr) {
            super.onPostExecute(exercisesStr);

            if(exercisesStr != null){
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, workExerciseJoin,exercisesStr);
                }

            }
        }
    }
}

