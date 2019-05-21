package com.hrcosta.simpleworkoutlogger;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.hrcosta.simpleworkoutlogger.ViewModel.RoutinesViewModel;
import com.hrcosta.simpleworkoutlogger.data.Entity.Routine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutinesActivity extends AppCompatActivity {

    private static final String DATEARG = "date";
    @BindView(R.id.tablayout_id) TabLayout tabLayout;
    @BindView(R.id.viewpager_id) ViewPager viewPager;
    @BindView(R.id.btn_addtab) ImageButton btnAddTab;


    private String mNewRoutineName;
    private RoutinesViewPagerAdapter mAdapter;
    private RoutinesViewModel mRoutinesViewModel;
    private List<Routine> mRoutines;
    private Date mSelectedDate;
    private ActionBar toolbar;
    private SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);
        ButterKnife.bind(this);

        mRoutinesViewModel = ViewModelProviders.of(this).get(RoutinesViewModel.class);
        mAdapter = new RoutinesViewPagerAdapter(getSupportFragmentManager());

        mSelectedDate = (Date) this.getIntent().getExtras().get(DATEARG);
        toolbar = this.getSupportActionBar();
        toolbar.setTitle(dateFormatForDisplaying.format(mSelectedDate));

        new loadAllExercisesAsyncTask(this, mRoutinesViewModel).execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_routines, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            deleteSelectedRoutine();
        }
        return super.onOptionsItemSelected(item);
    }



    private void deleteSelectedRoutine() {

        int position = viewPager.getCurrentItem();
        Routine routine = mRoutines.get(position);

        mRoutinesViewModel.Delete(routine);


        new loadAllExercisesAsyncTask(this, mRoutinesViewModel).execute();
//
//        mRoutines.remove(position);
//
//        RoutinesFragment currentFragment  = mAdapter.getItem(position);
//        mAdapter.removeFragment(currentFragment,position);
//
//        mAdapter.notifyDataSetChanged();
//        if (position > 0) {
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
//        }

    }



    private void updateFragments(List<Routine> routines) {

        mRoutines = routines;
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);

        for (Routine r : routines) {
            mAdapter.AddFragment(RoutinesFragment.newInstance(r.getId()), r.getRoutine_name());
            mAdapter.notifyDataSetChanged();
        }

        btnAddTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(v.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(v.getContext()).setIcon(android.R.drawable.ic_dialog_info).setTitle(getString(R.string.new_routine_alert_title))
                        .setMessage(getString(R.string.please_imput_routine_name))
                        .setView(input)
                        .setPositiveButton(getString(R.string.ok_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mNewRoutineName = input.getText().toString();
                                int routineid = mRoutinesViewModel.Insert(new Routine(mNewRoutineName));
                                mAdapter.AddFragment(RoutinesFragment.newInstance(routineid), mNewRoutineName);
                                mAdapter.notifyDataSetChanged();
                                viewPager.setAdapter(mAdapter);
                                viewPager.setCurrentItem(mAdapter.getCount(),true);

                            }
                        }).setNegativeButton(getString(R.string.cancel_button), null).show();
            }
        });

    }





    private static class loadAllExercisesAsyncTask extends AsyncTask<Void, Void, Void> {
        private List<Routine> routineList = new ArrayList<>();
        private RoutinesViewModel routinesViewModel;
        private RoutinesActivity activity;


        public loadAllExercisesAsyncTask(RoutinesActivity activity, RoutinesViewModel routinesViewModel) {
            this.activity = activity;
            this.routinesViewModel = routinesViewModel;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            routineList = routinesViewModel.getRoutines();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.updateFragments(routineList);
        }
    }


//    private static class insertRoutineAsyncTask extends AsyncTask<Routine, Void, Void> {
//        private RoutinesViewModel routinesViewModel;
//        private RoutinesActivity activity;
//        private Routine routine;
//
//
//        public insertRoutineAsyncTask(RoutinesActivity activity, RoutinesViewModel routinesViewModel) {
//            this.activity = activity;
//            this.routinesViewModel = routinesViewModel;
//        }
//
//        @Override
//        protected Void doInBackground(Routine... routines) {
//            routine = routines[0];
//            int routineId = routinesViewModel.Insert(routine);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            activity.updateFragments(routineList);
//        }
//    }
}

