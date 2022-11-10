package com.apps.drpersonalmanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.Training;

import java.util.List;

public class TreinosAlunoAdapter extends RecyclerView.Adapter<TreinosAlunoAdapter.TreinosAlunoViewHolder> {

    private List<Training> trainings;
    private Context context;

    public TreinosAlunoAdapter(List<Training> trainings, Context context) {
        this.trainings = trainings;
        this.context = context;
    }

    @NonNull
    @Override
    public TreinosAlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewTreinosAluno = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_treinos_aluno,parent,false);
        return new TreinosAlunoViewHolder(viewTreinosAluno);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinosAlunoViewHolder holder, int position) {
        holder.nomeSerie.setText(trainings.get(position).getNomeSerie());
        holder.descSerie.setText(trainings.get(position).getDescSerie());
    }

    @Override
    public int getItemCount() { return trainings.size();}

    public class TreinosAlunoViewHolder extends RecyclerView.ViewHolder{
        TextView nomeSerie, descSerie;

        public TreinosAlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeSerie = itemView.findViewById(R.id.textNomeSerie);
            descSerie = itemView.findViewById(R.id.textDescSerie);
        }

    }
}
