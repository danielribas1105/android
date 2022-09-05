package com.apps.agenda.model;

import androidx.annotation.NonNull;

public class Aluno {
    private final String nome;
    private final String tel;
    private final String email;

    public Aluno(String nome, String tel, String email) {
        this.nome = nome;
        this.tel = tel;
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return nome;
    }
}
