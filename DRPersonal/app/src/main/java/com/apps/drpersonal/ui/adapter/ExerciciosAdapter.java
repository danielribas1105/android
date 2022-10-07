package com.apps.drpersonal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.Exercise;

import java.util.List;

public class ExerciciosAdapter extends RecyclerView.Adapter<ExerciciosAdapter.ExerciciosViewHolder> {

    List<Exercise> exercises;

    public ExerciciosAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewExerc = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_exercises,parent,false);
        return new ExerciciosViewHolder(viewExerc);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciciosViewHolder holder, int position) {
        holder.imgExerc.setImageResource(exercises.get(position).getImgExercId());
        holder.nomeExerc.setText(exercises.get(position).getNomeExerc());
    }

    @Override
    public int getItemCount() { return exercises.size(); }

    public class ExerciciosViewHolder extends RecyclerView.ViewHolder{
        ImageView imgExerc;
        TextView nomeExerc;

        public ExerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            imgExerc = itemView.findViewById(R.id.imgExercise);
            nomeExerc = itemView.findViewById(R.id.textExercise);
        }
    }
}