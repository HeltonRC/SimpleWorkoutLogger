package com.hrcosta.simpleworkoutlogger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.hrcosta.simpleworkoutlogger.data.Entity.Exercise;
import com.hrcosta.simpleworkoutlogger.data.Entity.Workout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolderCalendarList> {

    private List<Workout> workoutList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolderCalendarList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        return new ViewHolderCalendarList(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCalendarList holder, int position) {
        Workout workout = workoutList.get(position);
        holder.tvName.setText(workout.getNotes());
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void setWorkoutList(List<Workout> workouts) {
        this.workoutList = workouts;
        notifyDataSetChanged();
    }

    public class ViewHolderCalendarList extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvDetails;

        public ViewHolderCalendarList(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvDetails = itemView.findViewById(R.id.tv_details);


        }
    }
}
