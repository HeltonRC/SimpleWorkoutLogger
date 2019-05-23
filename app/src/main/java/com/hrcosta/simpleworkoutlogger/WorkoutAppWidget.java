package com.hrcosta.simpleworkoutlogger;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.hrcosta.simpleworkoutlogger.data.DAO.WorkExerciseJoinDao;
import com.hrcosta.simpleworkoutlogger.data.WorkoutDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Implementation of App Widget functionality.
 */
public class WorkoutAppWidget extends AppWidgetProvider {

    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    private List<String> exercisesListStr = null;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        new AsyncTask<Context, Void, List<String>>(){
            @Override
            protected List<String> doInBackground(Context... context) {
                Date lastWorkoutDate = null;
                List<String> List = null;
                WorkoutDatabase db = WorkoutDatabase.getInstance(context[0]);
                WorkExerciseJoinDao workExerciseJoinDao = db.workExerciseJoinDao();

                Date date = null;
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                date = calendar.getTime();

                List = workExerciseJoinDao.getLastDateOfWorkout(date);


                return List;
            }

            @Override
            protected void onPostExecute(List<Quote> List) {
                super.onPostExecute(List);

                if(List != null){

                    final Random random=new Random();
                    for (int appWidgetId : appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId, quoteList.get(random.nextInt(List.size())));
                    }

                }

            }

        }.execute(context);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.workout_app_widget);
        if (workoutExerciseJoin!=null) {

            views.setTextViewText(R.id.appwidget_date, widgetText);
            views.setTextViewText(R.id.appwidget_details, widgetText);


        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

