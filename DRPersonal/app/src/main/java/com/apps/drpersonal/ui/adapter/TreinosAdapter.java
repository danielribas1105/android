package com.apps.drpersonal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.Training;

public class TreinosAdapter extends RecyclerView.Adapter<TreinosAdapter.TreinosViewHolder > {

    List<Training> trainings;
    Context context;

    public TreinosAdapter(List<Training> trainings, Context context) {
        this.trainings = trainings;
        this.context = context;
    }

    @NonNull
    @Override
    public TreinosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewTreino = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_training,parent,false);
        return new TreinosViewHolder(viewTreino);
    }

    @Override
    public void onBindViewHolder(@NonNull TreinosViewHolder holder, int position) {
        holder.nomeSerie.setText(trainings.get(position).getNomeSerie());
        holder.descSerie.setText(trainings.get(position).getDescSerie());
    }

    @Override
    public int getItemCount() {return trainings.size();}

    public class TreinosViewHolder extends RecyclerView.ViewHolder {
        TextView nomeSerie, descSerie;

        public TreinosViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeSerie = itemView.findViewById(R.id.textNomeSerie);
            descSerie = itemView.findViewById(R.id.textDescSerie);
        }
    }
}