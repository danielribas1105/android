package com.apps.drpersonaltrainer.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.drpersonaltrainer.R;
import com.apps.drpersonaltrainer.model.Aluno;

import java.util.List;

public class BuscarAlunoAdapter extends RecyclerView.Adapter<BuscarAlunoAdapter.MyViewHolder> {

    private List<Aluno> alunos;

    public BuscarAlunoAdapter(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_buscar_alunos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Aluno aluno = alunos.get(position);
        holder.campoNome.setText(aluno.getNomeAluno());
        holder.campoData.setText(aluno.getDataInicio());
        holder.campoEmail.setText(aluno.getEmailAluno());
    }

    @Override
    public int getItemCount() { return this.alunos.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView campoNome, campoData, campoEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            campoNome = itemView.findViewById(R.id.textAdapterNome);
            campoData = itemView.findViewById(R.id.textAdapterData);
            campoEmail = itemView.findViewById(R.id.textAdapterEmail);
        }
    }
}
