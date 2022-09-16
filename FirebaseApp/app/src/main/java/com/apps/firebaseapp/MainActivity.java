package com.apps.firebaseapp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    /*
    private DatabaseReference reference = FirebaseDatabase
            .getInstance().getReference();
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

     */

    private ImageView imageView;
    private ImageView imgLoad;
    private Button button;
    private Button btnDel;
    private Button btnDownload;
    //Define nós para o Storage
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private StorageReference imagens = storageReference.child("imagens");
    //Nome da imagem
    //String nomeImg = UUID.randomUUID().toString();
    private StorageReference imagemRef = imagens.child("celular.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imgFoto);
        button = findViewById(R.id.btnUpload);
        btnDel = findViewById(R.id.btnDel);
        btnDownload = findViewById(R.id.btnDownload);

        //Carregar imagem para o Firebase
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Configura para a imagem ser salva em memória
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();

                //Recupera Bitmap da imagem (image a ser carregada)
                Bitmap bitmap = imageView.getDrawingCache();

                //Comprimir bitmap para o formato JPEG/PNG
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                //Converte baos em uma matriz se pixels brutos (dados da imagem)
                byte[] dadosImagem = baos.toByteArray();

                //Retorna objeto que irá controlar o upload
                UploadTask uploadTask = imagemRef.putBytes(dadosImagem);

                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Erro ao carregar Imagem"
                                + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri url = task.getResult();
                                Toast.makeText(MainActivity.this,
                                        "Sucesso ao carregar Imagem" + url.toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
            }
        });

        //Deletar imagem no Firebase Storage
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imagemRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Erro ao apagar imagem",
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this,
                                "Sucesso ao apagar imagem",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        //Realizar download da imagem do Firebase
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagemRef.getDownloadUrl().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(MainActivity.this).load(uri).into(imageView);
                        Toast.makeText(MainActivity.this,
                                "Sucesso ao fazer download da imagem",
                                Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(MainActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,
                                        "Erro ao fazer download da imagem",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


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

        //DatabaseReference usuarios = reference.child("Usuários");
        //DatabaseReference userSearch = usuarios.child("-NBzIgbcqXNo7mu-KVFK");

        /*

        //Pesquisas no DB Firebase
        //Query userSearch = usuarios.orderByChild("nome").equalTo("Daniel");
        //Query userSearch = usuarios.orderByKey().limitToFirst(3);
        //Query userSearch = usuarios.orderByChild("idade").endBefore(12);
        //Query userSearch = usuarios.orderByChild("idade").startAt(10).endAt(45);
        //Query userSearch = usuarios.orderByChild("nome").startAt("M").endAt("M"+"\uf8ff");


        userSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Usuario dadosUser = snapshot.getValue(Usuario.class);
                //Log.i("Dados usuário", "Nome: "+dadosUser.getNome());
                Log.i("Dados usuário:", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //DatabaseReference produtos = reference.child("Produtos");

        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Firebase",snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Usuario usuario = new Usuario();
        usuario.setNome("Marcela");
        usuario.setSobrenome("Pinheiro");
        usuario.setIdade(45);
        usuarios.push().setValue(usuario);

        Produtos produto = new Produtos();
        produto.setTipo("TV");
        produto.setMarca("Samsung");
        produto.setValor(1999.90);
        produtos.child("002").setValue(produto);

        */
    }
}