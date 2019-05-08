package com.hrcosta.simpleworkoutlogger;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrcosta.simpleworkoutlogger.data.Exercise;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolderCalendarList> {

    private List<Exercise> exerciseList;

    public CalendarListAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ViewHolderCalendarList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCalendarList holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
