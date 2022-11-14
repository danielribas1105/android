package com.apps.drpersonal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.ExerciseAluno;

import java.util.List;

public class ExerciciosAdapter extends RecyclerView.Adapter<ExerciciosAdapter.ExerciciosViewHolder> {

    List<ExerciseAluno> exercisesAluno;
    Context context;

    public ExerciciosAdapter(List<ExerciseAluno> exercisesAluno, Context context) {
        this.exercisesAluno = exercisesAluno;
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
        holder.idExerc.setText(exercisesAluno.get(position).getIdExerc());
        holder.nomeExerc.setText(exercisesAluno.get(position).getNomeExerc());
        holder.quantExerc.setText(exercisesAluno.get(position).getQuantExerc());
        holder.obsExerc.setText(exercisesAluno.get(position).getObsExerc());
    }

    @Override
    public int getItemCount() { return exercisesAluno.size(); }

    public class ExerciciosViewHolder extends RecyclerView.ViewHolder{
        TextView idExerc, nomeExerc, quantExerc, obsExerc;

        public ExerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            idExerc = itemView.findViewById(R.id.textIdExerc);
            nomeExerc = itemView.findViewById(R.id.textNomeExercise);
            quantExerc = itemView.findViewById(R.id.textQuantExerc);
            obsExerc = itemView.findViewById(R.id.textObsExerc);
        }
    }
}
