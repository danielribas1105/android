package com.apps.personalvirtual.ui.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.apps.personalvirtual.R;
import com.apps.personalvirtual.model.ExPeito;

import java.util.List;

public class AdapterExPeito extends RecyclerView.Adapter<AdapterExPeito.MyViewHolder> {

    private List<ExPeito> listaExerc;
    public AdapterExPeito(List<ExPeito> lista) {
        this.listaExerc = lista;
    }

    @NonNull
    @Override
    public AdapterExPeito.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemExPeito = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_expeito,parent,false);
        return new MyViewHolder(itemExPeito);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExPeito exercicio = listaExerc.get(position);
        holder.imgExerc.setText(exercicio.getImgEx());
        holder.tipoExec.setText(exercicio.getNomeEx());

    }

    @Override
    public int getItemCount() {
        return listaExerc.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView imgExerc;
        TextView tipoExec;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgExerc = itemView.findViewById(R.id.imgExerc);
            tipoExec = itemView.findViewById(R.id.tipoExPeito);
        }
    }

}
