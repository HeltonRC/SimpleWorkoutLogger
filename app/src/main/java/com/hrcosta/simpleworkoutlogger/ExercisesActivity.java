package com.hrcosta.simpleworkoutlogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.hrcosta.simpleworkoutlogger.Adapters.ExercisesListAdapter;
import com.hrcosta.simpleworkoutlogger.ViewModel.ExerciseListViewModel;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ExercisesActivity  extends AppCompatActivity {

    private static final String ARG_ROUTINE_ID = "routineid";
    private static final String ARG_EXERCISE_ID = "exerciseid";
    @BindView(R.id.rv_listexercises) RecyclerView rvListExercises;
    @BindView(R.id.ib_addexercistolist) ImageButton ibAddExercises;
    private ExerciseListViewModel mViewModel;
    private int mRoutineId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        ButterKnife.bind(this);

        mRoutineId = (int) this.getIntent().getExtras().get(ARG_ROUTINE_ID);

        rvListExercises.setLayoutManager(new LinearLayoutManager(this));
        ExercisesListAdapter adapter = new ExercisesListAdapter(this);

        rvListExercises.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(ExerciseListViewModel.class);

        mViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercisesList(exercises);
            }
        });

    }

    public void addExerciseToRoutine(int exerciseId){
        Intent intent = new Intent();
        intent.putExtra(ARG_EXERCISE_ID,exerciseId);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }





}
