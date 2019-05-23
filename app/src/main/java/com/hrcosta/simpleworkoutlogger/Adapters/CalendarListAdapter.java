package com.hrcosta.simpleworkoutlogger.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrcosta.simpleworkoutlogger.CalendarActivity;
import com.hrcosta.simpleworkoutlogger.R;
import com.hrcosta.simpleworkoutlogger.data.Entity.WorkExerciseJoin;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolderCalendarList> {

    private List<WorkExerciseJoin> exerciseDoneList = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolderCalendarList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_calendar,parent,false);


        ViewHolderCalendarList viewholder = new ViewHolderCalendarList(itemView);
        viewholder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkExerciseJoin workExerciseJoin = exerciseDoneList.get(viewholder.getAdapterPosition());
                ((CalendarActivity)itemView.getContext()).selectExerciseToEdit(workExerciseJoin);
            }
        });

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCalendarList holder, int position) {
        WorkExerciseJoin exerciseDone = exerciseDoneList.get(position);
        holder.tvName.setText(exerciseDone.getExerciseName());
        holder.tvDetails.setText(String.valueOf(exerciseDone.getRepetitions()));
    }

    @Override
    public int getItemCount() {
        return exerciseDoneList.size();
    }

    public void setWorkoutList(List<WorkExerciseJoin> exercisesDone) {
        this.exerciseDoneList = exercisesDone;
        notifyDataSetChanged();
    }

    public class ViewHolderCalendarList extends RecyclerView.ViewHolder{
        private CardView cvContainer;
        private TextView tvName;
        private TextView tvDetails;

        public ViewHolderCalendarList(@NonNull View itemView) {
            super(itemView);
            cvContainer = itemView.findViewById(R.id.cv_container);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDetails = itemView.findViewById(R.id.tv_listworkoutnotes);

        }
    }
}
