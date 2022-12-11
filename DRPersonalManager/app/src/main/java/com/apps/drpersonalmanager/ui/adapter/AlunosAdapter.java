package com.apps.drpersonalmanager.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.Aluno;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.AlunosViewHolder> {

    private List<Aluno> alunos;
    private Context context;

    public AlunosAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
    }

    @NonNull
    @Override
    public AlunosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewAluno = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_alunos, parent, false);
        return new AlunosViewHolder(viewAluno);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunosViewHolder holder, int position) {
        Glide.with(holder.fotoAluno.getContext()).load(alunos.get(position).getIdImgAluno())
                .into(holder.fotoAluno);
        holder.aluno.setText(alunos.get(position).getNomeAluno());
        holder.academia.setText(alunos.get(position).getAcademia());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public class AlunosViewHolder extends RecyclerView.ViewHolder {

        TextView aluno, academia;
        CircleImageView fotoAluno;

        public AlunosViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoAluno = itemView.findViewById(R.id.imgPerfilAluno);
            aluno = itemView.findViewById(R.id.textNomeAluno);
            academia = itemView.findViewById(R.id.textAcademia);
        }
    }
}
