package com.apps.drpersonalmanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.Historic;

import java.util.List;

public class HistoricAdapter extends RecyclerView.Adapter<HistoricAdapter.HistoricViewHolder> {

    List<Historic> historics;
    Context context;

    public HistoricAdapter(List<Historic> historics, Context context) {
        this.historics = historics;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoricAdapter.HistoricViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHistoric = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_historic,parent, false);
        return new HistoricViewHolder(viewHistoric);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricAdapter.HistoricViewHolder holder, int position) {
        holder.diaTreino.setText(historics.get(position).getDataSerie());
        holder.idTreino.setText(historics.get(position).getNomeSerie());
        holder.nomeTreino.setText(historics.get(position).getDescSerie());
    }

    @Override
    public int getItemCount() {return historics.size();}

    public class HistoricViewHolder extends RecyclerView.ViewHolder{
        TextView diaTreino, idTreino, nomeTreino;

        public HistoricViewHolder(@NonNull View itemView) {
            super(itemView);
            diaTreino = itemView.findViewById(R.id.textDiaSerie);
            idTreino = itemView.findViewById(R.id.textIdSerie);
            nomeTreino = itemView.findViewById(R.id.textTipoSerie);
        }
    }
}
