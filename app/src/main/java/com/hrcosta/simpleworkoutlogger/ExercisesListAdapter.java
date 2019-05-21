package com.hrcosta.simpleworkoutlogger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExercisesListAdapter extends RecyclerView.Adapter<ExercisesListAdapter.ViewHolderExercisesList> {

    private List<Exercise> exercisesList = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolderExercisesList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_exercises, parent, false);

        return new ViewHolderExercisesList(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderExercisesList holder, int position) {
        Exercise exercise = exercisesList.get(position);
        holder.tvExerciseName.setText(exercise.getExName());

    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public void setExercisesList(List<Exercise> exercises) {
        exercisesList = exercises;
        notifyDataSetChanged();

    }


    public class ViewHolderExercisesList extends RecyclerView.ViewHolder{

        private TextView tvExerciseName;

        public ViewHolderExercisesList(@NonNull View itemView) {
            super(itemView);
            tvExerciseName = itemView.findViewById(R.id.tv_exercisename);

        }
    }
}
