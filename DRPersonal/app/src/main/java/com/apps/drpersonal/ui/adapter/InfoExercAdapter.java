package com.apps.drpersonal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.InfoExercise;

import java.util.List;

public class InfoExercAdapter extends RecyclerView.Adapter<InfoExercAdapter.InfoExercViewHolder> {

    List<InfoExercise> infoExercises;
    Context context;

    public InfoExercAdapter(List<InfoExercise> infoExercises, Context context) {
        this.infoExercises = infoExercises;
        this.context = context;
    }

    @NonNull
    @Override
    public InfoExercViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewInfoExerc = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_info_exerc,parent, false);
        return new InfoExercViewHolder(viewInfoExerc);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoExercViewHolder holder, int position) {
        holder.campoVideoExerc.setText(infoExercises.get(position).getVideoExerc());
        holder.campoImgExerc.setText(infoExercises.get(position).getImgExerc());
        holder.campoDescExerc.setText(infoExercises.get(position).getDescExerc());
    }

    @Override
    public int getItemCount() {return infoExercises.size();}


    public class InfoExercViewHolder extends RecyclerView.ViewHolder{

        TextView campoVideoExerc, campoImgExerc, campoDescExerc;

        public InfoExercViewHolder(@NonNull View itemView) {
            super(itemView);
            campoVideoExerc = itemView.findViewById(R.id.videoExercText);
            campoImgExerc = itemView.findViewById(R.id.imgExercText);
            campoDescExerc = itemView.findViewById(R.id.descExercText);
        }
    }
}
