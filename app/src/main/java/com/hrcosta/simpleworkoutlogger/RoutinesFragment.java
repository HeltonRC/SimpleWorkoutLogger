package com.hrcosta.simpleworkoutlogger;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hrcosta.simpleworkoutlogger.ViewModel.RoutinesViewModel;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Routine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutinesFragment extends Fragment {


    private static final String EXTRA_ROUTINE = "routine";
    private static final String EXTRA_ROUTINE_ID = "routineid";
    private static final String EXTRA_EXERCISE_ID = "exerciseid";
    private static final int ADD_EXERCISE_REQUEST = 1;

    View view;
    private List<Exercise> mExercisesList;
    private int mRoutineId;
    private RoutinesViewModel routinesViewModel;

    @BindView(R.id.rv_routineslist) RecyclerView mRecyclerView;
    @BindView(R.id.ib_addexercise) ImageButton mBtnAddExercise;

    public RoutinesFragment() {
    }

    public static RoutinesFragment newInstance(int routineId) {
        RoutinesFragment myFragment = new RoutinesFragment();

        Bundle args = new Bundle();
        args.putInt(EXTRA_ROUTINE_ID, routineId);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_routines,container,false);
        ButterKnife.bind(this,view);

        mExercisesList = new ArrayList<>();

        RoutinesListAdapter adapter = new RoutinesListAdapter(getContext(),mExercisesList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        routinesViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RoutinesViewModel.class);

        routinesViewModel.getExercisesOfRoutine(mRoutineId).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                mExercisesList = exercises;
                adapter.setExerciseList(exercises);
                Toast.makeText(view.getContext(), "List updated.", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRoutineId = getArguments().getInt(EXTRA_ROUTINE_ID);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        mBtnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ExercisesActivity.class);
                intent.putExtra(EXTRA_ROUTINE_ID, mRoutineId);
                startActivityForResult(intent,ADD_EXERCISE_REQUEST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == ADD_EXERCISE_REQUEST) {
            int exerciseId = data.getExtras().getInt(EXTRA_EXERCISE_ID);
            routinesViewModel.addExerciseToRoutine(exerciseId, mRoutineId);

            }



        }
        
    }



//TODO first tab of the viewpager show all exercises... routines created will be in the second tab ahead
//TODO add button to add exercise to routine
//TODO implement onclick listener on the recycler view, the exercise will be returned to the calendaractivity adding it to the current workout.
