package com.apps.agendaalura.dao;

import androidx.annotation.Nullable;

import com.apps.agendaalura.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int countIds = 1;

    public void salvar(Aluno aluno) {
        aluno.setId(countIds);
        alunos.add(aluno);
        countIds++;
    }

    public void editar(Aluno aluno) {
        Aluno alunoFind = findAlunoId(aluno);
        if (alunoFind != null) {
            int positionAluno = alunos.indexOf(alunoFind);
            alunos.set(positionAluno, aluno);
        }
    }

    public void deletar(Aluno aluno) {
        Aluno alunoDel = findAlunoId(aluno);
        if(alunoDel != null){
            alunos.remove(alunoDel);
        }
    }

    @Nullable
    private Aluno findAlunoId(Aluno aluno) {
        for (Aluno a : alunos) {
            if (a.getId() == aluno.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Aluno> allAlunos() {
        return new ArrayList<>(alunos);
    }

}
