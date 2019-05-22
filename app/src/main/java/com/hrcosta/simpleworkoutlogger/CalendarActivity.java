package com.hrcosta.simpleworkoutlogger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;
import com.hrcosta.simpleworkoutlogger.ViewModel.CalendarActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private static final String DATEARG = "date";
    private static final int ADD_EXERCISE_REQUEST = 1;
    private static final String EXTRA_EXERCISE_ID = "exerciseid";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private boolean shouldShow = false;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @BindView(R.id.tv_user) TextView tvUser;
    @BindView(R.id.tv_arrow) TextView tvArrow;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_list_title) TextView tvListTitle;
    @BindView(R.id.tv_details) TextView tvDetails;

    @BindView(R.id.fab_add_exercise) FloatingActionButton fabAddExercise;
    @BindView(R.id.compactcalendar_view) CompactCalendarView compactCalendarView;
    @BindView(R.id.rv_calendarlist) RecyclerView recyclerView;

    private ActionBar toolbar;
    private Date mDateSelected;
    private int mCurrentWorkoutId;
    private CalendarActivityViewModel calendarActivityViewModel;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

    //todo change the Workout Notes to an edittext allowing to update it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        tvUser.setText(firebaseUser.getEmail());

        final View.OnClickListener exposeCalendarListener = getCalendarExposeLis();
        tvArrow.setOnClickListener(exposeCalendarListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        CalendarListAdapter calendarListAdapter = new CalendarListAdapter();
        recyclerView.setAdapter(calendarListAdapter);

        calendarActivityViewModel = ViewModelProviders.of(this).get(CalendarActivityViewModel.class);

        mDateSelected = currentCalender.getTime();
        calendarActivityViewModel.setDate(mDateSelected);
        tvDate.setText(dateFormatForDisplaying.format(mDateSelected));

        setupObservers(calendarListAdapter);


        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            //start routines activity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, RoutinesActivity.class);
                intent.putExtra(DATEARG,mDateSelected);
                startActivityForResult(intent,ADD_EXERCISE_REQUEST);
            }
        });

        toolbar = this.getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                mDateSelected = dateClicked;
                tvDate.setText(dateFormatForDisplaying.format(dateClicked));
                calendarActivityViewModel.setDate(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                addEventIndicators();
            }
        });

    }


    private void addEventIndicators() {
        List<Date> dates = calendarActivityViewModel.getDatesOfEvents();
        List<Event> events = new ArrayList<>();

        for (Date d : dates) {
            Event event = new Event(Color.BLUE, d.getTime());
            events.add(event);
        }

        compactCalendarView.addEvents(events);
//     snippet on how to add events to the calendar
//        formatter.setLenient(false);
//        Date curDate = new Date();
//        long curMillis = curDate.getTime();
//        String curTime = formatter.format(curDate);
//
//        String oldTime = "05.05.2019, 12:45";
//        Date oldDate = null;
//        try {
//            oldDate = formatter.parse(oldTime);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long oldMillis = oldDate.getTime();
//
//        Event ev1 = new Event(Color.GREEN, oldMillis, "Some extra data that I want to store.");
//
//        compactCalendarView.addEvent(ev1);
    }

    private void setupObservers(CalendarListAdapter calendarListAdapter) {
        //Get the workouts to populate the Notes for the day
        calendarActivityViewModel.getWorkoutByDate().observe(this, new Observer<Workout>() {
            @Override
            public void onChanged(Workout workout) {
                if (workout!=null) {
                    tvListTitle.setText(getResources().getString(R.string.exercises_completed));
                    tvDetails.setText(workout.getNotes());
                    mCurrentWorkoutId = workout.getId();
                } else {
                    tvDetails.setText(R.string.no_logs);
                }
            }
        });

        //Get the list of exercises with the reps
        calendarActivityViewModel.getExercisesDoneOnDate().observe(this, new Observer<List<WorkExerciseJoin>>() {
            @Override
            public void onChanged(List<WorkExerciseJoin> workExerciseJoins) {
                calendarListAdapter.setWorkoutList(workExerciseJoins);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ADD_EXERCISE_REQUEST) {
            int exerciseId = data.getExtras().getInt(EXTRA_EXERCISE_ID);

            //TODO CHECK IF WORKOUTID IS NULL OR 0, HAVE TO CREATE A NEW ONE.
            calendarActivityViewModel.addExerciseToWorkout(exerciseId,mCurrentWorkoutId,mDateSelected);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(CalendarActivity.this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @NonNull
    private View.OnClickListener getCalendarExposeLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendarWithAnimation();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_up_black_24dp );
                        tvArrow.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                    } else {
                        compactCalendarView.hideCalendarWithAnimation();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_down_black_24dp );
                        tvArrow.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("No", null).show();
    }


}
