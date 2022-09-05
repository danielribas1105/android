package com.apps.agenda.dao;

import com.apps.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class alunoDao {

    private final static List<Aluno> alunos = new ArrayList<>();

    public void salvar(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> todos() {
        return new ArrayList<>(alunos);
    }
}
