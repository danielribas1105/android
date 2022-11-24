package com.apps.drpersonalmanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.ExerciseAluno;

import java.util.List;

public class TreinoSelectAdapter extends RecyclerView.Adapter<TreinoSelectAdapter.TreinoSelectViewHolder> {

    List<ExerciseAluno> exerciseAlunos;
    Context context;

    public TreinoSelectAdapter(List<ExerciseAluno> exerciseAlunos, Context context) {
        this.exerciseAlunos = exerciseAlunos;
        this.context = context;
    }

    @NonNull
    @Override
    public TreinoSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewTreinoSelect = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_treino_select,parent,false);
        return new TreinoSelectViewHolder(viewTreinoSelect);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinoSelectViewHolder holder, int position) {
        holder.nomeExerc.setText(exerciseAlunos.get(position).getNomeExerc());
        holder.quantExerc.setText(exerciseAlunos.get(position).getQuantExerc());
        holder.pesoExerc.setText(exerciseAlunos.get(position).getPesoExerc());
        holder.obsExerc.setText(exerciseAlunos.get(position).getObsExerc());
    }

    @Override
    public int getItemCount() {return exerciseAlunos.size();}


    public class TreinoSelectViewHolder extends RecyclerView.ViewHolder{
        TextView nomeExerc, quantExerc, pesoExerc, obsExerc;

        public TreinoSelectViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeExerc = itemView.findViewById(R.id.textExercSerieSelect);
            quantExerc = itemView.findViewById(R.id.textExercQuantSelect);
            pesoExerc = itemView.findViewById(R.id.textExercPesoSelect);
            obsExerc = itemView.findViewById(R.id.textExercObsSelect);
        }
    }
}
