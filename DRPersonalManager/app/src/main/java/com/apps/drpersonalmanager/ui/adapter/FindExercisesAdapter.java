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

import de.hdodenhof.circleimageview.CircleImageView;

public class FindExercisesAdapter extends RecyclerView.Adapter<FindExercisesAdapter.FindExerciseViewHolder> {

    private List<Exercise> exercises;
    private Context context;

    public FindExercisesAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public FindExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewFindExerc = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_find_exercises,parent,false);
        return new FindExerciseViewHolder(viewFindExerc);
    }

    @Override
    public void onBindViewHolder(@NonNull FindExerciseViewHolder holder, int position) {
        holder.campoNomeExerc.setText(exercises.get(position).getNomeExerc());
    }

    @Override
    public int getItemCount() { return exercises.size();}


    public class FindExerciseViewHolder extends RecyclerView.ViewHolder{
        TextView campoNomeExerc;

        public FindExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            campoNomeExerc = itemView.findViewById(R.id.textNomeExercicio);
        }
    }
}
