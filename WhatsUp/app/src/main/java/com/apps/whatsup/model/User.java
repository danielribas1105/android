package com.apps.whatsup.model;

import com.apps.whatsup.config.ConfigFirebase;
import com.google.firebase.database.DatabaseReference;

public class User {
    private String idUser, nome, email, senha;

    public User() {}

    public void salvarUser(){
        DatabaseReference databaseReference = ConfigFirebase.getFirebaseDatabase();
        databaseReference.child("users").child(idUser).setValue(this);
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
