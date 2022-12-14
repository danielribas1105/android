package com.apps.drpersonal.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonal.R;
import com.apps.drpersonal.model.ExerciseAluno;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

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
        //holder.imgExerc.setImageResource(R.drawable.logo_circular);
        Glide.with(holder.imgExerc.getContext()).load(exercisesAluno.get(position).getIdImg()).into(holder.imgExerc);
        holder.nomeExerc.setText(exercisesAluno.get(position).getNomeExerc());
        holder.quantExerc.setText(exercisesAluno.get(position).getQuantExerc());
        holder.pesoExerc.setText(exercisesAluno.get(position).getPesoExerc());
        holder.obsExerc.setText(exercisesAluno.get(position).getObsExerc());
    }

    @Override
    public int getItemCount() { return exercisesAluno.size(); }

    public class ExerciciosViewHolder extends RecyclerView.ViewHolder{
        TextView nomeExerc, quantExerc, pesoExerc, obsExerc;
        ImageView imgExerc;

        public ExerciciosViewHolder(@NonNull View itemView) {
            super(itemView);
            imgExerc = itemView.findViewById(R.id.imgListExerc);
            nomeExerc = itemView.findViewById(R.id.textNomeExercise);
            quantExerc = itemView.findViewById(R.id.textQuantExerc);
            pesoExerc = itemView.findViewById(R.id.textPesoExerc);
            obsExerc = itemView.findViewById(R.id.textObsExerc);
        }
    }
}
