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
import com.apps.drpersonal.model.ExerciseInfo;

import java.util.List;

public class InfoExercAdapter extends RecyclerView.Adapter<InfoExercAdapter.InfoExercViewHolder> {

    List<ExerciseInfo> exerciseInfos;
    Context context;

    public InfoExercAdapter(List<ExerciseInfo> exerciseInfos, Context context) {
        this.exerciseInfos = exerciseInfos;
        this.context = context;
    }

    @NonNull
    @Override
    public InfoExercViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewInfoExerc = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_info_exerc,parent,false);
        return new InfoExercViewHolder(viewInfoExerc);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoExercViewHolder holder, int position) {
        //holder.campoImgExerc.setImageResource(exerciseInfos.get(position).getImgExerc());
        holder.campoImgExercText.setText(exerciseInfos.get(position).getImgExerc());
        holder.campoInfoExerc.setText(exerciseInfos.get(position).getDescExerc());
    }

    @Override
    public int getItemCount() {return exerciseInfos.size();}


    public class InfoExercViewHolder extends RecyclerView.ViewHolder{

        //private ImageView campoImgExerc;
        private TextView campoInfoExerc, campoImgExercText;

        public InfoExercViewHolder(@NonNull View itemView) {
            super(itemView);
            campoImgExercText = itemView.findViewById(R.id.imgExercText);
            campoInfoExerc = itemView.findViewById(R.id.descExerc);
        }
    }
}
