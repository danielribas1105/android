package com.apps.drpersonal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.Exercise;

import java.util.List;

public class ExerciciosAdapter extends RecyclerView.Adapter<ExerciciosAdapter.ExerciciosViewHolder> {

    List<Exercise> exercises;
    Context context;

    public ExerciciosAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
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
        holder.idExerc.setText(exercises.get(position).getIdExerc());
        holder.nomeExerc.setText(exercises.get(position).getNomeExerc());
        holder.quantExerc.setText(exercises.get(position).getQuantExerc());
    }

    @Override
    public int getItemCount() { return exercises.size(); }

    public class ExerciciosViewHolder extends RecyclerView.ViewHolder{
        TextView idExerc, nomeExerc, quantExerc;

        public ExerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            idExerc = itemView.findViewById(R.id.textIdExerc);
            nomeExerc = itemView.findViewById(R.id.textNomeExercise);
            quantExerc = itemView.findViewById(R.id.textQuantExerc);
        }
    }
}
