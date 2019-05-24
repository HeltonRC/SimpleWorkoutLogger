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
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hrcosta.simpleworkoutlogger.Adapters.CalendarListAdapter;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
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
    private static final String STATE_DATE = "state_date";
    private static final String STATE_WORKOUT = "state_workout";
    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private boolean shouldShow = false;
    private String mUser;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @BindView(R.id.tv_user) TextView tvUser;
    @BindView(R.id.btn_arrow) ImageButton btnArrow;
    @BindView(R.id.tv_date) TextView tvDate;
    @BindView(R.id.tv_list_title) TextView tvListTitle;
    @BindView(R.id.tv_workoutnotes) TextView tvWorkoutNotes;
    @BindView(R.id.btn_editnote) ImageButton btnEditNote;
    @BindView(R.id.fab_add_exercise) FloatingActionButton fabAddExercise;
    @BindView(R.id.compactcalendar_view) CompactCalendarView compactCalendarView;
    @BindView(R.id.rv_calendarlist) RecyclerView recyclerView;

    private ActionBar toolbar;
    private Date mDateSelected;
    private Workout mCurrentWorkout = null;
    private CalendarActivityViewModel calendarActivityViewModel;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        mUser = firebaseUser.getEmail();
        tvUser.setText(mUser);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mUser);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);

        final View.OnClickListener exposeCalendarListener = getCalendarExposeLis();
        btnArrow.setOnClickListener(exposeCalendarListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        CalendarListAdapter calendarListAdapter = new CalendarListAdapter();
        recyclerView.setAdapter(calendarListAdapter);

        calendarActivityViewModel = ViewModelProviders.of(this).get(CalendarActivityViewModel.class);



        if (savedInstanceState != null) {
            mDateSelected = (Date) savedInstanceState.getSerializable(STATE_DATE);
            tvDate.setText(dateFormatForDisplaying.format(mDateSelected));
        } else {
            mDateSelected = currentCalender.getTime();
        }

        calendarActivityViewModel.setDate(mDateSelected);
        compactCalendarView.setCurrentDate(mDateSelected);

        setupObservers(calendarListAdapter);

        btnEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentWorkout !=null) {
                    startDialogToUpdateNotes(mCurrentWorkout);
                }
            }
        });

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

    }

    //onActivityResult is being used to receive the value of the routines activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == ADD_EXERCISE_REQUEST) {
            int exerciseId = data.getExtras().getInt(EXTRA_EXERCISE_ID);
            calendarActivityViewModel.addExerciseToWorkout(exerciseId,mDateSelected);
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
            startActivity(new Intent(CalendarActivity.this,MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
                        btnArrow.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    } else {
                        compactCalendarView.hideCalendarWithAnimation();
                        btnArrow.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage(getString(R.string.exit_app_dialog))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);

                    }
                }).setNegativeButton(getString(R.string.no), null).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(STATE_DATE, mDateSelected);
        savedInstanceState.putParcelable(STATE_WORKOUT, mCurrentWorkout);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void selectExerciseToEdit(WorkExerciseJoin workExerciseJoin) {
        new showExerciseDialogAsyncTask(this,workExerciseJoin).execute();
    }

    private void setupObservers(CalendarListAdapter calendarListAdapter) {
        //Get the workouts to populate the Notes for the day
        calendarActivityViewModel.getWorkoutByDate().observe(this, new Observer<Workout>() {
            @Override
            public void onChanged(Workout workout) {
                if (workout!=null) {
                    tvListTitle.setText(getResources().getString(R.string.exercises_completed));
                    tvWorkoutNotes.setText(workout.getNotes());
                    mCurrentWorkout = workout;
                } else {
                    mCurrentWorkout = null;
                    tvWorkoutNotes.setText(R.string.no_logs);
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

        //add events to the calendar
        calendarActivityViewModel.getDatesOfEvents().observe(this, new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> dates) {
                List<Event> events = new ArrayList<>();
                events.clear();
                for (Date d : dates) {
                    Event event = new Event(Color.BLUE, d.getTime());
                    events.add(event);
                }
                compactCalendarView.removeAllEvents();
                compactCalendarView.addEvents(events);
            }
        });
    }

    private void startDialogToUpdateNotes(Workout workout) {
        Dialog notesDialog = new Dialog(this);
        notesDialog.setContentView(R.layout.dialog_editnotes);
        notesDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvNoteDay = notesDialog.findViewById(R.id.tv_note_day);
        TextView etNote = notesDialog.findViewById(R.id.et_work_notes);
        Button btnSaveNote = notesDialog.findViewById(R.id.btn_editnotes);

        tvNoteDay.setText(dateFormatForDisplaying.format(mDateSelected));
        etNote.setText(workout.getNotes());
        etNote.requestFocus();

        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = etNote.getText().toString();
                calendarActivityViewModel.updateWorkoutNote(workout,note);
                notesDialog.dismiss();
            }
        });
        notesDialog.show();
    }

    private class showExerciseDialogAsyncTask extends AsyncTask<Void, Void, Exercise> {
        Context mContext;
        Dialog myDialog;
        WorkExerciseJoin workExerciseJoin;

        public showExerciseDialogAsyncTask(Context context, WorkExerciseJoin workExerciseJoin) {
            this.mContext = context;
            this.workExerciseJoin = workExerciseJoin;
        }

        @Override
        protected Exercise doInBackground(Void... voids) {
            Exercise exercise = calendarActivityViewModel.getExerciseDetailFromJoin(workExerciseJoin);
            return exercise;
        }

        @Override
        protected void onPostExecute(Exercise exercise) {

            myDialog = new Dialog(mContext);
            myDialog.setContentView(R.layout.dialog_exercise);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView tvName = myDialog.findViewById(R.id.tv_exname);
            TextView tvDescription = myDialog.findViewById(R.id.tv_description);
            EditText etReps = myDialog.findViewById(R.id.et_reps);
            Button btnRemoveEx = myDialog.findViewById(R.id.btn_dialogremove);
            Button btnConfirm = myDialog.findViewById(R.id.btn_dialogconfirm);

            tvName.setText(exercise.getExName());
            tvDescription.setText(exercise.getExDescription());
            etReps.setText(String.valueOf(workExerciseJoin.getRepetitions()));
            etReps.requestFocus();

//            If the keyboard is forced up it does not close along with the dialog.
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

            btnRemoveEx.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendarActivityViewModel.removeExerciseFromWorkout(workExerciseJoin);
                    Toast.makeText(mContext, "Exercise removed.", Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                }
            });

            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int reps = Integer.parseInt(etReps.getText().toString());
                    calendarActivityViewModel.updateRepsInWorkoutExercise(workExerciseJoin, reps);
                    Toast.makeText(mContext, "Exercise updated.", Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                }
            });

            myDialog.show();
        }
    }



}
