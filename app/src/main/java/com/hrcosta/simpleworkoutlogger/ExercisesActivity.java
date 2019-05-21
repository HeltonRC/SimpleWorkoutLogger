package com.hrcosta.simpleworkoutlogger;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
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

    @BindView(R.id.rv_listexercises) RecyclerView rvListExercises;
    @BindView(R.id.ib_addexercistolist) ImageButton ibAddExercises;
    ExerciseListViewModel mViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        ButterKnife.bind(this);

        rvListExercises.setLayoutManager(new LinearLayoutManager(this));
        ExercisesListAdapter adapter = new ExercisesListAdapter();

        rvListExercises.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(ExerciseListViewModel.class);

        mViewModel.getAllExercises().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercisesList(exercises);
            }
        });




    }
}
