package com.apps.agendaalura.dao;

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
        Aluno alunoFind = null;
        for (Aluno a : alunos) {
            if (a.getId() == aluno.getId()) {
                alunoFind = a;
            }
        }
        if (alunoFind != null) {
            int positionAluno = alunos.indexOf(alunoFind);
            alunos.set(positionAluno, aluno);
        }
    }

    public List<Aluno> allAlunos() {
        return new ArrayList<>(alunos);
    }
}
