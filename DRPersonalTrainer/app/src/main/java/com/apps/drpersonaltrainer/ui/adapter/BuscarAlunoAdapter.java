package com.apps.drpersonaltrainer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonaltrainer.R;
import com.apps.drpersonaltrainer.model.Aluno;

import java.util.List;

public class BuscarAlunoAdapter extends RecyclerView.Adapter<BuscarAlunoAdapter.AlunosViewHolder> {

    private List<Aluno> alunos;

    public BuscarAlunoAdapter(List<Aluno> alunos) { this.alunos = alunos; }

    @NonNull
    @Override
    public AlunosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_buscar_alunos, parent, false);
        return new AlunosViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunosViewHolder holder, int position) {
        holder.campoNome.setText(alunos.get(position).getNomeAluno());
        holder.campoData.setText(alunos.get(position).getDataInicio());
        holder.campoEmail.setText(alunos.get(position).getEmailAluno());
    }

    @Override
    public int getItemCount() { return this.alunos.size(); }

    public class AlunosViewHolder extends RecyclerView.ViewHolder{

        TextView campoNome, campoData, campoEmail;

        public AlunosViewHolder(@NonNull View itemView) {
            super(itemView);

            campoNome = itemView.findViewById(R.id.textAdapterNome);
            campoData = itemView.findViewById(R.id.textAdapterData);
            campoEmail = itemView.findViewById(R.id.textAdapterEmail);
        }
    }
}
