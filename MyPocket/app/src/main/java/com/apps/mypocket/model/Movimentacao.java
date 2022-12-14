package com.apps.mypocket.model;

import android.util.Base64;
import android.widget.Toast;

import com.apps.mypocket.activity.DespesasActivity;
import com.apps.mypocket.config.ConfiguracaoFirebase;
import com.apps.mypocket.helper.Base64Custom;
import com.apps.mypocket.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {
    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private Double valor;
    private String key;

    public Movimentacao() {
    }

    public void salvar(String data) {
        FirebaseAuth auth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idUser = Base64Custom.codeToBase64(auth.getCurrentUser().getEmail());
        String mesAno = DateCustom.dataMesAno(data);
        DatabaseReference reference = ConfiguracaoFirebase.getFirebaseDatabase();
        reference.child("movimentacao")
                .child(idUser)
                .child(mesAno)
                .push()
                .setValue(this);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
