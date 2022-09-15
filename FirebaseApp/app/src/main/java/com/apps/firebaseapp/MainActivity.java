package com.apps.firebaseapp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference = FirebaseDatabase
            .getInstance().getReference();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        //Deslogar usuário
        //usuario.signOut();

        //Logar usuário
        usuario.signInWithEmailAndPassword("danielribas1105@gmail.com","drs1105")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("Sign In","Sucesso ao logar usuário!");
                        }else{
                            Log.i("Sign In","Erro ao logar usuário!");
                        }
                    }
                });



        //Verificação de usuário logado
        if(usuario.getCurrentUser()!=null){
            Log.i("User Validation","Usuário logado!");
        }else{
            Log.i("User Validation","Usuário não logado!");
        }

        //Cadastro de usuário
        usuario.createUserWithEmailAndPassword("danielribas1105@gmail.com","drs1105")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("Create User","Sucesso ao criar usuário!");
                        }else{
                            Log.i("Create User","Erro ao criar usuário!");
                        }
                    }
                });
        */

        DatabaseReference usuarios = reference.child("Usuários");
        //DatabaseReference produtos = reference.child("Produtos");

        /*
        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Firebase",snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        Usuario usuario = new Usuario();
        usuario.setNome("Marcela");
        usuario.setSobrenome("Pinheiro");
        usuario.setIdade(45);
        usuarios.push().setValue(usuario);

        /*
        Produtos produto = new Produtos();
        produto.setTipo("TV");
        produto.setMarca("Samsung");
        produto.setValor(1999.90);
        produtos.child("002").setValue(produto);

        */
    }
}