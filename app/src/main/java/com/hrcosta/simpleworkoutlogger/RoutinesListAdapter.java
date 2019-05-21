package com.hrcosta.simpleworkoutlogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoutinesListAdapter extends RecyclerView.Adapter<RoutinesListAdapter.RoutinesViewHolder> {

    private Context mContext;
    private List<Exercise> exerciseList;

    public RoutinesListAdapter(Context mContext, List<Exercise> exerciseList) {
        this.mContext = mContext;
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public RoutinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.list_item_exercises,parent,false);
        RoutinesViewHolder routinesViewHolder = new RoutinesViewHolder(view);
        return routinesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutinesViewHolder holder, int position) {
        holder.tvExerciseName.setText(exerciseList.get(position).getExName());
        //todo add description field to the list of exercises

    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }


    public void setExerciseList(List<Exercise> exercises) {
        this.exerciseList = exercises;
        notifyDataSetChanged();
    }


    static class RoutinesViewHolder extends RecyclerView.ViewHolder {

        private TextView tvExerciseName;

        public RoutinesViewHolder(@NonNull View itemView) {
            super(itemView);

            tvExerciseName = itemView.findViewById(R.id.tv_exercisename);

        }
    }


}
