package com.apps.mypocket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.mypocket.R;
import com.apps.mypocket.config.ConfiguracaoFirebase;
import com.apps.mypocket.helper.Base64Custom;
import com.apps.mypocket.helper.DateCustom;
import com.apps.mypocket.model.Movimentacao;
import com.apps.mypocket.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText campoData, campoCat, campoDesc;
    private EditText campoValor;
    private Movimentacao movimentacao;
    private DatabaseReference databaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        campoValor = findViewById(R.id.valorDespesa);
        campoData = findViewById(R.id.dataDespesa);
        campoCat = findViewById(R.id.catDespesa);
        campoDesc = findViewById(R.id.descDespesa);

        campoData.setText(DateCustom.dataAtual());
        recuperarDespesaTotal();
    }

    public void salvarDespesa(View view){
        if(validarCamposDespesas()){
            String data = campoData.getText().toString();
            Double novaDespesa = Double.parseDouble(campoValor.getText().toString());
            movimentacao = new Movimentacao();
            movimentacao.setValor(novaDespesa);
            movimentacao.setCategoria(campoCat.getText().toString());
            movimentacao.setDescricao(campoDesc.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("d");

            Double despesaAtualizada = novaDespesa + despesaTotal;
            atualizarDespesa(despesaAtualizada);
            movimentacao.salvar(data);
        }
    }

    public Boolean validarCamposDespesas(){
        String textoValor = campoValor.getText().toString();
        String textoData = campoData.getText().toString();
        String textoCat = campoCat.getText().toString();
        String textoDesc = campoDesc.getText().toString();

        if(!textoValor.isEmpty()){
            if(!textoData.isEmpty()){
                if(!textoCat.isEmpty()){
                    if(!textoDesc.isEmpty()){
                        return true;
                    }else {
                        Toast.makeText(this, "Descrição não preenchida!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{
                    Toast.makeText(this, "Categoria não preenchida!", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else {
                Toast.makeText(this, "Data não preenchida!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{
            Toast.makeText(this, "Valor não preenchido!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void recuperarDespesaTotal(){
        String idUser = Base64Custom.codeToBase64(autenticacao.getCurrentUser().getEmail());
        DatabaseReference usuarioRef = databaseRef.child("usuarios").child(idUser);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                despesaTotal = usuario.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void atualizarDespesa(Double despesa){
        String idUser = Base64Custom.codeToBase64(autenticacao.getCurrentUser().getEmail());
        DatabaseReference usuarioRef = databaseRef.child("usuarios").child(idUser);
        usuarioRef.child("despesaTotal").setValue(despesa);
    }
}