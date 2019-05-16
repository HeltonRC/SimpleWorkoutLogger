package com.hrcosta.simpleworkoutlogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;
import com.hrcosta.simpleworkoutlogger.data.ViewModel.CalendarActivityViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

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

    private CalendarActivityViewModel calendarActivityViewModel;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

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

        Date date = currentCalender.getTime();
        calendarActivityViewModel.setDate(date);
        tvDate.setText(dateFormatForDisplaying.format(date));

        calendarActivityViewModel.getWorkoutByDate().observe(this, new Observer<Workout>() {
            @Override
            public void onChanged(Workout workout) {
                if (workout!=null) {
                    tvListTitle.setText(getResources().getString(R.string.exercises_completed));
                    tvDetails.setText(workout.getNotes());
                } else {
                    tvDetails.setText("Nothing today =(");
                }
            }
        });

        calendarActivityViewModel.getExercisesDoneOnDate().observe(this, new Observer<List<WorkExerciseJoin>>() {
            @Override
            public void onChanged(List<WorkExerciseJoin> workExerciseJoins) {
                calendarListAdapter.setWorkoutList(workExerciseJoins);
            }
        });

        fabAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalendarActivity.this, "Add Exercise function", Toast.LENGTH_SHORT).show();
                //TODO Start activity to add exercises




            }
        });


        toolbar = this.getSupportActionBar();
        toolbar.setTitle(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                tvDate.setText(dateFormatForDisplaying.format(dateClicked));
                calendarActivityViewModel.setDate(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                toolbar.setTitle(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
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
    private View.OnClickListener getCalendarShowLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendar();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_up_black_24dp );
                        tvArrow.setCompoundDrawables(img,null,null,null);
                    } else {
                        compactCalendarView.hideCalendar();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_down_black_24dp );
                        tvArrow.setCompoundDrawables(img,null,null,null);
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
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




}
