package com.hrcosta.simpleworkoutlogger.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hrcosta.simpleworkoutlogger.ExercisesActivity;
import com.hrcosta.simpleworkoutlogger.R;
import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExercisesListAdapter extends RecyclerView.Adapter<ExercisesListAdapter.ViewHolderExercisesList> {
    Context mContext;
    private List<Exercise> exercisesList = new ArrayList<>();
    private LinearLayout llListExercises;

    public ExercisesListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderExercisesList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_exercises, parent, false);

        ViewHolderExercisesList vHolder = new ViewHolderExercisesList(itemView);

        vHolder.llListExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercise exercise = exercisesList.get(vHolder.getAdapterPosition());
                if (mContext instanceof ExercisesActivity) {
                    ((ExercisesActivity) mContext).addExerciseToRoutine(exercise.getId());
                }
            }
        });

        return vHolder;
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
        private LinearLayout llListExercises;

        public ViewHolderExercisesList(@NonNull View itemView) {
            super(itemView);
            tvExerciseName = itemView.findViewById(R.id.tv_exercisename);
            llListExercises = itemView.findViewById(R.id.ll_list_item_exe);
        }
    }
}
