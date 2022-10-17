package com.apps.drpersonal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.Historico;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder> {

    List<Historico> historicos;
    Context context;

    public HistoricoAdapter(List<Historico> historicos, Context context) {
        this.historicos = historicos;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoricoAdapter.HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHistorico = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_historical,parent,false);
        return new HistoricoViewHolder(viewHistorico);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoAdapter.HistoricoViewHolder holder, int position) {
        holder.diaTreino.setText(historicos.get(position).getDataSerie());
        holder.idTreino.setText(historicos.get(position).getNomeSerie());
        holder.nomeTreino.setText(historicos.get(position).getDescSerie());
    }

    @Override
    public int getItemCount() {return historicos.size();}

    public class HistoricoViewHolder extends RecyclerView.ViewHolder{
        TextView diaTreino, idTreino, nomeTreino;

        public HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            diaTreino = itemView.findViewById(R.id.textDiaSerie);
            idTreino = itemView.findViewById(R.id.textIdSerie);
            nomeTreino = itemView.findViewById(R.id.textTipoSerie);
        }
    }
}
