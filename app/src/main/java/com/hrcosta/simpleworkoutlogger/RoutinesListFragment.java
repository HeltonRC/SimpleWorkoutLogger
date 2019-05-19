package com.hrcosta.simpleworkoutlogger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class RoutinesListFragment extends Fragment {


    View view;
    private RecyclerView mRecyclerView;
    private List<Exercise> mExercisesList;


    public RoutinesListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.routines_list_fragment, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExercisesList = new ArrayList<>();
        //get exercises from the DB

    }
}
