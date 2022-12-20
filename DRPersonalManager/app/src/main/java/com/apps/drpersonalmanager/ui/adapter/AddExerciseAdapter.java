package com.apps.drpersonalmanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.Exercise;

import java.util.List;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.AddExerciseViewHolder> {

    private List<Exercise> exerciseList;
    private Context context;

    public AddExerciseAdapter(List<Exercise> exercises, Context context) {
        this.exerciseList = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public AddExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View addViewExerc = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_add_exercise,parent, false);
        return new AddExerciseViewHolder(addViewExerc);
    }

    @Override
    public void onBindViewHolder(@NonNull AddExerciseAdapter.AddExerciseViewHolder holder, int position) {
        holder.nomeExerc.setText(exerciseList.get(position).getNomeExerc());
    }

    @Override
    public int getItemCount() {return exerciseList.size();}

    public class AddExerciseViewHolder extends RecyclerView.ViewHolder{
        private TextView nomeExerc;

        public AddExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeExerc = itemView.findViewById(R.id.textAddExercicio);
        }
    }
}
