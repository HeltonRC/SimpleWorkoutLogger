package com.hrcosta.simpleworkoutlogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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


    private static final String ARG_ROUTINE = "routine";
    View view;
    private List<Exercise> mExercisesList;
    private Routine mRoutine;
    private RoutinesViewModel routinesViewModel;

    @BindView(R.id.rv_routineslist) RecyclerView mRecyclerView;
    @BindView(R.id.ib_addexercise) ImageButton mBtnAddExercise;

    public RoutinesFragment() {
    }

    public static RoutinesFragment newInstance(Routine routine) {
        RoutinesFragment myFragment = new RoutinesFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_ROUTINE, routine);
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

        routinesViewModel.getExercisesOfRoutine(mRoutine.getId()).observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                mExercisesList = exercises;
                adapter.setExerciseList(exercises);
            }
        });
        return view;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo also check if there is an argument to add exercise to a specific routine

        if (getArguments() != null) {
            mRoutine = getArguments().getParcelable(ARG_ROUTINE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();


        mBtnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ExercisesActivity.class));

            }
        });

    }
}


//TODO first tab of the viewpager show all exercises... routines created will be in the second tab ahead
//TODO add button to add exercise to routine
//TODO implement onclick listener on the recycler view, the exercise will be returned to the calendaractivity adding it to the current workout.
