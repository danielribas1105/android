package com.apps.agendaalura.dao;

import com.apps.agendaalura.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class AlunoDao {

    private final static List<Aluno> alunos = new ArrayList<>();

    public void salvar(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> allAlunos() {
        return new ArrayList<>(alunos);
    }
}
