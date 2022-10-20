package com.apps.drpersonalmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonalmanager.R;
import com.apps.drpersonalmanager.model.Aluno;

import java.util.List;

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
        holder.foto.setText(alunos.get(position).getFotoAluno());
        holder.aluno.setText(alunos.get(position).getNomeAluno());
        holder.academia.setText(alunos.get(position).getAcademia());
    }

    @Override
    public int getItemCount() {
        return alunos.size();
    }

    public class AlunosViewHolder extends RecyclerView.ViewHolder {

        TextView foto, aluno, academia;

        public AlunosViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.textFotoAluno);
            aluno = itemView.findViewById(R.id.textNomeAluno);
            academia = itemView.findViewById(R.id.textAcademia);
        }
    }
}
